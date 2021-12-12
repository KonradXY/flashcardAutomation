package coursera._02_stack_and_queues;

public class StackWithArray<T> {

    T[] stack;
    int pointer;

    public StackWithArray(int size) {
        stack = (T[]) new Object[size];
    }

    public boolean isEmpty() {
        return stack[0] == null;
    }

    public void push(T t) {
        stack[pointer++] = t;
    }

    public T pop() {
        return stack[pointer--];
    }

}


