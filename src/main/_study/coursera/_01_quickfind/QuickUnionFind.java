package main._study.coursera._01_quickfind;

import java.util.stream.IntStream;

public class QuickUnionFind extends DynamicConnectivity{

    private int[] sizeTree; // added memory complexity

    protected QuickUnionFind(int size) {
        super(size);
        initializeSizeTree(size);
    }

    protected QuickUnionFind(int[] array) {
        super(array);
        initializeSizeTree(array.length);
    }

    private void initializeSizeTree(int size) {
        this.sizeTree = new int[size];
        IntStream.range(0, sizeTree.length).forEach(i -> sizeTree[i] = 1);
    }

    @Override
    public boolean connected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    private int getRoot(int i) {
        while (array[i] != i) {
            pathCompression(i);
            i = array[i];
        }
        return i;
    }

    private void pathCompression(int i) {
        /* Make every other node in path point to its grandparent (thereby halving path length). */
        array[i] = array[array[i]];
    }


    // Depth of the tree is at most logN (with N as size of the input)
    @Override
    public void union(int p, int q) {
        int rootP = getRoot(p);
        int rootQ = getRoot(q);
        if (rootP == rootQ) return;
        if (sizeTree[rootP] < sizeTree[rootQ]) {
            array[rootP] = rootQ;
            sizeTree[rootQ] += sizeTree[rootP];
        }
        else {
            array[rootQ] = rootP;
            sizeTree[rootP] += sizeTree[rootQ];
        }
    }

}
