package br.com.encurtadorurlreativo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.encurtadorurlreativo.exception.ChaveComPrazoExpiradoException;
import br.com.encurtadorurlreativo.exception.ChaveInvalidaException;
import br.com.encurtadorurlreativo.model.URL;
import br.com.encurtadorurlreativo.service.EncurtadorURLStringRandomicaService;
import br.com.encurtadorurlreativo.util.TempoUtil;
import br.com.encurtadorurlreativo.util.URLConversorUtil;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers=EncurtadorURLController.class)
public class EncurtadorURLControllerTest {
	
	@Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EncurtadorURLStringRandomicaService encurtadorURLService;
    
    @Test
    public void encurtarURL() {
    	URL urlEncurtada = new URL("http://www.zambas.com.br", "aaaDDD12", TempoUtil.obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H());
        when(encurtadorURLService.encurtarURL("http://www.zambas.com.br")).thenReturn(Mono.just(URLConversorUtil.converterURLParaURLResponse(urlEncurtada, "http://localhost:8080/")));
        webTestClient.post()
                     .uri("/encurtar")
                     .contentType(MediaType.APPLICATION_JSON_UTF8)
                     .syncBody("{\"urlOriginal\":\"http://www.zambas.com.br\"}")
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.urlEncurtada")
                     .value(val -> assertThat(val).isEqualTo("http://localhost:8080/aaaDDD12"));
    }
    
    @Test
    public void buscarURLOriginalPelaChave_deveraRedirecinarParaURLOriginal() {
        when(encurtadorURLService.buscarURLOriginalPelaChave("aaaDDD12")).thenReturn(Mono.just(new URL("https://www.google.com", "asdDDD12", 124L)));
        webTestClient.get()
                     .uri("/aaaDDD12")
                     .exchange()
                     .expectStatus()
                     .isPermanentRedirect()
                     .expectHeader()
                     .value("Location", location -> assertThat(location).isEqualTo("https://www.google.com"));
    }
    
    @Test
    public void buscarURLOriginalPelaChave_deveraRetornarStatus400_ChaveInvalida() {
        when(encurtadorURLService.buscarURLOriginalPelaChave("asd")).thenThrow(ChaveInvalidaException.class);
        webTestClient.get()
                     .uri("/asd")
                     .exchange()
                     .expectStatus()
                     .isBadRequest();
    }
    
    @Test
    public void buscarURLOriginalPelaChave_deveraRetornarStatus404_ChaveNaoEncontrada() {
        when(encurtadorURLService.buscarURLOriginalPelaChave("aaaDDD12")).thenReturn(Mono.empty());
        webTestClient.get()
                     .uri("/aaaDDD12")
                     .exchange()
                     .expectStatus()
                     .isNotFound();
    }
    
    @Test
    public void buscarURLOriginalPelaChave_deveraRetornarStatus410_ChaveExpirada() {
        when(encurtadorURLService.buscarURLOriginalPelaChave("aaaDDD12")).thenThrow(ChaveComPrazoExpiradoException.class);
        webTestClient.get()
                     .uri("/aaaDDD12")
                     .exchange()
                     .expectStatus()
                     .is4xxClientError();
    }

}
