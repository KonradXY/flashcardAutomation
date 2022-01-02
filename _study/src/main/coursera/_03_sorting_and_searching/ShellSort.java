package coursera._03_sorting_and_searching;

public class ShellSort<T> extends Sorting {


    public ShellSort(Comparable[] array) {
        super(array);
    }

    @Override
    public Comparable[] sort() { // uguale all'insert sort pero' c'e' sta H che viene calcolata come 3H+1
        int N = array.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;
        while (h > 0) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (isLess(array[j], array[j - h])) {
                        swap(j, j - h);
                    }
                }
            }
            h = h / 3;
        }
        return array;
    }
}
