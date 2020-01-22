package main.java.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import main.java.contracts.IReader;
import main.java.strategy.ReadingFormatStrategy;

public class TextFileReader implements IReader {

	private static final Logger log = Logger.getLogger(TextFileReader.class);

	private final ReadingFormatStrategy reader;
	
	private int fileCounter = 0;

	public TextFileReader(ReadingFormatStrategy strategy) {
		this.reader = strategy;
	}

	@Override
	public Map<Path, String> readFile(Path... pathFiles) throws IOException {
		Map<Path, String> map = new HashMap<>();
		for (int i = 0; i < pathFiles.length; i++) {
			readContent(map, pathFiles[i]);
		}
		
		log.info("************* Fine Lettura ************* Numero di file letti: " + fileCounter + "\n");
		
		return map;
	}

	private void readContent(Map<Path, String> contentMap, Path filePath) {
		try {
			if (!Files.isDirectory(filePath)) {
				Stream.of(filePath)
						.filter(IReader::isParseable)
						.forEach(it -> addEntryToMap(contentMap, it));
				return;
			}
		
			Files.walk(filePath)
				.filter(p -> !p.equals(filePath))
				.sorted() 	// TODO - fare un comparator custom per sta roba (come li devo ordinare ? Sinceramente nn mi ricordo perche' l'ho segnato)
				.forEach(path -> readContent(contentMap, path));
		
		} catch (IOException ex) {
			log.error("Errore nella lettura dei file: " + ex);
		}

	}

	private void addEntryToMap(Map<Path, String> mapInput, Path pathFile) {
		if (!mapInput.containsKey(pathFile)) {
			log.info("lettura file: " + pathFile);
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile.toString()), "UTF-8"))) {
				br.lines().forEach(str -> sb.append(formatLine(str + "\n")));
				fileCounter++;
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			mapInput.put(pathFile, sb.toString());
		}
	}

	private String formatLine(String input) {
		return reader.format(input); 
	}

}
