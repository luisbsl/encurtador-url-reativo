package br.com.encurtadorurlreativo.controller.dto;

public class URLRequestDTO {

	private String urlOriginal;

	public URLRequestDTO() {
	}

	public URLRequestDTO(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}
}
