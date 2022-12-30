package othello;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.awt.*;
//Human player inherits player to establish connection between human players and computer players
public class HumanPlayer extends Player{
    private Pane mainPane;
    private Board board;
    private int player;
    //Not much here as it's simply used to represent the HumanPlayer and allow for switching
    public HumanPlayer(Pane mainPane, Board board, int player) {
        super(player);
        this.board = board;
        this.mainPane = mainPane;
        this.player = player;
    }
}
