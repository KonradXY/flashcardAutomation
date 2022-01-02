package excersises.linkedLists;

import org.junit.jupiter.api.Test;

class ReverseLinkedListTest {


    @Test
    public void test() {
        Node<Integer> one = new Node<>(1);
        one.next = new Node(2);
        one.next.next = new Node(3);
        one.next.next.next = new Node(4);

        Node<Integer> result = ReverseLinkedList.reversSubList(one, 1, 2);
        printList(result);

    }


    private void printList(Node<Integer> node) {
        while (node != null) {
            System.out.print(node.element + " - ");
            node = node.next;
        }
        System.out.print("end \n");
    }



}