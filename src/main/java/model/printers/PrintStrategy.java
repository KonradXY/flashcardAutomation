package main.java.model.printers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import main.java.model.card.IAnkiCard;
import org.apache.log4j.Logger;

import main.java.model.deck.AnkiDeck;
import main.java.model.card.KindleAnkiCard;

public enum PrintStrategy {

	NO_STRATEGY {
		@Override public void printCards(AnkiDeck deck) {
			try {
				Files.write(deck.getPathDest(), (Iterable<String>) deck.getCards().stream().map(IAnkiCard::toString)::iterator);
			} catch (IOException ex) {
				System.out.println("Errore nella scrittura del file: " + ex.getMessage());
				throw new RuntimeException(ex);
			}
		}
	},

	KINDLE_STRATEGY {
		@Override
		public void printCards(AnkiDeck deck) {
			int cardIndex = 0;
			List<KindleAnkiCard> kindleCards = deck.getCards().stream().map(it -> (KindleAnkiCard)it).collect(Collectors.toList());

			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(deck.getFileDestination()), StandardCharsets.UTF_8))) {
				for (KindleAnkiCard kindleCard : kindleCards) {
					bw.write(cardIndex++ + ". " + kindleCard.getTitle() + "|");
					bw.write(kindleCard.getBack() + "\n\n");
				}
			} catch (IOException ex) {
				log.error("Errore nella scrittura del file: " + deck.getDestFolder(), ex);
			}
		}

	};

	public abstract void  printCards(AnkiDeck cards);

	private final static Logger log = Logger.getLogger(PrintStrategy.class);

}
