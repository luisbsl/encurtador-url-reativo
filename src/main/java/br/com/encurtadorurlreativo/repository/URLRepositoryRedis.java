package br.com.encurtadorurlreativo.repository;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

import br.com.encurtadorurlreativo.model.URL;
import reactor.core.publisher.Mono;

@Repository
public class URLRepositoryRedis implements URLRepository {
	
	private final ReactiveRedisOperations<String, String> operations;

    public URLRepositoryRedis(ReactiveRedisOperations<String, String> operations) {
        this.operations = operations;
    }

	@Override
	public Mono<URL> salvarURL(final URL url) {
		return operations.opsForValue()
                .set(url.getChave(), url.getUrlOriginal()+"\\|"+url.getExpiraEm().toString())
                .map(__ -> url);
	}
	
	@Override
    public Mono<URL> buscarURLOriginalPelaChave(final String chave) {
        return operations.opsForValue()
                         .get(chave)
                         .map(camposDaURL -> {
                        	 final String[] arr = camposDaURL.split("\\|");
                        	 final String urlOriginal = arr[0]; 
                        	 final Long expiraEm = Long.valueOf(arr[1]);
                        	 return new URL(urlOriginal, chave, expiraEm);
                         });
    }
	
	public Mono<Boolean> deletarURLComChaveExpirada(final String chave) {
		 return operations.opsForValue().delete(chave);
	}

}
