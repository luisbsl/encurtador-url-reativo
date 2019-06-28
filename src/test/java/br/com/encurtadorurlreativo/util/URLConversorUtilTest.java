package br.com.encurtadorurlreativo.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.encurtadorurlreativo.controller.dto.URLResponseDTO;
import br.com.encurtadorurlreativo.model.URL;

public class URLConversorUtilTest {
	
	@Test
	public void converterURLParaURLResponse() {
		URL url = new URL("http://asd", "asd", new TempoUtil().obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H());
		URLResponseDTO urlResponse = URLConversorUtil.converterURLParaURLResponse(url, "http://localhost:8080/");
		assertTrue(url.getExpiraEm().equals(urlResponse.getExpiraEm()));
	}

}
