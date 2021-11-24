package main._study.algorithms._1_string_and_list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _01Test {

    private final _01 test = new _01();

    @Test
    public void testEmpty() {
        assertTrue(test.allUniqueChars(""));
    }

    @Test
    public void testSingleCharReturnsTrue() {
        assertTrue(test.allUniqueChars("A"));
    }

    @Test
    public void differentCharsReturnsTrue() { assertTrue(test.allUniqueChars("ABCDE"));}

    @Test
    public void testNonUniqueCharReturnsFalse() {
        assertFalse(test.allUniqueChars("AA"));
    }

    @Test
    public void testDifferentCaseReturnFalse() {
        assertFalse(test.allUniqueChars("aA"));
    }





}