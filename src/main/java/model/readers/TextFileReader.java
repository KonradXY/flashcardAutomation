package main.java.model.readers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

public class TextFileReader implements IReader {

    private static final Logger log = Logger.getLogger(TextFileReader.class);
    public static final String NEW_LINE = "\n";

    private final ReadStrategy reader;

    private int fileCounter = 0;

    public TextFileReader(ReadStrategy strategy) {
        this.reader = strategy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Path, String> readFile(Path... pathFiles) {
        Map<Path, String> map = new HashMap<>();
        for (Path pathFile : pathFiles) {
            readContent(map, pathFile);
        }

        log.info("************* Fine Lettura ************* Numero di file letti: " + fileCounter + "\n");

        return map;
    }

    private void readContent(Map<Path, String> contentMap, Path filePath) {
        try {
            if (!filePath.toFile().isDirectory()) {
                Stream.of(filePath)
                        .filter(IReader::filterParsable)
                        .forEach(it -> addEntryToMap(contentMap, it));
                return;
            }
            Files.walk(filePath)
                    .filter(p -> !p.equals(filePath))
                    .sorted()
                    .forEach(path -> readContent(contentMap, path));

        } catch (IOException ex) {
            log.error("Errore nella lettura dei file: ", ex);
        }

    }

    private void addEntryToMap(Map<Path, String> mapInput, Path pathFile) {
        if (!mapInput.containsKey(pathFile)) {
            log.info("lettura file: " + pathFile);
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = openFile(pathFile)) {
                br.lines().forEach(str -> sb.append(formatLine(str)).append(NEW_LINE));
                fileCounter++;
            } catch (IOException ex) {
                log.error("Errore nella funzione addEntryToMap", ex);
            }

            mapInput.put(pathFile, sb.toString());
        }
    }

	private BufferedReader openFile(Path pathFile) throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(pathFile.toString()), StandardCharsets.UTF_8));
	}

	private String formatLine(String input) {
        return reader.format(input);
    }

}
