
public class BankingSystemTest {

    public static void main(String[] args) {

        SavingsAccount savings =
                new SavingsAccount("S001", 1000.00, "Gaborone");

        InvestmentAccount investment =
                new InvestmentAccount("I001", 2000.00, "Gaborone");

        TransactionManager manager = new TransactionManager();

        manager.processDeposit(savings, 500.00);
        manager.processWithdrawal(investment, 200.00);

        System.out.println("Savings balance: BWP "
                + savings.getBalance());

        System.out.println("Investment balance: BWP "
                + investment.getBalance());

        System.out.println("Transactions recorded: "
                + manager.getTransactions().size());
    }
}
