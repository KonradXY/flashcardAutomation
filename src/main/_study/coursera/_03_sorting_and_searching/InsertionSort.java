package main._study.coursera._03_sorting_and_searching;

public class InsertionSort<T> extends Sorting {

    public InsertionSort(Comparable[] array) {
        super(array);
    }

    @Override
    public Comparable[] sort() {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (isLess(j, j - 1)) {
                    swap(j, j - 1);
                }
            }
        }

        return array;
    }

    /**
     * nell'insertion sort quello che facciamo e' partendo dal primo prenderli tutti e
     * confrontarli fino a che non ne troviamo uno che non sia minore o uguale (a quel punto interrompiamo)
     */


}
