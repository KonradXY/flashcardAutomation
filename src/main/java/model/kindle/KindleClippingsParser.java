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
import org.apache.log4j.Logger;

public class KindleClippingsParser implements IParser {

	private static final Logger log = Logger.getLogger(KindleClippingsParser.class);

	private static final String KINDLE_TOKEN = "==========";
	private static final String KINDLE_KEY = "evidenziazione";

	@Override
	public List<IAnkiCard> parse(Path fileName, String input) {
		String[] values = input.split(KINDLE_TOKEN);
		List<IAnkiCard> cardList = new ArrayList<>();
		Stream.of(values).forEach(clip -> {
			if (clip.contains(KINDLE_KEY)) {
				cardList.add(new KindleAnkiCard("", "").mapFromLine(clip));
			}});
		return cardList;
	}

	@Override
	public Map<Path, List<IAnkiCard>> sort(Map<Path, List<IAnkiCard>> mapContent) {
		List<IAnkiCard> cards = mapContent.values().iterator().next();
		Path parentPath = mapContent.keySet().iterator().next().getParent();

		String path = parentPath.toString().replace("input","output").concat("/");	// FIXME qua nn prendo la cartella di output dal engineModel !

		Map<Path, List<KindleAnkiCard>> kindleMap =  cards.stream().map(it -> (KindleAnkiCard)it).sorted()
				.collect(Collectors.groupingBy(KindleAnkiCard::getTitleAsPath));

		Map<Path, List<IAnkiCard>> newContent = new HashMap<>();
		for (Map.Entry<Path, List<KindleAnkiCard>> entry : kindleMap.entrySet()) {
			Path filePath = Paths.get(path + entry.getKey().toString().trim());
			newContent.put(filePath, entry.getValue().stream().map(it -> (IAnkiCard)it).collect(Collectors.toList()));
		}

		return newContent;
	}

}
