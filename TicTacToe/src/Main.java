public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing the game");

        Game game = new Game();
        game.startGame();
        Player winner = game.getWinner();

        if (winner != null) {
            System.out.println(winner.getName() + " won the game");
        } else {
            System.out.println("It's a draw");
        }
    }
}