// Riley Gavigan - 251150776
public class Location {
    // Initialize instance variables;
    private int xCoord;
    private int yCoord;

    // Constructor
    public Location(int x, int y) {
        // Initialize specified coords
        xCoord = x;
        yCoord = y;
    }

    // Get x-coordinate
    public int getx() {
        return xCoord;
    }

    // Get y-coordinate
    public int gety() {
        return yCoord;
    }

    // Compare coordinates with another location
    public int compareTo(Location p) {
        // If this object's y or y=y and x is greater
        if ((this.gety() > p.gety()) || ((this.gety() == p.gety()) && (this.getx() > p.getx()))) {
            return 1;
        }
        // If locations are equivalent
        else if ((this.getx() == p.getx()) && (this.gety() == p.gety())) {
            return 0;
        }
        // If this object's y or y=y and x is less
        else {
            return -1;
        }
    }
}