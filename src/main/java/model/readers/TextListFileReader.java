package main.java.model.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import main.java.contracts.IReader;

public class TextListFileReader implements IReader {

	private int fileCounter = 0;
	private final static Logger log = Logger.getLogger(TextListFileReader.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<Path, List<String>> readFile(Path... pathFiles) {
		Map<Path, List<String>> map = new HashMap<>();
		for (int i = 0; i < pathFiles.length; i++) {
			readContent(map, pathFiles[i]);
		}

		log.info("************* Fine Lettura ************* Numero di file letti: " + fileCounter + "\n");

		return map;
	}

	// *********** reading functions

	private void readContent(Map<Path, List<String>> contentMap, Path filePath) {
		try {
			if (!Files.isDirectory(filePath)) {
				Stream.of(filePath).filter(IReader::filterParsable).forEach(it -> addEntryToMap(contentMap, it));
				return;
			}

			Files.walk(filePath).filter(p -> !p.equals(filePath)).sorted()
					.forEach(path -> readContent(contentMap, path));

		} catch (IOException ex) {
			log.error("Errore nella lettura dei file: ", ex);
		}

	}

	private void addEntryToMap(Map<Path, List<String>> mapInput, Path pathFile) {
		if (!mapInput.containsKey(pathFile)) {
			log.info("lettura file: " + pathFile);
			try {
				mapInput.put(pathFile, getWordListFromFile(pathFile.toString()));
			} catch (IOException ex) {
				log.error("Errore durante la lettura del file: " + pathFile, ex);
			}
		}
	}

	public List<String> getWordListFromFile(String inputFile) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
		List<String> wordsList = br.lines().map(String::trim)
//                .limit(20)
				.collect(Collectors.toList());
		br.close();
		return wordsList;
	}
}
