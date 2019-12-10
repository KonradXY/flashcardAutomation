package main.java.modelDecorator;



import java.util.List;
import java.util.Map;

import main.java.contracts.IAnkiCard;
import org.jsoup.nodes.Element;

import main.java.model.AnkiCard;

public class WebParsedCardDecorator extends StandardFormatCardDecorator {

	public IAnkiCard create(String word, String traduzione, String contenuto, String listaSinonimi,
                            Map<String, String> definizioniMap, List<String> synonims) {
        this.card.create();
        addContentToFront(card, word, getBoldParagraphTag().addClass("wordLearned"));
        addContentToFront(card, traduzione, getParagraphTag().addClass("traduzione"));
        addContentToBack(card, contenuto, getParagraphTag().addClass("contenuto"));

		addDefinizioneToBack(card, definizioniMap); // TODO <--- questi sono entrambi da rivedere !
		addSinonimiToBack(card, synonims);          // TODO <--- questi sono entrambi da rivedere ! (e da eliminare)
		return this.card;
	}
	


	private void addDefinizioneToBack(IAnkiCard card, Map<String, String> definizioni) {
        if (definizioni.isEmpty())
            return;

        Element definizioniList = getUnorderedListTag().addClass("definizioni");
        for (Map.Entry<String, String> entry : definizioni.entrySet()) {
            Element listItem = createSingleDefinizione(entry);
            definizioniList.appendChild(listItem);
        }
        applyStandardFormatRecursively(definizioniList);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text("Definizioni"));
        card.getBack().appendChild(definizioniList);

    }
	
	protected void addSinonimiToBack(IAnkiCard card, List<String> sinonimi) {
        if (sinonimi.isEmpty())
            return;

        Element listaSinonimi = getUnorderedListTag().addClass("sinonimi");
        for (String str : sinonimi) {
            listaSinonimi.appendChild(getListItemTag().text(str));
        }
        applyStandardFormatRecursively(listaSinonimi);

        card.getBack().appendChild(getNewLineTag()).appendChild(getNewLineTag());
        card.getBack().appendChild(getBoldParagraphTag().text("Sinonimi"));
        card.getBack().appendChild(listaSinonimi);

    }
}
