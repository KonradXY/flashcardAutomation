package main._study.coursera._03_sorting_and_searching;

public class BottomUpMergeSort<T> extends Sorting{
    public BottomUpMergeSort(Comparable[] array) {
        super(array);
    }

    @Override
    public Comparable[] sort() {
        int N = array.length;
        Comparable[] aux = new Comparable[N];
        for (int size = 1; size < N; size *=2) {
            for (int lo = 0; lo < N-size; lo += 2*size) {
                merge(array, aux, lo, lo+size-1, Math.min(lo+2*size-1, N-1));
            }
        }
        return array;
    }

    private void merge(Comparable[] array, Comparable[] aux, int lo, int mid, int hi) {
        for(int k = lo; k<=hi; k++) aux[k] = array[k];
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                array[k] = aux[j++];
            }
            else if (j > hi) {
                array[k] = aux[i++];
            }
            else if (aux[i].compareTo(aux[j]) < 0) {
                array[k] = aux[i++];
            } else {
                array[k] = aux[j++];
            }
        }
    }
}
