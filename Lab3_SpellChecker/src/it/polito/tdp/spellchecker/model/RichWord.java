package it.polito.tdp.spellchecker.model;

public class RichWord {
	
	private String parola;
	private boolean corretta;
	
	
	public RichWord(String parola, boolean corretta) {
		super();
		this.parola = parola;
		this.corretta = corretta;
	}


	public String getParola() {
		return parola;
	}

	public boolean isCorretta() {
		return corretta;
	}
	
	

}
