
public class ChequeAccount extends Account {

    private String employerName;

    public ChequeAccount(String accountNumber, double balance,
                         String branch, String employerName) {
        super(accountNumber, balance, branch);

        if (employerName == null || employerName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Employer name is required for a cheque account."
            );
        }

        this.employerName = employerName;
    }

    public String getEmployerName() {
        return employerName;
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
