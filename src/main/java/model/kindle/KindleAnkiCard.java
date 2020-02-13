package main.java.model.kindle;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.model.AnkiCard;

public class KindleAnkiCard extends AnkiCard implements Comparable<KindleAnkiCard>{
	
	public static final Pattern timePattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
//			Pattern.compile("[0-9]");
	
	private String title;
	private String posizione;
	private String dataAggiunta;
	private String content;

	private int hashContent;
	
	
	public KindleAnkiCard(String front, String back) {
		super(front, back);
	}

	public KindleAnkiCard(String inputLine) {
		mapFromLine(inputLine);
	}

	public Path getTitleAsPath() {
		return Paths.get(title);
	}
	
	public String getTitle() { return title; }
	public String getPosizione() { return posizione; }
	public String getDataAggiunta() { return dataAggiunta; }
	public String getContent() { return content; }
	public int getHashContent() { return this.hashContent; }

	public void setTitle(String title) { this.title = title; }
	public void setPosizione(String posizione) { this.posizione = posizione; }
	public void setDataAggiunta(String dataAggiunta) { this.dataAggiunta = dataAggiunta; }

	private void setContent(String content) {
		this.content = content;

		if (content.length() > 5) {
			this.hashContent = Objects.hash(content.substring(0,5));
		}
		else {
			this.hashContent = Objects.hash(content);
		}
	}


	public KindleAnkiCard mapFromLine(String inputLine) {
		
		int idxTitle = inputLine.indexOf("-");
		int idxPosizione = inputLine.indexOf("|", idxTitle);
		
		int idxDataAggiunta = 0;
		Matcher oraAggiuntaMatcher = timePattern.matcher(inputLine);
		while (oraAggiuntaMatcher.find())  {
			idxDataAggiunta = oraAggiuntaMatcher.end();
		}
		
		int idxContent = idxDataAggiunta;
		
		this.setTitle(inputLine.substring(0,idxTitle));
		this.setPosizione(inputLine.substring(idxTitle+1, idxPosizione));
		this.setDataAggiunta(inputLine.substring(idxPosizione+1, idxContent));
		this.setContent(inputLine.substring(idxContent));
		
		this.addTextContentToFront(this.title);
		this.addTextContentToBack(this.content);

		return this;
		
	}

	public boolean cardsAreNearEquals(KindleAnkiCard card) {
		return hashContent == card.getHashContent();
	}



	@Override
	public int compareTo(KindleAnkiCard otherCard) {
		int compare = 0;
		compare = this.title.compareTo(otherCard.getTitle());
		if (compare != 0) return compare;
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KindleAnkiCard that = (KindleAnkiCard) o;
		return Objects.equals(title, that.title) &&
				Objects.equals(posizione, that.posizione) &&
				Objects.equals(dataAggiunta, that.dataAggiunta) &&
				Objects.equals(content, that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, posizione, dataAggiunta, content);
	}
}
