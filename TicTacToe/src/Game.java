import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {

    private Board board;

    private Deque<Player> players;

    private Player winner;

    public Game() {
        initializeGame(3, 2);
    }


    public void startGame() {
        Scanner sc = new Scanner(System.in);

        boolean isGameContinues = true;

        while (isGameContinues) {
            Player player = players.pop();

            System.out.println(player.getName() + "'s turn, enter the coordinates ");
            Coordinates coordinates = new Coordinates(sc.nextInt(), sc.nextInt());

            if (!board.addPiece(player.getPieceType(), coordinates)) {
                players.addFirst(player);
                continue;
            }

            players.addLast(player);

            board.printBoard();

            if (isWinner(player)) {
                winner = player;
                isGameContinues = false;
            }

            if (isBoardFull()) {
                isGameContinues = false;
            }
        }
    }

    public Player getWinner() {
        return winner;
    }

    private boolean isWinner(Player player) {
        return checkRows(player) || checkColumns(player) || checkLowerDiagonal(player) || checkUpperDiagonal(player);
    }

    private boolean checkRows(Player player) {
        for (int row = 0; row < board.getBoardSize(); row++) {
            int count = 0;
            for (int col = 0; col < board.getBoardSize(); col++) {
                if (board.getPiece(row, col) == player.getPieceType()) {
                    count++;
                }
            }
            if (count == board.getBoardSize()) {
                return true;
            }
        }

        return false;
    }

    private boolean checkColumns(Player player) {
        for (int col = 0; col < board.getBoardSize(); col++) {
            int count = 0;
            for (int row = 0; row < board.getBoardSize(); row++) {
                if (board.getPiece(row, col) == player.getPieceType()) {
                    count++;
                }
            }
            if (count == board.getBoardSize()) {
                return true;
            }
        }

        return false;
    }

    private boolean checkUpperDiagonal(Player player) {
        int row = board.getBoardSize()-1, col = 0;
        int count = 0;

        for (int index = 0; index < board.getBoardSize(); index++) {
            if (board.getPiece(row-index, col+index) == player.getPieceType()) {
                count++;
            }
        }

        if (count == board.getBoardSize()) {
            return true;
        }

        return false;
    }

    private boolean checkLowerDiagonal(Player player) {
        int count = 0;
        for (int row = 0; row < board.getBoardSize(); row++) {
            if (board.getPiece(row, row) == player.getPieceType()) {
                count++;
            }
        }
        if (count == board.getBoardSize()) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                if (board.getPiece(row, col) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initializeGame(int boardSize, int playersCount) {
        board = new Board(boardSize);
        players = new LinkedList<>();
        int count = 0;
        Scanner sc = new Scanner(System.in);
        while (playersCount != count) {
            System.out.println("Enter the player " + (++count) + " name");
            players.add(new Player(sc.next(), PieceType.values()[count-1]));
        }
    }
}
