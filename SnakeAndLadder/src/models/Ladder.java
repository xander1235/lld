package models;

public class Ladder extends Jump {

    public Ladder(int start, int end) {
        super(start, end);
    }

    @Override
    public boolean isSnake() {
        return false;
    }

}
