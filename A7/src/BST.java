import java.util.Iterator;
import java.util.NoSuchElementException;

//-----------------------------------------------------------------------
// Empty BST exception

class EmptyBSTE extends Exception {}

//-----------------------------------------------------------------------
// Abstract BST class

abstract class BST implements TreePrinter.PrintableNode, Iterable<Integer> {

    //--------------------------
    // Static fields and methods
    //--------------------------

    static EmptyBSTE EBSTX = new EmptyBSTE();

    static BST EBST = new EmptyBST();

    // A leaf is a tree with empty left and right children
    static BST BSTLeaf(int elem) {
        // TODO
        return new BSTNode(elem, EBST, EBST);
    }

    // Use the iterator (that you need to define below) to get the BST nodes
    // one-by-one and insert them into the resulting AVL tree.
    static AVL toAVL (BST bst) {
        // TODO
        AVL AVLTree = AVL.EAVL;
        for (int elem : bst){
            AVLTree = AVLTree.AVLinsert(elem);
        }
        return AVLTree;
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract int BSTData() throws EmptyBSTE;

    abstract BST BSTLeft() throws EmptyBSTE;

    abstract BST BSTRight() throws EmptyBSTE;

    abstract int BSTHeight();

    abstract boolean isEmpty();

    //--------------------------
    // Main methods
    //--------------------------

    abstract boolean BSTfind (int key);

    abstract BST BSTinsert(int key);

    abstract BST BSTdelete(int key) throws EmptyBSTE;

    abstract Pair<Integer, BST> BSTdeleteLeftMostLeaf() throws EmptyBSTE;
}

//-----------------------------------------------------------------------

class EmptyBST extends BST {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int BSTData() throws EmptyBSTE {
        throw EBSTX;
    }

    BST BSTLeft() throws EmptyBSTE {
        throw EBSTX;
    }

    BST BSTRight() throws EmptyBSTE {
        throw EBSTX;
    }

    int BSTHeight() {
        return 0;
    }

    boolean isEmpty () {
        return true;
    }

    //--------------------------
    // Main methods
    //--------------------------

    boolean BSTfind(int key) {
        return false;
    }

    BST BSTinsert(int key) {
        return BSTLeaf(key);
    }

    BST BSTdelete(int key) throws EmptyBSTE {
        throw EBSTX;
    }

    Pair<Integer, BST> BSTdeleteLeftMostLeaf() throws EmptyBSTE {
        throw EBSTX;
    }

    //--------------------------
    // Printable interface
    //--------------------------

    public TreePrinter.PrintableNode getLeft() {
        return null;
    }

    public TreePrinter.PrintableNode getRight() {
        return null;
    }

    public String getText() {
        return "";
    }

    //--------------------------
    // Iterable interface
    //--------------------------

    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            public boolean hasNext() {
                return false;
            }

            public Integer next() {
                throw new NoSuchElementException();
            }
        };
    }
}

//-----------------------------------------------------------------------
// Non-empty tree case

class BSTNode extends BST {
    private int data;
    private BST left, right;
    private int height;

    // constructor TODO
    BSTNode(int data, BST left, BST right){
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = Math.max(left.BSTHeight(),right.BSTHeight()) + 1;

    }

    int BSTData() throws EmptyBSTE {
        // TODO
        return this.data;

    }

    BST BSTLeft() throws EmptyBSTE {
        // TODO
        return this.left;
    }

    BST BSTRight() throws EmptyBSTE {
        // TODO
        return this.right;
    }

    int BSTHeight() {
        // TODO
        return this.height;
    }

    boolean isEmpty() {
        // TODO
        return false;
    }

    //--------------------------
    // Main methods
    //--------------------------

    boolean BSTfind(int key) {
        // TODO
        if (key == this.data){
            return true;
        }
        else if(key < data) {
            return left.BSTfind(key);
        }
        else{
            return right.BSTfind(key);

        }
    }

    BST BSTinsert(int key) {
        if (key >= this.data) {
            return new BSTNode(data, left, right.BSTinsert(key));
        } else{
            return new BSTNode(data, left.BSTinsert(key), right);
        }
    }

    BST BSTdelete(int key) throws EmptyBSTE {
        if (key > this.data) {
            return new BSTNode(data, left, right.BSTdelete(key));
        }
        else if (key < this.data){
            return new BSTNode(data, left.BSTdelete(key), right);
        }
        else{
            try{
                Pair<Integer,BST> newRight = right.BSTdeleteLeftMostLeaf();
                return new BSTNode(newRight.getFirst(),left,newRight.getSecond());
            }
            catch(EmptyBSTE e){
                return left;
            }
        }
    }

    Pair<Integer, BST> BSTdeleteLeftMostLeaf() {
        // TODO
        try{
            Pair<Integer, BST> recursion = left.BSTdeleteLeftMostLeaf();
            return new Pair<> (recursion.getFirst(),new BSTNode(data,recursion.getSecond(),right));
        }
        catch(EmptyBSTE e){
            return new Pair<>(data,right);
        }
    }

    //--------------------------
    // Printable interface
    //--------------------------

    public TreePrinter.PrintableNode getLeft() {
        return left.isEmpty() ? null : left;
    }

    public TreePrinter.PrintableNode getRight() {
        return right.isEmpty() ? null : right;
    }

    public String getText() {
        return String.valueOf(data);
    }

    //--------------------------
    // Iterable interface
    //--------------------------

    public Iterator<Integer> iterator() {
        // TODO
        return new Iterator<>() {
            private Iterator<Integer>iteratorL = left.iterator();
            private Iterator<Integer>iteratorR = right.iterator();
            boolean visited = false;
            boolean hasNext = true;
            public boolean hasNext() {
                return hasNext;
            }
            public Integer next() {
                if(iteratorL.hasNext()) {
                    return iteratorL.next();
                }
                if(visited == false){
                    visited = true;
                    hasNext = iteratorR.hasNext();
                    return data;
                }
                if(iteratorR.hasNext()){
                    int next = iteratorR.next();
                    hasNext = iteratorR.hasNext();
                    return next;
                }

                throw new NoSuchElementException();
            }
        };
    }
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
