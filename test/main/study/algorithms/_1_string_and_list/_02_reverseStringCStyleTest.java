package main.study.algorithms._1_string_and_list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _02Test {

    private final static String NULL_CHAR = "\0";

    private final _02 test = new _02();

    @Test
    public void testEmpty() {
        assertEquals(NULL_CHAR, test.reverseCStyle(""));
    }

    @Test
    public void testNull() {
        assertEquals(NULL_CHAR, test.reverseCStyle(null));
    }

    @Test
    public void testSingleChar() {
        assertEquals("A" + NULL_CHAR, test.reverseCStyle("A"));
    }

    @Test
    public void testManyChars() {
        assertEquals("DCBA" + NULL_CHAR, test.reverseCStyle("ABCD"));
    }

    @Test
    public void testNullChar() {
        assertEquals(NULL_CHAR, test.reverseCStyle("\0"));
    }
}