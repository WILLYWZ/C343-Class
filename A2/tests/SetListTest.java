import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//All my List changes is in setUp()
public class SetListTest {
    SetList<Integer> list1 = new SetList<>();
    SetList<Integer> list2 = new SetList<>();
    SetList<String> list3 = new SetList<>();
    SetList<String> list4 = new SetList<>();

    @Before
    public void setUp() throws Exception {
        list1.add(-2);
        list2.add(14);
        list3.add("Y");
        list4.add("B");
        list1.add(8);
        list1.add(24);
        list2.add(15);
        list3.add("W");
        list3.add("Z");
        list4.add("T");
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
    public void isEmpty() {
        list1.clear();
        assertTrue(list1.isEmpty());
        assertFalse(list2.isEmpty());
        assertFalse(list3.isEmpty());
        assertFalse(list4.isEmpty());
    }

    @Test
    public void add() {
        assertTrue(list1.contains(8));
        assertFalse(list2.contains(13));
        assertTrue(list3.contains("W"));
        assertTrue(list4.contains("T"));

    }

    @Test
    public void contains() {
        assertTrue(list1.contains(24));
        assertFalse(list2.contains(13));
        assertTrue(list3.contains("W"));
        assertTrue(list4.contains("T"));
    }

    @Test
    public void size() {
        assertEquals(3,list1.size());
        assertEquals(2,list2.size());
        assertEquals(3,list3.size());
        assertEquals(2,list4.size());
    }
}