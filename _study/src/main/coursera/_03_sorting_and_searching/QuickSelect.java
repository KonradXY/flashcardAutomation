package coursera._03_sorting_and_searching;

public class QuickSelect<T> extends QuickSort {

    public QuickSelect(Comparable[] array) {
        super(array);
    }

    public Comparable<T> quickSelect(int k_smallest_element) {
        // shuffling to do for better performance
        int lo = 0, hi = array.length-1;
        while (hi > lo) {
            int pivot = partitionArray(array, lo, hi);
            if (pivot < k_smallest_element) lo = pivot + 1;
            else if (pivot > k_smallest_element) hi = pivot - 1;
            else return array[k_smallest_element];
        }
        return array[k_smallest_element];
    }


}
