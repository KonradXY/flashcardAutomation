package java.coursera._03_sorting_and_searching.excersise;

public class DisorganizedCarpenter {


    /*
     A disorganized carpenter has a mixed pile of n nuts and n bolts.
     The goal is to find the corresponding pairs of nuts and bolts.
     Each nut fits exactly one bolt and each bolt fits exactly one nut. By fitting a nut and a bolt together,
     the carpenter can see which one is bigger (but the carpenter cannot compare two nuts or two bolts directly).

     Design an algorithm for the problem that uses at most proportional to nlogn compares (probabilistically).
     */

    // Q: how can we check if a nut and bolt fit together?? There is a condition we must satisfy?

    // TODO - This problem is solved by the quick-sort technique. By taking the last element of the bolt as a pivot,
    //  rearrange the nuts list and get the final position of the nut whose bolt is the pivot element.
    //  After partitioning the nuts list, we can partition the bolts list using the selected nut.
    //  The same tasks are performed for left and right sub-lists to get all matches.

    /*
        Input:
        The lists of locks and keys.
        nuts = { ),@,*,^,(,%, !,$,&,#}
        bolts = { !, (, #, %, ), ^, &, *, $, @ }

        Output:
        After matching nuts and bolts:
        Nuts:  ! # $ % & ( ) * @ ^
        Bolts: ! # $ % & ( ) * @ ^
     */

    // TODO - scrivere l'algorimo x sta cosa !
}
