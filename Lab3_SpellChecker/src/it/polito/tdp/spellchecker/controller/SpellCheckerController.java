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
import javafx.util.converter.NumberStringConverter;

public class SpellCheckerController {
	
	private Dictionary model;
	private List<String> inputText = new LinkedList<String>();
	
	public void setModel(Dictionary model) {
		this.model = model;
		
		 choiceBox.getItems().add("English");
	     choiceBox.getItems().add("Italiano");
	     choiceBox.setValue("English");
		//chiedo il riferimento alla proprietà che determina il valore del testo
		//e lo collego al riferimento di tentativi nel model così che quando modifico tentativi modifico anche txtCurr
		
		//choiceBox.valueProperty().bind(model.linguaProperty());
    
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
    	labelNumError.setDisable(true);
    	labelTimeError.setDisable(true);
    	txtInput.clear();
    	txtOutput.clear();
    	model.clear();

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	model.clear();
    	labelNumError.setDisable(false);
    	labelTimeError.setDisable(false);
    	
    	long inizio = System.currentTimeMillis();
    	String language = choiceBox.getValue();
    	model.loadDictionary(language);
    	
    	String daSpezzare = txtInput.getText();
    	daSpezzare.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]\\\"", "");
    	StringTokenizer st = new StringTokenizer(daSpezzare);
    	while(st.hasMoreTokens()) {
    		String token = st.nextToken();
    		inputText.add(token);
    	}
    	model.spellCheckText(inputText);
    	txtOutput.setText(model.getParoleErrate());
    	labelNumError.setText("The text contains "+ model.getNumErrate()+ " errors");
    	labelTimeError.setText("SpellCheck completed in "+ (System.currentTimeMillis()-inizio)/1000 + "seconds");
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


