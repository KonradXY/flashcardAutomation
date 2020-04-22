package main.java.model.parsers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.contracts.IParser;
import main.java.model.AnkiDeck;
import main.java.model.KindleAnkiCard;

public class KindleParser implements IParser {

    private static final String KINDLE_TOKEN = "==========";
    private static final String KINDLE_KEY = "evidenziazione";

    @Override
    public List<AnkiDeck> parse(Path fileName, String input, String destFolder) {
        List<KindleAnkiCard> kindleCards = mapCards(input);
		return createDecks(kindleCards, destFolder);
    }

    private List<KindleAnkiCard> mapCards(String input) {
        List<KindleAnkiCard> cardList = Stream.of(input.split(KINDLE_TOKEN))
                .filter(kindleEntry -> kindleEntry.contains(KINDLE_KEY))
                .map(KindleAnkiCard::new)
                .collect(Collectors.toList());

        removeNearDuplicates(cardList);

        return cardList;
    }

    private List<KindleAnkiCard> removeNearDuplicates(List<KindleAnkiCard> kindleCards) {
        Map<Integer, List<KindleAnkiCard>> contentByHash = kindleCards.stream().collect(Collectors.groupingBy(KindleAnkiCard::getHashContent));
        List<KindleAnkiCard> newCardList = new ArrayList<>();

        for (Map.Entry<Integer, List<KindleAnkiCard>> entry : contentByHash.entrySet()) {
            List<KindleAnkiCard> cardList = entry.getValue();
            cardList.sort(Comparator.comparingInt((KindleAnkiCard k) -> k.getContent().length()).reversed());
            newCardList.add(cardList.get(0));
        }

        return newCardList;

    }

    private List<AnkiDeck> createDecks(List<KindleAnkiCard> cardList, String destFolder) {
		Map<String, List<KindleAnkiCard>> decks =
				cardList.stream().sorted().collect(Collectors.groupingBy(KindleAnkiCard::getTitle));

		return decks.entrySet().stream().map(entry -> new AnkiDeck.Builder()
																.withTitle(entry.getKey())
																.withDestFolder(destFolder)
																.withCards(entry.getValue())
																.build())
				.collect(Collectors.toList());

    }

}
