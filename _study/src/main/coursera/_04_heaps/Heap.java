package coursera._04_heaps;

public class Heap {

    // TODO - da testare ma sembra ok. Le operazioni di moltiplicazione e divisione si potrebbero fare con uno shift di 1 binario (x2 == << 1)

    int[] array;
    int size;

    public Heap(int capacity) {
        array = new int[capacity];
        size = 0;
    }

    public void add(int value) {
        array[++size] = value;
        swim(array, size);
    }

    public int poll() {
        int valueToReturn = array[1];
        swap(1, size);
        array[size] = 0;
        size--;
        sink(array, 1);
        return valueToReturn;
    }

    private void swim(int[] array, int index) {
        while (index > 0) {
            int father = index / 2;
            if (array[father] <= array[index]) break;
            swap(father, index);
            index = father;
        }
    }

    private void sink(int[] array, int index) {
        while (index <= size) {
            int child = index * 2;
            if (child < size && array[child] > array[child + 1]) child++;
            if (array[child] > array[index]) break;
            swap(child, index);
            index = child;
        }
    }

    private void swap(int i, int j) {
        int swap = array[1];
        array[i] = array[j];
        array[j] = swap;
    }


}
