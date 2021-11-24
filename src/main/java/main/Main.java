package main.java.main;

import java.util.Arrays;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.java.config.AnkiApplicationModule;

public class Main {

    private static final String DEFAULT_ENGINE = "evernote";

    /**
     * @param args I valori che i parametri possono assumere sono i seguenti:
     *             <p>
     *             null - campo vuoto - "default"  <<<===  default engine
     *             evernote                        <<<=== evernote engine
     *             kindle                          <<<=== kindle engine
     *             languageLearning                <<<=== languageLearning engine
     *             generateIndex				   <<<=== generate index engine
     *             webcrawler general              <<<=== spanish general flashcard (spanishWebCrawlerService)
     *             wencrawler simple               <<<=== spanish simple flashcard (just definition x2 ita/esp && esp&ita)
     *             webcrawler cloze                <<<=== spanish cloze flashcard
     */

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AnkiApplicationModule());

        if (args.length == 0) args = new String[]{DEFAULT_ENGINE};
        List<String> inputArg = Arrays.asList(args);

        injector.getInstance(Launcher.class).buildFlashcards(inputArg);
    }




}
