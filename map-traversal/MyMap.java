// Riley Emma Gavigan - 251150776

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class MyMap {
    private Graph roadmap;
    private int startingNode;
    private int destinationNode;
    private int privateRoads;
    private int constructionRoads;
    private int width;
    private int length;
    private int numNodes;
    // Stack to hold path of node values
    private Stack<Node> pathway = new Stack<Node>();

    // Constructor
    public MyMap(String inputFile) throws MapException {
        // Open and read file
        BufferedReader reader;
        try {
            // check if file exists
            if (!(new File(inputFile).exists())) {
                throw new MapException("Map does not exist.");
            }

            reader = new BufferedReader(new FileReader(inputFile));
            reader.readLine();

            // Get start, dest, width, length, total nodes
            startingNode = Integer.parseInt(reader.readLine());
            destinationNode = Integer.parseInt(reader.readLine());
            width = Integer.parseInt(reader.readLine());
            length = Integer.parseInt(reader.readLine());
            numNodes = width * length;

            // Build empty graph of vertices
            roadmap = new Graph(numNodes);

            // Get private/construction rods
            privateRoads = Integer.parseInt(reader.readLine());
            constructionRoads = Integer.parseInt(reader.readLine());
            
            // Remaining Lines: Building Graph
            String line = reader.readLine();
            int lineLength = line.length();
            // Track current node value
            int curNode = 0;
            int curRow = 1;
            while (line != null) {
                // First: Build graph with current line (RHRHRHRH...RHR)
                // Track previous node in each row
                Node prevNode = null;
                String edgeType = null;

                // get cur node above and below (prev/next) the position for next row
                int prevRowCur = curNode;
                int nextRowCur = prevRowCur + width;

                for (int i = 0; i < lineLength; i++) {
                    if (curNode >= (width*curRow)) {
                        break;
                    }
                    // Get next character in line
                    char c = line.charAt(i);

                    // At a node
                    if (c == '+') {
                        // If no previous node, do nothing
                        if (prevNode == null) {
                            ;
                        }
                        // there is a previous node so connect edge
                        else {
                            // we have an edge type
                            if (edgeType != null) {
                                // add edge to graph
                                try {
                                    Node cur = roadmap.getNode(curNode);
                                    roadmap.addEdge(prevNode, cur, edgeType);
                                }
                                catch (GraphException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // set previous node to current and increment curNode
                        try {
                            prevNode = roadmap.getNode(curNode);
                        }
                        catch (GraphException e) {
                            e.printStackTrace();
                        }
                        curNode += 1;
                    }

                    // building -> make no edge and just increment column
                    else if (c == 'B') {
                        prevNode = null;
                        continue;
                    }

                    // when there is a road, get the edge type
                    else {
                        if (c == 'V') {
                            edgeType = "private";
                        }
                        else if (c == 'P') {
                            edgeType = "public";
                        }
                        else {
                            edgeType = "contruction";
                        }
                    }
                }

                // Second: Build graph with next line (HXHXHXHX...HXH)
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < lineLength; i++) {
                    edgeType = null;
                    // if out of bounds of the graph, break
                    if (nextRowCur > numNodes - 1) {
                        break;
                    }
                    char c = line.charAt(i);
                    // it is not a road
                    if (c == 'B') {
                        // if we are at even number index we want to increment cause theres no road b/w them
                        if (i % 2 == 0) {
                            prevRowCur += 1;
                            nextRowCur += 1;
                        }
                        continue;
                    }
                    // it is a road we need to connect vertically
                    else {

                        // get edge type from the character
                        if (c == 'V') {
                            edgeType = "private";
                        }
                        else if (c == 'P') {
                            edgeType = "public";
                        }
                        else {
                            edgeType = "construction";
                        }

                        // create new edge
                        try {
                            // prevRowCur = node above
                            // nextRowCur = node below
                            Node aboveNode = roadmap.getNode(prevRowCur);
                            Node belowNode = roadmap.getNode(nextRowCur);
                            roadmap.addEdge(aboveNode, belowNode, edgeType);
                        }
                        catch (GraphException e) {
                            e.printStackTrace();
                        }
                    }
                    // Increment nodes to next column
                    prevRowCur += 1;
                    nextRowCur += 1;
                }

                // Get next line
                line = reader.readLine();
                curRow += 1;
            }

            // TESTING CODE: Use to test your code with main function -> Discover if your edges are correct b/w nodes
            /*
            try {
                for (int i = 0; i < numNodes; i++) {
                    Node testNode = roadmap.getNode(i);
                    Iterator check = roadmap.incidentEdges(testNode);
                    while (check.hasNext()) {
                        Edge curEdge = (Edge) check.next();
                        System.out.println("Node " + i);
                        System.out.print(curEdge.firstNode().getId() + "->");
                        System.out.println(curEdge.secondNode().getId());
                    }
                    System.out.println("----------");
                }
            } catch (GraphException e) {
                e.printStackTrace();
            }
            */

            // close reader after
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for graph to represent roadmap
    public Graph getGraph() {
        return roadmap;
    }

    // Getter for the starting node
    public int getStartingNode() {
        return startingNode;
    }

    // Getter for the destination node
    public int getDestinationNode() {
        return destinationNode;
    }

    // Getter for max number of private roads
    public int maxPrivateRoads() {
        return privateRoads;
    }

    // Getter for max number of construction roads
    public int maxConstructionRoads() {
        return constructionRoads;
    }

    // Function to find a path from start to destination
    public Iterator<Node> findPath(int start, int destination, int maxPrivate, int maxConstruction) {
        try {
            // Check condition for complete path
            if (start == destination) {
                pathway.push(roadmap.getNode(start));
                return pathway.iterator();
            }

            // Get start node
            Node curNode = roadmap.getNode(start);

            // Add current node to stack pathway and mark true
            pathway.push(curNode);
            curNode.markNode(true);

            // Get list of edges to neighbouring nodes from current node
            Iterator<Edge> neighbourEdges = roadmap.incidentEdges(curNode);
            // Loop through all neighbouring edges
            while (neighbourEdges.hasNext()) {
                // Get the edge
                Edge nextEdge = (Edge) neighbourEdges.next();

                // check if next node has been marked
                Node nextNode = nextEdge.secondNode();
                if (nextNode.getMark() == true) {
                    continue;
                }
                int isPrivate = 0;
                int isConstruction = 0;
                // private road case (subtract max allowed next iteration)
                if (nextEdge.getType() == "private") {
                    // check if max reached already, if so continue
                    if (maxPrivate == 0) {
                        continue;
                    }
                    isPrivate = 1;
                }
                // construction road case (subtract max allowed next iteration) -> included "contruction" because prof spelled it wrong
                else if (nextEdge.getType() == "construction" || nextEdge.getType() == "contruction") {
                    // check if max reached already, if so continue
                    if (maxConstruction == 0) {
                        continue;
                    }
                    isConstruction = 1;
                }
                // recursively call algorithm for next node and necessary changes to max road counts
                Iterator<Node> retVal = findPath(nextNode.getId(), destination, maxPrivate - isPrivate, maxConstruction - isConstruction);
                if (retVal != null) {
                    return retVal;
                }
            }

            // All neighbouring nodes looped through, pop current node from stack and remove marking
            pathway.pop();
            curNode.markNode(false);
        }
        catch (GraphException e) {
            e.printStackTrace();
        }
        return null;
    }
}