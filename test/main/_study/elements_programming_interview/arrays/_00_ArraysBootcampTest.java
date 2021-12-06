package main._study.elements_programming_interview.arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class _00_ArraysBootcampTest {

    _00_ArrayBootcamp arrayBootcamp = new _00_ArrayBootcamp();

    @Test
    public void test1() {
        int[] result = arrayBootcamp.sort(new int[]{5, 4, 3, 2, 1});
        int[] expectedResult = {1, 3, 5, 2, 4};
        assertThat(result).isEqualTo(expectedResult);
    }
}