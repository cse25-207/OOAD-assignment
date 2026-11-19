public class AccountController {

    private TransactionManager transactionManager;

    public AccountController(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public boolean openAccount(Customer customer, Account account) {
        if (customer == null || account == null) {
            return false;
        }

        return customer.addAccount(account);
    }

    public boolean deposit(Account account, double amount) {
        return transactionManager.processDeposit(account, amount);
    }

    public boolean withdraw(Account account, double amount) {
        return transactionManager.processWithdrawal(account, amount);
    }

    public double checkBalance(Account account) {
        if (account == null) {
            return 0;
        }

        return account.getBalance();
    }

    public boolean payMonthlyInterest(Account account) {
        return transactionManager.payMonthlyInterest(account);
    }

    public java.util.List<Account> getCustomerAccounts(Customer customer) {
        if (customer == null) {
            return java.util.Collections.emptyList();
        }

        return customer.getAccounts();
    }

    public java.util.List<Transaction> getTransactions() {
        return transactionManager.getTransactions();
    }

    public boolean openAccount(
            Customer customer,
            String accountType,
            String accountNumber,
            double initialDeposit,
            String branch,
            String employerName,
            String employerAddress) {

        if (customer == null || initialDeposit < 0) {
            throw new IllegalArgumentException(
                    "Invalid customer or deposit amount."
            );
        }

        Account newAccount;

        switch (accountType) {

            case "Savings":
                newAccount = new SavingsAccount(
                        accountNumber, initialDeposit, branch);
                break;

            case "Investment":
                newAccount = new InvestmentAccount(
                        accountNumber, initialDeposit, branch);
                break;

            case "Cheque":
                newAccount = new ChequeAccount(
                        accountNumber,
                        initialDeposit,
                        branch,
                        employerName,
                        employerAddress);
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid account type.");
        }

        // Check for duplicate account numbers in memory.
        for (Account existing : customer.getAccounts()) {
            if (existing.getAccountNumber().equals(accountNumber)) {
                return false;
            }
        }

        // Check the database for duplicate account numbers.
        database.AccountDAO accountDAO =
                new database.AccountDAO();

        if (accountDAO.accountExists(accountNumber)) {
            return false;
        }

        // Save the account in the database.
        boolean saved = accountDAO.saveAccount(
                accountNumber,
                customer.getCustomerId(),
                accountType,
                initialDeposit,
                branch,
                employerName,
                employerAddress
        );

        if (!saved) {
            return false;
        }

        // Add the account to the customer's in-memory account list.
        return openAccount(customer, newAccount);
    }
    public java.util.List<String[]> getCustomerDatabaseTransactions(
            Customer customer) {

        java.util.List<String[]> allTransactions =
                new java.util.ArrayList<>();

        if (customer == null) {
            return allTransactions;
        }

        database.TransactionDAO transactionDAO =
                new database.TransactionDAO();

        for (Account account : customer.getAccounts()) {

            allTransactions.addAll(
                    transactionDAO.getTransactionsForAccount(
                            account.getAccountNumber()
                    )
            );
        }

        return allTransactions;
    }
}