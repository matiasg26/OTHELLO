package othello;

public class Move {

    private int row;
    private int col;
    private int value;
    public Move(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }
    //List of setter and getter methods for the row, col, and value numbers  stored in this class
    public int getRow() {
        return this.row;
    }
    public void setRow(int row) {this.row = row;}
    public int getCol() {
        return this.col;
    }
    public void setCol(int col) {this.col = col;}
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {this.value = value;}
}
