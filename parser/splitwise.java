package parser;
public void simplifyExpenses() {
    // Calculate net balances for each user
    Map<Integer, Double> netBalances = calculateNetBalances();

    // Create a list of users who owe money (debtors) and users who are owed money (creditors)
    List<Integer> debtors = new ArrayList<>();
    List<Integer> creditors = new ArrayList<>();
    for (Map.Entry<Integer, Double> entry : netBalances.entrySet()) {
        if (entry.getValue() < 0) {
            debtors.add(entry.getKey());
        } else if (entry.getValue() > 0) {
            creditors.add(entry.getKey());
        }
    }

    // Simplify expenses by settling debts
    for (int debtor : debtors) {
        for (int creditor : creditors) {
            double debtorBalance = netBalances.get(debtor);
            double creditorBalance = netBalances.get(creditor);

            double transferAmount = Math.min(Math.abs(debtorBalance), creditorBalance);
            if (transferAmount > 0) {
                // Update net balances
                netBalances.put(debtor, debtorBalance + transferAmount);
                netBalances.put(creditor, creditorBalance - transferAmount);

                // Add a simplified expense entry
                String metadata = "Simplified expense: " + debtor + " -> " + creditor;
                addSimplifiedExpense(debtor, creditor, transferAmount, metadata);
            }
        }
    }
}

private Map<Integer, Double> calculateNetBalances() {
    Map<Integer, Double> netBalances = new HashMap<>();
    for (User user : users) {
        double netBalance = 0.0;

        for (Expense expense : user.getExpenses()) {
            if (expense.getPayerId() != user.getUserId()) {
                netBalance -= expense.getAmount();
            }

            Map<Integer, Double> splitWith = expense.getSplitWith();
            for (Map.Entry<Integer, Double> entry : splitWith.entrySet()) {
                int userId = entry.getKey();
                double amount = entry.getValue();
                if (userId == user.getUserId()) {
                    netBalance += amount;
                }
            }
        }

        netBalances.put(user.getUserId(), netBalance);
    }
    return netBalances;
}

private void addSimplifiedExpense(int debtor, int creditor, double amount, String metadata) {
    Expense simplifiedExpense = new Expense(debtor, amount, metadata);
    users.get(debtor).addExpense(simplifiedExpense);
    users.get(creditor).addExpense(simplifiedExpense);
}

