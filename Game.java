package othello;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Game {
    private Board board;
    private Pane mainPane;
    private Referee ref;

    public Game(Pane mainPane){
        this.mainPane = mainPane;
        this.board = new Board(this.mainPane);
        this.ref = new Referee(this.mainPane, this.board);
        mainPane.setFocusTraversable(true);
    }
    //Makes setPlayers accessible from the Controls class
    public void setThePlayers(int player, int mode) {
        this.ref.setPlayers(player, mode);
    }
    //Makes the timeline startable from the Controls class
    public void startTimeline() {
        this.ref.gameTimeline();
    }
    //Makes the player label accessible from the Controls class
    public Label getThePlayerLabel() {
        return this.ref.getPlayerLabel();
    }

    //Makes the score label accessible from the Controls class
    public Label getTheScoreLabel() {
        return this.ref.getScoreLabel();
    }

    //Makes the game over label accessible from the Controls class
    public Label getTheGameOverLabel() {
        return this.ref.getGameOverLabel();
    }
    //Resets the board graphically and logically
    public void boardReset() {
        this.board.resetBoard();
        this.ref.setUpGame();
    }
}
