package java.coursera._03_sorting_and_searching;

public class QuickSort<T> extends Sorting{

    public QuickSort(Comparable[] array) {
        super(array);
    }

    @Override
    public Comparable[] sort() {
        // StdRandom.shuffle(array) // random shuffle for performance guarantee
        sort(array, 0, array.length-1);
        return array;
    }

    protected void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo) return;
        int pivot = partitionArray(array, lo, hi);
        sort(array, lo, pivot-1);
        sort(array, pivot+1, hi);
    }

    protected int partitionArray(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        while (true) {
            while (isLess(++i, lo)) {   // find item on left to swap
                if (i == hi) break;
            }
            while (isLess(lo, --j)) {   // find item on right to swap
                if (j == lo) break;
            }
            if (i >= j) break; // check if pointers cross
            swap(i,j);
        }
        swap(lo, j);// swap with patitioning item;
        return j;
    }

    protected int easierPartitioning(Comparable<T>[] a, int lo, int hi) {
        int i = lo-1, pivot = hi;
        for (int k = lo; k<hi; k++) {
            if (isLess(array[k], array[pivot])) {
                swap(k, ++i);
            }
        }
        swap(pivot, ++i);
        return i;
    }
}
