package br.com.encurtadorurlreativo.util;

import br.com.encurtadorurlreativo.controller.dto.URLResponseDTO;
import br.com.encurtadorurlreativo.model.URL;

public final class URLConversorUtil {

	public static URLResponseDTO converterURLParaURLResponse(final URL url, final String urlBase) {
		return new URLResponseDTO(urlBase+url.getChave(), url.getExpiraEm());
	}

}
