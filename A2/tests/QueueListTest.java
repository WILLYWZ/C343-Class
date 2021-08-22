import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueListTest {
    QueueList<Integer> list1 = new QueueList<>();
    QueueList<Integer> list2 = new QueueList<>();
    QueueList<String> list3 = new QueueList<>();
    QueueList<String> list4 = new QueueList<>();
    @Before
    public void setUp() throws Exception {
        list1.offer(-64);
        list2.offer(51);
        list3.offer("A");
        list4.offer("D");
        list1.offer(8);
        list1.offer(24);
        list2.offer(15);
        list3.offer("W");
        list3.offer("Z");
        list4.offer("T");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void clear() {
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        assertEquals(0,list1.size());
        assertEquals(0,list2.size());
        assertEquals(0,list3.size());
        assertEquals(0,list4.size());
    }

    @Test
    public void offer() throws NoSuchElementE {
        assertEquals((int) -64,(int) list1.poll());
        assertEquals((int) 51,(int) list2.poll());
        assertEquals("A", list3.poll());
        assertEquals("D", list4.poll());
    }

    @Test
    public void poll() throws NoSuchElementE {
        assertEquals((int) -64,(int) list1.poll());
        assertEquals((int) 51, (int) list2.poll());
        assertEquals("A",list3.poll());
        assertEquals("D",list4.poll());
    }

    @Test
    public void remove() throws NoSuchElementE {
        assertEquals((int) -64,(int) list1.remove());
        assertEquals((int) 51, (int) list2.remove());
        assertEquals("A",list3.remove());
        assertEquals("D",list4.remove());
    }

    @Test
    public void size() {
        assertEquals( 3, list1.size());
        assertEquals(2, list2.size());
        assertEquals(3, list3.size());
        assertEquals(2, list4.size());
    }
}
