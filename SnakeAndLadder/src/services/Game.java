package services;

import models.Player;

import java.util.*;

public class Game {

    private Board board;

    private Deque<Player> players;

    private List<Player> winners;

    private Dice dice;

    public Game(int dimension, int noOfSnakes, int noOfLadders, int noOfPlayers, int noOfDice) {
        initializeGame(dimension, noOfSnakes, noOfLadders, noOfPlayers, noOfDice);

    }

    private void initializeGame(int dimension, int noOfSnakes, int noOfLadders, int noOfPlayers, int noOfDice) {
        if (noOfLadders > dimension || noOfSnakes > dimension) {
            throw new RuntimeException("ladders or snakes should not exceed dimension");
        }

        board = new Board(dimension, noOfSnakes, noOfLadders);
        dice = new Dice(noOfDice);
        players = new LinkedList<>();
        winners = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        int count = 0;
        while(count < noOfPlayers) {
            System.out.println("Enter the name");
            players.add(new Player(++count, sc.next()));
        }

    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        while (players.size() > 1) {
            Player player = players.pop();
            System.out.println("It's " + player.getName() + " turn, Click enter to roll the dice");

            sc.nextLine();

            int num = dice.roll();

            System.out.println("You got " + num);

            int position = player.getPosition();

            if (isWinner(position + num)) {
                player.setPosition(position + num);
                winners.add(player);
                System.out.println("You won\n");
                continue;
            }

            int previousPosition = player.getPosition();
            player.setPosition(board.getPosition(position, num));
            System.out.println("Moving from " + previousPosition + " to " + player.getPosition() +"\n\n\n");
            players.add(player);
        }

        winners.add(players.pop());
    }

    private boolean isWinner(int position) {
        return position == board.getSize();
    }

    public List<Player> getWinners() {
        return winners;
    }

}
