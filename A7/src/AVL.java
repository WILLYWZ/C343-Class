//-----------------------------------------------------------------------
// Empty AVL exception

import java.util.NoSuchElementException;

class EmptyAVLE extends Exception {}

//-----------------------------------------------------------------------
// Abstract AVL class

abstract class AVL implements TreePrinter.PrintableNode {

    //--------------------------
    // Static fields and methods
    //--------------------------

    static EmptyAVLE EAVLX = new EmptyAVLE();

    static AVL EAVL = new EmptyAVL();

    static AVL AVLLeaf(int elem) {
        // TODO
        return new AVLNode(elem, EAVL, EAVL);
    }

    // Recursively copy the tree changing AVL nodes to BST nodes
    static BST toBST (AVL avl) {
        // TODO
        try{
            int data = avl.AVLData();
            BST leftS = toBST(avl.AVLLeft());
            BST rightS = toBST(avl.AVLRight());
            BST bstTree = new BSTNode(data,leftS,rightS);
            return bstTree;
        }
        catch(EmptyAVLE e){
            return new EmptyBST();
        }
    }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    abstract int AVLData() throws EmptyAVLE;

    abstract AVL AVLLeft() throws EmptyAVLE;

    abstract AVL AVLRight() throws EmptyAVLE;

    abstract int AVLHeight();

    abstract boolean isEmpty ();

    //--------------------------
    // Main methods
    //--------------------------

    abstract boolean AVLfind (int key);

    abstract AVL AVLinsert(int key);

    abstract AVL AVLeasyRight();

    abstract AVL AVLrotateRight();

    abstract AVL AVLeasyLeft();

    abstract AVL AVLrotateLeft();

    abstract AVL AVLdelete(int key) throws EmptyAVLE;

    abstract Pair<Integer, AVL> AVLshrink() throws EmptyAVLE;
}

//-----------------------------------------------------------------------

class EmptyAVL extends AVL {

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int AVLData() throws EmptyAVLE {
        throw EAVLX;
    }

    AVL AVLLeft() throws EmptyAVLE {
        throw EAVLX;
    }

    AVL AVLRight() throws EmptyAVLE {
        throw EAVLX;
    }

    int AVLHeight() {
        return 0;
    }

    boolean isEmpty () {
        return true;
    };

    //--------------------------
    // Main methods
    //--------------------------

    boolean AVLfind (int key) {
        return false;
    }

    AVL AVLinsert(int key) {
        return AVLLeaf(key);
    }

    AVL AVLeasyRight() {
        throw new Error("Internal bug: should never call easyRight on empty tree");
    }

    AVL AVLrotateRight() {
        throw new Error("Internal bug: should never call rotateRight on empty tree");
    }

    AVL AVLeasyLeft() {
        throw new Error("Internal bug: should never call easyLeft on empty tree");
    }

    AVL AVLrotateLeft() {
        throw new Error("Internal bug: should never call rotateLeft on empty tree");
    }

    AVL AVLdelete(int key) throws EmptyAVLE {
        throw EAVLX;
    }

    Pair<Integer, AVL> AVLshrink() throws EmptyAVLE {
        throw EAVLX;
    }

    //--------------------------
    // Override
    //--------------------------

    public boolean equals (Object o) {
        return (o instanceof EmptyAVL);
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
}

//-----------------------------------------------------------------------

class AVLNode extends AVL {
    private int data;
    private AVL left, right;
    private int height;

     // constructor TODO
     AVLNode(int data, AVL left, AVL right){
         this.data = data;
         this.left = left;
         this.right = right;
         this.height = Math.max(left.AVLHeight(),right.AVLHeight()) + 1;
     }

    //--------------------------
    // Getters and simple methods
    //--------------------------

    int AVLData() {
        return data;
    }

    AVL AVLLeft() {
        return left;
    }

    AVL AVLRight() {
        return right;
    }

    int AVLHeight() {
        return height;
    }

    boolean isEmpty () {
        return false;
    };

    //--------------------------
    // Main methods
    //--------------------------

    boolean AVLfind(int key) {
        // TODO
        if (key == this.data){
            return true;
        }
        else if(key < data) {
            return left.AVLfind(key);
        }
        else{
            return right.AVLfind(key);

        }
    }

    AVL AVLinsert(int key) {
        if (key < this.data) {
            AVL newLeft = left.AVLinsert(key);
            AVL newNode = new AVLNode(data, newLeft, right);

            if (newLeft.AVLHeight() > right.AVLHeight() + 1) {
                return newNode.AVLrotateRight();
            } else {
                return newNode;
            }
        } else {
            AVL newRight = right.AVLinsert(key);
            AVL newNode = new AVLNode(data, this.left, newRight);

            if (newRight.AVLHeight() > left.AVLHeight() + 1) {
                return newNode.AVLrotateLeft();
            } else {
                return newNode;
            }
        }
    }

    AVL AVLeasyRight() {
        // TODO
        try{
            int leftData = left.AVLData();
            AVL leftLT = left.AVLLeft();
            AVL leftRT = left.AVLRight();

            AVL newRight = new AVLNode(data,leftRT,right);
            return new AVLNode(leftData,leftLT,newRight);
        }
        catch(EmptyAVLE e){
            throw new Error();
        }
    }

    AVL AVLrotateRight() {
        // TODO
        try {
            AVL leftLT = left.AVLLeft();
            AVL leftRT = left.AVLRight();

            if (leftRT.AVLHeight() > leftLT.AVLHeight()) {
                AVL transit = new AVLNode(data, left.AVLeasyLeft(),right);
                return transit.AVLeasyRight();
            }
            else {
                return this.AVLeasyRight();
            }
        } catch (EmptyAVLE e) {
            throw new Error();
        }
    }

    AVL AVLeasyLeft() {
        // TODO
        try{
            int rightData = right.AVLData();
            AVL rightLT = right.AVLLeft();
            AVL rightRT = right.AVLRight();

            AVL newLeft = new AVLNode(data,left,rightLT);
            return new AVLNode(rightData,newLeft,rightRT);
        }
        catch(EmptyAVLE e){
            throw new Error();
        }
    }

    AVL AVLrotateLeft() {
        // TODO
        try {
            AVL rightLT = right.AVLLeft();
            AVL rightRT = right.AVLRight();

            if (rightRT.AVLHeight() < rightLT.AVLHeight()) {
                AVL transit = new AVLNode(data, left, right.AVLeasyRight());
                return transit.AVLeasyLeft();
            }
            else {
                return this.AVLeasyLeft();
            }
        } catch (EmptyAVLE e) {
            throw new Error();
        }
    }

    AVL AVLdelete(int key) throws EmptyAVLE {
        // TODO
        if(key < this.data) {
            AVL newLeft = left.AVLdelete(key);
            AVL newNode = new AVLNode(data, newLeft, right);

            if(1 < right.AVLHeight() - newLeft.AVLHeight()) {
                return newNode.AVLrotateLeft();
            } else {
                return newNode;
            }
        }

        else if (key > this.data) {
            AVL newRight = right.AVLdelete(key);
            AVL newNode = new AVLNode(data, left, newRight);

            if(1<this.left.AVLHeight() - newRight.AVLHeight()) {
                return newNode.AVLrotateRight();
            } else {
                return newNode;
            }
        }
        else {
            if(left.isEmpty()) {
                return right;
            } else {
                Pair<Integer, AVL> recursion = left.AVLshrink();
                AVL newNode = new AVLNode(recursion.getFirst(), recursion.getSecond(), right);

                if(1 < newNode.AVLRight().AVLHeight() - newNode.AVLLeft().AVLHeight()) {
                    return newNode.AVLrotateLeft();
                }
                return newNode;
            }
        }
    }

    Pair<Integer, AVL> AVLshrink() throws EmptyAVLE {
        // TODO
        try {
            Pair<Integer, AVL> recursion = right.AVLshrink();
            AVL result = new AVLNode(data, left, recursion.getSecond());
            int lHeight = result.AVLLeft().AVLHeight();
            int rHeight = result.AVLRight().AVLHeight();

            if(lHeight - rHeight > 1) {
                return new Pair(recursion.getFirst(),result.AVLrotateRight());
            }
            return new Pair(recursion.getFirst(), result);
        }
        catch (EmptyAVLE e) {
            return new Pair<>(data, left);
        }
    }


    //--------------------------
    // Override
    //--------------------------

    public boolean equals (Object o) {
        if (o instanceof AVLNode) {
            AVLNode other = (AVLNode) o;
            return data == other.data && left.equals(other.left) && right.equals(other.right);
        }
        return false;
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
}

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
