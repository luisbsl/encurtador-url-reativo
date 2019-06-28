package br.com.encurtadorurlreativo.controller.dto;

public class URLResponseDTO {

	private String urlEncurtada;
	private Long expiraEm;

	public URLResponseDTO() {
	}

	public URLResponseDTO(String urlEncurtada, Long expiraEm) {
		super();
		this.urlEncurtada = urlEncurtada;
		this.expiraEm = expiraEm;
	}

	public String getUrlEncurtada() {
		return urlEncurtada;
	}

	public void setUrlEncurtada(String urlEncurtada) {
		this.urlEncurtada = urlEncurtada;
	}

	public Long getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(Long expiraEm) {
		this.expiraEm = expiraEm;
	}

}
