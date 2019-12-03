package main.java.modelDecorator;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.contracts.IAnkiCard;
import main.java.model.AnkiCard;

public class StandardFormatCardDecorator extends AbstractCardDecorator {
	
	// TODO - non mi piace il fatto che creo la card qui .. verificare meglio questa cosa
	
	public IAnkiCard create(Elements front, Elements back) {
		
		for (Element e : front) 
    		applyLeftFormat(e);
		for (Element e : back) 
			applyLeftFormat(e);

		this.card = new AnkiCard(front, back);
		
		return card;
	}
	
	
}
