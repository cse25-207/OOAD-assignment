
public class ChequeAccount extends Account {

    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance,
                         String branch, String employerName,
                         String employerAddress) {
        super(accountNumber, balance, branch);

        if (employerName == null || employerName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Employer name is required."
            );
        }

        if (employerAddress == null || employerAddress.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Employer address is required."
            );
        }

        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }
}
