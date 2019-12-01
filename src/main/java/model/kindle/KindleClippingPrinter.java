package main.java.model.kindle;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import main.java.contracts.IPrinter;
import main.java.model.AnkiCard;

public class KindleClippingPrinter implements IPrinter {

	@Override
	public void printFile(String destFile, List<AnkiCard> input) throws IOException {

		Path destPath = Paths.get(destFile);
		
		Map<String, List<KindleAnkiCard>> list = 
				input.parallelStream().map(it -> (KindleAnkiCard) it).sorted().collect(Collectors.groupingBy(KindleAnkiCard::getTitle));

		
		if (!Files.isDirectory(destPath)) 
			destPath = destPath.getParent();
		
		if (Files.notExists(destPath) || (Files.exists(destPath)) && !Files.isDirectory(destPath)) {
			Files.createDirectory(destPath);
		}


		int cardIndex = 0;
		for (Map.Entry<String, List<KindleAnkiCard>> entryAnkiMap : list.entrySet()) {
			
			cardIndex = 0;
			String fileName = createFileName(entryAnkiMap.getKey()); 
			fileName = destPath+"\\"+fileName;


			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
				for (KindleAnkiCard ankiCard : entryAnkiMap.getValue()) {
					bw.write(cardIndex++ + ". " + ankiCard.getKey()+"|");
					bw.write(ankiCard.getValue()+"\n\n");
				}
			}
		}
		
		System.out.println("Numero file creati: " + list.size());
		
	}
	
	
	private String createFileName(String fileName) {
		if (fileName.lastIndexOf("\\") != -1) 
			fileName = fileName.substring(fileName.lastIndexOf("\\"));
		
		fileName = fileName.replace(".", "")
						.replace(":", " ")
						.replace("*", "")
						.replace("/", "_")
						.trim()
						.concat(".txt")
						;
		
		if (fileName.length() > 50) {
			fileName = fileName.substring(0,46).concat(".txt");
		}
		
		return fileName;
	}
	
}
