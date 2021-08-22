import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.*;

public class myHeapTest {

    @Test
    public void test() throws NoLeftChildE, NoRightChildE, NoParentE {
        ArrayList<Item> items = new ArrayList<>();

        Item item;

        item = new Item("a",  0);
        items.add(item);

        item = new Item("b",  1);
        items.add(item);

        item = new Item("c",  2);
        items.add(item);

        item = new Item("d",  3);
        items.add(item);

        item = new Item("e",  4);
        items.add(item);

        item = new Item("f",  5);
        items.add(item);

        item = new Item("g",  6);
        items.add(item);

        item = new Item("h",  7);
        items.add(item);

        item = new Item("i",  8);
        items.add(item);

        item = new Item("j",  9);
        items.add(item);


        BinaryHeap bhp = new BinaryHeap(items);
        TreePrinter.print(bhp.findMin());


        assertEquals(1, bhp.getLeftChildIndex(0));
        assertEquals(2, bhp.getRightChildIndex(0));

        assertEquals(9, bhp.getLeftChildIndex(4));
        assertEquals(10, bhp.getRightChildIndex(4));

        assertEquals(0, bhp.getParentIndex(1));
        assertEquals(0, bhp.getParentIndex(2));

        assertEquals(3, bhp.getParentIndex(8));
        assertEquals(2, bhp.getParentIndex(6));

        assertEquals(0, bhp.findMin().getValue());

        bhp.swap(7,8);
        bhp.swap(1,2);
        TreePrinter.print(bhp.findMin()); //success

        assertEquals(0, bhp.extractMin().getValue());

        bhp.moveDown(2);
        TreePrinter.print(bhp.findMin());
        assertEquals(1, bhp.extractMin().getValue());

        bhp.moveDown(2);
        TreePrinter.print(bhp.findMin());
        assertEquals(2, bhp.extractMin().getValue());
    }

}