// Riley Emma Gavigan - 251150776
import java.util.Map.Entry;
import java.util.*;

public class Graph implements GraphADT {
    private int numVertices;
    // Adjacency list holds linked lists of ints to represent nodes
    private HashMap<Node, ArrayList<Edge>> adj = new HashMap<>();
    
    // Constructor
    public Graph(int n) {
        numVertices = n;

        // Add nodes to adjacency list
        for (int i = 0; i < numVertices; i++) {
            // Create new node and list and add to hashmap
            Node newNode = new Node(i);
            ArrayList<Edge> newList = new ArrayList<Edge>();
            adj.put(newNode, newList);
        }
    }

    // Getter for node based on given id
    public Node getNode(int id) throws GraphException {
        for (int i = 0; i < numVertices; i++) {
            // Find hashmap entry with correct node
            for(Entry<Node, ArrayList<Edge>> entry: adj.entrySet()) {
                if (entry.getKey().getId() == id) {
                    return entry.getKey();
                }
            }
        }
        throw new GraphException("Node does not exist");
    }

    // Function to add an edge to connect 2 nodes
    public void addEdge(Node u, Node v, String edgeType) throws GraphException {
        // Create new undirected edge to be added to adjacency list
        Edge firstEdge = new Edge(u, v, edgeType);
        Edge secondEdge = new Edge(v, u, edgeType);

        // Check if one node does not exist or both
        if (getNode(u.getId()) == null || getNode(v.getId()) == null) {
            throw new GraphException("One or both nodes were not found.");
        }

        // Get ID's for both nodes
        int uID = u.getId();
        int vID = v.getId();

        // Check for edge in adjacency list
        for(Entry<Node, ArrayList<Edge>> entry: adj.entrySet()) {
            // Check ID of u
            if (entry.getKey().getId() == uID) {
                // Loop through arraylist edges
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Edge compare = entry.getValue().get(i);
                    // If edge already exists with equivalent vertices
                    if (compare.firstNode() == u && compare.secondNode() == v) {
                        throw new GraphException("Edge already exists.");
                    }
                }
            }
            // Check ID of v
            else if (entry.getKey().getId() == vID) {
                // Loop through arraylist edges
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Edge compare = entry.getValue().get(i);
                    // If edge already exists with equivalent vertices
                    if (compare.firstNode() == v && compare.secondNode() == u) {
                        throw new GraphException("Edge already exists.");
                    }
                }
            }
        }
        // add edges to adjacency list
        adj.get(u).add(firstEdge);
        adj.get(v).add(secondEdge);
    }

    // Function to return iterator storing all edges incident to a node
    public Iterator<Edge> incidentEdges(Node u) throws GraphException {
        // Get node ID
        int uID = u.getId();

        // If node does not exist
        if (getNode(uID) == null) {
            throw new GraphException("");
        }

        // Loop to find the arraylist corresponding to node u
        for (int i = 0; i < numVertices; i++) {
            for(Entry<Node, ArrayList<Edge>> entry: adj.entrySet()) {
                // Return iterator for edges when node found
                if (entry.getKey() == u) {
                    Iterator<Edge> it = entry.getValue().iterator();
                    return it;
                }
            }
        }
        return null;
    }

    // Getter for edge connecting two nodes
    public Edge getEdge(Node u, Node v) throws GraphException {
        // Check if one node does not exist or both
        if (getNode(u.getId()) == null || getNode(v.getId()) == null) {
            throw new GraphException("One or both nodes were not found.");
        }

        for (int i = 0; i < numVertices; i++) {
            // Find edge between u and v for key associated with node u
            for(Entry<Node, ArrayList<Edge>> entry: adj.entrySet()) {
                // If the node of the entry is u
                if (entry.getKey() == u) {
                    for (int j = 0; j < entry.getValue().size(); j++) {
                        Edge compare = entry.getValue().get(j);
                        // If nodes are both vertices of an edge, return that edge
                        if (compare.firstNode() == u && compare.secondNode() == v) {
                            return compare;
                        }
                    }
                }
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
