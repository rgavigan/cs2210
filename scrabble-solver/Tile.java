import java.util.Random;

public class Tile {
	// Declare value as private
	private char value;
	
	public Tile() {
		// Set value to space character
		this.value = ' ';
	}
	
	public Tile(char valueGiven) {
		// Set value in constructor to value passed in when object created
		this.value = valueGiven;
	}
	
	public void pickup() {
		// Create alphabet string for potential letters and obtain length integer
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int alphaLen = alphabet.length();
		Random r = new Random();
		
		// Set value to the character at a random integer value in alphabet length range
		this.value = alphabet.charAt(r.nextInt(alphaLen));
	}
	
	public char getValue() {
		// Return the value using getter method
		return this.value;
	}
	
}
