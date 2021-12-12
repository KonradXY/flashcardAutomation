package coursera._02_stack_and_queues;

public class QueueLinkedList<T> {

    Node<T> first;
    Node<T> last;

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(T t) {
        Node<T> oldLast = last;
        last = new Node<>();
        last.element = t;
        last.next = oldLast;
        if (isEmpty())
            first = last;
        else oldLast.next = last;

    }

    public T dequeue() {
        T element = first.element;
        first = first.next;
        if (isEmpty()) last = null;
        return element;
    }

    private static class Node<T> {
        T element;
        Node<T> next;
    }
}
