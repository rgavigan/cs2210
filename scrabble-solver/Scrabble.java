import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Scrabble {
	private Tile[] tiles;
	
	
	public Scrabble() {
		// Create tile array with 7 empty positions
		this.tiles = new Tile[7];
		for (int i = 0; i < this.tiles.length; ++i) {
			// Add into each tiles value a pickup of random letter
			Tile curTile = new Tile();
			curTile.pickup();
			this.tiles[i] = curTile;
		}
	}
	
	
	public Scrabble(Tile[] newTiles) {
		// Just use the provided tile array for tiles
		this.tiles = newTiles;
	}
	
	
	public String getLetters() {
		// Instantiate integer i and null string
		int i;
		String letterString = "";
		
		for (i = 0; i < this.tiles.length; ++i) {
			// Concatenate s with the value in the tile
			letterString = letterString + this.tiles[i].getValue();
		}
		// Return a string with every letter in the scrabble board
		return letterString;
	}
	
	
	public ArrayList<String> getWords() {
		// Instantiate array that will contain all words matching
		ArrayList<String> wordList = new ArrayList<String>();
		
		// Create new buffered reader to read file
		BufferedReader scrabbleReader;
		try {
			// Read file
			scrabbleReader = new BufferedReader(new FileReader("CollinsScrabbleWords2019.txt"));
			
			// Read each line and set its word to scrabbleWord string
			String scrabbleWord = scrabbleReader.readLine();
			
			
			// While not at the end of the file
			while (scrabbleWord != null) {
				// Create array for word characters and tiles
				ArrayList<Character> scrabbleList = new ArrayList<Character>();
				ArrayList<Character> tileList = new ArrayList<Character>();
				for (int i = 0; i < scrabbleWord.length(); i++) {
					scrabbleList.add(scrabbleWord.charAt(i));
				}
				for (int i = 0; i < this.getLetters().length(); i++) {
					tileList.add(this.getLetters().charAt(i));
				}
				
				// Sort both arrays
				Collections.sort(scrabbleList);
				Collections.sort(tileList);
				
				// Create array for matching characters between arrays
				ArrayList<Character> matchList = new ArrayList<Character>();
				
				// Loop through scrabble character array
				for (int i = 0; i < scrabbleList.size(); i++) {
					
					// For each scrabble character, loop through all tiles
					for (int j = 0; j < tileList.size(); j++) {
						
						// If scrabble character at i equals the tile at j
						if (scrabbleList.get(i) == tileList.get(j)) {
							
							// Add the letter from the lists
							matchList.add(scrabbleList.get(i));
							
							// Remove that letter from your list of tiles
							tileList.remove(j);
							
							// Return back to re-loop through the next scrabble character
							break;
						}
						
				// Sort match list
				Collections.sort(matchList);
				}
					
				// Check for equality, if equal add to wordList
				if (matchList.equals(scrabbleList)) {
					wordList.add(scrabbleWord);
				}
			}
				scrabbleWord = scrabbleReader.readLine();
			}
			
			// Close file at the end of reading
			scrabbleReader.close();
			}
		
		catch (IOException e) {
			// Catch and print an input error for scrabbleReader
			System.err.println(e);
		}
		// Finally, return wordList
		return wordList;
	}
	
	
	public int[] getScores() {
		
		// Create scores list of integers the size of created wordList
		ArrayList<String> wordList = this.getWords();
		int[] scoresList = new int[wordList.size()];
		
		// String of alphabet for indices, int list of letter values, sum counter
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int[] letterValues = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		int sum = 0;
		
		// Loop through each word in the wordList
		for (int i = 0; i < wordList.size(); i++) {
			
			// Obtain the word for the index i
			String word = wordList.get(i);
			
			// Loop through each letter in the word
			for (int j = 0; j < word.length(); j++) {
				// Add the letter's value to sum using alphabet/letterValues
				sum += letterValues[alphabet.indexOf(word.charAt(j))];
			}
			// Set i'th value of scoresList to the calculated sum
			scoresList[i] = sum;
			sum = 0;
		}
		// Sort the array before returning it [ascending order]
		Arrays.sort(scoresList);
		return scoresList;
	}
	
	
	public Boolean equals(Scrabble otherBoard) {
		// Convert into character arrays
		char[] first = this.getLetters().toCharArray();
		char[] second = otherBoard.getLetters().toCharArray();
		
		// Sort both arrays
		Arrays.sort(first);
		Arrays.sort(second);
		
		// Return boolean value if they equal one another
		return Arrays.equals(first, second);
	}
	
}
