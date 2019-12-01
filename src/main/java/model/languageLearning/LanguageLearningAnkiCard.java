package main.java.model.languageLearning;

import main.java.model.AnkiCard;

public class LanguageLearningAnkiCard extends AnkiCard {

	public enum PracticeMakesPerfectEnum {
		TRADUZIONE,
		VOCABOLARIO,
		GRAMMATICA
	}

	private PracticeMakesPerfectEnum kind;
	
	public LanguageLearningAnkiCard(String front, String back) {
		super(front, back);
	}
	
	public LanguageLearningAnkiCard(String front, String back, PracticeMakesPerfectEnum val) {
		super(front, back);
		this.kind = val;
	}
	
	public void setKind(PracticeMakesPerfectEnum kind) { this.kind = kind; }
	public PracticeMakesPerfectEnum getKind() 		   { return this.kind; }
	
	
}

