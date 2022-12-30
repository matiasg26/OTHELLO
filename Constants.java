package othello;

import javafx.scene.paint.Color;

public class Constants {
    //This is the constants class
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int CONTROLS_PANE_WIDTH = 250;
    public static final int FRAME_WIDTH = 1080;
    public static final int FRAME_HEIGHT = 800;
    public static final int SQUARE_WIDTH = 80;
    public static final int CIRCLE_WIDTH = 35 ;
    public static final int NUM_ROW = 10;
    public static final int NUM_COLUMN = 10;
    public static final Color BORDER_COLOR = Color.SADDLEBROWN;
    public static final Color BOARD_COLOR = Color.DARKGREEN;
    public static final int[] EVALUATE_BOARD_INTS = {200, -70, 30, 25, 25, 30, -70, 200,
            -70, -100, -10, -10, -10, -10, -100, -70,
            30, -10, 2, 2, 2, 2, -10, 30,
            25, -10, 2, 2, 2, 2, -10, 25,
            25, -10, 2, 2, 2, 2, -10, 25,
            30, -10, 2, 2, 2, 2, -10, 30,
            -70, -100, -10, -10, -10, -10, -100, -70,
            200, -70, 30, 25, 25, 30, -70, 200};

}
