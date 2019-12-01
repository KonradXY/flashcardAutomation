package main.java.modelDecorator;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import main.java.model.AnkiCard;

public class WebParsedCardDecorator extends StandardFormatCardDecorator {

	// TODO - dovrei implementare dei campi per le definizioni e per i sinonimi ? 
	
	// TODO - questo e' quello che piu' si avvicina al decorator che pensavo:
	// la carta parte inizialmente vuota e vengono aggiuti i vari elementi. Verificare e capire meglio il pattern
	
	public AnkiCard create(String word, String traduzione, String contenuto, String listaSinonimi,
			Map<String, String> definizioniMap, List<String> synonims) {
		AnkiCard card = new AnkiCard();
		addWordLearnedToFront(card, word);
		addTranslationToFront(card, traduzione);
		addContenutoToBack(card, contenuto);
		addParoleTradotteToBack(card, listaSinonimi);
		addDefinizioneToBack(card, definizioniMap);
		addSinonimiToBack(card, synonims);
		return card;
	}
	
	
	
	private static Element getDefinizioneTag() {
		return getUnorderedListTag().addClass("definizioni");
	}
	private static Element getSinonimiTag() {
		return getUnorderedListTag().addClass("sinonimi");
	}
	
	private void addDefinizioneToBack(AnkiCard card, Map<String, String> definizioni) {
        if (definizioni.isEmpty())
            return;

        Element definizioniList = getDefinizioneTag();
        for (Map.Entry<String, String> entry : definizioni.entrySet()) {
            Element listItem = createSingleDefinizione(entry);
            definizioniList.appendChild(listItem);
        }
        applyStandardFormatRecursively(definizioniList);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(applyStandardFormatRecursively(getBoldParagraphTag().text("Definizioni")));
        card.getBack().appendChild(definizioniList);

    }
	
	protected void addSinonimiToBack(AnkiCard card, List<String> sinonimi) {
        if (sinonimi.isEmpty())
            return;

        Element listaSinonimi = getSinonimiTag();
        for (String str : sinonimi) {
            listaSinonimi.appendChild(getListItemTag().text(str));
        }
        applyStandardFormatRecursively(listaSinonimi);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(applyStandardFormatRecursively(getBoldParagraphTag().text("Sinonimi")));
        card.getBack().appendChild(listaSinonimi);

    }
}
