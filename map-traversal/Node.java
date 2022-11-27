// Riley Emma Gavigan - 251150776

public class Node {
    private int nodeId;
    private boolean nodeMark;

    // Constructor
    public Node(int id) {
        nodeId = id;
        nodeMark = false;
    }

    // Function to mark node with boolean value
    public void markNode(boolean mark) {
        nodeMark = mark;
    }

    // Getter function for mark of node
    public boolean getMark() {
        return nodeMark;
    }

    // Getter function for ID of node
    public int getId() {
        return nodeId;
    }
}