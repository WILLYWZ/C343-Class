import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class myBSTTest {
    BST bst;

    @Test
    public void toBST() {

        AVL avl = AVL.AVLLeaf(5).AVLinsert(3).AVLinsert(6).AVLinsert(4).AVLinsert(0).AVLinsert(3);
        BST bst = AVL.toBST(avl);

        System.out.printf("%n%n%n");
        TreePrinter.print(avl);
        System.out.printf("%n%n%n");
        TreePrinter.print(bst);
    }

    @Test
    public void BSTLeaf () throws EmptyBSTE {
        bst = BST.BSTLeaf(11);

        assertEquals(11,bst.BSTData());
        assertTrue(bst.BSTLeft().isEmpty());
        assertTrue(bst.BSTRight().isEmpty());
        assertEquals(1, bst.BSTHeight());

        Iterator<Integer> iter = bst.iterator();
        assertTrue(iter.hasNext());
        assertEquals(11, (int)iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void BSTfind() {

        bst = BST.BSTLeaf(1).BSTinsert(2).BSTinsert(3).BSTinsert(4).BSTinsert(6).BSTinsert(2);

        assertFalse(bst.BSTfind(5));
        assertTrue(bst.BSTfind(2));
        assertTrue(bst.BSTfind(6));
    }

    @Test
    public void BSTdelete() throws EmptyBSTE {
        int d = bst.BSTData();
        BST smallerBST = bst.BSTdelete(d);
        assertFalse(smallerBST.BSTfind(d));
        int leftMost = bst.BSTRight().BSTdeleteLeftMostLeaf().getFirst();
        assertEquals(leftMost,(int)smallerBST.BSTData());
    }

    @Test
    public void BSTdeleteLeftMostLeaf() throws EmptyBSTE {
        BST bst = BST.BSTLeaf(8).BSTinsert(62).BSTinsert(4).BSTinsert(3).BSTinsert(0).BSTinsert(9);
        System.out.printf("%n%n%n");
        TreePrinter.print(bst);
        Pair<Integer,BST> BSTPair = bst.BSTdeleteLeftMostLeaf();
        assertEquals(0,(int)BSTPair.getFirst());

    }

}