package coursera._02_stack_and_queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueLinkedListTest {

    QueueLinkedList<Integer> queue;

    @Test
    public void testQueue() {
        queue = new QueueLinkedList<>();

        assertTrue(queue.isEmpty());

        queue.enqueue(1);
        assertFalse(queue.isEmpty());

        assertEquals(queue.dequeue(), 1);
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testQueue2() {
        queue = new QueueLinkedList<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i< 10; i++) {
            assertEquals(queue.dequeue(), i);
        }
    }

}