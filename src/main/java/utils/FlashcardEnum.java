package main.java.utils;

public enum FlashcardEnum {
	
	KINDLE	 			{ public String getValue() {return "kindle"; }},
	EVERNOTE 			{ public String getValue() {return "evernote";}},	
	LANGUAGE_LEARNING 	{ public String getValue() {return "languageLearning";}},
	GENERAL 			{ public String getValue() {return "general";}};
	
	public abstract String getValue();
	
	public FlashcardEnum getKindle() {
		return this.KINDLE;
	}
}
