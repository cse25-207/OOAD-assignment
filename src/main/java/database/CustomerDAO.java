package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {

    public boolean saveCustomer(
            int customerId,
            String customerType,
            String address,
            String username,
            String password,
            String firstName,
            String surname,
            String email,
            String phone,
            String companyName,
            String registrationNumber) {

        String sql = """
            INSERT INTO customers (
                customer_id, customer_type, address,
                username, password, first_name, surname,
                email, phone, company_name, registration_number
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.setString(2, customerType);
            statement.setString(3, address);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.setString(6, firstName);
            statement.setString(7, surname);
            statement.setString(8, email);
            statement.setString(9, phone);
            statement.setString(10, companyName);
            statement.setString(11, registrationNumber);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Could not save customer: " + e.getMessage()
            );
            return false;
        }
    }
    public boolean customerExists(int customerId) {

        String sql =
                "SELECT customer_id FROM customers WHERE customer_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (java.sql.ResultSet result =
                         statement.executeQuery()) {

                return result.next();
            }

        } catch (java.sql.SQLException e) {
            System.out.println(
                    "Could not check customer: " + e.getMessage()
            );
            return false;
        }
    }
    public int getNextCustomerId() {

        String sql =
                "SELECT COALESCE(MAX(customer_id), 0) + 1 AS next_id FROM customers";

        try (java.sql.Connection connection =
                     DatabaseConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql);
             java.sql.ResultSet result =
                     statement.executeQuery()) {

            if (result.next()) {
                return result.getInt("next_id");
            }

        } catch (java.sql.SQLException e) {
            System.out.println(
                    "Could not generate customer ID: " + e.getMessage()
            );
        }

        return -1;
    }
    public java.util.List<String[]> getAllCustomerRecords() {

        java.util.List<String[]> customers =
                new java.util.ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (java.sql.Connection connection =
                     DatabaseConnection.getConnection();
             java.sql.PreparedStatement statement =
                     connection.prepareStatement(sql);
             java.sql.ResultSet result =
                     statement.executeQuery()) {

            while (result.next()) {

                String[] customer = {
                        String.valueOf(result.getInt("customer_id")),
                        result.getString("customer_type"),
                        result.getString("address"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("first_name"),
                        result.getString("surname"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("company_name"),
                        result.getString("registration_number")
                };

                customers.add(customer);
            }

        } catch (java.sql.SQLException e) {
            System.out.println(
                    "Could not retrieve customers: " + e.getMessage()
            );
        }

        return customers;
    }
}