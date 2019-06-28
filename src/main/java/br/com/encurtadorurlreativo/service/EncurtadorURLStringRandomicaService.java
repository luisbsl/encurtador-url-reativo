package br.com.encurtadorurlreativo.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.encurtadorurlreativo.controller.dto.URLResponseDTO;
import br.com.encurtadorurlreativo.model.URL;
import br.com.encurtadorurlreativo.repository.URLRepository;
import br.com.encurtadorurlreativo.util.TempoUtil;
import br.com.encurtadorurlreativo.util.URLConversorUtil;
import reactor.core.publisher.Mono;

@Service
public class EncurtadorURLStringRandomicaService implements EncurtadorURLService {
	
	private final String urlBaseDoServidor;
	private URLRepository urlRepository;

	public EncurtadorURLStringRandomicaService(@Value("${servidor.urlBase}") String urlBaseDoServidor, URLRepository urlRepository) {
		this.urlBaseDoServidor = urlBaseDoServidor;
		this.urlRepository = urlRepository;
	}

	@Override
	public Mono<URLResponseDTO> encurtarURL(final String urlOriginal) {
		validarURL(urlOriginal);
		final String chave = RandomStringUtils.randomAlphanumeric(8);
		URL url = new URL(urlOriginal, chave, TempoUtil.obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H());
		return urlRepository
					.salvarURL(url)
					.map(urlGravada -> URLConversorUtil.converterURLParaURLResponse(urlGravada, urlBaseDoServidor));
	}

	@Override
	public Mono<URL> buscarURLOriginalPelaChave(String chave) {
		validarChave(chave);
		return urlRepository.buscarURLOriginalPelaChave(chave);
	}

	@Override
	public Mono<Boolean> deletarURLComChaveExpirada(String chave) {
		return urlRepository.deletarURLComChaveExpirada(chave);
	}

}
