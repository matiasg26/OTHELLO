package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the  main class where your Othello game will start.
  * The main method of this application calls the App constructor. You
  * will need to fill in the constructor to instantiate your game.
  *
  * Class comments here...
  *
  */

public class App extends Application {
//sets the size of the scene and disables resizability
    @Override
    public void start(Stage stage) {
        stage.setTitle("OTHELLO");
        stage.show();
        stage.setResizable(false);
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        stage.setScene(scene);
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
