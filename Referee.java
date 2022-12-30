package othello;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Referee {
    private int counter;
    private Label playerLabel;
    private Label scoreLabel;
    private Timeline othelloTimeline;
    private Board board;
    private Pane mainPane;
    private VBox labelBox;
    private Label gameOverLabel;
    private int player;
    private int player1;
    private int player2;
    private int mode1;
    private int mode2;
    private ComputerPlayer computerPlayer;
    private HumanPlayer humanPlayer;
    private boolean clickable;
    //Sets up the labels for the pieces and the game, and sets clickable to false
    public Referee(Pane mainPane, Board board) {
        this.mainPane = mainPane;
        clickable = false;
        this.board = board;
        this.scoreLabel = new Label();
        this.gameOverLabel = new Label();
        this.labelBox = new VBox();
        this.playerLabel = new Label();
        this.setUpGame();
        this.othelloTimeline = new Timeline();
        this.mainPane.setOnMouseClicked((MouseEvent e) -> this.onClick(e));
        this.mainPane.setFocusTraversable(true);
    }
    //If the player is allowed to move, a piece is added to wherever is clicked on the board
    public void onClick(MouseEvent e) {
        if(clickable && player1 == player || clickable && player2 == player) {
            this.addPiece(e);
        }

    }
    /*First sets local variables of row and column to whichever square is clicked. Next, removes all previous
      highlights from the board. Then if the move is valid, a piece is added to the board, the teams are switched,
      the score label and player label are updated, and the highlights are removed, and the timeline is started again.*/
    public void addPiece(MouseEvent e) {
        int row = (int) e.getY() / Constants.SQUARE_WIDTH;
        int col = (int) e.getX() / Constants.SQUARE_WIDTH;
        if(this.board.highlightable(player,row,col)) {
            this.board.removeHighlight(player);
        }
        if (this.board.isValid(row, col,player)){
            this.board.getBoard()[row][col].addPieceBoard( row,col, player);
            player = this.teamChoose();
            this.changeScoreLabel();
            this.board.removeHighlight(player);
            this.changePlayerLabel();
            this.othelloTimeline.play();
        }
    }
    //Returns the player label for use in the Controls class
    public Label getPlayerLabel() {
        return this.playerLabel;
    }
    //Returns the score label for use in the Controls class
    public Label getScoreLabel() {
        return this.scoreLabel;
    }
    //Returns the score label for use in the Controls class
    public Label getGameOverLabel() {
        return this.gameOverLabel;
    }
    //Method that if, the game is not over and checkSkipTurn returns true, switches the players
    public void skipTurn() {
        if(!this.board.checkGameOver(player)) {
            if (this.board.checkSkipTurn(player)) {
                if (counter % 2 == 0) {
                    player = Constants.WHITE;
                }
                if (counter % 2 ==1) {
                    player = Constants.BLACK;
                }
            }
        }
    }
    /*If checkGameOver returns true from the boards class, the player label is updated one last time, the timeline
      is stopped, and reliant on who has the most pieces, the game over label is updated as well*/
    public void gameOver() {
        if (this.board.checkGameOver(player)) {
            this.changePlayerLabel();
            this.othelloTimeline.stop();
            if(this.board.getNumWhitePieces() >this.board.getNumBlackPieces()) {
                this.gameOverLabel.setText("White Wins!");
            } else if (this.board.getNumWhitePieces() <this.board.getNumBlackPieces() ) {
                this.gameOverLabel.setText("Black Wins!");
            } if(this.board.getNumBlackPieces() == this.board.getNumWhitePieces()) {
                this.gameOverLabel.setText("Tie Game!");
            }
        }
    }
    /*Set players method. Intakes some values of mode and player and, depending on these results, assigns either a
      human player or computer player, assigning the player and modes an instance variable to be referenced later on*/
    public void setPlayers(int player, int mode) {
        if (player == Constants.WHITE && mode == 0) {
            this.humanPlayer = new HumanPlayer(mainPane,board,player);
            this.player1 = player;
            this.mode1 = mode;
        }
        if (player == Constants.BLACK && mode == 0) {
            this.humanPlayer = new HumanPlayer(mainPane,board,player);
            this.player2 = player;
            this.mode2 = mode;
        }
        if (player == Constants.WHITE && mode >= 1) {
            this.computerPlayer = new ComputerPlayer(board, mode, player);
            this.player1 = player;
            this.mode1 = mode;
        }
        if (player == Constants.BLACK && mode >= 1) {
            this.computerPlayer = new ComputerPlayer(board, mode, player);
            this.player2 = player;
            this.mode2 = mode;
        }
    }
    /*List of if statements. If the player is a black or white player and if the mode is greater or equal to 1,
      the computer player makes a move reliant on the player and board it is assigned. If the opposing player to a
      computer is a human, the teams are then switched, as currently team switching only happens when a human player
      puts a piece on the boardAdditionally, if a human player is selected and the requirements are met (if the player
      is correct and the mode is 0), clickable is entered as true and the player can make a move. After these if
      statements, the labels are updated, game over and skip turned are checked, and, if both players are computers,
      the teams are then swtiched.*/
    public void gameTimeline() {
        KeyFrame gf = new KeyFrame(Duration.seconds(1), (f) -> {
            if(player == player2 && mode1 >= 1) {
                this.board.removeHighlight(player);
                this.computerPlayer.makeMove(player2,mode1);
                if(mode2 ==0) {
                    player = this.teamChoose();
                }
                othelloTimeline.stop();
            } else if (player == player2 && mode1 ==0){
                this.board.addHighlight(player);
                clickable = true;
            }
            if(player == player1 && mode2 >=1) {
                this.board.removeHighlight(player);
                this.computerPlayer.makeMove(player1,mode2);
                if(mode1 ==0) {
                    player = this.teamChoose();
                }
                othelloTimeline.play();
            } if (player == player1 && mode2 == 0) {
                this.board.addHighlight(player);
                clickable = true;
            }

            this.changeScoreLabel();
            this.changePlayerLabel();
            this.gameOver();
            this.skipTurn();
            if(mode1>=1 && mode2>=1&& !board.checkGameOver(player)) {
                player = this.teamChoose();
                othelloTimeline.play();
            }
        });
        this.othelloTimeline = new Timeline(gf);
        this.othelloTimeline.setCycleCount(Animation.INDEFINITE);
        this.othelloTimeline.play();

    }
    //Updates the player label based on whose turn it is
    public void changePlayerLabel() {
           if ( player == Constants.BLACK || player1==Constants.BLACK) {
               this.playerLabel.setText("Black to Move");
           } else if (player == Constants.WHITE) {
               this.playerLabel.setText("White to Move");
       }
    }
    //Updates the score label based on the get number of pieces methods in the Board class
    private void changeScoreLabel() {
        this.scoreLabel.setText("White: " + this.board.getNumWhitePieces()+ "  Black: " + this.board.getNumBlackPieces());
    }
    /*Sets the up the game by setting initial values for the counter and player, resetting the game over label,
      calling the change score and player label methods for the first time, and adding highlights to the
      initial pieces on the board*/
    public void setUpGame() {
        counter = 1;
        player = 0;
        this.gameOverLabel.setText("");
        this.changeScoreLabel();
        this.changePlayerLabel();
        this.board.addHighlight(player);
    }
    //Mostly in charge of turn switching (unless another method is necessary)
    public int teamChoose() {
        counter = counter + 1;
        if (counter % 2 == 1) {
            return 0;
        } return 1;

    }


}
