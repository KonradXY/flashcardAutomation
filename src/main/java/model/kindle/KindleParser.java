package main.java.model.kindle;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;

public class KindleParser implements IParser {

	private static final String KINDLE_TOKEN = "==========";
	private static final String KINDLE_KEY = "evidenziazione";

	@Override
	public List<IAnkiCard> parse(Path fileName, String input) {
		String[] values = input.split(KINDLE_TOKEN);
		List<IAnkiCard> cardList = new ArrayList<>();
		Stream.of(values).forEach(inputLine -> {
			if (inputLine.contains(KINDLE_KEY)) {
				cardList.add(new KindleAnkiCard(inputLine));
			}});
		return cardList;
	}

	@Override
	public Map<Path, List<IAnkiCard>> sort(Map<Path, List<IAnkiCard>> mapContent) {
		List<IAnkiCard> cards = mapContent.values().iterator().next();
		Path parentPath = mapContent.keySet().iterator().next().getParent();

		Map<Path, List<KindleAnkiCard>> kindleMap = createKindleMapContent(cards);

		String outputFolder = parentPath.toString().replace("input","output").concat("/");	// FIXME qua nn prendo la cartella di output dal engineModel !
		return createMapContent(kindleMap, outputFolder);


	}

	private Map<Path, List<KindleAnkiCard>> createKindleMapContent(List<IAnkiCard> cards) {
		return cards.stream().map(it -> (KindleAnkiCard)it).sorted()
				.collect(Collectors.groupingBy(KindleAnkiCard::getTitleAsPath));
	}

	private Map<Path, List<IAnkiCard>> createMapContent(Map<Path, List<KindleAnkiCard>> kindleMap, String outputFolder) {
		Map<Path, List<IAnkiCard>> newContent = new HashMap<>();
		for (Map.Entry<Path, List<KindleAnkiCard>> entry : kindleMap.entrySet()) {
			Path filePath = Paths.get(outputFolder + entry.getKey().toString().trim());
			newContent.put(filePath, entry.getValue().stream().map(it -> (IAnkiCard)it).collect(Collectors.toList()));
		}

		return newContent;
	}

}
