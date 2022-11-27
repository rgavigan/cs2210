// Riley Gavigan - 251150776
public class Pel {
    // Initialize instance variables
    private Location pelLocation;
    private int pelColor;

    // Constructor
    public Pel(Location p, int color) {
        // Initialized specified coords and color
        pelLocation = p;
        pelColor = color;
    }

    // Get location
    public Location getLocus() {
        return pelLocation;
    }

    // Get color
    public int getColor() {
        return pelColor;
    }
}
