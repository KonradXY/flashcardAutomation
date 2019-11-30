package main.java.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Test;

class AbstractAnkiCardTest {

	private final static Logger log = Logger.getLogger(AbstractAnkiCardTest.class);
	
	private final static String emptyFront 	= "<div class=\"front\"></div>";
	private final static String emptyBack 	= "<div class=\"back\"></div>";
	private final static String emptyCard = emptyFront + "\t" + emptyBack + "\n";
	
	private final static String content = "this is my content";
	
	@Test
	void testCreatingEmptyAbstractCard() {
		AbstractAnkiCard card = new AbstractAnkiCard();
		
		assertEquals(emptyFront, card.getFront().toString());
		assertEquals(emptyBack, card.getBack().toString());
		assertEquals(emptyCard, card.toString());
		
		assertEquals(card.getFront().html(), "");
		assertEquals(card.getBack().html(), "");
		
		assertTrue(card.isCardEmpty());

		
		assertFalse(card.getFront().hasText());
		assertFalse(card.getBack().hasText());
	}
	
	@Test
	void testAddingSimpleTextAbstractAnkiCard() {
		AbstractAnkiCard card = new AbstractAnkiCard();
		
		card.addTextContentToFront(content);
		assertEquals(content, card.getFront().ownText());
		
		card.addTextContentToBack(content);
		assertEquals(content, card.getBack().ownText());
		
	}
	
	@Test
	void testAddingElementAbstractAnkiCard() {
		AbstractAnkiCard card = new AbstractAnkiCard();
		Tag tag = Tag.valueOf("div");
		Element elem = new Element(tag, "").addClass("inner-content");
		elem.appendText(content);
		
		card.addElementToFront(elem);
		card.addElementToBack(elem);
		
		
		assertEquals("<div class=\"front\"> <div class=\"inner-content\">  this is my content </div></div>"
				+ "	<div class=\"back\"> <div class=\"inner-content\">  this is my content </div></div>\n",
				card.toString());
		
	}

}