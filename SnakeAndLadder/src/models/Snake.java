package models;

public class Snake extends Jump {

    public Snake(int start, int end) {
        super(start, end);
    }

    @Override
    public boolean isSnake() {
        return true;
    }
}
