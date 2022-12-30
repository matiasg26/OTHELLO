package othello;

public class Player {
    private int player;
    /*Class solely created for the purpose of relating ComputerPlayer to HumanPlayer, allowing both to be called under
      one class */
    public Player(int player) {
        this.player = player;
    }
    public int getPlayer() {
        return this.player;
    }
}
