package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
	
	private int numErrori;
	private List<String> dizionario;
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
		String lingua = "";
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
		//dal testo penso che voglia restituito una lista con parole corrette E errate, non solo errate
		
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
	
	public List<RichWord> spellCheckTextLinear(List<String> inputText){
		boolean flag = false;
		
		for(String s : inputText) {
			
			for(String d : this.dizionario) {
				
				if(d.equals(s.toLowerCase())) {
					flag=true;
					RichWord w = new RichWord(s, false);
					paroleInput.add(w);
					break;
				}
			}
			
			if(!flag) {
				RichWord w = new RichWord(s, false);
				paroleInput.add(w);
				
			}
		}
		return paroleInput;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputText){
		
		for(String s : inputText) {
			int inizio = 0;
			int fine = inputText.size();
			boolean trovato = false;
			while(inizio<=fine && !trovato) {
				int centro = (inizio+fine)/2;
				if(s.toLowerCase().compareTo(dizionario.get(centro))==0) {
					trovato=true;
					RichWord w = new RichWord(s,true);
					paroleInput.add(w);
					break;
				}
				else if(s.toLowerCase().compareTo(dizionario.get(centro))>0) {
					inizio = centro+1;
				}
				else {
					fine = centro-1;	
				}
			}
			
			if(!trovato) {
				RichWord w = new RichWord(s,false);
				paroleInput.add(w);
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
