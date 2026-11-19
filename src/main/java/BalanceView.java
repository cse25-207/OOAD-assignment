import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class BalanceView {

    private AccountController accountController;
    private Runnable onBack;

    public BalanceView(AccountController accountController,
                       Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }

    public void display(Stage stage, Customer customer) {

        Label heading = new Label("ACCOUNT BALANCES");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox balanceList = new VBox(15);
        balanceList.setAlignment(Pos.CENTER);

        for (Account account :
                accountController.getCustomerAccounts(customer)) {

            String accountType;

            if (account instanceof SavingsAccount) {
                accountType = "Savings Account";
            } else if (account instanceof InvestmentAccount) {
                accountType = "Investment Account";
            } else if (account instanceof ChequeAccount) {
                accountType = "Cheque Account";
            } else {
                accountType = "Bank Account";
            }

            Label accountBalance = new Label(
                    accountType + " (" + account.getAccountNumber()
                            + "): BWP "
                            + accountController.checkBalance(account)
            );

            balanceList.getChildren().add(accountBalance);
        }

        if (accountController.getCustomerAccounts(customer).isEmpty()) {
            balanceList.getChildren().add(
                    new Label("You have no accounts yet.")
            );
        }

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading,
                balanceList,
                backButton
        );

        stage.setTitle("Account Balances");
        stage.setScene(new Scene(layout, 600, 400));
        stage.show();
    }
}
