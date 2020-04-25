package main.java.webpages.wordreference;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.enums.CssClass;
import main.java.webpages.AbstractPage;

public class WordReferenceDefinitionPage extends AbstractPage {

	private static final String WORD_REFERENCE_ESP_DEFINITION_PAGE_URL = "https://www.wordreference.com/definicion/";

	private Document definitionPage;

	@Override
	public void scrapePageWithWord(String word) {
		this.definitionPage = scrapePage(WORD_REFERENCE_ESP_DEFINITION_PAGE_URL, word);
	}

	public Element getWordDefinitions() {

		if (this.definitionPage == null)
			throw new IllegalStateException("the page cannot be null !");

		Element article = definitionPage.getElementById("article");        
		Elements li = article.getElementsByTag("li");

		Element definizioniList = getDivElement(CssClass.DEFINIZIONI_CLASS);
		li.stream().sequential().limit(3).forEach(it -> it.appendTo(definizioniList));
		
		return definizioniList;
	}


	void setDefinitionPage(Document doc) {
		this.definitionPage = doc;
	}
}
