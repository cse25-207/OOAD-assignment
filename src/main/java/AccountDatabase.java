
import java.util.HashMap;
import java.util.Map;

public class AccountDatabase {

    private Map<String, Account> accounts = new HashMap<>();

    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }

        String accountNumber = account.getAccountNumber();

        if (accounts.containsKey(accountNumber)) {
            return false;
        }

        accounts.put(accountNumber, account);
        return true;
    }

    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean removeAccount(String accountNumber) {
        return accounts.remove(accountNumber) != null;
    }

    public int getAccountCount() {
        return accounts.size();
    }
}
