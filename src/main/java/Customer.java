import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerId;
    private String address;
    private String username;
    private String password;

    private List<Account> accounts = new ArrayList<>();

    public Customer(int customerId, String address,
                    String username, String password) {
        this.customerId = customerId;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean verifyCredentials(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }
    public boolean addAccount(Account account) {
        if (account == null) return false;

        for (Account existingAccount : accounts) {
            if (existingAccount.getAccountNumber()
                    .equals(account.getAccountNumber())) {
                return false;
            }
        }

        accounts.add(account);
        return true;
    }
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
}