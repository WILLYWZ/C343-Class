import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiFunction;

// -------------------------------------------------------

/**
 *
 * The abstract class for the four hash tables we will implement. The
 * file HashTableTest has four test cases that should produce the same
 * information as Figures 5.5, 5.11, 5.13, and 5.18 in the book. You
 * should implement many more test cases!!!
 *
 */
abstract class HashTable {
    abstract int getCapacity();
    abstract void setCapacity(int capacity);

    abstract void insert (int key);
    abstract void delete (int key);
    abstract boolean search (int key);

    abstract void rehash ();
}

// -------------------------------------------------------

/**
 *
 * An implementation of a hash table that uses separate chaining. The
 * constructor should take one arguments 'hf' of type HashFunction.
 * The bound should be extracted from the HashFunction and an ArrayList
 * of that bound should be created in the constructor. Each
 * entry in that ArrayList should be initialized to an empty linked list.
 * The three methods to insert, delete, and search
 * should be implemented as described in the book. You should also
 * implement a method rehash that resizes the hash table to the next prime
 * after doubling the capacity.
 */
class HashSeparateChaining extends HashTable {
    // finish the implementation and remove the abstract annotation

    private int capacity;
    private HashFunction hf;
    private ArrayList<LinkedList<Integer>> chains;

    HashSeparateChaining (HashFunction hf) {
        this.capacity = hf.getBound();
        this.hf = hf;
        this.chains = new ArrayList<>(capacity);
        for (int i=0; i<capacity; i++)
            chains.add(i, new LinkedList<>());
    }

    public String toString () {
        String result = "";
        for (int i=0; i<capacity; i++) {
            result += "chain[" + i + "] = ";
            result += chains.get(i).toString();
            result += "\n";
        }
        return result;
    }

    int getCapacity(){
        return capacity;
    }

    void setCapacity(int capacity){
        this.capacity = capacity;
    }

    void insert (int key){
        int hash = hf.apply(key);
        chains.get(hash).add(Integer.valueOf(key));
    }

    void delete (int key){
        int hash = hf.apply(key);
        chains.get(hash).remove(Integer.valueOf(key));
    }

    boolean search (int key){
        int hash = hf.apply(key);
        return chains.get(hash).contains(Integer.valueOf(key));
    }

    void rehash (){
        capacity = BigInteger.valueOf(capacity*2).nextProbablePrime().intValue();
        hf.setBound(capacity);
        ArrayList<LinkedList<Integer>> rehash = new ArrayList<>(capacity);

        for(int i = 0; i < capacity; i++){
            rehash.add(i,new LinkedList<>());
        }

        for(int j = 0; j < chains.size(); j++) {
            LinkedList<Integer> key = chains.get(j);
            for (Integer l : key) {
                int hash = hf.apply(l);
                rehash.get(hash).add(l);
            }
        }
        chains = rehash;
    }
}

// -------------------------------------------------------

/**
 *
 * Before implementing the next three variants of hash tables, we will
 * implement a small class hierarchy of hash tables entries. There are
 * three kinds of entries: an entry that contains an 'int' value, an
 * entry that is empty and hence available, and an entry that is
 * marked as deleted. A deleted entry is available for insertions but
 * cannot be marked as empty. See the book for details.
 *
 */

abstract class Entry {
    static Entry EMPTY = new Empty();
    static Entry DELETED = new Deleted();
    abstract boolean available ();
}

class Empty extends Entry {
    boolean available () { return true; }
    public String toString () { return ""; }
}

class Deleted extends Entry {
    boolean available () { return true; }
    public String toString () { return "X"; }
}

class Value extends Entry {
    private int i;
    Value (int i) { this.i = i; }
    int get () { return this.i; }
    boolean available () { return false; }
    public String toString () { return String.valueOf(this.i); }
}

// -------------------------------------------------------

/**
 *
 * This class, although abstract, will have a constructor and an
 * implementation of each of the three methods: insert, delete, and
 * search.
 *
 * The constructor should take two arguments: an
 * argument 'hf' of type HashFunction, and an argument 'f' of type
 * BiFunction<Integer,Integer,Integer>. The constructor should create
 * an ArrayList of the given size and initialize each slot in that
 * array with an Empty entry. The constructor should also define a
 * BiFunction<Integer,Integer,Integer> called 'dhf' as follows:
 *
 *   this.dhf = (key,index) -> (hf.apply(key) + f.apply(key,index)) % size;
 *
 * This auxiliary hash function applies the regular hash function 'hf'
 * and then modifies the result using the BiFunction 'f' that depends
 * on the current index in the hash table. The subclasses for linear
 * probing, quadratic probing, and double hashing, will each pass an
 * appropriate function 'f' to control the auxiliary function.
 *
 * Each of the methods insert, delete, and search takes a value 'key'
 * to process. Each of the methods will involve a loop that iterates
 * over all the positions in the ArrayList. At iteration 'i', an
 * integer position is calculated using:
 *
 *   int h = dhf.apply(key,i)
 *
 * For insert: if the position 'h' is available then the value 'key'
 * is stored.
 *
 * For delete: if position 'h' has an entry of kind 'Value' and if the
 * stored integer is identical to 'key' then the entry is marked as
 * deleted.
 *
 * For search: if position 'h' is Empty then the item is not found. If
 * position 'h' has an entry of kind 'Value' and if the stored value
 * is identical to 'key' then the item is found.
 *
 */

abstract class HashTableAux extends HashTable {
    private int capacity;
    private HashFunction hf;
    private BiFunction<Integer,Integer,Integer> f;
    private BiFunction<Integer,Integer,Integer> dhf;
    private ArrayList<Entry> slots;

    HashTableAux (HashFunction hf, BiFunction<Integer,Integer,Integer> f) {
        this.capacity = hf.getBound();
        this.hf = hf;
        this.f = f;
        this.dhf = (key,index) -> Math.floorMod(hf.apply(key) + f.apply(key,index), capacity);
        this.slots = new ArrayList<>(capacity);
        for (int i = 0; i< capacity; i++) this.slots.add(i, Entry.EMPTY);
    }

    // finish the implementation

    public String toString () {
        String result = "";
        for (int i = 0; i< capacity; i++) {
            result += "entry[" + i + "] = ";
            result += slots.get(i).toString();
            result += "\n";
        }
        return result;
    }

    int getCapacity(){
        return capacity;
    }

    void setCapacity(int capacity){
        capacity = this.capacity;
    }

    void insert (int key){
        for (int i=0; i<capacity; i++) {
            int pos = dhf.apply(key,i);
            if (slots.get(pos).available()) {
                slots.set(pos, new Value(key));
                return;
            }
        }
        rehash();
        insert(key);
    }

    void delete (int key){
        for (int i=0; i<capacity; i++) {
            int pos = dhf.apply(key,i);
            if (slots.get(pos) instanceof Empty){
                break;
            }
            if (!slots.get(pos).available()) {
                if(slots.get(pos).toString().equals(String.valueOf(key))){
                    slots.set(pos, new Deleted());
                    return;
                }
            }
        }
    }

    boolean search (int key) {
        for (int i = 0; i < capacity; i++) {
            int pos = dhf.apply(key, i);
            if (slots.get(pos) instanceof Empty){
                break;
            }
            if (slots.get(pos).toString().equals(String.valueOf(key))) {
                return true;
            }
        }
        return false;
    }

    void rehash() {
        ArrayList<Entry> rehash = slots;
        slots = new ArrayList<Entry>();
        capacity = BigInteger.valueOf(capacity*2).nextProbablePrime().intValue();
        hf.setBound(capacity);

        for(int i = 0; i < capacity; i++){
            this.slots.add(Entry.EMPTY);
        }
        for (Entry j : rehash){
            if ( j instanceof Value){
                for (int i=0; i<capacity; i++) {
                    int pos = dhf.apply(((Value)j).get(),i);
                    if (slots.get(pos).available()) {
                        slots.set(pos, new Value(((Value) j).get()));
                        break;
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------

class HashLinearProbing extends HashTableAux {
    HashLinearProbing(HashFunction hf) {
        super(hf, (key,index) -> index);
    }
}

// -------------------------------------------------------


class HashQuadProbing extends HashTableAux {
    HashQuadProbing(HashFunction hf) {
        super(hf, (key,index) -> index*index);
    }
}


// -------------------------------------------------------

class HashDouble extends HashTableAux {

    HashDouble(HashFunction hf,HashFunction hf2) {
        super(hf, (key,index) -> index * hf2.apply(key));
    }

}
// -------------------------------------------------------
