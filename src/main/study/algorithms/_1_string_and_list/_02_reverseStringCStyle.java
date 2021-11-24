package main.study.algorithms._1_string_and_list;

import org.apache.commons.lang3.StringUtils;

class _02 {

    /*
     Write code to reverse a C-Style String.
     (C-String means that “abcd” is represented as five characters, including the null character.)
     */

    // OK qua un problema che ho notato e' che in effetti il problema diceva di stampare. Questo significa che posso farlo al volo senza storare
    // all'interno dello stringBuilder. Da verificare com'e' possibile


    private final static String NULL_CHAR = "\0";

    public String reverseCStyle(String string) {

        if (StringUtils.isEmpty(string))
            return NULL_CHAR;

        if (string.equals(NULL_CHAR))
            return NULL_CHAR;

        printReverseCStyle(string);
        return reverseString(string);
    }

    // Simple loop O(n) x memoria e tempo
    private String reverseString(String string) {
        int stringBuilderCapacity = string.length() + 1;
        StringBuilder stringBuilder = new StringBuilder(stringBuilderCapacity);
        for (int i = string.length() - 1; i >= 0; i--) {
            stringBuilder.append(string.charAt(i));
        }
        stringBuilder.append(NULL_CHAR);
        return stringBuilder.toString();
    }

    // in realta' era questa la cosa che dovevo fare
    private void printReverseCStyle(String string) {
        for (int i = string.length() - 1; i >= 0; i--) {
            System.out.print(string.charAt(i));
        }
        System.out.print(NULL_CHAR);
    }

}
