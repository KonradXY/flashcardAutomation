package coursera._03_sorting_and_searching;

public class YaroslavskyQuickSort {

    // quicksort the array a[] using dual-pivot quicksort
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    // quicksort the subarray a[lo .. hi] using dual-pivot quicksort
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        if (less(a[hi], a[lo])) exch(a, lo, hi);        // make sure a[lo] <= a[hi]

        int lt = lo + 1, gt = hi - 1;
        int i = lo + 1;
        while (i <= gt) {
            if (less(a[i], a[lo])) exch(a, lt++, i++);
            else if (less(a[hi], a[i])) exch(a, i, gt--);
            else i++;
        }
        exch(a, lo, --lt);
        exch(a, hi, ++gt);

        // recursively sort three subarrays
        sort(a, lo, lt - 1);
        if (less(a[lt], a[gt])) sort(a, lt + 1, gt - 1);
        sort(a, gt + 1, hi);
    }


    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
