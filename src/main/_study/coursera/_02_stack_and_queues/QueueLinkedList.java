package main._study.coursera._02_stack_and_queues;

public class QueueLinkedList<T> {

    Node<T> first;
    Node<T> last;

    public boolean isEmpty() {
        return last.next == null;
    }

    public void enqueue(T t) {
        Node<T> oldLast = last;
        last.element = t;
        last.next = null;
        if (isEmpty()) first = last;    // Rivedere bene questi due passaggi
        oldLast.next = last;

    }

    public T dequeue() {
        T item = first.element;
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }

    private static class Node<T> {
        T element;
        Node<T> next;
    }
}
