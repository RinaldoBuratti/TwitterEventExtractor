package it.uniroma3.sii.event;

public class Event {
	private String data;
	private String luogo;
	private String nome;
	private String originalText;

	public Event(String nome, String luogo, String data) {
		this.nome = nome;
		this.luogo = luogo;
		this.data = data;
	}
	
	public Event(){
		this.nome="";
		this.luogo="";
		this.data="";
	}
	
	public String getOriginalText() {
		return this.originalText;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	
	public String toString() {
		return this.nome + "  " + this.luogo + "  " + this.data;
	}
	
}
