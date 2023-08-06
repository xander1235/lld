package services;

import models.Cell;
import models.Jump;
import models.Ladder;
import models.Snake;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Cell[][] board;

    private int size;

    private final int dimension;

    private final int noOfSnakes;

    private final int noOfLadders;

    public Board(int dimension, int noOfSnakes, int noOfLadders) {
        this.dimension = dimension;
        this.noOfSnakes = noOfSnakes;
        this.noOfLadders = noOfLadders;
        board = new Cell[dimension][dimension];
        size = dimension * dimension;
        initializeBoard();

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                System.out.print(board[row][col].getPosition() + " ");
            }
            System.out.println("\n");
        }
    }

    private void initializeBoard() {

        Map<Integer, Jump> snakesAndLadders = getSnakesAndLadders();

        for (int index = 1; index <= size; index++) {
            int row = getRowFromBoard(index);
            int col = getColumnFromBoard(index);
            Cell cell = new Cell(index, snakesAndLadders.get(index));
            board[row][col] = cell;
        }

    }

    public int getPosition(int position, int incr) {
        if (position + incr > size) {
            return position;
        }
        position += incr;
        int row = getRowFromBoard(position);
        int col = getColumnFromBoard(position);

        if (board[row][col].getJump() != null) {
            if (board[row][col].getJump().isSnake()) {
                System.out.println("It's a snake, going down at " + board[row][col].getJump().getEnd());
                position = board[row][col].getJump().getEnd();
            } else {
                System.out.println("It's a ladder, going up at " + board[row][col].getJump().getEnd());
                position = board[row][col].getJump().getEnd();
            }
        }
        return position;
    }

    private int getRowFromBoard(int position) {
       return (int) Math.floor((double) (position - 1)/dimension);
    }

    private int getColumnFromBoard(int position) {
        return (int) Math.floor((double) (position - 1)%dimension);
    }

    private Map<Integer, Jump> getSnakesAndLadders() {
        Map<Integer, Jump> snakesAndLadders = new HashMap<>();

        int snakes = noOfSnakes, ladders = noOfLadders;
        while (snakes > 0) {
            int min = 2, max = 98;
            int start = (int) Math.floor(Math.random()*(max-min+1) + min);
            max = start - 1;
            if (snakesAndLadders.containsKey(start)) {
                continue;
            }
            int end = (int) Math.floor(Math.random()*(max-min+1) + min);
            snakesAndLadders.put(start, new Snake(start, end));
            System.out.println("Snake at position start " + start + " ends " + end);
            snakes--;
        }

        while (ladders > 0) {
            int min = 2, max = 98;
            int start = (int) Math.floor(Math.random()*(max-min+1) + min);
            min = start + 1;
            if (snakesAndLadders.containsKey(start)) {
                continue;
            }
            int end = (int) Math.floor(Math.random()*(max-min+1) + min);
            snakesAndLadders.put(start, new Ladder(start, end));
            System.out.println("Ladder at position start " + start + " ends " + end);
            ladders--;
        }

        return snakesAndLadders;
    }

    public int getSize() {
        return size;
    }
}
