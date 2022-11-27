// Riley Emma Gavigan - 251150776

import java.util.ArrayList;

public class Dictionary implements DictionaryADT {
    /*
    Implements dictionary using hash table that uses separate chaining. 
    Stores objects of LinkedNode class.
    Contains methods to add, remove, and get from the hash table.
    */

    private ArrayList<LinkedNode> hashTable;
    private int numBuckets;
    private int size;

    // Constructor
    public Dictionary(int buckets) {
        this.numBuckets = buckets;
        this.size = 0;

        // Initialize hash table with null entries
        this.hashTable = new ArrayList<>();
        for (int i = 0; i < this.numBuckets; i++) {
            this.hashTable.add(null);
        }
    }

    // Generates hashcode
    private int hashFunction(String key) {
        int x = 33;
        int m = this.numBuckets;
        int code = 0;

        // Hash function for each char in key
        for (int i = 0; i < key.length(); i++) {
            code = ((code * x) + key.charAt(i)) % m;
        }
        return code;
    }

    // Generates index from key
    private int getIndex(String key) {
        int hashcode = hashFunction(key);
        int index = hashcode % this.numBuckets;

        // Handle negative indices
        index = index < 0 ? index * -1 : index;
        return index;
    }

    // Inserts given Record in the dictionary
    public int put(Record rec) throws DuplicatedKeyException {
        // Retrieve key to find correct index in hash table
        String key = rec.getKey();
        int hashcode = hashFunction(key);
        int index = getIndex(key);
        LinkedNode head = this.hashTable.get(index);

        // Loop through and if key exists then throw exception
        while (head != null) {
            if (head.getKey() == rec.getKey() && head.getHashcode() == hashcode) {
                throw new DuplicatedKeyException(key);
            }
            head = head.next;
        }

        // Add the new node to the hash table
        this.size++;
        head = this.hashTable.get(index);
        LinkedNode newNode = new LinkedNode(rec, hashcode, key);
        newNode.next = head;
        this.hashTable.set(index, newNode);

        // Return number based on if there is a collision
        if (head == null) {
            return 0;
        }
        return 1;
    }

    // Removes Record from dictionary given key
    public void remove(String key) throws InexistentKeyException {
        // Get index and initialize prev/head nodes
        int hashcode = hashFunction(key);
        int index = getIndex(key);
        LinkedNode head = this.hashTable.get(index);
        LinkedNode prev = null;
        
        // Loop until finding correct entry, or if end of linked list reached
        while (head != null) {
            if (head.getKey().equals(key) && hashcode == head.getHashcode()) {
                break;
            }

            // Increment prev/head
            prev = head;
            head = head.next;
        }

        // If the key does not exist
        if (head == null) {
            throw new InexistentKeyException(key);
        }

        this.size--;
        // If previous node exists, remove head from Linked List
        if (prev != null) { 
            prev.next = head.next;
        }
        // If previous is null, just set hash table reference to next node in list
        else {
            this.hashTable.set(index, head.next);
        }
    }

    // Returns Record stored in hash table containing key value. Null otherwise.
    public Record get(String key) {
        // Get index from hash function
        int hashcode = hashFunction(key);
        int index = getIndex(key);

        // Return Record or null
        LinkedNode node = this.hashTable.get(index);
        while (node != null) {
            if (node.getKey().equals(key) && hashcode == node.getHashcode()) {
                return node.getRecord();
            }
            node = node.next;
        }
        return null;
    }

    // Return number of Record objects
    public int numRecords() {
        return this.size;
    }
}