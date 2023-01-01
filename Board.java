package othello;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
public class Board {
    private Pane mainPane;
    private int play;
    Square[][] squareArray;
    /*creates two new arrays, both of type Square[][]. One is the outer border and the other s the inner board.
    Additionally, the initial pieces are added to the board using the setUpPieces() method.
    */
    public Board(Pane mainPane) {
        this.mainPane = mainPane;
        this.squareArray = new Square[Constants.NUM_ROW][Constants.NUM_COLUMN];
        for (int row = 0; row < Constants.NUM_ROW; row++) {
            for (int col = 0; col < Constants.NUM_COLUMN; col++) {
                if (row == 0 || row == Constants.NUM_ROW - 1 || col == 0 || col == Constants.NUM_COLUMN - 1) {
                    this.squareArray[row][col] = new Square(this.mainPane, row, col,null);
                    this.squareArray[row][col].setSquareColor(Constants.BORDER_COLOR);
                }

            }
        }
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (1 < row || row < Constants.NUM_ROW - 1 || 1 < col || col < Constants.NUM_COLUMN - 1) {
                    this.squareArray[row][col] = new Square(this.mainPane, row, col,null);
                    this.squareArray[row][col].setSquareColor(Constants.BOARD_COLOR);
                        }
            }
        }
        this.setUpPieces();
    }
    /*Copy constructor of the board that loops through the entire original board and makes a copy of the squares.
     This copy constructor also checks to see if there are any pieces on the original board and copies them to this
     copied board.  */
    public Board(Board aiBoard) {
        Pane dummyPane = new Pane();
        this.squareArray = new Square[Constants.NUM_ROW][Constants.NUM_COLUMN];
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                this.squareArray[row][col] = new Square (dummyPane,row,col,null);
                if(!aiBoard.getBoard()[row][col].isEmpty()) {
                    this.squareArray[row][col].addPieceBoard(row,col,aiBoard.getBoard()[row][col].getPieceTeam(row,col));
                }
            }
        }
    }
    //This method checks if a move is valid for the current player (without flipping pieces) and returns true if so
    public boolean highlightable(int player, int row, int col) {
                if (this.getBoard()[row][col].isEmpty()) {
                    if (this.onBoard(row, col)) {
                        if (this.checkSandwich(row, col, player, false)) {
                            return true;
                        }
            }
        }return false;
    }
    /*This method loops through the whole board and if the square is "highlightable" and if it is, sets the square
    to gray*/
    public void addHighlight(int player) {
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (highlightable(player, row ,col)) {
                                this.getBoard()[row][col].setSquareColor(Color.GREY);
                            }
                    }
        }
    }
    //This method removes all highlighted squares by setting the color to the original color of the board
    public void removeHighlight(int player){
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (highlightable(player,row,col)) {
                    this.getBoard()[row][col].setSquareColor(Color.DARKGREEN);
                }
            }
        }
    }
    /*Loops through the entire board and if the piece in a square is black, adds one to the
    total number of black pieces and returns it*/
    public int getNumBlackPieces() {
        int black = 0;
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (!this.getBoard()[row][col].isEmpty()) {
                    if (this.getBoard()[row][col].getPiece().getTeamInt() == 1) {
                        black = black + 1;
                   }
                }
            }
            }return black;
        }
    /*Loops through the entire board and if the piece in a square is white, adds one to the
    total number of white pieces and returns it*/
    public int getNumWhitePieces() {
        int white = 0;
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (!this.getBoard()[row][col].isEmpty()) {
                    if (this.getBoard()[row][col].getPiece().getTeamInt() == 0) {
                        white = white + 1;
                    }
                }
            }
        }return white;
    }
    //Creates a new boolean "check" and if the board square is highlightable returns check = false
    public Boolean checkSkipTurn(int player) {
        boolean check = true;
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if(highlightable(player,row,col)) {
                    check = false;
                }
            }
        } return check;
    }
    /*Boolean method to check if the game is over. Sets boolean value check to true, loops through array, and
    sets instance variable "play" to 0. Then uses the if statements to check if either player is highlightable
    and if so, check returns false, signifying that the game is NOT over.*/
    public Boolean checkGameOver(int player) {
        boolean check = true;
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                play = 0;
                if (player == 0) {
                    play = 1;
                }
                if (player == 1) {
                    play = -1;
                }
                if (this.highlightable(player, row, col) || this.highlightable(player + play, row, col)) {
                    check = false;
                }
            }

        }
        return check;
    }
    /*Method to remove all the squares from the board as well as the highlights. If the board square is NOT empty, it
     removes the piece from the square. Lastly, it sets up the original pieces*/
    public void resetBoard() {
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                for (int player = 0; player <= 1; player++) {
                    this.getBoard()[row][col].setSquareColor(Color.DARKGREEN);
                    if (!this.getBoard()[row][col].isEmpty()){
                        this.getBoard()[row][col].removePiece();
                    }
                }
            }
        }
        this.setUpPieces();
    }
    /*Checks to see if row/column pair entered is a: empty b: on the board and c: if there is a sandwich. If all of
    these conditions are met, flipMode is entered as true and so is the boolean isValid */
    public boolean isValid(int row, int col, int player) {
        if (this.getBoard()[row][col].isEmpty()) {
            if (this.onBoard(row, col)) {
                if (this.checkSandwich(row, col, player,true)) {
                    return true;
                }
                }
            } return false;
        }
    /*Recursive method to flip the pieces on the board. While the method is running, the method looks in the direction
      given, and as long as the pieces it is passing through are not on the team of the current player, the opposite
      teams pieces are flipped. Exit condition is if whatever getTeam() returns is the same as the current player*/
    private void flipPiece(int row, int col,int rowDirection, int colDirection, int player) {
        while (this.getBoard()[row + rowDirection][col + colDirection].getTeam() != player) {
            this.getBoard()[row+rowDirection][col+colDirection].setTeam(player);
            this.flipPiece(row+rowDirection,col+colDirection,rowDirection,colDirection,player);
            }
        }
    /*Initially sets boolean scan to false. Then loops through all directions of both rows and columns. And plugs them
      into the checkOneDirection method. If checkOneDirection returns true and scan returns true. If flipPiece returns
      true as well does as well the pieces are flipped and */
    private boolean checkSandwich(int row, int col, int player, Boolean flipMode) {
            boolean scan = false;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (this.checkOneDirection(row, col, i, j, player, false)) {
                    if(flipMode) {
                        this.flipPiece(row, col, i, j, player);
                    }
                        scan = true;
                }
            }
        }return scan;
    }
    /*Another recursive method. First condition that would return false is if the square in the direction pointed is
    empty OR not on the board, checkOneDirection would automatically return false, because these moves are not valid.
    Next, if the piece in the direction inputted is NOt the same as the current team, the method runs again. If the
    piece in the direction inputted IS that of the current player and the opponent has been seen, the method returns
    true and the exit case is met.
      is */
    boolean checkOneDirection(int row, int col, int rowDirection, int colDirection, int player, boolean seenOpponent) {
        if (this.getBoard()[row +rowDirection][col + colDirection].isEmpty() || !this.onBoard(row, col)) {
            return false;
        }
        if (this.getBoard()[row + rowDirection][col + colDirection].getPiece().getTeamInt() != player) {
            return this.checkOneDirection(row + rowDirection, col + colDirection, rowDirection, colDirection, player, true);
        }
        if (this.getBoard()[row + rowDirection][col + colDirection].getPiece().getTeamInt() == player && seenOpponent == true) {
            return true;
        }
        return false;
    }
    /*Runs through the row and column inputted and if the color of the square is the same as the color of the main baord,
    returns true*/
    public boolean onBoard(int row, int col) {
        if (this.squareArray[row][col].getColor() == Constants.BORDER_COLOR) {
            return false;
        }
        return true;
    }
    //Sets the initial 4 pieces on the board
    public void setUpPieces() {
        this.getBoard()[5][4].addPieceBoard(5,4,1);
        this.getBoard()[4][5].addPieceBoard(4,5,1);
        this.getBoard()[5][5].addPieceBoard(5,5,0);
        this.getBoard()[4][4].addPieceBoard(4,4,0);
    }
    //Retrieves the squareArray for use in other classes
    public Square[][] getBoard() {
        return this.squareArray;
    }
}
