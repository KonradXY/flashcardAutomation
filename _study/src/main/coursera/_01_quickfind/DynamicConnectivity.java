package coursera._01_quickfind;

import java.util.stream.IntStream;

public abstract class DynamicConnectivity {

    protected int[] array;

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
