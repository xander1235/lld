package models;

public class Cell {

    private int position;

    private Jump jump;

    public Cell(int position, Jump jump) {
        this.position = position;
        this.jump = jump;
    }

    public Jump getJump() {
        return this.jump;
    }

    public int getPosition() {
        return position;
    }
}
