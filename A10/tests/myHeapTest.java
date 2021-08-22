import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class myHeapTest {
    private WeakHeap wh1;
    @Before
    public void setUp () {
        wh1 = new WeakHeap();
        wh1.insert(new Item(47));
        wh1.insert(new Item(38));
        wh1.insert(new Item(56));
        wh1.insert(new Item(43));
        wh1.insert(new Item(81));
        wh1.insert(new Item(79));
        wh1.insert(new Item(68));
        wh1.insert(new Item(17));
        wh1.insert(new Item(213));
        wh1.insert(new Item(584));
        wh1.insert(new Item(762));
    }

    @Test
    public void checkOrder() {
        assertTrue(wh1.checkOrder());
        /*
              17_0
                └───────────────────────────────────────┐
                                                      38_1
                                    ┌───────────────────┴───────────────────┐
                                  43_3                                    56_2
                          ┌─────────┴─────────┐                   ┌─────────┴─────────┐
                        47_7                68_6                81_4                79_5
                                                             ┌────┴────┐         ┌────┘
                                                           213_8     584_9    762_10
         */
        TreePrinter.print(wh1.findMin());
    }

    @Test
    public void getParentIndex() throws RootE {
        assertEquals(79, wh1.getValue(wh1.getParentIndex(10)));
        assertEquals(81, wh1.getValue(wh1.getParentIndex(9)));
        assertEquals(81, wh1.getValue(wh1.getParentIndex(8)));
        assertEquals(43, wh1.getValue(wh1.getParentIndex(7)));
        assertEquals(43, wh1.getValue(wh1.getParentIndex(6)));
        assertEquals(56, wh1.getValue(wh1.getParentIndex(5)));
        assertEquals(56, wh1.getValue(wh1.getParentIndex(4)));
        assertEquals(38, wh1.getValue(wh1.getParentIndex(3)));
        assertEquals(38, wh1.getValue(wh1.getParentIndex(2)));
        assertEquals(17, wh1.getValue(wh1.getParentIndex(1)));
    }

    @Test
    public void getLeftChildIndex() throws NoLeftChildE {
        assertEquals(43, wh1.getValue(wh1.getLeftChildIndex(1)));
        assertEquals(81, wh1.getValue(wh1.getLeftChildIndex(2)));
        assertEquals(47, wh1.getValue(wh1.getLeftChildIndex(3)));
        assertEquals(213, wh1.getValue(wh1.getLeftChildIndex(4)));
        assertEquals(762, wh1.getValue(wh1.getLeftChildIndex(5)));
    }

    @Test
    public void getRightChildIndex() throws NoRightChildE {
        assertEquals(38, wh1.getValue(wh1.getRightChildIndex(0)));
        assertEquals(56, wh1.getValue(wh1.getRightChildIndex(1)));
        assertEquals(79, wh1.getValue(wh1.getRightChildIndex(2)));
        assertEquals(68, wh1.getValue(wh1.getRightChildIndex(3)));
        assertEquals(584, wh1.getValue(wh1.getRightChildIndex(4)));
    }

    @Test
    public void isLeftChild() throws RootE {
        assertFalse(wh1.isLeftChild(1));
        assertFalse(wh1.isLeftChild(2));
        assertTrue(wh1.isLeftChild(3));
        assertTrue(wh1.isLeftChild(4));
        assertFalse(wh1.isLeftChild(5));
        assertFalse(wh1.isLeftChild(6));
        assertTrue(wh1.isLeftChild(7));
        assertTrue(wh1.isLeftChild(8));
        assertFalse(wh1.isLeftChild(9));
        assertTrue(wh1.isLeftChild(10));
    }

    @Test
    public void isRightChild() throws RootE {
        TreePrinter.print(wh1.findMin());
        assertTrue(wh1.isRightChild(1));
        assertTrue(wh1.isRightChild(2));
        assertFalse(wh1.isRightChild(3));
        assertFalse(wh1.isRightChild(4));
        assertTrue(wh1.isRightChild(5));
        assertTrue(wh1.isRightChild(6));
        assertFalse(wh1.isRightChild(7));
        assertFalse(wh1.isRightChild(8));
        assertTrue(wh1.isRightChild(9));
        assertFalse(wh1.isRightChild(10));

    }

    @Test
    public void getDAncestorIndex() throws RootE {
        assertEquals(0, wh1.getDAncestorIndex(1));
        assertEquals(1, wh1.getDAncestorIndex(2));
        assertEquals(0, wh1.getDAncestorIndex(3));
        assertEquals(1, wh1.getDAncestorIndex(4));
        assertEquals(2, wh1.getDAncestorIndex(5));
        assertEquals(3, wh1.getDAncestorIndex(6));
        assertEquals(0, wh1.getDAncestorIndex(7));
        assertEquals(1, wh1.getDAncestorIndex(8));
        assertEquals(4, wh1.getDAncestorIndex(9));
        assertEquals(2, wh1.getDAncestorIndex(10));
    }

    @Test
    public void getLeftMostChildIndex() throws NoLeftChildE {
        assertEquals(7,wh1.getLeftMostChildIndex());
    }

    @Test
    public void swap() {
        assertEquals(56, wh1.getValue(2));
        assertEquals(584, wh1.getValue(9));
        wh1.swap(9,2);
        assertEquals(584, wh1.getValue(2));
        assertEquals(56, wh1.getValue(9));
    }

    @Test
    public void join() {
        assertTrue(wh1.join(0,7));
        wh1.getElem(0).setValue(857);
        assertFalse(wh1.join(0,7));
        assertEquals(47, wh1.getValue(0));
        assertEquals(38, wh1.getValue(1));
        assertEquals(56, wh1.getValue(2));
        assertEquals(43, wh1.getValue(3));
        assertEquals(81, wh1.getValue(4));
        assertEquals(79, wh1.getValue(5));
        assertEquals(68, wh1.getValue(6));
        assertEquals(857, wh1.getValue(7));
        assertEquals(213, wh1.getValue(8));
        assertEquals(584, wh1.getValue(9));
        assertEquals(762, wh1.getValue(10));

        wh1.getElem(2).setValue(72);
        assertEquals(47, wh1.getValue(0));
        assertEquals(38, wh1.getValue(1));
        assertEquals(72, wh1.getValue(2));
        assertEquals(43, wh1.getValue(3));
        assertEquals(81, wh1.getValue(4));
        assertEquals(79, wh1.getValue(5));
        assertEquals(68, wh1.getValue(6));
        assertEquals(857, wh1.getValue(7));
        assertEquals(213, wh1.getValue(8));
        assertEquals(584, wh1.getValue(9));
        assertEquals(762, wh1.getValue(10));
    }

    @Test
    public void weakHeapify() {
        Random r = new Random(1);
        ArrayList<Item> items = new ArrayList<>();
        for (int i=0; i<18; i++) {
            items.add(new Item(r.nextInt(10)));
        }
        WeakHeap wh = new WeakHeap(items);
        TreePrinter.print(wh.findMin());
        assertEquals(2, wh.getValue(0));
        assertEquals(2, wh.getValue(1));
        assertEquals(3, wh.getValue(2));
        assertEquals(3, wh.getValue(3));
        assertEquals(4, wh.getValue(4));
        assertEquals(7, wh.getValue(5));
        assertEquals(7, wh.getValue(6));
        assertEquals(4, wh.getValue(7));
        assertEquals(2, wh.getValue(8));
        assertEquals(8, wh.getValue(9));
        assertEquals(9, wh.getValue(10));
        assertEquals(4, wh.getValue(11));
        assertEquals(8, wh.getValue(12));
        assertEquals(4, wh.getValue(13));
        assertEquals(3, wh.getValue(14));
        assertEquals(6, wh.getValue(15));
        assertEquals(5, wh.getValue(16));
        assertEquals(8, wh.getValue(17));

        assertEquals(0, wh.getFlip(2));
        assertEquals(1, wh.getFlip(3));
        assertEquals(0, wh.getFlip(4));
        assertEquals(1, wh.getFlip(5));
        assertTrue(wh.checkOrder());
    }

    @Test
    public void moveUp() {
        wh1.getElem(9).setValue(0);
        wh1.moveUp(9);
        assertEquals(0, wh1.getValue(0));
        assertEquals(17, wh1.getValue(1));
        assertEquals(56, wh1.getValue(2));
        assertEquals(43, wh1.getValue(3));
        assertEquals(38, wh1.getValue(4));
        assertEquals(79, wh1.getValue(5));
        assertEquals(68, wh1.getValue(6));
        assertEquals(47, wh1.getValue(7));
        assertEquals(0,wh1.getFlip(1));
        assertEquals(1,wh1.getFlip(3));
        assertTrue(wh1.checkOrder());

        wh1.getElem(4).setValue(8);
        wh1.moveUp(4);
        assertEquals(0, wh1.getValue(0));
        assertEquals(17, wh1.getValue(1));
        assertEquals(56, wh1.getValue(2));
        assertEquals(43, wh1.getValue(3));
        assertEquals(8, wh1.getValue(4));
        assertEquals(79, wh1.getValue(5));
        assertEquals(68, wh1.getValue(6));
        assertEquals(47, wh1.getValue(7));
        assertEquals(0,wh1.getFlip(1));
        assertEquals(1,wh1.getFlip(3));
        assertTrue(wh1.checkOrder());
    }

    @Test
    public void insert() {
        wh1.insert(new Item(280));
        assertTrue(wh1.checkOrder());
        wh1.insert(new Item(171));
        assertTrue(wh1.checkOrder());
        wh1.insert(new Item(123));
        assertTrue(wh1.checkOrder());
        wh1.insert(new Item(413));
        assertTrue(wh1.checkOrder());
        wh1.insert(new Item(914));
        assertTrue(wh1.checkOrder());
    }

    @Test
    public void extractMin() {
        WeakHeap wh = new WeakHeap();
        wh.insert(new Item(5));
        wh.insert(new Item(71));
        wh.insert(new Item(34));
        wh.insert(new Item(11));
        wh.insert(new Item(65));
        wh.insert(new Item(640));
        wh.insert(new Item(127));
        wh.insert(new Item(105));
        wh.insert(new Item(275));
        wh.insert(new Item(950));
        wh.insert(new Item(355));
        wh.insert(new Item(70));
        wh.insert(new Item(18));
        wh.insert(new Item(44));
        wh.checkOrder();

        wh.extractMin();
        assertEquals(11, wh.getValue(0));
        assertEquals(34, wh.getValue(1));
        assertEquals(65, wh.getValue(2));
        assertEquals(71, wh.getValue(3));
        assertEquals(127, wh.getValue(4));
        assertEquals(70, wh.getValue(5));
        assertEquals(44, wh.getValue(6));
        assertEquals(105, wh.getValue(7));
        assertEquals(275, wh.getValue(8));
        assertEquals(950, wh.getValue(9));
        assertEquals(355, wh.getValue(10));
        assertEquals(640, wh.getValue(11));
        assertEquals(18, wh.getValue(12));

        assertEquals(1,wh.getFlip(1));
        assertEquals(1,wh.getFlip(2));
        assertEquals(0,wh.getFlip(8));
    }
}