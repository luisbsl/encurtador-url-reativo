package br.com.encurtadorurlreativo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import br.com.encurtadorurlreativo.exception.ChaveInvalidaException;
import br.com.encurtadorurlreativo.exception.URLInvalidaException;
import br.com.encurtadorurlreativo.model.URL;
import br.com.encurtadorurlreativo.repository.URLRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class EncurtadorURLStringRandomicaServiceTest {

	private static final String URL_BASE_TESTE = "http://localhost:8080/";
	private URLRepository urlRepository = mock(URLRepository.class);
	private EncurtadorURLService encurtadorURLService = new EncurtadorURLStringRandomicaService(URL_BASE_TESTE, urlRepository);
	
	@Before
	public void configurar() { 
		when(urlRepository.salvarURL(any()))
        .thenAnswer((Answer<Mono<URL>>) invocationOnMock -> Mono.just((URL) invocationOnMock.getArguments()[0]));
	}

	@Test
	public void encurtarURL() {
		StepVerifier.create(encurtadorURLService.encurtarURL("http://www.zambas.com.br"))
					.expectNextMatches(urlResponseDTO -> 
							urlResponseDTO != null 
								&& urlResponseDTO.getUrlEncurtada().length() > 0
								&& urlResponseDTO.getUrlEncurtada().startsWith(URL_BASE_TESTE))
					.expectComplete().verify();
	}

	@Test(expected = URLInvalidaException.class)
	public void encurtarURL_deveRetornarExcecaoURLInvalidaException() {
		encurtadorURLService.encurtarURL("");
	}
	
	@Test(expected = ChaveInvalidaException.class)
	public void buscarURLPelaChave_deveRetornarExcecaoChaveInvalidaException() {
		encurtadorURLService.buscarURLOriginalPelaChave("");
	}

}
