package main.java.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class AbstractAnkiCardTest {

	private final static String emptyFront 	= "<div class=\"front\"></div>";
	private final static String emptyBack 	= "<div class=\"back\"></div>";
	private final static String emptyCard = emptyFront + "\t" + emptyBack + "\n";
	
	private final static String content = "this is my content";
	
	@Test
	void testCreatingEmptyAbstractCard() {
		AnkiCard card = new AnkiCard();
		
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
		AnkiCard card = new AnkiCard();
		
		card.addTextContentToFront(content);
		assertEquals(content, card.getFront().ownText());
		
		card.addTextContentToBack(content);
		assertEquals(content, card.getBack().ownText());
		
	}
	
	@Test
	void testAddingElementAbstractAnkiCard() {
		AnkiCard card = new AnkiCard();
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
