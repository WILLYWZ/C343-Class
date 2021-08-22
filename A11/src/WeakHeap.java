import java.util.ArrayList;
import java.util.List;

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

    WeakHeap() {
        this.size = 0;
        this.elems = new ArrayList<>();
        this.flips = new ArrayList<>();
    }

    WeakHeap (ArrayList<Item> items) {
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

    void weakHeapify () {
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
        if(i != 0){
            return i/2;
        }else{
            throw new RootE();
        }
    }

    int getLeftChildIndex(int i) throws NoLeftChildE {
        if (i == 0){
            throw new NoLeftChildE();
        }

        int temp = 2* i + getFlip(i);
        if(temp < size){
            return temp;
        }else{
            throw new NoLeftChildE();
        }
    }

    int getRightChildIndex(int i) throws NoRightChildE {
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
        if (isRightChild(i)){
            return getParentIndex(i);
        }
        return getDAncestorIndex(getParentIndex(i));
    }

    int getLeftMostChildIndex () throws NoLeftChildE {
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
        Item entryI = elems.get(i);
        Item entryJ = elems.get(j);

        elems.set(i,entryJ);
        elems.set(j,entryI);

        entryI.setPosition(j);
        entryJ.setPosition(i);
    }

    boolean join (int i, int j) {
        if(getValue(i) > getValue(j)){
            swap (i,j);
            flips.set(j, 1-getFlip(j));
            return false;
        }
        return true;
    }

    void moveUp (int j) {
        try{
            int ancestorIndex = getDAncestorIndex(j);
            if(join(ancestorIndex,j) == false){
                moveUp(ancestorIndex);
            }
        }
        catch(RootE e){

        }
    }

    void moveDown (int j) {
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

    void updateKey (int i, int value) {
        // TODO
        assert value < elems.get(i).getValue();
        elems.get(i).setValue(value);
        moveUp(i);
    }

    // Main methods: insert and extractMin

    void insert (Item item) {
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

    Item extractMin () {
        Item min = elems.get(0);
        swap(size -1,0);

        elems.remove(size-1);
        size--;
        if(size > 1){
            moveDown(0);
        }
        return min;
    }

    // For debugging

    boolean checkOrder () {
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


