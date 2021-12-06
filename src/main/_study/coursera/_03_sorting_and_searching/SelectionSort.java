package main._study.coursera._03_sorting_and_searching;

public class SelectionSort<T> extends Sorting {


    public SelectionSort(Comparable[] array) {
        super(array);
    }

    public Comparable[] sort() {
        for (int i = 0; i < array.length; i++) {
            int indexMin = i;
            for (int j = i + 1; j < array.length; j++) {
                indexMin = getMinIndex(indexMin, j);
            }
            swap(i, indexMin);
        }
        return array;
    }


}