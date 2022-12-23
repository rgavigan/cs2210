public class Letter {
	// Private variables
	private char letter;
	private int label;
	
	// Private constants
	private final int UNSET = 0;
	private final int UNUSED = 1;
	private final int USED = 2;
	private final int CORRECT = 3;
	
	// Constructor
	public Letter (char c) {
		letter = c;
		label = UNSET;
	}
	
	// Equals method
	public boolean equals (Object otherObject) {
		// Check if otherObject is class Letter
		if (otherObject.getClass().getSimpleName() == "Letter") {
			// Compare letter attributes between objects
			if (this.letter == ((Letter) otherObject).letter) {
				return true;
			}
		}
		return false;
	}
	
	// Decorator for printing out letters as string
	public String decorator() {
		// If label is USED (int = 2)
		if (this.label == 2) {
			return "+";
		}
		// If label is UNUSED (int = 1)
		else if (this.label == 1) {
			return "-";
		}
		// If label is CORRECT (int = 3)
		else if (this.label == 3) {
			return "!";
		}
		// If label is UNSET (int = 0)
		else if (this.label == 0) {
			return " ";
		}
		// If none of the above
		return null;
	}
	
	// String return method to give representation of the letter and it's label
	public String toString() {
		return decorator() + letter + decorator();
	}
	
	// Setter method for UNUSED
	public void setUnused() {
		label = UNUSED;
	}
	
	// Setter method for USED
	public void setUsed() {
		label = USED;
	}
	
	// Setter method for CORRECT
	public void setCorrect() {
		label = CORRECT;
	}
	
	// Method to check if letter is unused
	public boolean isUnused() {
		if (label == 1) {
			return true;
		}
		return false;
	}
	
	// Array creation method of letter objects from a string
	public static Letter[] fromString(String s) {
		// Initialize array of length of the string
		Letter[] letters = new Letter[s.length()];
		
		// Loop through the word's letters
		for (int i = 0; i < s.length(); i++) {
			letters[i] = new Letter(s.charAt(i));
		}
		return letters;
	}
}
