// Riley Emma Gavigan - 251150776

public class Evaluate {
    /* 
    Implements methods needed by algorithm that plays game
    Includes state checking methods, and methods to evaluate game board.
    */

    private int boardSize;
    private int numTilesToWin;
    private int playQuality;
    private char[][] gameBoard;

    // Constructor
    public Evaluate(int size, int tilesToWin, int maxLevels) {
        this.boardSize = size;
        this.numTilesToWin = tilesToWin;
        this.playQuality = maxLevels;
        this.gameBoard = new char[this.boardSize][this.boardSize];

        // Initialize empty board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.gameBoard[i][j] = 'e';
            }
        }
    }

    // Returns empty dictionary
    public Dictionary createDictionary() {
        return new Dictionary(9887);
    }

    // Converts gameBoard to String representation
    private String boardToString() {
        // Initialize String, index/column position, and string array
        String gameStr = new String("");
        int curIndex = 0;
        int column = 0;
        String[] columnArr = new String[this.boardSize];

        // Add each column to the string array
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                // Get current column of the entry
                curIndex = (this.boardSize * row) + (col + 1);
                column = (curIndex % this.boardSize) - 1;
                if (column == -1) {
                    column = this.boardSize - 1;
                }

                // Append this entry to our array of columns
                columnArr[column] = columnArr[column] + this.gameBoard[row][col];
            }
        }
        // Loop through array and build string
        for (int i = 0; i < this.boardSize; i++) {
            // Handling "null" entry in array
            for (int j = 4; j < this.boardSize + 4; j++) {
                gameStr = gameStr + columnArr[i].charAt(j);
            }
        }

        return gameStr;
    }

    // Check if the state is repeated
    public Record repeatedState(Dictionary dict) {
        // Represent board as String
        String gameString = boardToString();

        // Check if dict has the string as a key, if so return Record
        if (dict.get(gameString) == null) {
            return null;
        }
        else {
            return dict.get(gameString);
        }
    }

    // Insert state into dictionary
    public void insertState(Dictionary dict, int score, int level) {
        // Represent board as String
        String gameString = boardToString();

        Record rec = new Record(gameString, score, level);
        dict.put(rec);
    }

    // Stores symbol in board
    public void storePlay(int row, int col, char symbol) {
        this.gameBoard[row][col] = symbol;
    }

    // Returns true if empty tile, otherwise false
    public boolean squareIsEmpty(int row, int col) {
        if (this.gameBoard[row][col] == 'e') {
            return true;
        }
        else {
            return false;
        }
    }

    // Returns true if computer tile, otherwise false
    public boolean tileOfComputer(int row, int col) {
        if (this.gameBoard[row][col] == 'c') {
            return true;
        }
        else {
            return false;
        }
    }

    // Returns true if human tile, otherwise false
    public boolean tileOfHuman(int row, int col) {
        if (this.gameBoard[row][col] == 'h') {
            return true;
        }
        else {
            return false;
        }
    }

    // Returns true if there is a winner, otherwise false
    public boolean wins(char symbol) {
        String gameString = boardToString();
        // Check Rows For Winner
        int numAdjacent= 0;
        for (int row = 0; row < this.boardSize; row++) {
            // For Each Row
            for (int col = 0; col < this.boardSize; col++) {
                // Increment number of adjacent tiles and check for win
                if (this.gameBoard[row][col] == symbol) {
                    numAdjacent++;
                    if (numAdjacent == this.numTilesToWin) {
                        return true;
                    }
                }
                // Reset number of adjacent tiles to 0
                else {
                    numAdjacent = 0;
                }
            }
            numAdjacent = 0;
        }

        // Check Columns For Winner
        int column, numAdj;
        column = numAdj = 0;

        // For Each Column
        while (column < this.boardSize) {
            // Start at first entry in column and loop until next column
            for (int curNum = (this.boardSize * column); curNum < (this.boardSize * (column + 1)); curNum++) {

                // Increment number of adjacent tiles and check for win
                if (gameString.charAt(curNum) == symbol) {
                    numAdj++;
                    if (numAdj == this.numTilesToWin) {
                        return true;
                    }
                }

                // Reset number of adjacent tiles to 0
                else {
                    numAdj = 0;
                }
            }
            numAdj = 0;
            column++;
        }

        // Check Diagonals For Winner
        int[][] directions = {{-1, 1}, {-1, -1}, {1, 1}, {1, -1}};
        final int maxx = this.boardSize;
        final int maxy = this.boardSize;
        // For each direction
        for (int[] d: directions) {
            int dx = d[0];
            int dy = d[1];

            for (int x = 0; x < maxx; x++) {
                for (int y = 0; y < maxy; y++) {
                    int tilesNeeded = this.numTilesToWin - 1;
                    // Set last x/y to x/y + number of tiles to win in that direction
                    int lastx = x + (tilesNeeded * dx);
                    int lasty = y + (tilesNeeded * dy);
                    int status = 0;

                    // If lastx and lasty are in bounds of the board
                    if ((0 <= lastx && lastx < maxx) && (0 <= lasty && lasty < maxy)) {
                        // If current board value is not the right symbol, just continue
                        char w = gameBoard[x][y];
                        if (w == symbol) {
                            // Check all values from [x][y] up until [lastx][lasty] on the diagonal
                            for (int dist = 1; dist <= tilesNeeded; dist++) {
                                if (w == gameBoard[x + (dist * dx)][y + (dist * dy)]) {
                                    continue;
                                }
                                // Change the status -> indicates the diagonal is invalid
                                else {
                                    status = 1;
                                    break;
                                }
                            }
                            // If the correct number of adjacent diagonal tiles were found
                            if (status == 0) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // Returns true if game is draw, otherwise false
    public boolean isDraw() {
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                // Empty position means the game is still going
                if (this.gameBoard[i][j] == 'e') {
                    return false;
                }
            }
        }
        return true;
    }

    // Evaluates the current game board
    public int evalBoard() {
        // Computer wnins
        if (wins('c')) {
            return 3;
        }
        // Human wins
        else if (wins('h')) {
            return 0;
        }
        // Draw
        else if (isDraw()) {
            return 2;
        }
        // Undecided
        else {
            return 1;
        }

    }
}