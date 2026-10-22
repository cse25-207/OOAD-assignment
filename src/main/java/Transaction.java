
import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionDate;

    public Transaction(String transactionId, String transactionType,
                       double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Transaction amount must be greater than zero."
            );
        }

        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionDetails() {
        return "Transaction ID: " + transactionId
                + "\nType: " + transactionType
                + "\nAmount: BWP " + amount
                + "\nDate: " + transactionDate;
    }
}
