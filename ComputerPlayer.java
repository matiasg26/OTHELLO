package othello;

import java.util.ArrayList;
//Human player inherits player to establish connection between human players and computer players
public class ComputerPlayer extends Player {
    private Board board;
    int intelligence;
    //Stores the intelligence and board inputted into the constructor
    public ComputerPlayer(Board board, int intelligence, int player) {
        super(player);
        this.intelligence = intelligence;
        this.board = board;
    }
    //Uses the getBestMove method to make a move on behalf of the computer, if the move is valid
    public void makeMove(int player, int intelligence) {
        int row = this.getBestMove(this.board,intelligence,player).getRow();
        int col = this.getBestMove(this.board,intelligence,player).getCol();
        if(this.board.isValid(row,col,player)) {
            this.board.getBoard()[row][col].addPieceBoard(row, col, player);
        }
    }
    /*Creates an int array of the numbers provided in the help doc and assigns each one to the rows and columns of
      the board. Then, returns the number associated to the row and column inputted into the method*/
    public int getSquareValue(int rowLoc, int colLoc) {
        int[] numbers = Constants.EVALUATE_BOARD_INTS;
        int squareArrayNum[][] = new int[10][10];
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (1 < row || row < Constants.NUM_ROW - 1 || 1 < col || col < Constants.NUM_COLUMN - 1) {
                    int k = (row - 1) * 8 + col - 1;
                    squareArrayNum[row][col] = numbers[k];
                }
            }
        }
        return squareArrayNum[rowLoc][colLoc];
    }
    /*Sets local variables whiteValue and blackValue to 0. Then, loops through entire playable section of the board.
      Checks if the move is valid and if, so moves to check the team of the piece the current row and column is
      pointing at. If this piece is white, for example, the total white value is increased by 1, and same for black.
      Once the loop has gone through every square, the method returns the white value minus the black value if the
      current team is white and vice versa.*/
    public int evaluateBoard(int player,Board board) {
        int whiteValue = 0;
        int blackValue = 0;
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
                if (1 < row || row < Constants.NUM_ROW - 1 || 1 < col || col < Constants.NUM_COLUMN - 1) {
                    if (!board.getBoard()[row][col].isEmpty()) {
                        if (board.onBoard(row, col)) {
                            if (board.getBoard()[row][col].getPieceTeam(row, col) == Constants.WHITE) {
                                whiteValue = whiteValue + this.getSquareValue(board.getBoard()[row][col].getPiece().getRow(),
                                        board.getBoard()[row][col].getPiece().getColumn());
                            }
                            if (board.getBoard()[row][col].getPieceTeam(row, col) == Constants.BLACK) {
                                blackValue = blackValue + this.getSquareValue(board.getBoard()[row][col].getPiece().getRow(),
                                        board.getBoard()[row][col].getPiece().getColumn());
                            }
                        }
                    }
                }
            }
        }
        if (player == Constants.WHITE) {
            return whiteValue - blackValue;
        } else {
            return blackValue - whiteValue;
        }

    }
    /*An array list of type Move that contains all the legal moves available on the board the moment the method is
    called. This makes it easy to access when looping through the list in the getBestMove method. At the end the
    arrayList of the all the legal moves are returned*/
    public ArrayList<Move> getLegalMoves(int player, Board board) {
        ArrayList<Move> legalMoves = new ArrayList<>();
        for (int row = 0; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 0; col < Constants.NUM_COLUMN - 1; col++) {
                // check if the current square is a legal move for the given player
                if (this.board.highlightable(player, row, col)) {
                    // create a new move object and add it to the list of legal moves
                    legalMoves.add(new Move(row, col, this.evaluateBoard(player, board)));
                }
            }
        }
        return legalMoves;
    }
    /*Initially sets up the bestMove and currMove local variables. Then creates a loop through the board.
      If, in the next move, the game will be over, the method runs through a bunch of scenarios to return either a
      high, low, or neutral value to make sure the computer player either certainly plays a move that gives a winning
      result, or definitely does not play a move the gives a losing result. Next, a new local variable of the
      ArrayList created above. If there are no valid moves and the intelligence is set to 1, a new move is returned
      that has an extremely low value, because there is nothing we can do here. If the intelligence is not 1, new
      local variables are created and returned with a negated value of the opponents beset move. If there are valid
      moves, every valid move is looped through. New local variables of rows and column are created and the valid moves'
      rows and columns are assigned to these variables. Next, a new copy board is created to make imaginary moves on.
      A piece is added to the board and currMove's rows and columns are set to those of the valid move above.
      If the intelligence is 1, the board is simply evaluated and the move that yields the highest value is made.
      If the intelligence is not current move's value is set to the negated value of the opponent's move and the
      intelligence is subtracted by 1. Ultimately,whenever currMove is larger than or equal to bestMove, bestMove is
      set equal to currMove. The method is called recursively until the base case (intelligence =1) is met and the
      highest possible move value is then set.*/
    public Move getBestMove(Board board, int intelligence, int player) {
        Move bestMove = new Move(0, 0, -1000);
        Move currMove = new Move(0,0,0);
        for (int row = 1; row < Constants.NUM_ROW - 1; row++) {
            for (int col = 1; col < Constants.NUM_COLUMN - 1; col++) {
        if (this.board.checkGameOver(player)) {
            if (this.board.getNumBlackPieces() > this.board.getNumWhitePieces() && player == Constants.BLACK ||
                    this.board.getNumWhitePieces() > this.board.getNumBlackPieces() && player == Constants.WHITE) {
                return new Move(row, col, 1000);
            } else if (this.board.getNumBlackPieces() < this.board.getNumWhitePieces() && player == Constants.BLACK ||
                    this.board.getNumWhitePieces() < this.board.getNumBlackPieces() && player == Constants.WHITE) {
                return new Move(row, col, -1000);
            } else if(this.board.getNumWhitePieces() == this.board.getNumBlackPieces()) {
                return new Move(row, col, 0);
            }

        }
                ArrayList<Move> validMoves = this.getLegalMoves(player,board);
                if (validMoves.isEmpty()) {
                    if (intelligence == 1) {
                        return new Move(row, col, -1000);
                    }else{
                            if (player == Constants.WHITE) {
                                Move move = new Move(row,col,0);
                                move.setValue(this.getBestMove(board, intelligence, 1).getValue() * -1);
                                return move;
                            }
                            if (player == Constants.BLACK) {
                                Move move = new Move(row,col,0);
                                move.setValue(this.getBestMove(board, intelligence, 0).getValue() * -1);
                                return move;
                            }
                        }
                    } else {
                    for (Move validMove : validMoves) {
                        int row1 = validMove.getRow();
                        int col1 = validMove.getCol();
                        Board aiBoard = new Board(this.board);
                        aiBoard.getBoard()[row][col].addPieceBoard(row1, col1, player);
                        currMove = new Move(row1,col1,0);
                        if (intelligence == 1) {
                            currMove.setValue(this.evaluateBoard(player, aiBoard));
                        } else {
                            intelligence = intelligence -1;
                            currMove.setValue(-1 * (getBestMove(board, intelligence, player).getValue()));
                        }
                        if (bestMove.getValue() <= currMove.getValue()) {
                            bestMove = currMove;
                        }
                    }
                }
            }
        }
        return bestMove;
    }
    }
