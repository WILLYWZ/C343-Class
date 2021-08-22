import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class MyDynamicArrayTest {
    private DynamicArray<Integer> d;

    @Before
    public void setUp() throws Exception {
        d = new DynamicArray<>(3);
    }

    @After
    public void tearDown() throws Exception {
        d = null;
    }

    @Test
    public void size() {
        assertEquals(0, d.size());
    }

    @Test
    public void one() throws NoSuchElementE {
        assertEquals(0, d.size());
        d.addFirst(5);
        d.addFirst(0);
        d.addFirst(16);
        d.addFirst(-1);
        System.out.println(Arrays.toString(d.getElements()));
        assertEquals(-1, (int) d.removeFirst());
        assertEquals(5, (int) d.removeLast());
        assertEquals(0, (int) d.removeLast());
        assertEquals(16, (int) d.removeLast());
        assertEquals(0, d.size());
        d.addLast(4);
        d.addLast(15);
        d.addFirst(8);
        d.addLast(20);
        d.addFirst(-8);
        System.out.println(Arrays.toString(d.getElements()));
        assertEquals(-8, (int) d.removeFirst());
        assertEquals(8, (int) d.removeFirst());
        assertEquals(4, (int) d.removeFirst());
        assertEquals(15, (int) d.removeFirst());
        assertEquals(20, (int) d.removeFirst());
    }

    @Test
    public void two() throws NoSuchElementE {
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addFirst(79);
        d.addLast(4);
        d.addLast(15);
        d.addFirst(8);
        d.addFirst(-8);
        d.addLast(-1);
        d.addLast(4);
        System.out.println(Arrays.toString(d.getElements()));

        assertEquals(10, d.size());
        assertEquals(12, d.getCapacity());
        assertEquals(4, (int) d.removeLast());
        assertEquals(-8, (int) d.removeFirst());
        assertEquals(-1, (int) d.removeLast());
        assertEquals(8, (int) d.removeFirst());
        assertEquals(15, (int) d.removeLast());
        assertEquals(79, (int) d.removeFirst());
        assertEquals(4, (int) d.removeLast());
        assertEquals(3, (int) d.removeFirst());
        assertEquals(2, (int) d.removeLast());
        assertEquals(1, (int) d.removeFirst());
        assertEquals(0, d.size());

    }

    @Test
    public void clear() {
        d.addLast(1);
        d.addLast(2);
        d.addFirst(79);
        d.addFirst(8);
        d.addLast(-1);
        d.addLast(4);
        d.clear();
        assertEquals(0, d.size());
    }

    @Test
    public void testSize() {
        d.addLast(1);
        d.addLast(2);
        d.addFirst(79);
        d.addFirst(8);
        d.addLast(-1);
        d.addLast(4);
        assertEquals(6, d.size());
    }

    @Test
    public void push() throws NoSuchElementE {
        d.push(34);
        d.push(1);
        d.push(5);
        d.push(64);
        assertEquals(64,(int)d.getFirst());
    }

    @Test
    public void peek() throws NoSuchElementE {
        d.push(34);
        d.push(1);
        d.push(5);
        d.push(64);
        d.push(7);
        assertEquals((int) 7, (int) d.peek());
    }

    @Test
    public void pop() throws NoSuchElementE {
        d.push(34);
        d.push(1);
        d.push(5);
        d.push(64);
        d.push(7);
        d.pop();
        d.pop();
        d.pop();
        d.pop();
        d.pop();
        assertEquals(0,d.size());
    }

    @Test
    public void offer() throws NoSuchElementE {
        d.offer(34);
        d.push(1);
        d.push(5);
        d.push(64);
        d.offer(7);
        assertEquals(7,(int) d.getLast());


    }

    @Test
    public void poll() throws NoSuchElementE {
        d.push(34);
        d.push(1);
        d.push(5);
        d.push(64);
        d.offer(7);
        assertEquals(64,(int) d.poll());

    }

    @Test
    public void remove() throws NoSuchElementE {
        d.push(34);
        d.push(1);
        d.push(5);
        d.push(64);
        d.push(7);
        d.remove();
        d.remove();
        d.remove();
        assertEquals(2,d.size());
    }
}
