import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class WithdrawalView {

    private AccountController accountController;
    private Runnable onBack;

    public WithdrawalView(AccountController accountController,
                          Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }

    public void display(Stage stage, Customer customer) {

        Label heading = new Label("WITHDRAW MONEY");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ComboBox<String> accountChoice = new ComboBox<>();

        for (Account account :
                accountController.getCustomerAccounts(customer)) {
            accountChoice.getItems().add(account.getAccountNumber());
        }

        accountChoice.setPromptText("Select account");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter withdrawal amount");
        amountField.setMaxWidth(250);

        Label message = new Label();

        Button withdrawButton = new Button("Withdraw");
        Button backButton = new Button("Back");

        withdrawButton.setOnAction(e -> {
            try {
                String selected = accountChoice.getValue();

                if (selected == null) {
                    message.setText("Please select an account.");
                    return;
                }

                double amount = Double.parseDouble(amountField.getText());

                Account selectedAccount = null;

                for (Account account :
                        accountController.getCustomerAccounts(customer)) {
                    if (account.getAccountNumber().equals(selected)) {
                        selectedAccount = account;
                        break;
                    }
                }

                if (selectedAccount != null &&
                        accountController.withdraw(selectedAccount, amount)) {
                    message.setText("Withdrawal successful! New balance: BWP "
                            + selectedAccount.getBalance());
                } else {
                    message.setText(
                            "Withdrawal rejected. Check account rules and balance."
                    );
                }

            } catch (NumberFormatException ex) {
                message.setText("Please enter a valid number.");
            }
        });

        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading,
                accountChoice,
                amountField,
                withdrawButton,
                message,
                backButton
        );

        stage.setTitle("Withdraw Money");
        stage.setScene(new Scene(layout, 600, 450));
        stage.show();
    }
}