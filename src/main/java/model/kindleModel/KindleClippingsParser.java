package main.java.model.kindleModel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import main.java.contracts.IParser;
import main.java.model.AbstractAnkiCard;

public class KindleClippingsParser implements IParser {

	private final String kindleToken = "==========";
	private final String kindleKey = "evidenziazione";
	
	@Override
	public List<AbstractAnkiCard> parseToAnkiFlashcard(Map<Path, String> input) {

		String[] values;
		List<AbstractAnkiCard> ankiCardList = new ArrayList<>();
		for (Map.Entry<Path, String> clipping : input.entrySet()) {
			values = clipping.getValue().split(kindleToken);
			Stream.of(values).forEach(clip -> {
				if (clip.contains(kindleKey)) {
					ankiCardList.add(new KindleAnkiCard("", "").mapFromLine(clip));
				}
			});
		}
		
		return ankiCardList;
	}

}
