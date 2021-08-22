import java.util.ArrayList;
import java.util.List;
//just checking

class RootE extends Exception {
}

class NoLeftChildE extends Exception {
}

class NoRightChildE extends Exception {
}

public class WeakHeap {
    private int size;
    private ArrayList<Item> elems;
    private ArrayList<Integer> flips;
    /*
     * Position 0 in elems is for the root.
     * The root has no left child.
     * Position 1 in elems is for the right child of the root
     * After that the left child of an item at position i is at position 2i+flips(i)
     * and the right child is at position 2i+1-flips(i)
     * The parent of a child at position i is at position i/2
     * flips(0) should never be set to 1 because that would give the root
     * a left child instead of a right one
     */

    WeakHeap() {
        this.size = 0;
        this.elems = new ArrayList<>();
        this.flips = new ArrayList<>();
    }

    /*
     * The following constructor does the following sequences of steps:
     *   - it copies the incoming items to a the array items
     *   - for each item, setPosition and setHeap are called with the
     *     appropriate parameters
     *   - for each item, a corresponding flip bit is initialized to 0
     *   - call weakHeapify
     */
    WeakHeap (ArrayList<Item> items) {
	    // TODO
        elems = new ArrayList<>();
        flips = new ArrayList<>();
        size = items.size();

        for (int i = 0; i < size; i++){
            Item temp = items.get(i);
            temp.setPosition(i);
            temp.setHeap(this);
            elems.add(temp);
            flips.add(0);
        }
        weakHeapify();
    }

    /*
     * This method executes a loop that starts with the last element
     * in the array and ends with the element at position 1. In each
     * iteration, the item is joined with its distinguished ancestor. 
     * Note that when calling join, the distinguished ancestor is 
     * always in the first argument position. 
     */
    void weakHeapify () {
	    // TODO
        try{
            for(int i = size -1; i > 0; i--){
                join(getDAncestorIndex(i),i);
            }
        }catch (RootE e){

        }
    }

    // Trivial methods

    boolean isEmpty() {
        return size == 0;
    }

    int getSize() {
        return size;
    }

    Item findMin() {
        return elems.get(0);
    }

    List<Item> getElems() {
        return elems;
    }

    Item getElem (int i) {
        return elems.get(i);
    }

    int getValue(int i) {
        return elems.get(i).getValue();
    }

    int getFlip (int i) {
        return flips.get(i);
    }

    public String toString() {
        return getElems().toString();
    }

    // Computations with indices

    int getParentIndex(int i) throws RootE {
	    // TODO
	    if(i != 0){
	        return i/2;
        }else{
	        throw new RootE();
        }
    }

    int getLeftChildIndex(int i) throws NoLeftChildE {
	    // TODO
        if (i == 0){
            throw new NoLeftChildE();
        }

        int temp = 2* i +getFlip(i);
	    if(temp < size){
	        return temp;
        }else{
            throw new NoLeftChildE();
        }
    }

    int getRightChildIndex(int i) throws NoRightChildE {
	    // TODO
        if (i == 0){
            return 1;
        }
        int temp = 2* i + 1 - getFlip(i);
        if(temp < size){
            return temp;
        }else{
            throw new NoRightChildE();
        }

    }

    boolean isLeftChild (int i) throws RootE {
        // TODO
        if (i == 0) {
            throw new RootE();
        }
        if (i != 0) {
            if (i % 2 == getFlip(getParentIndex(i))){
                return true;
            }
            return false;
        }
        return false;
    }

    boolean isRightChild (int i) throws RootE {
        if (i == 0) {
            throw new RootE();
        }
        if (i != 0) {
            if (i % 2 != getFlip(getParentIndex(i))){
                return true;
            }
            return false;
        }
        return false;
    }

    int getDAncestorIndex(int i) throws RootE {
	    // TODO
        if (isRightChild(i)){
            return getParentIndex(i);
        }
        return getDAncestorIndex(getParentIndex(i));
    }

    int getLeftMostChildIndex () throws NoLeftChildE {
	    // TODO
        int temp = 1;
        if (size < 2) {
            throw new NoLeftChildE();
        }
        try {
            while (true) {
                temp = getLeftChildIndex(temp);
            }
        } catch (NoLeftChildE e){
            return temp;
        }
    }

    // Helpers for main methods

    void swap(int i, int j) {
	    // TODO -- should be identical to swap in A9
        Item entryI = elems.get(i);
        Item entryJ = elems.get(j);

        elems.set(i,entryJ);
        elems.set(j,entryI);

        entryI.setPosition(j);
        entryJ.setPosition(i);
    }

    /*
     * If the value at position j is smaller than the value 
     * at position i, they are swapped and the flip bit at 
     * position j is negated. In that case the method returns 
     * false. If no action was taken, the method returns true.
     */
    boolean join (int i, int j) {
	// TODO
        if(getValue(i) > getValue(j)){
            swap (i,j);
            flips.set(j, 1-getFlip(j));
            return false;
        }
        return true;
    }

    /*
     * The method starts by joining j with its distinguished ancestor. 
     * If a swap was performed, the method recursively continues by moving
     * the distinguished ancestor up. If not, the method returns immediately.
     */
    void moveUp (int j) {
	// TODO
        try{
            int ancestorIndex = getDAncestorIndex(j);
            if(join(ancestorIndex,j) == false){
                moveUp(ancestorIndex);
            }
        }
        catch(RootE e){

        }
    }

    /*
     * The method starts by locating the leftmost child along the leftmost
     * spine. It then repeatedely joins j with that leftmost child and its 
     * parents. 
     */
    void moveDown (int j) {
	    // TODO
        try{
            int leftIndex = getLeftMostChildIndex();
            while (leftIndex != j) {
                join(j, leftIndex);
                leftIndex = getParentIndex(leftIndex);
            }
        }
        catch (NoLeftChildE | RootE e){

        }
    }

    // Main methods: insert and extractMin

    /*
     * The method adds the new item at the end of the array making sure 
     * it calls setPosition and setHeap with the appropriate parameters 
     * and initializes the associated flip bit correctly. As a little 
     * optimization, if the inserted item is in a left child position, it 
     * will reset the flip bit of the parent to 0. 
     * The method then calls moveUp.
     */
    void insert (Item item) {
	    // TODO
        size++;
        item.setPosition(size-1);
        item.setHeap(this);
        flips.add(0);
        elems.add(item);

        try{
            if(isLeftChild(size-1)){
                flips.set(getParentIndex(size -1),0);
            }
        }catch (RootE e){

        }
        moveUp(size-1);
    }

    /*
     * Like we did in the previous and as is outlined in the lecture notes, 
     * the last item in the array is moved to location 0. And then moveDown 
     * is called. Just make sure you don't call moveDown if the array has exactly 
     * one element!
     */
    Item extractMin () {
	// TODO
        Item min = elems.get(0);
        swap(size -1,0);

        elems.remove(size-1);
        size--;
        if(size > 1){
            moveDown(0);
        }
        return min;
    }

    /*
     * This method is useful for testing and debugging. It loops over the elements
     * of the array starting from the end until reaching index 1. For each item,
     * the method checks that it is larger than its distinguished ancestor.
     */

    boolean checkOrder () {
	// TODO
        try {
            for (int i = size-1; i >= 1; i--) {
                int index = getDAncestorIndex(i);
                if (index > i){
                    return false;
                }
            }
        } catch (RootE e) {
            throw new Error();
        }
        return true;
    }
}
