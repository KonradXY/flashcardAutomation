package main.java.strategy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PrinterStrategyTest {

    PrinterStrategy printerStrategy;

    private final Path kindleName1 = Paths.get("/home/corrado.giovanniello/personalDev/flashcardAutomation/output/kindle/My Clippings - Kindle_parsed.txt");
    private final Path kindleNameOutput1 = Paths.get("/home/corrado.giovanniello/personalDev/flashcardAutomation/output/kindle/My Clippings - Kindle_parsed.txt");

    @BeforeAll
    static void setup() {

    }

    @Test
    void testKindleGetName() {  // TODO - verificare il discorso tramite data providing in junit + aggiungere nuovi test !
        printerStrategy = PrinterStrategy.KINDLE_STRATEGY;

        Path newName = printerStrategy.createNameOutputFile(kindleName1);
        assertEquals(kindleNameOutput1, newName);

    }

}