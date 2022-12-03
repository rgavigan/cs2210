import java.io.FileNotFoundException;
import java.io.IOException;

public class PathFinder {
	// Private instance variable
	Map pyramidMap;
	
	// Constructor method
	public PathFinder (String fileName) {
		try {
			pyramidMap = new Map(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to find path to treasure chambers
	public DLStack<Chamber> path() {
		// Stack to represent visited chambers
		DLStack<Chamber> visitedChambers = new DLStack<Chamber>();
		
		// Get starting chamber and the number of treasures that must be found
		Chamber startingChamber = pyramidMap.getEntrance();
		int treasureCount = pyramidMap.getNumTreasures();
		
		// Push the entrance to the stack and mark as pushed
		visitedChambers.push(startingChamber);
		startingChamber.markPushed();
		
		// Track the amount of treasure the path finds
		int pathTreasure = 0;
		
		// While the stack of visited chambers is not empty
		while (!visitedChambers.isEmpty()) {
			// Current chamber
			Chamber currChamber = visitedChambers.peek();
			// Base Case: The current chamber contains treasure, leading to treasureCount being reached
			if (currChamber.isTreasure()) {
				// Increase path treasure count
				pathTreasure += 1;
				if (pathTreasure == treasureCount) {
					break;
				}
				// Break loop
			}
			// Find the next chamber by using the bestChamber method
			Chamber nextChamber = bestChamber(currChamber);
			// If this next chamber is not null
			if (nextChamber != null) {
				// Push the next chamber onto the stack and mark it as pushed so that the path continues in next loop iteration
				visitedChambers.push(nextChamber);
				nextChamber.markPushed();
			}
			else {
				// Remove the top chamber from the stack, mark it as popped
				Chamber poppedChamber = visitedChambers.pop();
				poppedChamber.markPopped();
			}
		}
		return visitedChambers;
	}
	
	// Method to return value of pyramidMap
	public Map getMap() {
		return pyramidMap;
	}
	
	// Method to check if the current chamber is dim
	public boolean isDim(Chamber currentChamber) {
		// If the chamber is not null, not sealed, not lighted
		if (currentChamber != null && !currentChamber.isSealed() && !currentChamber.isLighted() && !currentChamber.isTreasure()) {
			// Check if one of the neighbours if lighted
			for (int i = 0; i < 6; i++) {
				Chamber neighbour = currentChamber.getNeighbour(i);
				if (neighbour.isLighted() || neighbour.isTreasure()) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Method to select the optimal chamber to move on to in path
	public Chamber bestChamber (Chamber currentChamber) {
		// To loop around the 6 neighbours of currentChamber [TREASURE]
		for (int i = 0; i < 6; i++) {
			// Check for lowest index that is treasure, and that is not marked already
			Chamber currNeighbour = currentChamber.getNeighbour(i);
			if (currNeighbour == null) {
				continue;
			}
			else if (currNeighbour.isTreasure() && !currNeighbour.isMarked()) {
				return currNeighbour;
			}
		}
		
		// To loop around the 6 neighbours of currentChamber [LIGHTED]
		for (int i = 0; i < 6; i++) {
			// Get current neighbour
			Chamber currNeighbour = currentChamber.getNeighbour(i);
			if (currNeighbour == null) {
				continue;
			}
			// Check for lowest index that is lighted, and that is not marked already
			else if (currNeighbour.isLighted() && !currNeighbour.isMarked()) {
				return currNeighbour;
			}
		}
		
		// To loop around the 6 neighbours of currentChamber [DIM]
		for (int i = 0; i < 6; i++) {
			Chamber currNeighbour = currentChamber.getNeighbour(i);
			if (currNeighbour == null) {
				continue;
			}
			// Check if this neighbour has either treasure or a lighted neighbour
			for (int j = 0; j < 6; j++) {
				Chamber neighboursNeighbour = currNeighbour.getNeighbour(j);
				if (neighboursNeighbour == null) {
					continue;
				}
				else if (neighboursNeighbour.isLighted() || neighboursNeighbour.isTreasure()) {
					if (!currNeighbour.isMarked() && !currNeighbour.isSealed()) {
						return currNeighbour;
					}
				}
			}
		}
		// if nothing is good, return null
		return null;
	}
}
