package java.coursera._03_sorting_and_searching;

public abstract class Sorting<T extends Comparable<T>> {

    protected final T [] array;

    public Sorting(T[] array) {
        this.array = array;
    }

    public abstract T[] sort();

    protected void swap(int i, int j) {
        T swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }


    protected int getMinIndex(int i, int j) {
        if (array[i].compareTo(array[j]) < 0) return i; return j;
    }

    protected boolean isLess(int i, int j) {
        return array[i].compareTo(array[j]) < 0;
    }

    protected boolean isLess(T i, T j) {
        return i.compareTo(j) < 0;
    }
}


