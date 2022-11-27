// Riley Emma Gavigan - 251150776

public class Record {
    /* 
    Represents records that will be stored in hash table
    Stores string and two integers. 
    String will be used as key attribute
    */
    
    private String recordKey;
    private int recordScore;
    private int recordLevel;

    // Constructor
    public Record(String key, int score, int level) {
        this.recordKey = key;
        this.recordScore = score;
        this.recordLevel = level;
    }

    // Returns the key
    public String getKey() {
        return this.recordKey;
    }

    // Returns the score
    public int getScore() {
        return this.recordScore;
    }

    // Returns the level
    public int getLevel() {
        return this.recordLevel;
    }
}