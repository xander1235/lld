public class Board {

    private PieceType[][] board;

    public Board(int size) {
        board = new PieceType[size][size];
    }

    public boolean addPiece(PieceType piece, Coordinates coordinates) {
        if (!isValidateCoordinates(coordinates)) {
            System.out.println("Coordinates entered or not valid");
            return false;
        }

        if (board[coordinates.getRow()][coordinates.getColumn()] != null) {
            System.out.println("Coordinates already occupied");
            return false;
        }

        board[coordinates.getRow()][coordinates.getColumn()] = piece;

        return true;
    }

    public void printBoard() {
        System.out.println("-------------");

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                String piece = board[row][col] == null ? " " : board[row][col].toString();
                System.out.print("| " + piece + " ");
            }
            System.out.println("|");
            System.out.println("-------------");
        }
    }

    private boolean isValidateCoordinates(Coordinates coordinates) {
        return coordinates.getRow() < board.length  && coordinates.getRow() >=0 && coordinates.getColumn() < board.length && coordinates.getColumn() >= 0;
    }

    public PieceType getPiece(int row, int col) {
        return board[row][col];
    }

    public int getBoardSize() {
        return board.length;
    }
}
