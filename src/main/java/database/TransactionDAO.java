package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionDAO {

    public boolean saveTransaction(
            String accountNumber,
            String transactionType,
            double amount,
            String details) {

        String sql = """
            INSERT INTO bank_transactions (
                account_number,
                transaction_type,
                amount,
                transaction_date,
                details
            )
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            statement.setString(2, transactionType);
            statement.setDouble(3, amount);
            statement.setTimestamp(
                    4, new Timestamp(System.currentTimeMillis())
            );
            statement.setString(5, details);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Could not save transaction: " + e.getMessage()
            );
            return false;
        }
    }
    public java.util.List<String[]> getTransactionsForAccount(
            String accountNumber) {

        java.util.List<String[]> transactions =
                new java.util.ArrayList<>();

        String sql = """
        SELECT transaction_id, transaction_type,
               amount, transaction_date, details
        FROM bank_transactions
        WHERE account_number = ?
        ORDER BY transaction_id DESC
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (java.sql.ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String[] transaction = {
                            String.valueOf(result.getLong("transaction_id")),
                            result.getString("transaction_type"),
                            String.valueOf(result.getDouble("amount")),
                            String.valueOf(result.getTimestamp("transaction_date")),
                            result.getString("details")
                    };

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not retrieve transactions: " + e.getMessage()
            );
        }

        return transactions;
    }
}