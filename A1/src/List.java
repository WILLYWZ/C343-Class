import java.util.NoSuchElementException;

class EmptyListE extends Exception {}

abstract class List<E> {
    abstract boolean isEmpty ();
    abstract boolean isSingleton();
    abstract E getFirst() throws EmptyListE;
    abstract List<E> getRest() throws EmptyListE;
    abstract E get (int index) throws EmptyListE;
    abstract int length ();
    abstract List<E> append (List<E> other);
    abstract boolean contains (E elem);

}

class EmptyL<E> extends List<E> {

    boolean isEmpty(){
        return true;
    }

    @Override
    boolean isSingleton(){
        return false;
    }

    @Override
    E getFirst() throws EmptyListE {
        throw new EmptyListE();
    }

    @Override
    List<E> getRest() throws EmptyListE {
        throw new EmptyListE();
    }

    E get (int index) throws EmptyListE{
        throw new EmptyListE();
    }

    @Override
    int length(){
        return 0;
    }

    @Override
    List<E> append(List<E> other) {
        return other;
    }

    @Override
    boolean contains(E elem) {
        return false;
    }
}

class NodeL<E> extends List<E> {

    private E data;
    private List<E> rest;

    NodeL(E data, List<E> rest){
        this.data = data;
        this.rest = rest;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    boolean isSingleton(){
        return rest.isEmpty();
    }

    @Override
    E getFirst() throws EmptyListE {
        return this.data;
    }

    @Override
    List<E> getRest() throws EmptyListE {
        return this.rest;
    }

    @Override
    E get (int index) throws EmptyListE {
        if(index == 0) {
            return this.data;
        }else{
            return rest.get(index-1);
        }
    }

    @Override
    int length(){
        return rest.length() + 1;
    }

    @Override
    List<E> append(List<E> other) {
        return new NodeL<>(data,rest.append(other));
    }

    @Override
    boolean contains(E elem) {
        if(this.data.equals(elem)){
            return true;
        }else{
            return this.rest.contains(elem);
        }
    }
}


