import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    // create a test that ensures that each elementary statement of m is executed at least once
    public void m1() {
        assertEquals(20,Main.m(0,0));
        assertEquals(40,Main.m(10,0));
        assertEquals(40,Main.m(20,0));
        assertEquals(30,Main.m(0,10));
        assertEquals(0,Main.m(0,20));
        assertEquals(10,Main.m(0,30));
    }

    @Test
    // create a test that ensures that each branch of m is executed at least once
    public void m2() {
	// below is an example of a test for m2
	// step through it and take note of what parts of the program is hits and doesn't hit
	// take note that this example test alone doesn't cover everything it should
        Main.m(0,20);
        // when x is smaller than ten, the while loop does not hit
        Main.m(31,30);
        Main.m(21,10);
        // in all conditions, if statement for y always run.

    }

    @Test
    // create a test that ensures that each branch of m is executed at least once
    // and each condition is exercised at least once
    public void m3() {        
	// below is an example of a test for m3  
	// step through it and take note of what parts of the program is hits and doesn't hit
	// take note that this example test alone doesn't cover everything it should
	assertEquals(-8,Main.m(21,10));
	//after two while loops, x is one and the while loop stops
    //in all conditions, if statement for y always run.

    }


    @Test
    // create a test that ensures that each path from start to end is executed at least once
    public void m4() {
	// below is an example of a test for m4  
	// step through it and take note of what parts of the program is hits and doesn't hit
	// take note that this example test alone doesn't cover everything it should
        assertEquals(0,Main.m(5,10));
        assertEquals(20,Main.m(0,0));
        assertEquals(40,Main.m(10,0));
        assertEquals(40,Main.m(20,0));
        assertEquals(30,Main.m(0,10));
        assertEquals(0,Main.m(0,20));
        assertEquals(10,Main.m(0,30));
        assertEquals(-18,Main.m(1,0));
        assertEquals(29,Main.m(8,33));
    }


}
