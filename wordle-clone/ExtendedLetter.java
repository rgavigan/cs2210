public class ExtendedLetter extends Letter {
	// Private variables and constants
	private String content;
	private int family;
	private boolean related;
	private final int SINGLETON = -1;
	
	// Constructor with just string parameter
	public ExtendedLetter(String s) {
		// Super using arbitrary char
		super('a');
		content = s;
		related = false;
		family = SINGLETON;
	}
	
	// Constructor with string and int parameter
	public ExtendedLetter(String s, int fam) {
		super('a');
		content = s;
		related = false;
		family = fam;
	}
	
	// Equals method comparing to another object
	public boolean equals(Object other) {
		// Check if other is ExtendedLetter object
		if (other.getClass().getSimpleName() == "ExtendedLetter") {
			if (this.family == ((ExtendedLetter) other).family) {
				this.related = true;
			}
			if (this.content == ((ExtendedLetter) other).content) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	// String representation of this ExtendedLetter object
	public String toString() {
		// Override with necessary requirements
		if (this.decorator() == "-" && this.related == true) {
			return "." + content + ".";
		}
		// Otherwise just use the parent method
		else {
			return this.decorator() + content + this.decorator();
		}
	}
	
	// Array of Letter objects creation
	public static Letter[] fromStrings(String[] content, int[] codes) {
		// Loop through the string array
		Letter[] letterArray = new Letter[content.length];
		for (int i = 0; i < content.length; i++) {
			// If codes[i] is null
			if (codes[i] == 0) {
				letterArray[i] = new ExtendedLetter(content[i]);
			}
			else {
				letterArray[i] = new ExtendedLetter(content[i], codes[i]);
			}
		}
		
		
		return letterArray;
	}
}
