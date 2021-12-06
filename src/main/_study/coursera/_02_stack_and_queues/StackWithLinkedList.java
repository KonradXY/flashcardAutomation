package main._study.coursera._02_stack_and_queues;


public class StackWithLinkedList<T> {

    T element;
    Node<T> top;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T t) {
        Node<T> previousTop = top;
        top = new Node<>();
        top.element = t;
        top.next = previousTop;
    }


    /**
     * @throws NullPointerException: when stack is empty
     * @return
     */
    public T pop() {
        T toReturn = top.element;
        top = top.next;
        return toReturn;
    }


    private static class Node<T> {
        T element;
        Node<T> next;
    }

}
