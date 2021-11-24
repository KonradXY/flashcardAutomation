package main.study.algorithms._1_string_and_list;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class _01 {

    /*
    Implement an algorithm to determine if a string has all unique characters. What if you can not use additional data structures?
     */

    public boolean allUniqueChars(String string) {
        if (string.isEmpty()) return true;
        char[] stringChar = string.toUpperCase().toCharArray();       // O(n)

        return allUniqueCharsWithSorting(stringChar);

//        return allUniqueCharsWithSet(stringChar);
    }

    // Questa soluzione e' abbastanza semplice. Ha il prezzo del sorting che costituisce il tradeoff x il risparmio di memoria
    // O(t) = O(n^2) - caso peggiore
    // O(m) = O(n) - char[] come variabile d'appoggio - se fosse passata come parametro nn ci sarebbe nessun problema.
    public boolean allUniqueCharsWithSorting(char[] string) {
        Arrays.sort(string);                                      // O(nlogn) -> worst case O(n^2)
        return checkForDuplicates(string);                        // O(n)
    }

    private boolean checkForDuplicates(char[] sortedString) {
        for (int i = 0; i < sortedString.length - 1; i++) {
            if (sortedString[i] == sortedString[i + 1])
                return false;
        }
        return true;
    }


    // O(t) = O(n)
    // O(m) = O(n)
    public boolean allUniqueCharsWithSet(char[] string) {
        Set<Character> charSet = new HashSet<>();   // O(m) = O(n) caso peggiore
        for (int i = 0; i < string.length; i++) {
            if (charSet.contains(string[i])) return false;   // O(1)
            charSet.add(string[i]);
        }
        return true;
    }


}
