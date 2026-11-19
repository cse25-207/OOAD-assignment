
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

            recordTransaction(
                    "DEPOSIT",
                    amount,
                    account.getAccountNumber()
            );

            database.TransactionDAO transactionDAO =
                    new database.TransactionDAO();

            transactionDAO.saveTransaction(
                    account.getAccountNumber(),
                    "DEPOSIT",
                    amount,
                    "Money deposited successfully"
            );

            database.AccountDAO accountDAO =
                    new database.AccountDAO();

            accountDAO.updateAccountBalance(
                    account.getAccountNumber(),
                    account.getBalance()
            );
        }

        return successful;
    }

    public boolean processWithdrawal(Account account, double amount) {

        if (account == null || amount <= 0) {
            return false;
        }

        boolean successful = account.withdraw(amount);

        if (successful) {

            recordTransaction(
                    "WITHDRAWAL",
                    amount,
                    account.getAccountNumber()
            );

            database.TransactionDAO transactionDAO =
                    new database.TransactionDAO();

            transactionDAO.saveTransaction(
                    account.getAccountNumber(),
                    "WITHDRAWAL",
                    amount,
                    "Money withdrawn successfully"
            );

            database.AccountDAO accountDAO =
                    new database.AccountDAO();

            accountDAO.updateAccountBalance(
                    account.getAccountNumber(),
                    account.getBalance()
            );
        }

        return successful;
    }

    public boolean payMonthlyInterest(Account account) {

        if (account == null) {
            return false;
        }

        double interest = account.calculateInterest();

        if (interest <= 0) {
            return false;
        }

        boolean successful = account.deposit(interest);

        if (successful) {

            recordTransaction(
                    "INTEREST PAYMENT",
                    interest,
                    account.getAccountNumber()
            );

            database.TransactionDAO transactionDAO =
                    new database.TransactionDAO();

            transactionDAO.saveTransaction(
                    account.getAccountNumber(),
                    "INTEREST PAYMENT",
                    interest,
                    "Monthly interest paid successfully"
            );

            database.AccountDAO accountDAO =
                    new database.AccountDAO();

            accountDAO.updateAccountBalance(
                    account.getAccountNumber(),
                    account.getBalance()
            );
        }

        return successful;
    }

    private void recordTransaction(String type, double amount, String accountNumber){
        String id = "TXN" + nextTransactionId++;

        Transaction transaction = new Transaction(id, type, amount, accountNumber);
        transactions.add(transaction);
    }
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
