package br.com.encurtadorurlreativo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.encurtadorurlreativo.controller.dto.URLRequestDTO;
import br.com.encurtadorurlreativo.controller.dto.URLResponseDTO;
import br.com.encurtadorurlreativo.exception.ChaveInvalidaException;
import br.com.encurtadorurlreativo.exception.URLInvalidaException;
import br.com.encurtadorurlreativo.service.EncurtadorURLService;
import br.com.encurtadorurlreativo.service.EncurtadorURLStringRandomicaService;
import br.com.encurtadorurlreativo.util.TempoUtil;
import reactor.core.publisher.Mono;

@RestController
public class EncurtadorURLController {

	private final EncurtadorURLService encurtadorService;

	public EncurtadorURLController(EncurtadorURLStringRandomicaService encurtadorURLStringRandomicaService) {
		this.encurtadorService = encurtadorURLStringRandomicaService;
	}

	@PostMapping("/encurtar")
	Mono<ResponseEntity<Object>> encurtarURL(@RequestBody URLRequestDTO urlRequest) {
		Mono<URLResponseDTO> urlRetorno = null;
		try {
			urlRetorno = encurtadorService.encurtarURL(urlRequest.getUrlOriginal());
		} catch (URLInvalidaException e) {
			return Mono.just(ResponseEntity.badRequest().body("URL Inválida!"));
		}
		return Mono.just(ResponseEntity.ok().body(urlRetorno));
	}

	@GetMapping("/{chave}")
	Mono<ResponseEntity<Object>> buscarURLOriginalPelaChave(@PathVariable String chave) {
		try {
			return encurtadorService.buscarURLOriginalPelaChave(chave)
						.map(urlRetorno -> { 
							if(TempoUtil.oPrazoDaURLEncurtadaExpirou(urlRetorno.getExpiraEm())) {
								encurtadorService.deletarURLComChaveExpirada(chave);
								return ResponseEntity.status(HttpStatus.GONE).build();
							}
							return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).header("Location", urlRetorno.getUrlOriginal()).build();
						})
						.defaultIfEmpty(ResponseEntity.notFound().build());
			
		} catch (ChaveInvalidaException e) {
			return Mono.just(ResponseEntity.badRequest().body("Chave Inválida!"));
		}
	}

}
