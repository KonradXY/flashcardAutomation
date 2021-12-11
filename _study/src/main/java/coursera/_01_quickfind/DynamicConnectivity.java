package java.coursera._01_quickfind;

import java.util.stream.IntStream;

public abstract class DynamicConnectivity {

    protected int[] array;

    // TODO - fare l'inizializzazione random mi porta dei problemi xke' posso creare dei cicli all'interno. Dovrei vedere come fare una inizializzazione senza cicli
    protected DynamicConnectivity(int size) {
        this.array = new int[size];
        IntStream.range(0, array.length).forEach(i -> array[i] = i);
    }

    protected DynamicConnectivity(int[] array) {
        this.array = array;
    }

    public abstract boolean connected(int p, int q);

    public abstract void union(int p, int q);

}
