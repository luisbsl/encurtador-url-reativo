package br.com.encurtadorurlreativo.model;

public class URL {

	private String urlOriginal;
	private String chave;
	private Long expiraEm;
	
	public URL() {}

	public URL(String urlOriginal, String chave, Long expiraEm) {
		super();
		this.urlOriginal = urlOriginal;
		this.chave = chave;
		this.expiraEm = expiraEm;
	}

	public String getUrlOriginal() {
		return urlOriginal;
	}

	public void setUrlOriginal(String urlOriginal) {
		this.urlOriginal = urlOriginal;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Long getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(Long expiraEm) {
		this.expiraEm = expiraEm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URL other = (URL) obj;
		if (chave == null) {
			if (other.chave != null)
				return false;
		} else if (!chave.equals(other.chave))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "URL [urlOriginal=" + urlOriginal + ", chave=" + chave + ", expiraEm=" + expiraEm + "]";
	}
}
