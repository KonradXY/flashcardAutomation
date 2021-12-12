package practice;

public class QuickSelect {

    public Comparable quickSelect(Comparable[] array, int n) {
        return quickSelect(array, 0, array.length-1, n);
    }

    private Comparable quickSelect(Comparable[] array, int lo, int hi,  int n) {
        int pivot = -1;
        while (hi > lo) {
            pivot = partition(array, lo, hi);
            if (pivot == n) return array[n];
            else if (pivot < n) hi = pivot-1;
            else lo = pivot +1;
        }
        return array[n];
    }

    private int partition(Comparable[] array, int lo, int hi) {
        int arbitratyPivot = hi;
        int indexPivot = lo-1;
        for (int k = lo; k <= hi; k++) {
            if (array[k].compareTo(array[arbitratyPivot]) < 0) {
                swap(array, ++indexPivot, k);
            }
        }
        swap(array, ++indexPivot, arbitratyPivot);
        return indexPivot; // returns the new index of the pivot
    }

    private void swap(Comparable[] array, int i, int j) {
        Comparable swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }
}
