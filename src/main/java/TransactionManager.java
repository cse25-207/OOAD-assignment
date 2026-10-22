
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {

    private List<Transaction> transactions = new ArrayList<>();
    private int nextTransactionId = 1;

    public boolean processDeposit(Account account, double amount) {
        if (account == null || amount <= 0) {
            return false;
        }

        boolean successful = account.deposit(amount);

        if (successful) {
            recordTransaction("DEPOSIT", amount);
        }

        return successful;
    }

    public boolean processWithdrawal(Account account, double amount) {
        if (account == null || amount <= 0) {
            return false;
        }

        boolean successful = account.withdraw(amount);

        if (successful) {
            recordTransaction("WITHDRAWAL", amount);
        }

        return successful;
    }

    private void recordTransaction(String type, double amount) {
        String id = "TXN" + nextTransactionId++;

        Transaction transaction =
                new Transaction(id, type, amount);

        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
