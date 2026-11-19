package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO {

    public boolean saveAccount(
            String accountNumber,
            int customerId,
            String accountType,
            double balance,
            String branch,
            String employerName,
            String employerAddress) {

        String sql = """
            INSERT INTO accounts (
                account_number, customer_id, account_type,
                balance, branch, employer_name, employer_address
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            statement.setInt(2, customerId);
            statement.setString(3, accountType);
            statement.setDouble(4, balance);
            statement.setString(5, branch);
            statement.setString(6, employerName);
            statement.setString(7, employerAddress);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Could not save account: " + e.getMessage()
            );
            return false;
        }
    }

    public boolean accountExists(String accountNumber) {

        String sql =
                "SELECT account_number FROM accounts WHERE account_number = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);

            try (java.sql.ResultSet result =
                         statement.executeQuery()) {

                return result.next();
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not check account: " + e.getMessage()
            );
            return false;
        }
    }
    public java.util.List<String[]> getAllAccountRecords() {

        java.util.List<String[]> accounts =
                new java.util.ArrayList<>();

        String sql = "SELECT * FROM accounts";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             java.sql.ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                String[] account = {
                        result.getString("account_number"),
                        String.valueOf(result.getInt("customer_id")),
                        result.getString("account_type"),
                        String.valueOf(result.getDouble("balance")),
                        result.getString("branch"),
                        result.getString("employer_name"),
                        result.getString("employer_address")
                };

                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Could not retrieve accounts: " + e.getMessage()
            );
        }

        return accounts;
    }
    public boolean updateAccountBalance(
            String accountNumber,
            double newBalance) {

        String sql =
                "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);
            statement.setString(2, accountNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Could not update account balance: " + e.getMessage()
            );
            return false;
        }
    }
}