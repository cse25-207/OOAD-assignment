
public class AuthenticationService {

    private Customer loggedInCustomer;

    public boolean login(Customer customer, String username,
                         String password) {
        if (customer == null
                || username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }

        // Placeholder for credential verification.
        // Real credential checking will be added later.
        loggedInCustomer = customer;
        return true;
    }

    public void logout() {
        loggedInCustomer = null;
    }

    public boolean isLoggedIn() {
        return loggedInCustomer != null;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}
