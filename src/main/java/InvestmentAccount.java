
public class InvestmentAccount extends Account implements InterestBearing {

    private static final double INTEREST_RATE = 0.05;
    private static final double MINIMUM_INITIAL_DEPOSIT = 500.00;

    public InvestmentAccount(String accountNumber, double initialDeposit,
                             String branch) {
        super(accountNumber, initialDeposit, branch);

        if (initialDeposit < MINIMUM_INITIAL_DEPOSIT) {
            throw new IllegalArgumentException(
                "Investment account requires a minimum initial deposit of BWP 500."
            );
        }
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
        return balance * INTEREST_RATE;
    }
}
