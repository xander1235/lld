package parser;

import java.util.*;

// parser.User class representing a user with ID, name, balance, and expenses
class User {
    private int userId;
    private String name;
    private double balance;
    private List<Expense> expenses;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.balance = 0.0;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void updateBalance(double amount) {
        balance += amount;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}

// parser.Expense class representing an expense with payer, amount, splitWith, metadata, and splitting type
class Expense {
    private int payerId;
    private double amount;
    private Map<Integer, Double> splitWith;
    private String metadata;
    private SplittingType splittingType;

    public Expense(int payerId, double amount, Map<Integer, Double> splitWith, String metadata, SplittingType splittingType) {
        this.payerId = payerId;
        this.amount = amount;
        this.splitWith = splitWith;
        this.metadata = metadata;
        this.splittingType = splittingType;
    }

    public int getPayerId() {
        return payerId;
    }

    public double getAmount() {
        return amount;
    }

    public Map<Integer, Double> getSplitWith() {
        return splitWith;
    }

    public String getMetadata() {
        return metadata;
    }

    public SplittingType getSplittingType() {
        return splittingType;
    }
}

// Enum for different splitting types
enum SplittingType {
    PERCENTAGE,
    INDIVIDUAL,
    EQUAL,
    CUSTOM
}

// Interface for expense management
interface ExpenseManager {
    void addUser(int userId, String name);
    void addExpense(int payerId, double amount, Map<Integer, Double> splitWith, String metadata, SplittingType splittingType);
    void printBalances();
    void simplifyExpenses();
}

// Implementation of parser.ExpenseManager interface
class Splitwise implements ExpenseManager {
    private Map<Integer, User> users;

    public Splitwise() {
        users = new HashMap<>();
    }

    public void addUser(int userId, String name) {
        if (!users.containsKey(userId)) {
            User newUser = new User(userId, name);
            users.put(userId, newUser);
        } else {
            System.out.println("parser.User already exists.");
        }
    }

    public void addExpense(int payerId, double amount, Map<Integer, Double> splitWith, String metadata, SplittingType splittingType) {
        if (users.containsKey(payerId)) {
            User payer = users.get(payerId);
            double totalSplitAmount = 0.0;
            for (double splitAmount : splitWith.values()) {
                totalSplitAmount += splitAmount;
            }

            if (totalSplitAmount == amount) {
                Expense expense = new Expense(payerId, amount, splitWith, metadata, splittingType);
                payer.addExpense(expense);
                payer.updateBalance(-amount);

                for (Map.Entry<Integer, Double> entry : splitWith.entrySet()) {
                    int userId = entry.getKey();
                    double splitAmount = entry.getValue();

                    if (users.containsKey(userId)) {
                        User user = users.get(userId);
                        user.addExpense(expense);
                        user.updateBalance(splitAmount);
                    } else {
                        System.out.println("parser.User with ID " + userId + " does not exist.");
                    }
                }
            } else {
                System.out.println("Total split amount does not match the expense amount.");
            }
        } else {
            System.out.println("parser.User with ID " + payerId + " does not exist.");
        }
    }

    public void printBalances() {
        for (User user : users.values()) {
            System.out.println("parser.User ID: " + user.getUserId() + ", Name: " + user.getName() + ", Balance: " + user.getBalance());
            System.out.println("Expenses:");
            for (Expense expense : user.getExpenses()) {
                System.out.println("Payer ID: " + expense.getPayerId() + ", Amount: " + expense.getAmount() + ", Splitting Type: " + expense.getSplittingType());
                System.out.println("Split with:");
                for (Map.Entry<Integer, Double> entry : expense.getSplitWith().entrySet()) {
                    System.out.println("parser.User ID: " + entry.getKey() + ", Split Amount: " + entry.getValue());
                }
                System.out.println("Metadata: " + expense.getMetadata());
                System.out.println();
            }
            System.out.println();
        }
    }

    public void simplifyExpenses() {
        for (User user : users.values()) {
            Map<Integer, Double> simplifiedExpenses = new HashMap<>();

            for (Expense expense : user.getExpenses()) {
                int payerId = expense.getPayerId();
                double amount = expense.getAmount();
                Map<Integer, Double> splitWith = expense.getSplitWith();

                if (users.containsKey(payerId) && users.get(payerId).getExpenses().contains(expense)) {
                    double totalSplitAmount = 0.0;
                    for (double splitAmount : splitWith.values()) {
                        totalSplitAmount += splitAmount;
                    }

                    if (totalSplitAmount == amount) {
                        for (Map.Entry<Integer, Double> entry : splitWith.entrySet()) {
                            int userId = entry.getKey();
                            double splitAmount = entry.getValue();

                            if (users.containsKey(userId) && users.get(userId).getExpenses().contains(expense)) {
                                double reverseAmount = users.get(userId).getExpenses().stream()
                                        .filter(e -> e.getPayerId() == user.getUserId())
                                        .mapToDouble(Expense::getAmount)
                                        .sum();

                                double minAmount = Math.min(splitAmount, reverseAmount);

                                if (minAmount > 0) {
                                    simplifiedExpenses.put(userId, minAmount);
                                    user.updateBalance(minAmount - splitAmount);
                                    users.get(userId).updateBalance(splitAmount - minAmount);
                                }
                            }
                        }
                    } else {
                        System.out.println("Total split amount does not match the expense amount for expense with payer ID " + payerId);
                    }
                } else {
                    System.out.println("parser.User with ID " + payerId + " does not exist or expense is not associated with the user.");
                }
            }

            user.getExpenses().clear();
            user.getExpenses().addAll(simplifiedExpenses.keySet().stream()
                    .map(userId -> users.get(userId).getExpenses())
                    .flatMap(List::stream)
                    .collect(Collectors.toList()));
        }
    }
}

// Client class that uses the parser.ExpenseManager interface
public class Main {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new Splitwise();

        // Add users
        expenseManager.addUser(1, "Alice");
        expenseManager.addUser(2, "Bob");
        expenseManager.addUser(3, "Charlie");

        // Add expenses
        Map<Integer, Double> splitWith1 = new HashMap<>();
        splitWith1.put(2, 50.0);
        splitWith1.put(3, 50.0);
        expenseManager.addExpense(1, 100, splitWith1, "Dinner", SplittingType.EQUAL);

        Map<Integer, Double> splitWith2 = new HashMap<>();
        splitWith2.put(1, 25.0);
        splitWith2.put(3, 25.0);
        expenseManager.addExpense(2, 50, splitWith2, "Movie tickets", SplittingType.PERCENTAGE);

        Map<Integer, Double> splitWith3 = new HashMap<>();
        splitWith3.put(1, 10.0);
        splitWith3.put(2, 20.0);
        expenseManager.addExpense(3, 30, splitWith3, "Shopping", SplittingType.CUSTOM);

        // Simplify expenses
        expenseManager.simplifyExpenses();

        // Print balances
        expenseManager.printBalances();
    }
}
