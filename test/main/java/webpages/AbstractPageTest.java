package main.java.webpages;

import java.util.Map;

import org.jsoup.nodes.Element;

// Questa classe al momento e0 una utility per i test (visto che jsoup nn ha equals all'interno)

public class AbstractPageTest {
	
	public boolean elementsAreEquals(Element e1, Element e2) {
		return e1.text().equals(e2.text());
	}
	
	public <K,V> V getFirstObjectFromMap(Map<K, V> map) {
		if (map == null || map.size() <= 0) 
			throw new IllegalArgumentException("Error ! Map should contain at least one value. Map: " + map);
		return map.values().iterator().next();
	}
}
