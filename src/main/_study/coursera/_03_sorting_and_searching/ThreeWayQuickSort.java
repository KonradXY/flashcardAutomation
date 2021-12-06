package main._study.coursera._03_sorting_and_searching;

public class ThreeWayQuickSort<T> extends Sorting {

    public ThreeWayQuickSort(Comparable[] array) {
        super(array);
    }

    @Override
    public Comparable[] sort() {
        return sort(array, 0, array.length - 1);
    }

    private Comparable[] sort(Comparable[] array, int lo, int hi) {
        if (hi < lo) return array;
        int less = lo, more = hi;
        Comparable pivot = array[lo];
        int i = lo;
        while (i <= more) {
            int compare = array[i].compareTo(pivot);
            if (compare < 0) swap(less++, i++);
            else if (compare > 0) swap(i, more--);
            else i++;
        }
        array = sort(array, lo, less - 1);
        array = sort(array, more + 1, hi);
        return array;
    }
}
