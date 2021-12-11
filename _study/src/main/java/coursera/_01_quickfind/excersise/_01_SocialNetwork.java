package java.coursera._01_quickfind.excersise;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class _01_SocialNetwork {

    /**
     * Given a social network containing N members and a log file containing M timestamps at which times pairs of members formed friendships,
     * design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend).
     * Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
     * The running time of your algorithm should be mlogn or better and use extra space proportional to n.
     */

    // at 1 timestamp m correspond 2 people n that formed a friendship ? (we exclude that more people could form a friendship at the same time?)

    // log file will be something like this:
    // m1 - n1 - n2
    // m2 - n3 - n4
    // m3 - n1 - n4

    // N is an integer ?? (an id of a person?) and in the case it's not?

    // timestamp is sorted

    // Problem: earliest time for which all members are connected

    // What about if we have loops in our tree?
    // What if our starting array is not canonical (we have already friends before starting ?)


    int[] N;

    public boolean connected(int p1, int p2) {
        return root(p1) == root(p2);
    }

    public void union(int p1, int p2) {
        N[p2] = root(p1);
    }

    public OptionalInt firstNotConnected() {
        return IntStream.range(0, N.length-1).filter(it -> !connected(it, it+1)).findFirst();
    }

    private int root(int p) {   // TODO - se gli mettiamo anche il peso dell'albero abbiamo l'extra consumo di memoria ma il tempo del root diventa logN
        while (N[p] != p) p = N[p];
        return p;
    }

    public static void main(String[] args) {
        _01_SocialNetwork socialNetwork = new _01_SocialNetwork();
        OptionalInt optionalInt = socialNetwork.firstNotConnected();
        while (optionalInt.isPresent()) {

        }
    }




    private static class Person {
        int friend;
    }


}
