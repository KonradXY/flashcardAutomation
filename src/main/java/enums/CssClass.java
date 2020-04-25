package main.java.enums;

public enum CssClass {

	TRADUZIONE_CLASS("traduzione"),
    TRADUZIONI_LIST_CLASS("traduzioni_list"),
    DEFINIZIONI_CLASS("definizioni"),
    SINONIMI_CLASS("sinonimi"),
    WORD_LEARNED("word learned");
	
	private String value;
	
	CssClass(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
