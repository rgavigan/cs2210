// Riley Gavigan - 251150776
public class BNode {
    // Initialize instance variables
    private Pel data;
    private BNode leftNode;
    private BNode rightNode;
    private BNode parentNode;
    
    // Data Constructor
    public BNode(Pel value, BNode left, BNode right, BNode parent) {
        data = value;
        leftNode = left;
        rightNode = right;
        parentNode = parent;
    }

    // Leaf Constructor (null values)
    public BNode() {
        data = null;
        leftNode = null;
        rightNode = null;
        parentNode = null;
    }

    // Get parent
    public BNode parent() {
        return parentNode;
    }

    // Set parent
    public void setParent(BNode newParent) {
        parentNode = newParent;
    }

    // Set left child
    public void setLeftChild(BNode p) {
        leftNode = p;
    }

    // Set right child
    public void setRightChild(BNode p) {
        rightNode = p;
    }

    // Set Pel object in this node
    public void setContent(Pel value) {
        data = value;
    }

    // Check if leaf node (no children)
    public boolean isLeaf() {
        if ((leftNode == null) && (rightNode == null)) {
            return true;
        }
        return false;
    }

    // Get Pel object
    public Pel getData() {
        return data;
    }

    // Get left child node
    public BNode leftChild() {
        return leftNode;
    }

    // Get right child node
    public BNode rightChild() {
        return rightNode;
    }


}
