package main.java.model.kindle;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import main.java.contracts.IAnkiCard;
import main.java.contracts.IParser;

public class KindleClippingsParser implements IParser {

	private final String kindleToken = "==========";
	private final String kindleKey = "evidenziazione";
	
	@Override
	public List<IAnkiCard> parse(Path fileName, String input) {
		String[] values = input.split(kindleToken);
		List<IAnkiCard> cardList = new ArrayList<>();
		Stream.of(values).forEach(clip -> {
			if (clip.contains(kindleKey)) {
				cardList.add(new KindleAnkiCard("", "").mapFromLine(clip));
			}});
		return cardList;
	}

}
