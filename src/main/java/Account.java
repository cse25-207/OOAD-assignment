
public abstract class Account {

    private String accountNumber;
    protected double balance;
    private String branch;

    public Account(String accountNumber, double balance, String branch) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

    public abstract boolean withdraw(double amount);

    public abstract double calculateInterest();

    public String getAccountDetails() {
        return "Account Number: " + accountNumber
                + "\nBalance: BWP " + balance
                + "\nBranch: " + branch;
    }
}
