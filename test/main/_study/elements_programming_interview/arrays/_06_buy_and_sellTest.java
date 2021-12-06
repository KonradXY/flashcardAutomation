package main._study.elements_programming_interview.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _06_buy_and_sellTest {

    _06_buy_and_sell test = new _06_buy_and_sell();

    @Test
    public void test() {
        int result = test.buyAndSell(new int[]{310,315, 275, 295, 260, 270, 290, 230, 255, 250});
        int expectedResult = 30;
        assertEquals(expectedResult, result);
    }



}