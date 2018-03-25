package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
	
	private int numErrori;
	private List<String> dizionario;
	private String lingua;
	private List<RichWord> paroleInput;
	
	
	public Dictionary() {
		this.numErrori = 0;
		this.dizionario = new LinkedList<String>();
		paroleInput = new LinkedList<RichWord>();
	}
	
	
	public int getNumErrori() {
		return numErrori;
	}

	public void loadDictionary(String language) {
		
		if (language.toLowerCase().equals("italiano"))
			lingua = "rsc/Italian.txt";
		else if (language.toLowerCase().equals("english"))
			lingua = "rsc/English.txt";

		try {
			FileReader fr = new FileReader(lingua);
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				// Aggiungere parola alla struttura dati
				dizionario.add(word);
			}
			br.close();
		} catch (IOException e){
			System.out.println("Errore nella lettura del file");
		}		
	}

	public List<RichWord> spellCheckText(List<String> inputText) {
		for(String s : inputText) {
			if(dizionario.contains(s)) {
				RichWord parola = new RichWord(s, true);
				paroleInput.add(parola);
			}else {
				RichWord parola = new RichWord(s, false);
				paroleInput.add(parola);
			}
		}
		return paroleInput;
	}
	
	public String getParoleErrate(){
		String errate="";
		for(RichWord r : this.paroleInput) {
			if(r.isCorretta()==false) {
				errate +=r.getParola()+"\n";
				this.numErrori++;
			}
		}
		return errate;
	}

	public void clear() {
		this.numErrori = 0;
		this.dizionario.clear();
		this.paroleInput.clear();
	}

}
