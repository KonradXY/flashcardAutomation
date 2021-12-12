package coursera._01_quickfind;

public class QuickUnion extends DynamicConnectivity {

    protected QuickUnion(int size) {
        super(size);
    }

    protected QuickUnion(int[] array) {
        super(array.length);
        this.array = array;
    }

    @Override
    public boolean connected(int p, int q) {
        int rootP = getRoot(p);
        int rootQ = getRoot(q);
        return rootP == rootQ;
    }

    private int getRoot(int i) {
        while (array[i] != i) i = array[i]; // TODO - il problema qui e' che puo' ciclare su un albero che diventa molto lungo
        return i;
    }

    @Override
    public void union(int p, int q) {
        array[getRoot(p)] = getRoot(q);     // union ha tempo costante d'accesso a memoria
    }

}
