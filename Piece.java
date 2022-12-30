package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece {
    private Pane mainPane;
    private int row;
    private int col;
    private Circle circle;
    private int team;
    //Creates a new circle with every piece made and assigns a team depending on the integer inputted
    public Piece(Pane mainPane, int row, int col, int team) {
        this.mainPane = mainPane;
        this.row = row;
        this.col = col;
        this.team = team;
        this.circle = new Circle(col * Constants.SQUARE_WIDTH + 40, row * Constants.SQUARE_WIDTH + 40,
                Constants.CIRCLE_WIDTH);
        mainPane.getChildren().add(this.circle);
        if(team == Constants.WHITE) {
            this.setPieceColor(Color.WHITE);
        }
        if(team == Constants.BLACK) {
            this.setPieceColor(Color.BLACK);
        }

    }
    //Removes the circle from the board both graphically and logically
    public void removeCircle() {
        this.mainPane.getChildren().remove(this.circle);
        this.circle= null;
    }
    //Retrieves the row of the circle
    public int getRow() {
        return (int) this.circle.getCenterY()/Constants.SQUARE_WIDTH;
    }
    //Retrieves the column of the circle
    public int getColumn() {
        return (int) this.circle.getCenterX()/Constants.SQUARE_WIDTH;
    }
    //Returns the team that is inputted into the constructor
    public int getTeamInt() {
        return this.team;
     }
     /*Sets the color of the pieces to whatever player is inputted into the method and updates
       this.team*/
     public void setTeamInt(int player) {
        this.team = player;
        if (player ==0) {
            this.setPieceColor(Color.WHITE);
        } else {
            this.setPieceColor(Color.BLACK);
        }
     }
     //Sets the color of the pieces
    public void setPieceColor(Color color) {
        this.circle.setFill(color);
    }
    //Gets the color of the circles
    public Color getCircleColor() {
        return (Color) this.circle.getFill();
    }
}
