import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

public class DynamicArray<E> implements StackI<E>, QueueI<E>, DequeI<E> {
    private Optional<E>[] elements;
    private int capacity, front, back, size;
    //
    // data stored in locations:
    // front+1, front+2, ... back-2, back-1 (all mod capacity)
    //
    // common cases:
    // front points to an empty location
    // back points to an empty location
    // adding to front decreases 'front' by 1
    // adding to back increases 'back' by 1
    // removing does the opposite
    //
    // |-------------------------|
    // | 4 5 6 _ _ _ _ _ _ 1 2 3 |
    // |-------------------------|
    //         /\        /\      /\
    //        back      front  capacity
    //

    @SuppressWarnings("unchecked")
    DynamicArray (int initialCapacity) {
        elements = (Optional<E>[]) Array.newInstance(Optional.class, initialCapacity);
        Arrays.fill(elements, Optional.empty());
        capacity = initialCapacity;
        front = capacity - 1;
        back = 0;
        size = 0;
    }

    // Complete the definitions of the following methods from the interfaces


    @Override
    public void clear() {
        Arrays.fill(elements,Optional.empty());
        front = capacity - 1;
        back = 0;
        size = 0;
    }

    public int size () {
        return size;
    }

    public void push(E item) {
        addFirst(item);
    }

    public E peek() throws NoSuchElementE {
        return getFirst();
    }

    public E pop() throws NoSuchElementE {
        return removeFirst();
    }

    public void offer(E elem) {
        addLast(elem);
    }

    public E poll() throws NoSuchElementE {
        return getFirst();
    }

    public E remove() throws NoSuchElementE {
        return removeFirst();
    }

    public void addFirst(E elem) {
        if(size == capacity){
            doubleCapacity();
        }
        elements[front] =Optional.of(elem);
        front = Math.floorMod(front - 1,capacity);
        size += 1;
    }

    public void addLast(E elem) {
        if(size == capacity){
            doubleCapacity();
        }
        elements[back] =Optional.of(elem);
        back = Math.floorMod(back + 1,capacity);
        size += 1;
    }

    public E getFirst() throws NoSuchElementE {
        if(size > 0){
            return elements[Math.floorMod(front + 1,capacity)].get();
        }else{
            throw new NoSuchElementE();
        }
    }

    public E getLast() throws NoSuchElementE {
        if (size > 0) {
            return elements[Math.floorMod(back - 1,capacity)].get();
        }else{
            throw new NoSuchElementE();
        }
    }

    public E removeFirst() throws NoSuchElementE {
        if (size == 0) {
            throw new NoSuchElementE();
        }else{
            E first = elements[Math.floorMod(front + 1,capacity)].get();
            elements[Math.floorMod(front + 1,capacity)] = Optional.empty();
            front = Math.floorMod(front+1,capacity);
            size = size - 1;
            return first;
        }
    }


    public E removeLast() throws NoSuchElementE {
        E flag = getLast();
        back = Math.floorMod(back - 1,capacity);
        elements[back] = Optional.empty();
        size = size - 1;
        return flag;
    }

    public void doubleCapacity(){
        Optional<E>[] temp = (Optional<E>[]) Array.newInstance(Optional.class,this.capacity*2);
        Arrays.fill(temp, Optional.empty());

        for(int i = 0; i < this.capacity; i++){
            temp[i] = elements[Math.floorMod(front+1+i,capacity)];
        }
        this.capacity = 2 * this.capacity;
        this.front = capacity-1;
        this.back = this.capacity/2;
        elements = temp;
    }

    // the following methods are used for debugging and testing;
    // please do not edit them

    Optional<E>[] getElements () { return elements; }

    int getCapacity () { return capacity; }

    int getFront () { return front; }

    int getBack () { return back; }

}
