package main.java.model.evernote; 

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.java.config.AnkiApplicationModule;
import main.java.factory.AbstractAnkiEngine;
import main.java.factory.EvernoteEngine;

class EvernoteHtmlParserTest {
	
	private final static Path testfilepath = Paths.get("");
	private final Injector injector = Guice.createInjector(new AnkiApplicationModule());
	private final AbstractAnkiEngine evernoteEngine = injector.getInstance(EvernoteEngine.class);

	@BeforeAll
	public static void setup() {
			
	}
	
	@Test
	void test() {
		evernoteEngine.buildEngine();
	}

}
