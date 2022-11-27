// Riley Gavigan - 251150776
public class BinarySearchTree implements BinarySearchTreeADT {
    private BNode root;

    // Constructor
    public BinarySearchTree() {
        // Initialize tree with root that is a leaf node
        root = new BNode();
    }

    private BNode getTreeNode(BNode r, Location key) {
        // empty tree
        if (r.isLeaf()) {
            return r;
        }
        else {
            int compared = r.getData().getLocus().compareTo(key);

            // found node
            if (compared == 0){
                return r;
            }
            // recurse down left subtree
            else if (compared == 1) {
                return getTreeNode(r.leftChild(), key);
            }
            // recurse down right subtree
            else {
                return getTreeNode(r.rightChild(), key);
            }
        }
    }

    // Get Pel object storing given key
    public Pel get(BNode r, Location key) {
        // While an internal node
        BNode node = getTreeNode(r, key);
        if (node != null) {
            return node.getData();
        }
        return null;
    }


    // Inserts new data into tree if non-existant
    public void put(BNode r, Pel newData) throws DuplicatedKeyException {
        BNode cur = getTreeNode(r, newData.getLocus());

        if (cur.getData() != null) {
            throw new DuplicatedKeyException("Duplicate key");
        }
        else {
            // set content, children, and children's parent for cur
            cur.setContent(newData);
            cur.setLeftChild(new BNode());
            cur.setRightChild(new BNode());
            cur.leftChild().setParent(cur);
            cur.rightChild().setParent(cur);
        }
    }



    // Removes data from tree if it exists
    public void remove(BNode r, Location key) throws InexistentKeyException {
        BNode node = getTreeNode(r, key);
        if (node.isLeaf()) {
            throw new InexistentKeyException("Key does not exist");
        }
        else {
            // if one/both node(s) is/are a leaf
            if (node.leftChild().isLeaf() || node.rightChild().isLeaf()) {
                BNode child;
                // Set child to the other child of node
                if (node.leftChild().isLeaf()) {
                    child = node.rightChild();
                } 
                else {
                    child = node.leftChild();
                }

                BNode parentNode = node.parent();
                if (parentNode != null) {
                    int comparedDist = parentNode.getData().getLocus().compareTo(node.getData().getLocus());
                    // set left child if it is smaller than parent
                    if (comparedDist == 1) {
                        parentNode.setLeftChild(child);
                    }
                    // set right child if it is greater than parent
                    else {
                        parentNode.setRightChild(child);
                    }
                    child.setParent(parentNode);
                    node.setParent(null);
                }
                // Set child as root of tree
                else {
                    r.setContent(child.getData());
                    r.setLeftChild(child.leftChild());
                    r.setRightChild(child.rightChild());
                }
            }
            else {
                BNode smallestNode = null;
                try {
                    Pel smallestPel = smallest(node.rightChild());
                    smallestNode = getTreeNode(r, smallestPel.getLocus());
                } catch (Exception e) {
                    // do nothing
                }
                // set content to smallest right child and then remove that child
                node.setContent(smallestNode.getData());
                remove(smallestNode, smallestNode.getData().getLocus());
            }
        }
    }

    // Getter for successor node of the given node if it exists
    public Pel successor(BNode r, Location key) {
        BNode curNode = getTreeNode(r, key);
        if ((curNode.rightChild() != null) && !(curNode.rightChild().isLeaf())) {
            Pel next;
            try {
                next = smallest(curNode.rightChild());
                return next;
            } catch (Exception e) {
                // do nothing
            }
        }

        BNode parentNode = curNode.parent();
        // until cur is a left child
        while (parentNode != null && curNode == parentNode.rightChild()) {
            curNode = parentNode;
            parentNode = parentNode.parent();
        }
        if (parentNode == null) {
            return null;
        }
        return parentNode.getData();
    }

    // Getter for predecessor node of the given node if it exists
    public Pel predecessor(BNode r, Location key) {
        BNode curNode = getTreeNode(r, key);
        if ((curNode.leftChild() != null) && !(curNode.leftChild().isLeaf())) {
            Pel next;
            try {
                next = largest(curNode.leftChild());
                return next;
            } catch (Exception e) {
                // do nothing
            }
        }

        BNode parentNode = curNode.parent();
        // until cur is a right child
        while (parentNode != null && curNode == parentNode.leftChild()) {
            curNode = parentNode;
            parentNode = parentNode.parent();
        }
        if (parentNode == null) {
            return null;
        }
        return parentNode.getData();
    }

    // Getter for Pel object representing the smallest key in tree
    public Pel smallest(BNode r) throws EmptyTreeException {
        // Empty tree
        if (r.isLeaf()) {
            throw new EmptyTreeException("Tree is empty");
        }
        else {
            BNode cur = r;
            // Traverse left until reaching a leaf node
            while (!(cur.isLeaf())) {
                cur = cur.leftChild();
            }
            return cur.parent().getData();
        }
    }

    // Getter for Pel object representing the largest key in tree
    public Pel largest(BNode r) throws EmptyTreeException {
        // Empty tree
        if (r.isLeaf()) {
            throw new EmptyTreeException("Tree is empty");
        }

        else {
            BNode cur = r;
            // Traverse right until reaching a leaf node
            while (!(cur.isLeaf())) {
                cur = cur.rightChild();
            }
            return cur.parent().getData();
        }
    }

    // Getter for root of tree
    public BNode getRoot() {
        return root;
    }
}