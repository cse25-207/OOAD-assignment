import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountView {
    private AccountController accountController;
    private Runnable onBack;

    public AccountView(AccountController accountController, Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }
    public void displayAccounts(Stage stage, Customer customer) {

        Label heading = new Label("MY BANK ACCOUNTS");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox layout = new VBox(20);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(30));

        layout.getChildren().add(heading);

        if (accountController.getCustomerAccounts(customer).isEmpty()) {

            layout.getChildren().add(
                    new Label("You have no bank accounts yet.")
            );

        } else {

            for (Account account :
                    accountController.getCustomerAccounts(customer)) {

                String accountType;

                if (account instanceof SavingsAccount) {
                    accountType = "SAVINGS ACCOUNT";
                } else if (account instanceof InvestmentAccount) {
                    accountType = "INVESTMENT ACCOUNT";
                } else if (account instanceof ChequeAccount) {
                    accountType = "CHEQUE ACCOUNT";
                } else {
                    accountType = "BANK ACCOUNT";
                }

                Label accountDetails = new Label(
                        accountType + "\n" + account.getAccountDetails()
                );

                layout.getChildren().add(accountDetails);
            }
        }

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> onBack.run());

        layout.getChildren().add(backButton);

        stage.setTitle("My Bank Accounts");
        stage.setScene(new Scene(layout, 600, 600));
        stage.show();
    }
}