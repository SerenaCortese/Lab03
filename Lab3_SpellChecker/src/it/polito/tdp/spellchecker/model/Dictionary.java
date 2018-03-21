package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dictionary {
	
	private List<String> diziItaliano;
	private List<String> diziInglese;
	private StringProperty lingua;

	List<RichWord> parole;
	
	private boolean inInglese;
	private boolean inItaliano;
	private int numErrate;
	
	public Dictionary() {
		diziItaliano = new LinkedList<String>();
		diziInglese = new LinkedList<String>();
		lingua = new SimpleStringProperty();
		parole = new LinkedList<RichWord>();
		inInglese=false;
		inItaliano=false;
		numErrate=0;
	}
	
	public void loadDictionary(String language) {
		
		if(language.toLowerCase().compareTo("english")==0) {
			inInglese=true;
			try {
				FileReader fr = new FileReader("rsc/English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
				// Aggiungere parola alla struttura dati
					diziInglese.add(word);
				}
				br.close();
				} catch (IOException e){
					System.out.println("Errore nella lettura del file");
				}
			
		}else {
			inItaliano=true;
			try {
				FileReader fr = new FileReader("rsc/Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
				// Aggiungere parola alla struttura dati
					diziItaliano.add(word);
				}
				br.close();
				} catch (IOException e){
					System.out.println("Errore nella lettura del file");
				}
		
			
		}
		
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		boolean trovata = false;
		RichWord parola;
		if(inItaliano == true) {
			for(String s : inputTextList) {
				for(String d : diziItaliano) {
					if(s.compareToIgnoreCase(d)==0) {
						trovata= true;
						parola = new RichWord(s,trovata);
						parole.add(parola);
					}
				}
				if(!trovata) {
					parola= new RichWord(s,trovata);
					parole.add(parola);
					numErrate++;
				}
			}
		}
		
		if(inInglese==true) {
			for(String s : inputTextList) {
				for(String d : diziInglese) {
					if(s.compareToIgnoreCase(d)==0) {
						trovata= true;
						parola = new RichWord(s,true);
						parole.add(parola);
					}
				}
				if(!trovata) {
					parola= new RichWord(s,trovata);
					parole.add(parola);
					numErrate++;
				}
			}
		}
		return parole;
	}
	public String getParoleErrate(){
		String errate="";
		for(RichWord r : parole) {
			if(r.isCorretta()==false) {
				errate+= r.getParola()+"\n";
			}
		}
		return errate;
	}
	public int getNumErrate() {
		return numErrate;
	}

	public final StringProperty linguaProperty() {
		return this.lingua;
	}
	

	public final String getLingua() {
		return this.linguaProperty().get();
	}
	

	public final void setLingua(final String lingua) {
		this.linguaProperty().set(lingua);
	}

	public void clear() {
		inInglese=false;
		inItaliano=false;
		numErrate=0;
		parole.clear();
	}
	

}
