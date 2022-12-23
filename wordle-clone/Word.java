public class Word {
    // Private variable
    private LinearNode<Letter> firstLetter;

    // Constructor
    public Word(Letter[] letters) {
        // Initialize first letter to first node of linked list
        firstLetter = new LinearNode<Letter>(letters[0]);
        // Curr pointer for looping
        LinearNode<Letter> curr = firstLetter;
        // Loop through letters array
        for (int i = 1; i < letters.length; i++) {
            // To connect future nodes to the first node
            LinearNode<Letter> node = new LinearNode<Letter>(letters[i]);
            curr.setNext(node);
            curr = node;
        }
    }

    // String creation method
    public String toString() {
        // Start of string
        String printStr = "Word: ";
        // Add first letter
        printStr += firstLetter.getElement().toString() + " ";
        LinearNode<Letter> loopLetter = firstLetter.getNext();
        while (loopLetter != null) {
            printStr += loopLetter.getElement().toString() + " ";
            loopLetter = loopLetter.getNext();
        }
        // Remove space at the end, then add newline character
        printStr.trim();
        return printStr;
    }

    public boolean labelWord(Word mystery) {
        // Loop through characters in word (firstLetter and next elements)
    	boolean boolValue = true;
    	LinearNode<Letter> curr = firstLetter;
    	LinearNode<Letter> resetCurr = firstLetter;
    	
    	// Need to clear all labels from word first and set to UNSET
    	while (resetCurr != null) {
    		resetCurr.getElement().setUnused();
    		resetCurr = resetCurr.getNext();
    	}
    	
    	// Counter to count position in this LinkedList
    	int counter = 0;
    	while (curr != null) {
    		LinearNode<Letter> mysteryCurr = mystery.firstLetter;
    		int mysteryCounter = 0;
    		while (mysteryCurr != null) {
    			// Compare curr and mysteryCurr
    			if (curr.getElement().equals(mysteryCurr.getElement())) {
    				if (counter == mysteryCounter) {
    					// They are the same letter in same position, set correct
    					curr.getElement().setCorrect();
    				}
    				else {
    					// They are the same letter but different position, set used
    					curr.getElement().setUsed();
    					boolValue = false;
    				}
    			}
    			
    			// Set mysteryCurr to next node after
    			mysteryCurr = mysteryCurr.getNext();
    			// Increment mysteryCounter by 1 index
    			mysteryCounter += 1;
    		}
    		if (curr.getElement().decorator() == " ") {
    			curr.getElement().setUnused();
    			boolValue = false;
    		}
    		
    		// Set curr to the next node after
    		curr = curr.getNext();
    		// Increment counter by 1 index
    		counter += 1;
    	}
    	return boolValue;
    }
}
