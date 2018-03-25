package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {

	private Dictionary model;
	private List<String> inputText = new LinkedList<String>();
	
	public void setModel(Dictionary model) {
		this.model = model;
		choiceBox.getItems().add("English");
		choiceBox.getItems().add("Italiano");
		choiceBox.setValue("English"); //visualizzo a schermo un valore iniziale sulla box
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<String> choiceBox;

	@FXML
	private TextArea txtInput;

	@FXML
	private Button btnSpellCheck;

	@FXML
	private TextArea txtOutput;

	@FXML
	private Label labelNumError;

	@FXML
	private Button btnClear;

	@FXML
	private Label labelTimeError;

	@FXML
	void doClearText(ActionEvent event) {
		//1.pulire memoria
		model.clear();
		
		//2.cancella testo da entrambe le caselle di testo
		txtInput.clear();
		txtOutput.clear();
		labelNumError.setText("");
		labelTimeError.setText("");
		
		//3.disabilitare tasti/text area
		labelNumError.setDisable(true);
		labelTimeError.setDisable(true);
		txtOutput.setDisable(true);
		
	}

	@FXML
	void doSpellCheck(ActionEvent event) {
		long start = System.nanoTime();
		
		//1.abilita label di errore e cancella output vecchio
		labelNumError.setDisable(false);
		labelTimeError.setDisable(false);
		txtOutput.setDisable(false);
		txtOutput.clear();
		model.clear();
		inputText.clear();
		
		//2.prende la lingua selezionata e memorizza dizionario
		String lingua = choiceBox.getSelectionModel().getSelectedItem(); 
		model.loadDictionary(lingua);
		
		//3.richiama il metodo spellChecker del model
		String input = txtInput.getText();
		
		if (input == null || input.length() == 0) {
			txtInput.setText("Insert one or more words in the selected language.");
			long stop = System.nanoTime();
	    	double tempo= (stop-start)/Math.pow(10, 9);
	    	labelTimeError.setText("Spell Check completed in "+tempo+" seconds");
			return;
		}
		String inputFinale = input.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]"," ");
		StringTokenizer st = new StringTokenizer(inputFinale.toLowerCase());
		while(st.hasMoreTokens()) {
    		String token = st.nextToken();
    		inputText.add(token);
    	}
		
		model.spellCheckText(inputText);
		
		//4.restituisce elenco parole errate
		txtOutput.setText(model.getParoleErrate());
		
		//5.settare label con numero di errori 
		labelNumError.setText("The text contains " +model.getNumErrori()+" errors");
		
		//6.settare label con tempo impiegato da spellcheck
		long stop = System.nanoTime();
    	double tempo= (stop-start)/Math.pow(10, 9);
    	labelTimeError.setText("Spell Check completed in "+tempo+" seconds");

	}

	@FXML
	void initialize() {
		assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert labelNumError != null : "fx:id=\"labelNumError\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert labelTimeError != null : "fx:id=\"labelTimeError\" was not injected: check your FXML file 'SpellChecker.fxml'.";

	}

}
