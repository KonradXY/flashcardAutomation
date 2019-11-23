package main.java.languageLearningModel;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import main.java.baseModel.AbstractAnkiCard;
import main.java.contracts.IPrinter;

public class LanguageLearningPrinter implements IPrinter {

	private final String nameFile = "languageExcerciseParsed.txt";
	
	@Override
	public void printFile(String destFolderPath, List<AbstractAnkiCard> input) throws IOException {
		printAnkiCard(destFolderPath + nameFile, input);
	}
	
	private void printAnkiCard(String destPath, List<AbstractAnkiCard> input) throws IOException  {
		checkOutputFolder(Paths.get(destPath));
		
		try (BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath), "UTF-8"))) {
			List<LanguageLearningAnkiCard> list =
					input.stream().map(
					it -> (LanguageLearningAnkiCard)it)
					.collect(Collectors.toList());
			
			for (LanguageLearningAnkiCard card : list) {
				pw.write(card.toString());
			}
		} 
	}
	
	private void checkOutputFolder(Path filePath) throws IOException {
		Path folderPath = filePath.getParent();
		if (Files.notExists(folderPath) || !Files.isDirectory(folderPath))
			Files.createDirectories(folderPath);
	}

	

}
