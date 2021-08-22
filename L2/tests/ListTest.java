import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ListTest {
    private Random rand;
    private List<Integer> empty, one, three, big;

    @Before
    public void setUp() {
        rand = new Random(1);
        empty = new EmptyL<>();
        one = new NodeL<>(3, new EmptyL<>());
        three = new NodeL<>(3, new NodeL<>(3, new NodeL<>(3, new EmptyL<>())));
        big = new EmptyL();
        for (int i=0; i<rand.nextInt(10000); i++) {
            big = new NodeL(rand.nextInt(), big);
        }
    }

    @After
    public void tearDown() {
        rand = null;
        empty = null;
        one = null;
        three = null;
        big = null;
    }

    @Test
    public void isEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(one.isEmpty());
        assertFalse(three.isEmpty());
    }

    @Test
    public void isSingleton() {
        assertFalse(empty.isSingleton());
        assertTrue(one.isSingleton());
        assertFalse(three.isSingleton());
    }

    @Test(expected = EmptyListE.class)
    public void getFirst1() throws Exception {
        empty.getFirst();
    }

    @Test
    public void getFirst2() throws Exception {
        assertEquals(3 , (int) one.getFirst());
        assertEquals(3, (int) three.getFirst());
    }

    @Test(timeout = 1) // one millisecond
    public void getFirst3() throws Exception {
        big.getFirst();
    }

    @Test
    public void getRest() throws Exception{
        assertEquals(three, one.append(three).getRest());
        assertEquals(one, one.append(one).getRest());
    }

    @Test(expected = EmptyListE.class)
    public void getError() throws EmptyListE {
        assertEquals(new EmptyListE(), empty.get(4));
        assertEquals(new EmptyListE(), big.get(4));
    }
    @Test
    public void get() throws EmptyListE {
        assertEquals((int) 3, (int) one.get(0));
        assertEquals((int) 3, (int) three.get(2));
    }

    @Test
    public void length() {
        assertEquals(0,empty.length());
        assertEquals(1,one.length());
        assertEquals(3,three.length());
        assertEquals(2,one.append(one).length());
        assertEquals(4,three.append(one).length());
        assertEquals(6,three.append(three).length());
    }

    @Test
    public void append() throws Exception{

        List<Integer> temp1 = one.append(one);
        Integer[] check1 = {3, 3};
        for(int i = 0; i < temp1.length(); i++) {
            assertEquals(temp1.get(i), check1[i]);
        }
        List<Integer> temp2 = three.append(one);
        Integer[] check2 = {3, 3, 3, 3};
        for(int j = 0; j < temp2.length(); j++){
            assertEquals(temp2.get(j),check2[j]);
        }
    }

    @Test
    public void contains() {
        assertFalse(empty.contains(1));
        assertTrue((one.contains(3)));
        assertFalse(three.contains(2));

    }
}