package br.com.encurtadorurlreativo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.encurtadorurlreativo.model.URL;
import br.com.encurtadorurlreativo.util.TempoUtil;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class URLRepositoryRedisTest {
	
	@Autowired
	private URLRepositoryRedis urlRepositoryRedis;
	
	@Test
	public void salvarURLEncurtada_deveSalvarERetornarAURLPassada() {
		URL url = new URL("http://www.zambas.com.br", "aaaDDD12", TempoUtil.obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H());
		
		StepVerifier.create(urlRepositoryRedis.salvarURL(url))
			        .expectNext(url)
			        .verifyComplete();
	}
	
	@Test
	public void salvarURLEncurtada() {
		URL url = new URL("http://www.zambas.com.br", "aaaDDD12", TempoUtil.obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H());
		
		StepVerifier.create(urlRepositoryRedis.salvarURL(url)
					.flatMap(__ -> urlRepositoryRedis.buscarURLOriginalPelaChave(url.getChave())))
					.expectNext(url)
					.verifyComplete();
	}
	
}
