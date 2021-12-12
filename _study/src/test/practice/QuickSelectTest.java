package practice;

import coursera._03_sorting_and_searching.SortingTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSelectTest extends SortingTest {

    @Test
    public void quickSelectTest() {
        QuickSelect quickSelect = new QuickSelect();
        Comparable result = quickSelect.quickSelect(UNSORTED_ARRAY, 3);
        assertEquals(result, 4);

    }

}