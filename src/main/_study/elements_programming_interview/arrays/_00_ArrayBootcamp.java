package main._study.elements_programming_interview.arrays;

public class _00_ArrayBootcamp {

    /*
     Given an integer list reorder the list so that you have even entries on the left and odd to the right.
     Try to reach the optimal performance without using additional space.

     Domanda: le entry a sinistra devono essere anche loro ordinate ? da verificare sto concetto.
     Di base partirei con una versione base del quicksort
     */

    public int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    private int[] sort(int[] array, int lo, int hi) {
        int i = lo - 1;
        // solo con questa roba mi ritrovo gli array divisi tra pari e dispari pero' nella stessa sequenza
        for (int k = lo; k <= hi; k++) {
            if (isOdd(array[k])) {
                swap(array, ++i, k);
            }
        }
        // adesso pero' vorrei trovarmeli pari e dispari pero' ordinati. Famolo coi quickSort nel frattempo (mi viene una cosa come 3NlogN.
        // So gia' che i e' l'ultimo punto per il quale la funzione predicato ritorna true (quindi e' la fine del primo array)
        // TODO - e' possibile fare lo stesso senza ulteriori chiamate al quicksort in questa maniera ma mettendole all'interno del for di sopra?
        array = quickSort(array, 0, i);
        array = quickSort(array, i + 1, hi);
        return array;
    }

    public int[] quickSort(int[] array, int lo, int hi) {
        if (hi <= lo) return array;
        int pivot = getPivot(array, lo, hi);
        array = quickSort(array, lo, pivot - 1);
        array = quickSort(array, pivot + 1, hi);
        return array;
    }

    private int getPivot(int[] array, int lo, int hi) {
        int pivot = hi;
        int i = lo - 1;
        for (int k = lo; k <= hi; k++) {
            if (isLess(array, k, pivot)) {
                swap(array, ++i, k);
            }
        }
        swap(array, ++i, pivot);
        return i;
    }

    private boolean isLess(int[] array, int i, int j) {
        return array[i] < array[j];
    }

    private boolean isOdd(int i1) {
        return i1 % 2 != 0;
    }

    private void swap(int[] array, int i, int j) {
        int swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }
}
