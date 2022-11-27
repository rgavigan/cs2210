// Riley Gavigan - 251150776
public class MyObject implements MyObjectADT{
    // Initialize instance variables
    private int identifier;
    private int rectangleWidth;
    private int rectangleHeight;
    private String objType;
    private Location locus;
    private BinarySearchTree tree;
    
    // Constructor
    public MyObject(int id, int width, int height, String type, Location pos) {
        identifier = id;
        rectangleWidth = width;
        rectangleHeight = height;
        objType = type;
        locus = pos;

        // Create Empty BST
        tree = new BinarySearchTree();
    }

    // Setter for type of MyObject
    public void setType(String type) {
        objType = type;
    }

    // Getter for width
    public int getWidth() {
        return rectangleWidth;
    }

    // Getter for height
    public int getHeight() {
        return rectangleHeight;
    }

    // Getter for type
    public String getType() {
        return objType;
    }

    // Getter for ID
    public int getId() {
        return identifier;
    }

    // Getter for locus
    public Location getLocus() {
        return locus;
    }

    // Setter for locus
    public void setLocus(Location value) {
        locus = value;
    }

    // Insert pix into binary search tree for MyObject
    public void addPel(Pel pix) throws DuplicatedKeyException {
        try {
            tree.put(tree.getRoot(), pix);
        } catch (DuplicatedKeyException e) {
            System.out.println("Duplicate key.");
        }
    }

    private boolean findPel(Location p) {
        Pel pelFound = tree.get(tree.getRoot(), p);
        if (pelFound == null) {
            return false;
        }
        return true;
    }

    // Checks if this object intersects with the given object
    public boolean intersects(MyObject otherObject) {
        // Get locus coords for f (xf, yf)
        int xf = getLocus().getx();
        int yf = getLocus().gety();

        // Get locus coords for f' (xf', yf')
        int xfprime = otherObject.getLocus().getx();
        int yfprime = otherObject.getLocus().gety();

        // Get smallest node for this object
        Pel node;
        try {
            node = tree.smallest(tree.getRoot());
        } catch (Exception e) {
            // Tree is empty (no coords even exist)
            return false;
        }

        // Go through all BST nodes
        while (node != null) {
            // Get Pel coords for f (x, y)
            int x = node.getLocus().getx();
            int y = node.getLocus().gety();

            // Get x' and y'
            int xprime = (x + xf - xfprime);
            int yprime = (y + yf - yfprime);

            // Check if a location (x', y') exists in this object's tree
            Location loc = new Location(xprime, yprime);
            if (otherObject.findPel(loc)) {
                return true;
            }

            // Update node to the successor for next iteration
            node = tree.successor(tree.getRoot(), node.getLocus());
        }
        return false;
    }
}
