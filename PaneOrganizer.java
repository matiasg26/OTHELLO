package othello;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {
    private BorderPane root;
    private Controls control;
    private Game othello;
    private Pane mainPane;
    /*Creates the mane pane, sets up the game, controls, and root borderPane. Also sets the
      controlPane to the right side of the borderPane and the mainPane to the center of it*/
    public PaneOrganizer() {
        this.mainPane = new Pane();
        this.othello = new Game(mainPane);
        this.control = new Controls(othello);
        this.root = new BorderPane();
        this.root.setRight(this.control.getPane());
        this.root.setCenter(mainPane);
    }
    public BorderPane getRoot() {
        return root;
    }

}
