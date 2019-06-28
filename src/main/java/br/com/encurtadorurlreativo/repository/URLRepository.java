package br.com.encurtadorurlreativo.repository;

import br.com.encurtadorurlreativo.exception.ChaveComPrazoExpiradoException;
import br.com.encurtadorurlreativo.model.URL;
import reactor.core.publisher.Mono;

public interface URLRepository {

	Mono<URL> salvarURL(URL url);

	Mono<URL> buscarURLOriginalPelaChave(final String chave) throws ChaveComPrazoExpiradoException;
	
	Mono<Boolean> deletarURLComChaveExpirada(final String chave);

}
