package main.java.model.parsers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;
import main.java.model.AnkiDeck;
import main.java.model.KindleAnkiCard;
import main.java.utils.Property;

public class KindleParser implements IParser {

	private static final String KINDLE_TOKEN = "==========";
	private static final String KINDLE_KEY = "evidenziazione";

	@Override
	public List<AnkiDeck> parse(Path fileName, String input, String destFolder) {
		List<KindleAnkiCard> kindleDeck = mapCardsFromInput(input);
		//kindleDeck = removeNearDuplicates(kindleDeck);
//		return kindleDeck;
		throw new UnsupportedOperationException("da implementare");
	}
	
	private List<KindleAnkiCard> mapCardsFromInput(String input) {
		String[] lines = input.split(KINDLE_TOKEN);
		return Stream.of(lines).filter(kindleEntry -> kindleEntry.contains(KINDLE_KEY))
				.map(kindleEntry -> new KindleAnkiCard(kindleEntry))
				.collect(Collectors.toList());
	}

	private AnkiDeck removeNearDuplicates(AnkiDeck deck) {
		List<KindleAnkiCard> kindleDeck = deck.getCards().stream().map(it -> (KindleAnkiCard)it).collect(Collectors.toList());
		Map<Integer, List<KindleAnkiCard>> contentByHash = kindleDeck.stream().collect(Collectors.groupingBy(KindleAnkiCard::getHashContent));
		List<IAnkiCard> newCardList = new ArrayList<>();

		for (Map.Entry<Integer, List<KindleAnkiCard>> entry : contentByHash.entrySet()) {
			List<KindleAnkiCard> cardList = entry.getValue();
			cardList.sort(Comparator.comparingInt((KindleAnkiCard k) -> k.getContent().length()).reversed());
			newCardList.add(cardList.get(0));
		}

		return new AnkiDeck.Builder().withCards(newCardList).build();
	}

	public Map<Path, AnkiDeck> sort(Map<Path, AnkiDeck> mapContent) {
		List<IAnkiCard> cards = mapContent.values().iterator().next().getCards();
		Path parentPath = mapContent.keySet().iterator().next().getParent();

		Map<Path, List<KindleAnkiCard>> kindleMap = createKindleMapContent(cards);

		String outputFolder = parentPath.toString().replace("input","output").concat("/");	// FIXME qua nn prendo la cartella di output dal engineModel !
		return createMapContent(kindleMap, outputFolder);


	}

	private Map<Path, List<KindleAnkiCard>> createKindleMapContent(List<IAnkiCard> cards) {
		return cards.stream().map(it -> (KindleAnkiCard)it).sorted()
				.collect(Collectors.groupingBy(KindleAnkiCard::getTitleAsPath));
	}

	private Map<Path, AnkiDeck> createMapContent(Map<Path, List<KindleAnkiCard>> kindleMap, String outputFolder) {
		Map<Path, AnkiDeck> newContent = new HashMap<>();
		for (Map.Entry<Path, List<KindleAnkiCard>> entry : kindleMap.entrySet()) {
			Path filePath = Paths.get(outputFolder + entry.getKey().toString().trim());

			AnkiDeck.Builder builder = new AnkiDeck.Builder();

			newContent.put(filePath, builder.withCards(entry.getValue().stream()
					.map(it -> (IAnkiCard)it)
					.collect(Collectors.toList()))
					.build());
		}

		return newContent;
	}
	
	private Path getKindleOutputFolder() {
		return Paths.get(Property.OUTPUT_DIR + Property.KINDLE_PATH);
	}

}
