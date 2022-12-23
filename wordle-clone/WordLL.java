public class WordLL {
	// Initialize private variables
	private Word mysteryWord;
	private LinearNode<Word> history;
	
	// Constructor
	public WordLL(Word mystery) {
		// Empty history
		history = new LinearNode<Word>();
		// Setting mystery word to word given
		mysteryWord = mystery;
	}
	
	// Word guessing function
	public boolean tryWord(Word guess) {
		guess.labelWord(mysteryWord);
		LinearNode<Word> guessObj = new LinearNode<Word>(guess);
		guessObj.setNext(history);
		history = guessObj;
		if (guess.labelWord(mysteryWord) == true) {
			return true;
		}
		return false;
	}
	
	// String representation function
	public String toString() {
		String completeString = null;
		LinearNode<Word> curr = history;
		while (curr != null) {
			if (curr.getElement() == null) {
				break;
			}
			completeString = completeString + curr.getElement().toString() + "\n";
			curr = curr.getNext();
		}
		return completeString;
	}
}
