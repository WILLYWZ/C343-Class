import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class myGraphTestC {
    @Test
    public void testGraph() {

        //        30        15
        //    A ------ B ------ C
        //     \   6 / \ 3    /
        //    8 \  / 24 \   / 16
        //       D ------ E
        //         \     /
        //       20 \  / 53
        //           F

        Item A, B, C, D, E, F;
        A = new Item("A", 0);
        B = new Item("B", 0);
        C = new Item("C", 0);
        D = new Item("D", 0);
        E = new Item("E", 0);
        F = new Item("F", 0);

        ArrayList<Item> nodes =
                new ArrayList<>(Arrays.asList(A, B, C, D, E, F));

        Hashtable<Item, ArrayList<Item>> neighbors = new Hashtable<>();

        //        30        15
        //    A ------ B ------ C
        //     \   6 / \ 3    /
        //    8 \  / 24 \   / 16
        //       D ------ E
        //         \     /
        //       20 \  / 53
        //           F

        neighbors.put(A, new ArrayList<>(Arrays.asList(B, D)));
        neighbors.put(B, new ArrayList<>(Arrays.asList(C, D, E)));
        neighbors.put(C, new ArrayList<>(Arrays.asList(E)));
        neighbors.put(D, new ArrayList<>(Arrays.asList(E, F)));
        neighbors.put(E, new ArrayList<>(Arrays.asList(F)));
        neighbors.put(F, new ArrayList<>());

        Edge e1, e2, e3, e4, e5, e6, e7, e8, e9;
        e1 = new Edge(A, B);
        e2 = new Edge(A, D);
        e3 = new Edge(B, C);
        e4 = new Edge(B, D);
        e5 = new Edge(B, E);
        e6 = new Edge(C, E);
        e7 = new Edge(D, E);
        e8 = new Edge(D, F);
        e9 = new Edge(E, F);

        Hashtable<Edge, Integer> weights = new Hashtable<>();
        weights.put(e1, 30);
        weights.put(e2, 8);
        weights.put(e3, 15);
        weights.put(e4, 6);
        weights.put(e5, 3);
        weights.put(e6, 16);
        weights.put(e7, 24);
        weights.put(e8, 20);
        weights.put(e9, 53);

        Graph g = new Graph(nodes, neighbors, weights);

        //        30        15
        //    A ------ B ------ C
        //     \   6 / \ 3    /
        //    8 \  / 24 \   / 16
        //       D ------ E
        //        \     /
        //      20 \  / 53
        //          F

        g.shortestPath(C, E);

        assertFalse(A.isVisited());
        assertFalse(B.isVisited());
        assertTrue(C.isVisited());
        assertFalse(D.isVisited());
        assertFalse(E.isVisited());
        assertFalse(F.isVisited());

        assertEquals(null, A.getPrevious());
        assertEquals(null, B.getPrevious());
        assertEquals(null, C.getPrevious());
        assertEquals(null, D.getPrevious());
        assertEquals(C, E.getPrevious());
        assertEquals(null, F.getPrevious());


        //        30        15
        //    A ------ B ------ C
        //     \   6 / \ 3    /
        //    8 \  / 24 \   / 16
        //       D ------ E
        //        \     /
        //      20 \  / 53
        //          F

        g.shortestPath(C, D);

        assertFalse(A.isVisited());
        assertFalse(B.isVisited());
        assertTrue(C.isVisited());
        assertFalse(D.isVisited());
        assertTrue(E.isVisited());
        assertTrue(F.isVisited());

        assertEquals(null, A.getPrevious());
        assertEquals(null, B.getPrevious());
        assertEquals(null, C.getPrevious());
        assertEquals(null, D.getPrevious());
        assertEquals(C, E.getPrevious());
        assertEquals(E, F.getPrevious());

        //        30        15
        //    A ------ B ------ C
        //     \   6 / \ 3    /
        //    8 \  / 24 \   / 16
        //       D ------ E
        //        \     /
        //      20 \  / 53
        //          F

        g.shortestPath(C, F);

        assertFalse(A.isVisited());
        assertFalse(B.isVisited());
        assertTrue(C.isVisited());
        assertFalse(D.isVisited());
        assertTrue(E.isVisited());
        assertFalse(F.isVisited());

        assertEquals(null, A.getPrevious());
        assertEquals(null, B.getPrevious());
        assertEquals(null, C.getPrevious());
        assertEquals(null, D.getPrevious());
        assertEquals(C, E.getPrevious());
        assertEquals(E, F.getPrevious());


    }
}
