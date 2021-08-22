import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EventListener;

import static org.junit.Assert.*;

public class DequeListTest {
    DequeList<Integer> list1 = new DequeList<>();
    DequeList<Integer> list2 = new DequeList<>();
    DequeList<String> list3 = new DequeList<>();
    DequeList<String> list4 = new DequeList<>();

    @Before
    public void setUp() throws Exception {
        //set up
        list1.addFirst(-2);
        list2.addFirst(14);
        list3.addFirst("Y");
        list4.addFirst("B");
        list1.addLast(8);
        list1.addLast(24);
        list2.addLast(15);
        list3.addLast("W");
        list3.addLast("Z");
        list4.addLast("T");
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
    public void addFirst() throws NoSuchElementE {
        assertEquals((int) -2,(int) list1.getFirst());
        assertEquals((int) 14,(int) list2.getFirst());
        assertEquals("Y",list3.getFirst());
        assertEquals("B",list4.getFirst());
    }

    @Test
    public void addLast() throws NoSuchElementE {
        assertEquals((int) 24,(int) list1.getLast());
        assertEquals((int) 15,(int) list2.getLast());
        assertEquals("Z", list3.getLast());
        assertEquals("T", list4.getLast());
    }

    @Test
    public void getFirst() throws NoSuchElementE {
        assertEquals((int) -2,(int) list1.getFirst());
        assertEquals((int) 14,(int) list2.getFirst());
        assertEquals("Y",list3.getFirst());
        assertEquals("B",list4.getFirst());
    }

    @Test
    public void getLast() throws NoSuchElementE {
        assertEquals((int) 24,(int) list1.getLast());
        assertEquals((int) 15,(int) list2.getLast());
        assertEquals("Z", list3.getLast());
        assertEquals("T", list4.getLast());
    }

    @Test
    public void size() {
        assertEquals( 3, list1.size());
        assertEquals(2, list2.size());
        assertEquals(3, list3.size());
        assertEquals(2, list4.size());
    }
}