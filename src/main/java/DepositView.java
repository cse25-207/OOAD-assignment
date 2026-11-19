import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.util.List;

public class DepositView {

    private AccountController accountController;
    private Runnable onBack;

    public DepositView(AccountController accountController,
                       Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }

    public void display(Stage stage, Customer customer) {

        Label heading = new Label("DEPOSIT MONEY");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label accountLabel = new Label("Select Account:");

        ComboBox<String> accountChoice = new ComboBox<>();

        for (Account account :
                accountController.getCustomerAccounts(customer)) {
            accountChoice.getItems().add(account.getAccountNumber());
        }

        accountChoice.setPromptText("Choose account");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter deposit amount");
        amountField.setMaxWidth(250);

        Label message = new Label();

        Button depositButton = new Button("Deposit");
        Button backButton = new Button("Back");

        depositButton.setOnAction(e -> {
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
                        accountController.deposit(selectedAccount, amount)) {
                    message.setText("Deposit successful! New balance: BWP "
                            + selectedAccount.getBalance());
                } else {
                    message.setText("Enter a valid amount greater than zero.");
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
                accountLabel,
                accountChoice,
                amountField,
                depositButton,
                message,
                backButton
        );

        stage.setTitle("Deposit Money");
        stage.setScene(new Scene(layout, 600, 450));
        stage.show();
    }
}