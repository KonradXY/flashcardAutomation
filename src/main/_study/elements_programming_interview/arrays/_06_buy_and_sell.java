package main._study.elements_programming_interview.arrays;

public class _06_buy_and_sell {

    /*
    Given an array of integer find the optimal value for which buying and selling stocks

    // TODO - questo lo devo capire meglio
     */

    public int buyAndSell(int[] array) {
        int maxProfit = Integer.MIN_VALUE;
        int minPrice = Integer.MAX_VALUE;
        for (int price : array) {
            maxProfit = Math.max(maxProfit, price - minPrice);
            minPrice = Math.min(price, minPrice);
        }
        return maxProfit;
    }

    /*
    TODO - variante. finds the length of a longest subarray all of whose entries are equal.
     */


}
