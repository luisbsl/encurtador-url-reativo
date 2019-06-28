package br.com.encurtadorurlreativo.service;

import org.apache.commons.lang3.StringUtils;

import br.com.encurtadorurlreativo.controller.dto.URLResponseDTO;
import br.com.encurtadorurlreativo.exception.ChaveComPrazoExpiradoException;
import br.com.encurtadorurlreativo.exception.ChaveInvalidaException;
import br.com.encurtadorurlreativo.exception.URLInvalidaException;
import br.com.encurtadorurlreativo.model.URL;
import reactor.core.publisher.Mono;

public interface EncurtadorURLService {
	
	static final Integer TAMANHO_DA_CHAVE = 8;

	Mono<URLResponseDTO> encurtarURL(String url) throws URLInvalidaException;

	Mono<URL> buscarURLOriginalPelaChave(String chave) throws ChaveInvalidaException, ChaveComPrazoExpiradoException;
	
	Mono<Boolean> deletarURLComChaveExpirada(final String chave);

	default Boolean validarURL(final String url) {
		if(StringUtils.isBlank(url)) throw new URLInvalidaException();
		return true;
	}
	
	default Boolean validarChave(final String chave) {
		if(StringUtils.isBlank(chave) || !TAMANHO_DA_CHAVE.equals(chave.length())) throw new ChaveInvalidaException();
		return true;
	}

}
