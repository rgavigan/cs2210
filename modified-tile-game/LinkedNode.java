// Riley Emma Gavigan - 251150776

public class LinkedNode {
    /*
    Linked List Node -> holds Record, hashcode, key, and next pointer
    Used in Dictionary for separate chaining
    */
    
    private String key;
    private Record record;
    private final int hashCode;
    LinkedNode next;

    // Constructor
    public LinkedNode(Record rec, int hash, String word) {
        this.record = rec;
        this.hashCode = hash;
        this.key = word;
    }

    // Returns the key
    public String getKey() {
        return this.key;
    }

    // Returns the record
    public Record getRecord() {
        return this.record;
    }

    // Returns the hashCode
    public int getHashcode() {
        return this.hashCode;
    }
}
