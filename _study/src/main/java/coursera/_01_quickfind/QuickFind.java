package java.coursera._01_quickfind;

import com.google.inject.Singleton;

import java.util.stream.IntStream;

@Singleton
public class QuickFind extends DynamicConnectivity {

    public QuickFind(int size) {
      super(size);
    }

    public QuickFind(int[] array) {
        super(array);
    }

    @Override
    public boolean connected(int p, int q) {
        return array[p] == array[q];
    }

    @Override
    public void union(int p, int q) {   // TODO <-- problem is we take N*N to union all the array in the worst case (worst case is all elements are different)
        int valP = array[p];
        int valQ = array[q];
        for (int i = 0; i<array.length; i++) {
            if (array[i] == valP) {
                array[i] = valQ;
            }
        }
    }

}
