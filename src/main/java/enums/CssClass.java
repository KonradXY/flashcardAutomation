package main.java.enums;

public enum CssClass {

	TRADUZIONE_CLASS("Traduzione"),
    TRADUZIONI_LIST_CLASS("Traduzioni"),
    DEFINIZIONI_CLASS("Definizioni"),
    SINONIMI_CLASS("Sinonimi"),
    WORD_LEARNED("Word Learned");
	
	private String value;
	
	CssClass(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
