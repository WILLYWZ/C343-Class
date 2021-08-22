import java.util.ArrayList;
import java.util.List;

/**
 * Binary heap with reverse bits...
 * We can flip left and right subtrees in one operation
 *
 * There is a subtle interaction between the heap and the items it contains.
 * - the heap maintains an arraylist of all items
 * - each item has a reference to the heap and its position within the arraylist
 */

class NoParentE extends Exception {
}

class NoLeftChildE extends Exception {
}

class NoRightChildE extends Exception {
}

public class BinaryHeap {
    private int size;
    private ArrayList<Item> elems;

    BinaryHeap() {
        this.size = 0;
        this.elems = new ArrayList<>();
    }

    /**
     * This constructor performs "heapify". First it copy the incoming
     * elements one-by-one to the arraylist 'elems' stored as an instance variable.
     * For each item copied, the constructor should initialize properly using
     * setPosition and setHeap. When everything is properly initialized and
     * copied to 'elems' the constructor calls 'heapify'.
     */
    BinaryHeap(ArrayList<Item> es) {
        // TODO
        elems = new ArrayList<>();
        size = es.size();

        for (int i = 0; i < size; i++){
            Item temp = es.get(i);
            temp.setPosition(i);
            temp.setHeap(this);
            elems.add(temp);
        }
        heapify();
    }

    /**
     * Implement it as discussed in class...
     */
    void heapify () {
        // TODO
        int s = this.size /2 - 1;
        for (int i = s; i >= 0;  i--){
            moveDown(i);
        }
    }

    boolean isEmpty() {
        return size == 0;
    }

    int getSize() {
        return size;
    }

    /**
     * We will location 0 in the array. The minimum is always guaranteed to be there
     * unless of course the array is empty
     */
    Item findMin() {
        // TODO
        return elems.get(0);
    }

    List<Item> getElems() {
        return elems;
    }

    Item getElem (int i) {
        return elems.get(i);
    }

    /**
     * As discussed in class and in the notes, the parent is at index i/2
     * unless of course the current node is the root of the tree
     */
    int getParentIndex(int i) throws NoParentE {
        // TODO
        if (i >= 1) {
            return (i-1)/2;
        }else {
            throw new NoParentE();
        }
    }

    /**
     * Under normal circumstances the left child is at index 2i+1. It is possible
     * the index 2i+1 is outside of the array bounds and in that case the node
     * does not have a left child. It is also possible that the current element
     * has its reverse bit set, which means that the child at index 2i+1 is actually
     * the right child and the child at index 2i+2 is the left child.
     */
    int getLeftChildIndex(int i) throws NoLeftChildE {
        // TODO
        if (i >= size){
            throw new NoLeftChildE();
        }
        int value = 2*i+1+getElem(i).getRevbit();
        if(value < size) {
            return value;
        } else {
            throw new NoLeftChildE();
        }
    }

    int getRightChildIndex(int i) throws NoRightChildE {
        // TODO
        if (i >= size){
            throw new NoRightChildE();
        }
        int value = 2*i+2-getElem(i).getRevbit();
        if(value < size) {
            return value;
        } else {
            throw new NoRightChildE();
        }
    }

    /**
     * This method swaps the array entries at the given indices. It also needs
     * to update each element with its new position.
     */
    void swap(int i, int j) {
        // TODO
        Item entryI = elems.get(i);
        Item entryJ = elems.get(j);

        entryI.setPosition(j);
        entryJ.setPosition(i);

        elems.set(i,entryJ);
        elems.set(j,entryI);
    }

    int getValue(int i) {
        return elems.get(i).getValue();
    }

    /**
     * When an element is inserted, it is inserted in the bottom layer of the
     * tree and then moveUp is recursively called to move it to its proper
     * position.
     */
    void moveUp(int i) {
        // TODO
        try{
            int parentIndex = getParentIndex(i);
            if(getValue(parentIndex) > getValue(i)){
                swap(i,parentIndex);
                moveUp(parentIndex);
            }
        }
        catch(NoParentE e){

        }
    }

    void insert(Item ek) {
        // TODO
        size++;
        ek.setPosition(size-1);
        elems.add(ek);
        moveUp(size-1);
    }

    /**
     * When a large element finds itself high in the tree for some reason,
     * we need to move it down. For that we need to compare it to both its
     * children and swap it with the smaller of them
     */
    int minChildIndex(int i) throws NoLeftChildE {
        // TODO
        int leftIndex = getLeftChildIndex(i);
        try{
            int rightIndex = getRightChildIndex(i);
            if (getValue(leftIndex) < getValue(rightIndex)){
                return leftIndex;
            }
            return rightIndex;
        }
        catch (NoRightChildE e){
            return leftIndex;

        }
    }

    void moveDown(int i) {
        // TODO
        try{
            int minIndex = minChildIndex(i);
            if(getValue(minIndex) < getValue(i)){
                swap(i,minIndex);
                moveDown(minIndex);
            }
        }
        catch (NoLeftChildE e){

        }
    }

    /**
     * The minimum is at location 0. To remove it we take the last element
     * in the array and move it to location 0 and then recursively apply
     * moveDown.
     */
    Item extractMin() {
        // TODO
        Item min = elems.get(0);
        Item last = elems.get(size-1);
        elems.set(0,last);
        elems.get(0).setPosition(0);

        elems.remove(size-1);
        size--;
        moveDown(0);
        return min;
    }


    public String toString() {
        return getElems().toString();
    }
}
