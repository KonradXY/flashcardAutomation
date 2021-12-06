package main._study.elements_programming_interview.arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class _05_delete_repeated_from_sorted_arrayTest {

    public static final int[] INPUT = {1, 1, 2, 2};
    public static final int[] EXPECTED_RESULT = {1, 2, 0, 0};
    _05_delete_repeated_from_sorted_array test = new _05_delete_repeated_from_sorted_array();

    @Test
    public void test() {
        assertThat(test.deleteRepeated(INPUT)).isEqualTo(EXPECTED_RESULT);
    }

}