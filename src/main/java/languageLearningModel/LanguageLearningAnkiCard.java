package main.java.languageLearningModel;

import java.util.function.Predicate;

import main.java.abstractModel.AbstractAnkiCard;

public class LanguageLearningAnkiCard extends AbstractAnkiCard {

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
	
//	public final static Predicate<LanguageLearningAnkiCard> isVocabolario = (it) -> it.kind == PracticeMakesPerfectEnum.VOCABOLARIO;
//	public final static Predicate<LanguageLearningAnkiCard> isGrammatica  = (it) -> it.kind == PracticeMakesPerfectEnum.GRAMMATICA;
//	public final static Predicate<LanguageLearningAnkiCard> isTraduzione  = (it) -> it.kind == PracticeMakesPerfectEnum.TRADUZIONE;
	public final static Predicate<LanguageLearningAnkiCard>  noFilter 	  = (it -> true);
	
	public void setKind(PracticeMakesPerfectEnum kind) { this.kind = kind; }
	public PracticeMakesPerfectEnum getKind() 		   { return this.kind; }
	
	
}
