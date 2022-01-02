package excersises.linkedLists;

public class ReverseLinkedList {

    public static Node<Integer> reversSubList(Node<Integer> head, int start, int finish) {  // 0 - based

        if (start == finish) return head;
        Node<Integer> iter = head;

        int i = 0;
        while (i < start) {
            iter = iter.next;
            i++;
        }
        Node<Integer> tailFirstList = iter;

        while (i != finish) {
            iter = iter.next;
            i++;
        }
        Node<Integer> headSecondList = iter;

        Node<Integer> headToReverse = tailFirstList.next;


        return null;

    }

    private Node<Integer> reverseList(Node<Integer> head) {
        Node<Integer> current = head;
        Node<Integer> prev = null, next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }


}
