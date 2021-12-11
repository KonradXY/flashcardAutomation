package java.coursera._03_sorting_and_searching;

public class BinarySearch {

    public int search(int[] orderedArray, int n) {
        return search(orderedArray, n, 0, orderedArray.length - 1);
    }

    private int search(int[] orderedArray, int n, int lo, int hi) {
        if (n > orderedArray[hi] || n < orderedArray[lo]) return -1;
        int mid = (hi + lo) / 2;
        if (n == orderedArray[mid]) return mid;
        else if (n > orderedArray[mid]) return search(orderedArray, n, mid+1, hi);
        else return search(orderedArray, n, lo, mid-1);
    }
}
