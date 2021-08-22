
import java.util.Random;
import java.util.function.Function;

// -------------------------------------------------------

abstract class HashFunction implements Function<Integer,Integer> {
    abstract int getBound ();
    abstract void setBound (int bound);
}

// -------------------------------------------------------

/**
 *
 * An instance of this class is created with a parameter 'bound'. Then
 * every time the hash function is applied to an argument 'x', it
 * returns the value of 'x' modulo the 'bound'. See the test cases in
 * HashFunctionTest for some examples.
 *
 */
class HashMod extends HashFunction {
    // complete the implementation and remove the abstract annotation
    private int bound;
    public HashMod(int bound){
        this.bound = bound;
    }

    public int getBound(){
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public Integer apply (Integer x) {
        return Math.floorMod(x,bound);
    }
}

// -------------------------------------------------------

/**
 *
 * An instance of this class is created with two parameters: a first
 * argument 'bound' that is used to create an instance of the
 * superclass, and a second argument 'after' of type
 * Function<Integer,Integer> that is used as follows. When the hash
 * function is invoked, the basic functionality of the super class is
 * first invoked, and then that result is given to the function
 * 'after'. The result of 'after' is also wrapped around so that it does not exceed bound.
 * See the test cases in HashFunctionTest for some examples.
 *
 */
class HashModThen extends HashMod {

    private Function<Integer,Integer> after;
    private int bound;

    public HashModThen(int bound, Function<Integer, Integer> after) {
        super(bound);
        this.bound = bound;
        this.after = after;
    }

    public Integer apply (Integer x) {
        int superHash = super.apply(x);
        int result = after.apply(superHash);
        return Math.floorMod(result,getBound());
    }
}

// -------------------------------------------------------

/**
 *
 * An instance of this class is created with three parameters: a
 * random number generator 'r' of type Random and two integers 'p' and
 * 'm' where 'p' should be a prime number greater than or equal to
 * 'm'. Using the random number generator, the constructor generates
 * two random numbers 'a' and 'b' such that 'a' is in the range 1
 * (inclusive) and p (exclusive) and 'b' is in the range 0 (inclusive)
 * and p (exclusive). The resulting hash function should be a
 * universal hash function as explained in the book. Again see the
 * test cases in HashFunctionTest for some examples.
 *
 */
class HashUniversal extends HashFunction {

    public int p, m, a, b;
    public HashUniversal(Random r, int p, int m) {
        this.p = p;
        this.m = m;
        a = r.nextInt(p-1)+1;
        b = r.nextInt(p);
    }

    public int getBound() {
        return m;
    }

    public void setBound(int bound) {
        this.m = bound;
    }

    public Integer apply(Integer x){
        return (int) Math.floorMod(Math.floorMod(a * x + b,p),m);
    }
}

// -------------------------------------------------------
// -------------------------------------------------------
