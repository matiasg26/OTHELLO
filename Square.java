package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square {
    private Pane mainPane;
    private int row;
    private int col;
    private Rectangle square;
    private Piece piece;
    private Boolean[][] blackPiece;
    private Boolean[][] whitePiece;
    //Creates a new square and also creates a new 2D array of white and black pieces
    public Square(Pane mainPane, int row, int col, Piece piece) {
    this.mainPane = mainPane;
        this.piece = piece;
        this.row = row;
        this.col = col;
        blackPiece = new Boolean[9][9];
        whitePiece = new Boolean[9][9];
        this.square = new Rectangle(col * Constants.SQUARE_WIDTH, row * Constants.SQUARE_WIDTH,
                Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
        this.square.setStroke(Color.BLACK);
        mainPane.getChildren().add(this.square);
    }
    //Checks to see if there are any pieces in a specific square
    public boolean isEmpty() {
        return this.piece == null;
    }
    //Gets the color of the piece and returns a team based on its result
    public int getTeam() {
        if (this.piece.getCircleColor() == Color.WHITE){
            return 0;
        } else {return 1;}
    }
    //Gets the mainPane
    public Pane getPane() {
        return this.mainPane;
  }
    //Removes a piece from the squares graphically and logically
    public void removePiece() {
        this.piece.removeCircle();
        this.piece=null;
    }
    //Sets the team of the piece based on the input, also updates the 2D array that stores the pieces
    public void setTeam(int player) {
        this.piece.setTeamInt(player);
        if (player == Constants.BLACK) {
            blackPiece[row][col] = true;
            whitePiece[row][col] = false;
        }
        if (player == Constants.WHITE) {
            blackPiece[row][col] = false;
            whitePiece[row][col] = true;
        }
    }
    //Gets the team of the piece in the square inputted into the parameters
    public int getPieceTeam(int row, int col) {
        int team = 2;
        if(blackPiece[row][col] != null || whitePiece[row][col] != null) {
            if (blackPiece[row][col]) {
                team = 1;
            }
            if (whitePiece[row][col]) {
                team = 0;
            }
        }
            return team;
    }
    //Sets the color of the square
    public void setSquareColor(Color color) {
        this.square.setFill(color);
    }
    //Gets the color of the square
    public Color getColor() {
            return (Color) this.square.getFill();
    }
    //Gets the piece
    public Piece getPiece() {
        return this.piece;
    }
    //Adds a piece to the board and updates the 2D array containing black and white pieces
    public void addPieceBoard(int row, int col, int team) {
        if (team ==1 ) {
            blackPiece[row][col] = true;
            whitePiece[row][col] = false;
        }
        if (team == 0) {
            whitePiece[row][col] = true;
            blackPiece[row][col] = false;
        }
        this.piece = new Piece(mainPane, row, col, team);
    }
}

