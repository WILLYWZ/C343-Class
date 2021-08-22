import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {
    public List<Integer> list1 = new EmptyL<Integer>();
    public List<String> list2 = new NodeL<String>("Y", new EmptyL<String>());

    public List<String> list3 = new NodeL<String>("Y", new NodeL<String>("W",
            new EmptyL<String>()));

    public List<Integer> list4 = new NodeL<Integer>(1, new NodeL<Integer>(2,
            new EmptyL<Integer>()));

    public List<Integer> list5 = new NodeL<Integer>(6, new NodeL<Integer>(5,
            new NodeL<Integer>(4,
                    new NodeL<Integer>(3,
                            new NodeL<Integer>(2,
                                    new EmptyL<Integer>())))));

    //for getRest()
    public List<String> List3_x = new NodeL<String>("W", new EmptyL<String>());

    public List<String> List3 = new NodeL<String>("Y", List3_x);

    public List<Integer> List4_x =new NodeL<Integer>(2, new EmptyL<Integer>());

    public List<Integer> List4 = new NodeL<Integer>(1,List4_x);

    public List<Integer> List5_x = new NodeL<Integer>(5,
            new NodeL<Integer>(4,
                    new NodeL<Integer>(3,
                            new NodeL<Integer>(2,
                                    new EmptyL<Integer>()))));

    public List<Integer> List5 = new NodeL<Integer>(6, List5_x);




    @Test
    public void isEmpty() {
        assertEquals(true, list1.isEmpty());
        assertEquals(false, list2.isEmpty());
        assertEquals(false, list3.isEmpty());
        assertEquals(false, list4.isEmpty());
        assertEquals(false, list5.isEmpty());
    }

    @Test
    public void isSingleton() {
        assertEquals(false, list1.isSingleton());
        assertEquals(true, list2.isSingleton());
        assertEquals(false, list3.isSingleton());
        assertEquals(false, list4.isSingleton());
        assertEquals(false, list5.isSingleton());
    }

    @Test(expected = EmptyListE.class)
    public void getFirstError() throws EmptyListE{
        assertEquals(new EmptyListE(), list1.getFirst());
    }

    @Test
    public void getFirst() throws EmptyListE{
        assertEquals("Y", list2.getFirst());
        assertEquals("Y", list3.getFirst());
        assertEquals((int) 1, (int) list4.getFirst());
        assertEquals((int) 6, (int) list5.getFirst());
    }

    @Test(expected = EmptyListE.class)
    public void getRestError() throws EmptyListE{
        assertEquals(new EmptyListE(), list1.getRest());
        assertEquals(new EmptyListE(), list2.getRest());
    }

    @Test
    public void getRest() throws EmptyListE{
        assertEquals(List3_x, List3.getRest());
        assertEquals(List4_x, List4.getRest());
        assertEquals(List5_x, List5.getRest());
    }

    @Test(expected = EmptyListE.class)
    public void getError() throws EmptyListE {
        assertEquals(new EmptyListE(), list1.get(4));
        assertEquals(new EmptyListE(), list4.get(4));
    }
    @Test
    public void get() throws EmptyListE{
        assertEquals("Y", list2.get(0));
        assertEquals("W", list3.get(1));
        assertEquals((int) 2, (int)list5.get(4));
    }

    @Test
    public void length() {
        assertEquals(0, list1.length());
        assertEquals(1, list2.length());
        assertEquals(2, list3.length());
        assertEquals(2, list4.length());
        assertEquals(5, list5.length());
    }

    @Test
    public void append()throws EmptyListE{
        assertEquals(list4, list1.append(list4));
        assertEquals(list5, list1.append(list5));

        List<String> temp1 = list2.append(list2);
        String[] check1 = {"Y","Y","W"};
        for(int i = 0; i < temp1.length(); i++){
            assertEquals(temp1.get(i),check1[i]);
        }
        List<Integer> temp2 = list4.append(list5);
        Integer[] check2 = {1, 2, 6, 5, 4, 3, 2};
        for(int i = 0; i < temp2.length(); i++){
            assertEquals(temp2.get(i),check2[i]);
        }
    }

    @Test
    public void contains(){
        assertEquals(false, list1.contains(1));
        assertEquals(true, list2.contains("Y"));
        assertEquals(true, list3.contains("W"));
        assertEquals(true, list4.contains(1));
        assertEquals(false, list5.contains(1));
    }
}