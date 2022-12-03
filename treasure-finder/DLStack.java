public class DLStack<T> implements DLStackADT<T> {
	// Declare private instance variables
	DoubleLinkedNode<T> top;
	int numItems;
	
	// Constructor
	public DLStack() {
		// Initialize top as null and num items as 0 in stack
		top = null;
		numItems = 0;
	}
	
	// Method to push item to top of stack
	public void push (T dataItem) {
		// Create a temp linked node and set its element to dataItem
		DoubleLinkedNode<T> temp = new DoubleLinkedNode<T>();
		temp.setElement(dataItem);
		
		// If the stack is empty
		if (size() == 0) {
			top = temp;
		}
		// If the stack is not empty
		else {
			// Temp needs to be top - set its previous node to top
			temp.setPrevious(top);
			// Set top's next node to temp, then set top to be temp
			top.setNext(temp);
			top = temp;
		}
		// After pushing an item, increment numItems
		numItems += 1;
	}
	
	// Pop element from the top of the stack
	public T pop() throws EmptyStackException {
		// If the stack is empty
		if (size() == 0) {
			throw new EmptyStackException("");
		}
		// If stack is not empty, get the top element for returning
		T elem = top.getElement();
		// Set top to its previous element, then decrement numItems
		top = top.getPrevious();
		numItems -= 1;
		
		return elem;
	}
	
	// Pop k'th element from the top of the stack
	public T pop(int k) throws InvalidItemException {
		// If k is greater than stack size or its 0 or less
		if (k > size() || k <= 0) {
			throw new InvalidItemException("Invalid item");
		}
		
		// Set the top node to current, previous node in stack to its next node, and next node in stack to the previous
		DoubleLinkedNode<T> curr = top;
		DoubleLinkedNode<T> prevNode = curr.getNext();
		DoubleLinkedNode<T> nextNode = curr.getPrevious();
		
		// Loop through k value, starting at 1 so that k = 1 just keeps curr as top and prevNode as null
		for (int i = 1; i < k; i++) {
			prevNode = curr;
			curr = nextNode;
			// Set the next node in stack to the previous node
			nextNode = nextNode.getPrevious();
		}
		
		// If we are removing the top node
		if (prevNode == null) {
			// Element to be returned
			T elem = curr.getElement();
			// Top is set to the next element, decrement numItems and return the element
			top = nextNode;
			numItems -= 1;
			return elem;
		}
		// If we are removing the bottom node
		else if (nextNode == null) {
			T elem = curr.getElement();
			prevNode.setPrevious(null);
			numItems -= 1;
			return elem;
		}
		// If we are anywhere in the middle
		else {
			T elem = curr.getElement();
			prevNode.setPrevious(nextNode);
			nextNode.setNext(prevNode);
			numItems -= 1;
			return elem;
		}
	}
	
	// Peek at the element at the top of the stack
	public T peek() throws EmptyStackException {
		if (size() <= 0) {
			throw new EmptyStackException("");
		}
		return top.getElement();
	}
	
	// Method to check if stack is empty
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}
	
	// Method to return the number of items in the stack
	public int size() {
		return numItems;
	}
	
	// Method to get the top of the stack
	public DoubleLinkedNode<T> getTop() {
		return top;
	}
	
	// Method to return a string representation of the stack
	public String toString() {
		String res = "";
		DoubleLinkedNode<T> curr = top;
		for (int i = 0; i < size(); i++) {
			res += curr.getElement().toString() + " ";
			curr = curr.getNext();
		}
		return res;
	}
}
