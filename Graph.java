// Riley Emma Gavigan - 251150776
import java.util.*;

public class Graph implements GraphADT {
    private int numVertices;

    // initialize arraylist with n values that are Node objects
    private ArrayList<Node> nodes = new ArrayList<Node>();

    // initialize arraylist that holds list of Edge objects
    private ArrayList<ArrayList<Edge>> edges = new ArrayList<ArrayList<Edge>>();
    
    // Constructor
    public Graph(int n) {
        numVertices = n;

        // Add nodes to adjacency list
        for (int i = 0; i < numVertices; i++) {
            // Create new node
            Node newNode = new Node(i);
            // Add empty ArrayList of edges, and add node to ArrayList of nodes
            edges.add(i, new ArrayList<Edge>());
            nodes.add(i, newNode);
        }
    }

    // Getter for node based on given id
    public Node getNode(int id) throws GraphException {
        // Get node for given id
        try {
            Node node = nodes.get(id);
            return node;
        // If node does not exist, throw GraphException (would be out of bounds index)
        } catch (Exception IndexOutOfBoundsException) {
            throw new GraphException("Node does not exist");
        }
    }

    // Function to add an edge to connect 2 nodes
    public void addEdge(Node u, Node v, String edgeType) throws GraphException {
        // Create new undirected edge to be added to adjacency list
        Edge firstEdge = new Edge(u, v, edgeType);
        Edge secondEdge = new Edge(v, u, edgeType);

        // Get ID's for both nodes
        int uID = u.getId();
        int vID = v.getId();

        // Check if one node does not exist or both
        if (getNode(uID) == null || getNode(vID) == null) {
            throw new GraphException("One or both nodes were not found.");
        }

        // Check if edge already exists between u and v by checking the edge list of u
        for (int j = 0; j < edges.get(uID).size(); j++) {
            Edge compare = edges.get(uID).get(j);
            // If edge already exists with equivalent vertices
            if (compare.firstNode() == u && compare.secondNode() == v) {
                throw new GraphException("Edge already exists.");
            }
        }

        // add the firstEdge to the adjacency list by adding it to the edges arraylist for index uID
        edges.get(uID).add(firstEdge);
        edges.get(vID).add(secondEdge);
    }

    // Function to return iterator storing all edges incident to a node
    public Iterator<Edge> incidentEdges(Node u) throws GraphException {
        // Get node ID
        int uID = u.getId();

        // If node does not exist
        if (getNode(uID) == null) {
            throw new GraphException("Node does not exist");
        }

        // If the node exists, get the edge list for the index uID and return an iterator to the arraylist of edges
        ArrayList<Edge> edgeList = edges.get(uID);
        Iterator<Edge> it = edgeList.iterator();
        return it;
    }

    // Getter for edge connecting two nodes
    public Edge getEdge(Node u, Node v) throws GraphException {
        int uID = u.getId();
        int vID = v.getId();
        // Check if one node does not exist or both
        if (getNode(uID) == null || getNode(vID) == null) {
            throw new GraphException("One or both nodes were not found.");
        }

        // Check if edge exists between u and v by checking edge list of u
        for (int j = 0; j < edges.get(uID).size(); j++) {
            Edge compare = edges.get(uID).get(j);
            // If nodes are both vertices of an edge, return that edge
            if (compare.firstNode() == u && compare.secondNode() == v) {
                return compare;
            }
        }
        return null;
    }

    // Function to check if two nodes are adjacent
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        // Check if one node does not exist or both
        if (getNode(u.getId()) == null || getNode(v.getId()) == null) {
            throw new GraphException("One or both nodes were not found.");
        }

        // Check if edge exists between u and v
        if (getEdge(u, v) != null || getEdge(v, u) != null) {
            return true;
        }
        return false;
    }
}
