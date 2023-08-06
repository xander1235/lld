package services;

public class Dice {

    private final int noOfDice;

    public Dice(int noOfDice) {
        this.noOfDice = noOfDice;
    }

    public int roll() {
        int min = 1, max = noOfDice * 6;
        return (int) Math.floor(Math.random()*(max-min+1)+min);
    }
}
