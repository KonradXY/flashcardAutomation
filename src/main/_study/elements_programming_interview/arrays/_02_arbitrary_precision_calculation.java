package main._study.elements_programming_interview.arrays;

public class _02_arbitrary_precision_calculation {

    public int[] incrementDigit(int[] digit, int toSum) {
        // TODO - potrei convertire toSum in un secondo array e fare la somma dei due campo x campo vedendo anche il riporto.

        // 1. Convertire toSum in array (un ciclo while facendo il mod di 10 finche' non diventa 0 - importante la capacita' iniziale dell'array -> da trovare qlks per questo)
        // 2. Somma partendo da destra e tenendo in conto il riporto + return

        // ATTENZIONE: c'e' anche il concetto dell'overflow da tenere a mente (se l'array e' troppo grande per essere rappresentato da un int. In questo caso stiamo chiedendo
        // di fare la somma di due numeri con precisione arbitraria (si possono utilizzare gli array x questo)
        return null;
    }

    /* TODO - variante dell'esercizio e' fare la stessa cosa pero' con due stringhe. Oltre al convert sarebbe utile fare un check x vedere se le stringhe
        sono digit e rientrano negli int. Questo lo posso fare a perdita di tempo xo' mi sembra abbastanza stupido come esercizio.
        */

    /* TODO - altra variante e' quella delle moltiplicazioni, considerando anche i numeri negativi.      */
}
