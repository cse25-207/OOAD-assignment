
public class SavingsAccount extends Account implements InterestBearing {

    private static final double INTEREST_RATE = 0.0005;

    public SavingsAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public boolean withdraw(double amount) {
        // Savings accounts do not allow withdrawals.
        return false;
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }
}

