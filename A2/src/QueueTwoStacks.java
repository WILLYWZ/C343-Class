// Complete the implementation of this class
// For this implementation all the methods should take amortized O(1) time

class QueueTwoStacks<E> implements QueueI<E> {
    private StackList<E> front, back;

    QueueTwoStacks () {
        front = new StackList<>();
        back = new StackList<>();
    }

    public void clear() {
        front.clear();
        back.clear();
    }

    public void offer(E elem) {
        front.push(elem);
    }

    public E poll() throws NoSuchElementE {
        if (back.size() == 0) {
            while (!front.empty()) {
                back.push(front.pop());
            }
        }
        return back.peek();
    }

    public E remove() throws NoSuchElementE {
        if (back.size() == 0) {
            while (!front.empty()) {
                back.push(front.pop());
            }
        }
        return back.pop();
    }

    public int size() {
        return this.front.size() + this.back.size();
    }
}
