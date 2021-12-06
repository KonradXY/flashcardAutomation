package main._study.elements_programming_interview.arrays;

public class _05_delete_repeated_from_sorted_array {

    /* given a sorted array of integer delete the repetition so that the array is still ordered, without repetition, and the integers deleted should be at the right with 0 */

    // we assume that 0 is not a valid input in the integer
    // the array is already sorted

    // the solution takes O(n) time and O(1) memory

    // La soluzione consiste nel tenere un pivot (un puntatore) che si distanzia dall'indice del for e rappresenta il numero di entry uguale
    // La chiave sta nel fatto che essendo l'array ordinato noi dobbiamo tenere in considerazione solo i numeri in sequenza. (Da spiegare meglio sto esercizio)
    public int[] deleteRepeated(int[] array) {
        int pivot = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] != array[pivot]) {
                array[++pivot] = array[i];
            }
        }
        for (int i = pivot+1; i < array.length; i++) {
            array[i] = 0;
        }
        return array;
    }

}
