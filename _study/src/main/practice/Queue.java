package practice;

public class Queue<T> { // FIFO

    Node<T> first, last;

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(T element) {
        Node<T> previousLast = last;
        last = new Node<>();
        last.element = element;
        last.next = null;
        if (isEmpty()) first = last;
        else previousLast.next = last;
    }

    public T dequeue() {
        if (isEmpty()) throw new UnsupportedOperationException("queue is empty");

        Node<T> oldFirst = first;
        first = first.next;
        if (isEmpty()) last = null;
        return oldFirst.element;
    }

    private static class Node<T> {
        T element;
        Node next;
    }
}
