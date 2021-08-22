import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.*;

public class MyHashTableTest {

    @Test
    public void insert_delete_searchTest() {
        HashFunction test = new HashMod(10);
        HashTable one = new HashSeparateChaining(test);
        one.insert(-18);
        one.insert(6);
        one.insert(67);
        one.insert(1);
        one.insert(-47);
        one.insert(3);
        one.insert(55);
        one.insert(0);
        one.delete(6);
        one.delete(67);
        one.delete(1);
        one.delete(3);

        assertFalse(one.search(6));
        assertFalse(one.search(67));
        assertFalse(one.search(1));
        assertFalse(one.search(3));
        assertTrue(one.search(0));
        assertTrue(one.search(-47));
        assertTrue(one.search(55));
        assertTrue(one.search(-18));
    }

    @Test
    public void rehashTest() {
        HashFunction hf = new HashMod(4);
        HashTable ht = new HashLinearProbing(hf);
        ht.insert(-89);
        ht.insert(7078);
        ht.insert(539);
        ht.insert(575);
        ht.insert(-69);
        assertEquals(11,hf.getBound());
    }

    @Test
    public void quadTest() {
        HashFunction hf = new HashMod(3);
        HashTable ht = new HashQuadProbing(hf);
        ht.insert(-89);
        ht.insert(70);
        ht.insert(539);
        ht.insert(575);
        ht.insert(19);

        ht.insert(128);
        assertEquals(7,hf.getBound());
    }
    @Test
    public void doubleTest() {
        HashFunction hf = new HashMod(3);
        HashFunction hf2 = new HashModThen(5, h -> 5 - h);
        HashTable ht = new HashDouble(hf,hf2);
        ht.insert(-19);
        ht.insert(23);
        ht.insert(7);
        ht.insert(2);
        ht.insert(15);
        ht.insert(30);
        assertEquals(17,hf.getBound());
    }
}