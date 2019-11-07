package main.java.kindleModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.abstractModel.AbstractAnkiCard;

public class KindleAnkiCard extends AbstractAnkiCard implements Comparable<KindleAnkiCard>{
	
	public static Pattern timePattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
//			Pattern.compile("[0-9]");
	
	// kindle gestisce i clipping tra segnalibri ed evidenziazioni. Le
	// evidenziazioni (che sono quelle che mi servono) non hanno la pagina
	// all'interno dei dati. Comunque mantengo il campo chissa' mi possa servire
	
	private String title;
//	private String pagina;
	private String posizione;
	private String dataAggiunta;
	private String content;
	
	
	public KindleAnkiCard(String front, String back) {
		super(front, back);
	}
	
	public String getTitle() { return title; }
//	public String getPagina() { return pagina; }
	public String getPosizione() { return posizione; }
	public String getDataAggiunta() { return dataAggiunta; }
	public String getContent() { return content; }

	public void setTitle(String title) { this.title = title; }
//	public void setPagina(String pagina) { this.pagina = pagina; }
	public void setPosizione(String posizione) { this.posizione = posizione; }
	public void setDataAggiunta(String dataAggiunta) { this.dataAggiunta = dataAggiunta; }
	public void setContent(String content) { this.content = content; }


	public KindleAnkiCard mapFromLine(String inputLine) {
		
		int idxTitle = inputLine.indexOf("-");
		int idxPosizione = inputLine.indexOf("|", idxTitle);
		
		int idxDataAggiunta = 0;
		Matcher oraAggiuntaMatcher = timePattern.matcher(inputLine);
		while (oraAggiuntaMatcher.find())  {
			idxDataAggiunta = oraAggiuntaMatcher.end();
		}
		
		int idxContent = idxDataAggiunta;
		
		this.title = inputLine.substring(0,idxTitle);
		this.posizione = inputLine.substring(idxTitle+1, idxPosizione);
		this.dataAggiunta = inputLine.substring(idxPosizione+1, idxContent);
		this.content = inputLine.substring(idxContent);
		
		this.front = this.title;
		this.back = this.content;
		
		return this;
		
	}
	
	@Override
	public int compareTo(KindleAnkiCard otherCard) {
		
		int compare = 0;
		compare = this.title.compareTo(otherCard.getTitle());
		if (compare != 0) return compare;
		
		return 0;
	}

}
