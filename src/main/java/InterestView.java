import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class InterestView {

    private AccountController accountController;
    private Runnable onBack;

    public InterestView(AccountController accountController,
                        Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }

    public void display(Stage stage, Customer customer) {

        Label heading = new Label("MONTHLY INTEREST PAYMENT");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ComboBox<String> accountChoice = new ComboBox<>();

        for (Account account :
                accountController.getCustomerAccounts(customer)) {
            if (account instanceof InterestBearing) {
                accountChoice.getItems().add(account.getAccountNumber());
            }
        }

        accountChoice.setPromptText("Select account");

        Label message = new Label();

        Button payButton = new Button("Pay Interest");
        Button backButton = new Button("Back to Dashboard");

        payButton.setOnAction(e -> {

            String selected = accountChoice.getValue();

            if (selected == null) {
                message.setText("Please select an account.");
                return;
            }

            for (Account account :
                    accountController.getCustomerAccounts(customer)) {

                if (account.getAccountNumber().equals(selected)) {

                    if (accountController.payMonthlyInterest(account)) {
                        message.setText(
                                "Interest paid successfully! New balance: BWP "
                                        + account.getBalance()
                        );
                    } else {
                        message.setText("No interest payment available.");
                    }

                    break;
                }
            }
        });

        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading,
                accountChoice,
                payButton,
                message,
                backButton
        );

        stage.setTitle("Monthly Interest");
        stage.setScene(new Scene(layout, 600, 450));
        stage.show();
    }
}