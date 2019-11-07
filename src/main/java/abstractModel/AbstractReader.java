package main.java.abstractModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import main.java.strategy.ReadingStrategy;

public class AbstractReader {



	private static final Logger log = Logger.getLogger(AbstractReader.class);
	private static int fileCounter = 0;

	public final ReadingStrategy reader;

	public AbstractReader(ReadingStrategy strategy) {
		this.reader = strategy;
	}

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
				if (IReader.isParseable(filePath)) {
					addEntryToMap(contentMap, filePath); 
					log.info("lettura file: " + filePath);
					fileCounter++;
				}
				return;
			}
		
			Files.walk(filePath)
				.filter(p -> !p.equals(filePath))
				.sorted() 	// fare un comparator custom per sta roba
				.forEach(path -> readContent(contentMap, path));
		
		} catch (IOException ex) {
			log.error("Errore nella lettura dei file: " + ex);
		}

	}

	private void addEntryToMap(Map<Path, String> mapInput, Path pathFile) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile.toString()), "UTF-8"))) {
			br.lines().forEach(str -> sb.append(formatLine(str)));
		}

		mapInput.put(pathFile, sb.toString());
	}

	public String formatLine(String input) {
		return reader.format(input); 
	}

}
