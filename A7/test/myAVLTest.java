import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class myAVLTest {
    AVL avl;

    @Test
    public void AVLLeaf () throws EmptyAVLE {
        avl = AVL.AVLLeaf(11);

        assertEquals(11,avl.AVLData());
        assertTrue(avl.AVLLeft().isEmpty());
        assertTrue(avl.AVLRight().isEmpty());
        assertEquals(1, avl.AVLHeight());

    }

    @Test
    public void toAVL() {

        BST bst = BST.BSTLeaf(1).BSTinsert(2).BSTinsert(3).BSTinsert(4).BSTinsert(6).BSTinsert(2);
        AVL avl = BST.toAVL(bst);

        System.out.printf("%n%n%n");
        TreePrinter.print(bst);
        System.out.printf("%n%n%n");
        TreePrinter.print(avl);
    }

    @Test
    public void AVLfind() {
        AVL avl = AVL.AVLLeaf(5).AVLinsert(3).AVLinsert(6).AVLinsert(4).AVLinsert(0).AVLinsert(3);

        assertTrue(avl.AVLfind(5));
        assertTrue(avl.AVLfind(0));
        assertFalse(avl.AVLfind(7));
    }

    @Test
    public void AVLeasyRight() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(40);
        avl = avl.AVLinsert(50);
        avl = avl.AVLinsert(20);
        avl = avl.AVLinsert(10);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(25);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();

        assertEquals(20, avl.AVLData());
        assertEquals(10, left.AVLData());
        assertEquals(5, left.AVLLeft().AVLData());

        assertEquals(40, right.AVLData());
        assertEquals(25, right.AVLLeft().AVLData());
        assertEquals(50, right.AVLRight().AVLData());

        AVL check = AVL.AVLLeaf(40).AVLinsert(50).AVLinsert(20)
                .AVLinsert(10).AVLinsert(5).AVLinsert(25);
        System.out.printf("%n%n%n");
        TreePrinter.print(check);
        System.out.printf("%n%n%n");
    }

    @Test
    public void AVLrotateRight() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(3);
        avl = avl.AVLinsert(6);
        avl = avl.AVLinsert(4);
        avl = avl.AVLinsert(0);
        avl = avl.AVLinsert(3);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();

        assertEquals(4, avl.AVLData());
        assertEquals(3, left.AVLData());
        assertEquals(0, left.AVLLeft().AVLData());
        assertEquals(3, left.AVLRight().AVLData());

        assertEquals(5, right.AVLData());
        assertEquals(6, right.AVLRight().AVLData());

        AVL check = AVL.AVLLeaf(5).AVLinsert(3).AVLinsert(6)
                .AVLinsert(4).AVLinsert(0).AVLinsert(3);
        System.out.printf("%n%n%n");
        TreePrinter.print(check);
        System.out.printf("%n%n%n");
    }

    @Test
    public void AVLeasyleft() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(3);
        avl = avl.AVLinsert(2);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(4);
        avl = avl.AVLinsert(6);
        avl = avl.AVLinsert(5);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();

        assertEquals(5, avl.AVLData());
        assertEquals(3, left.AVLData());
        assertEquals(2, left.AVLLeft().AVLData());
        assertEquals(4, left.AVLRight().AVLData());

        assertEquals(6, right.AVLData());
        assertEquals(5, right.AVLLeft().AVLData());

        AVL check = AVL.AVLLeaf(3).AVLinsert(2).AVLinsert(5)
                .AVLinsert(4).AVLinsert(6).AVLinsert(5);
        System.out.printf("%n%n%n");
        TreePrinter.print(check);
        System.out.printf("%n%n%n");

    }

    @Test
    public void AVLrotateleft() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(3);
        avl = avl.AVLinsert(2);
        avl = avl.AVLinsert(5);
        avl = avl.AVLinsert(4);
        avl = avl.AVLinsert(3);
        avl = avl.AVLinsert(6);

        AVL left = avl.AVLLeft();
        AVL right = avl.AVLRight();

        assertEquals(4, avl.AVLData());
        assertEquals(3, left.AVLData());
        assertEquals(2, left.AVLLeft().AVLData());
        assertEquals(3, left.AVLRight().AVLData());

        assertEquals(5, right.AVLData());
        assertEquals(6, right.AVLRight().AVLData());

        AVL check = AVL.AVLLeaf(3).AVLinsert(2).AVLinsert(5)
                .AVLinsert(4).AVLinsert(3).AVLinsert(6);
        System.out.printf("%n%n%n");
        TreePrinter.print(check);
        System.out.printf("%n%n%n");
    }

    @Test
    public void AVLdelete() throws EmptyAVLE {
        avl = AVL.EAVL;
        avl = avl.AVLinsert(98);
        avl = avl.AVLinsert(78);
        avl = avl.AVLinsert(32);
        avl = avl.AVLinsert(108);
        avl = avl.AVLinsert(36);
        avl = avl.AVLinsert(7);
        avl = avl.AVLinsert(81);
        avl = avl.AVLinsert(92);

        AVL l1 = avl.AVLdelete(78);
        AVL left = l1.AVLLeft();
        AVL right = l1.AVLRight();

        assertEquals(36, l1.AVLData());
        assertEquals(32,left.AVLData());
        assertEquals(7, left.AVLLeft().AVLData());

        assertEquals(98,right.AVLData());
        assertEquals(81, right.AVLLeft().AVLData());
        assertEquals(92, right.AVLLeft().AVLRight().AVLData());
        assertEquals(108, right.AVLRight().AVLData());

        AVL check = AVL.AVLLeaf(98).AVLinsert(78).AVLinsert(32)
                .AVLinsert(108).AVLinsert(36).AVLinsert(7).AVLinsert(81).AVLinsert(92);
        System.out.printf("%n%n%n");
        TreePrinter.print(check.AVLdelete(78));
        System.out.printf("%n%n%n");

        AVL l2 = l1.AVLdelete(98);
        AVL lft = l2.AVLLeft();
        AVL rht = l2.AVLRight();

        assertEquals(36, l2.AVLData());
        assertEquals(32,lft.AVLData());
        assertEquals(7, lft.AVLLeft().AVLData());

        assertEquals(92,rht.AVLData());
        assertEquals(81, rht.AVLLeft().AVLData());
        assertEquals(108, rht.AVLRight().AVLData());

        System.out.printf("%n%n%n");
        TreePrinter.print(check.AVLdelete(78).AVLdelete(98));
        System.out.printf("%n%n%n");
    }
}

