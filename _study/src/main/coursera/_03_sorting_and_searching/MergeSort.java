package coursera._03_sorting_and_searching;

public class MergeSort<T> extends Sorting{

    public MergeSort(Comparable[] array) {
        super(array);
    }

    @Override
    public Comparable[] sort() {
        Comparable[] a = this.array;
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
        return a;
    }

    private void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (isLess(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }


}
