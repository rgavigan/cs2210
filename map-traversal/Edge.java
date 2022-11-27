// Riley Emma Gavigan - 251150776

public class Edge {
    private Node first;
    private Node second;
    private String edgeType;

    // Constructor
    public Edge(Node u, Node v, String type) {
        first = u;
        second = v;
        edgeType = type;
    }

    // Getter function for first endpoint
    public Node firstNode() {
        return first;
    }

    // Getter functino for second endpoint
    public Node secondNode() {
        return second;
    }

    // Getter function for edge type
    public String getType() {
        return edgeType;
    }
}
