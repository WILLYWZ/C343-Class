import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

//All my List changes is in setUp()
public class StackListTest {
    StackList<Integer> list1 = new StackList<>();
    StackList<Integer> list2 = new StackList<>();
    StackList<String> list3 = new StackList<>();
    StackList<String> list4 = new StackList<>();

    @Before
    public void setUp() throws Exception {
        list1.push(-2);
        list2.push(14);
        list3.push("Y");
        list4.push("B");
        list1.push(8);
        list1.push(24);
        list2.push(15);
        list3.push("W");
        list3.push("Z");
        list4.push("T");
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
    public void empty() {
        list1.clear();
        assertTrue(list1.empty());
        assertFalse(list2.empty());
        assertFalse(list3.empty());
        assertFalse(list4.empty());
    }

    @Test
    public void peek() throws NoSuchElementE {
        assertEquals((int) 24, (int) list1.peek());
        assertEquals((int) 15, (int) list2.peek());
        assertEquals("Z",list3.peek());
        assertEquals("T",list4.peek());
    }

    @Test
    public void pop() throws NoSuchElementE {
        assertEquals((int) 24, (int) list1.peek());
        assertEquals((int) 15, (int) list2.peek());
        assertEquals("Z",list3.peek());
        assertEquals("T",list4.peek());

    }

    @Test
    public void push() throws NoSuchElementE {
        assertEquals((int) 24, (int) list1.peek());
        assertEquals((int) 15, (int) list2.peek());
        assertEquals("Z",list3.peek());
        assertEquals("T",list4.peek());

    }

    @Test
    public void size() {
        assertEquals(3,list1.size());
        assertEquals(2,list2.size());
        assertEquals(3,list3.size());
        assertEquals(2,list4.size());
    }
}