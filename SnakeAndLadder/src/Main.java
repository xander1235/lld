import models.Player;
import services.Game;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the board dimension");
        int dimension = sc.nextInt();

        System.out.println("Snakes and ladders should be less than or equal to the dimension\n");

        System.out.println("Please enter the number of snakes");
        int noOfSnakes = sc.nextInt();

        System.out.println("Please enter the number of ladders");
        int noOfLadders = sc.nextInt();

        System.out.println("Please enter the number of dice to roll");
        int noOfDice = sc.nextInt();

        System.out.println("Please enter the number of players to play the game");
        int noOfPlayers = sc.nextInt();

        Game game = new Game(dimension, noOfSnakes, noOfLadders, noOfPlayers, noOfDice);

        System.out.println("Starting the game\n\n");

        game.startGame();

        System.out.println("\n\nLeaderboard");

        List<Player> winners = game.getWinners();
        for (int index = 0; index < winners.size(); index++) {
            System.out.println((index+1) + " " + winners.get(index).getName());
        }
    }
}