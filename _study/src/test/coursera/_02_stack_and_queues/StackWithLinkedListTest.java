package coursera._02_stack_and_queues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackWithLinkedListTest {

    private StackWithLinkedList<Integer> stack;

    @Test
    void testStack() {
        stack = new StackWithLinkedList<>();
        assertTrue(stack.isEmpty());

        stack.push(1);
        assertFalse(stack.isEmpty());

        Integer pop = stack.pop();
        assertEquals(pop, 1);
        assertTrue(stack.isEmpty());
    }

    @Test
    void testStackOrder() {
        stack = new StackWithLinkedList<Integer>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        for (int i = 9; i >=0; i--) {
            assertEquals(stack.pop(), i);
        }
    }

}