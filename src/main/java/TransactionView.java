import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class TransactionView {

    private AccountController accountController;
    private Runnable onBack;

    public TransactionView(AccountController accountController,
                           Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }

    public void display(Stage stage, Customer customer) {

        Label heading = new Label("Transaction History");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(heading);

        layout.getChildren().add(
                new Label("Customer ID: " + customer.getCustomerId())
        );

        java.util.List<String[]> transactions =
                accountController.getCustomerDatabaseTransactions(customer);

        if (transactions.isEmpty()) {

            layout.getChildren().add(
                    new Label("No transactions recorded yet.")
            );

        } else {

            for (String[] transaction : transactions) {

                Label details = new Label(
                        "Transaction ID: " + transaction[0] +
                                "\nType: " + transaction[1] +
                                "\nAmount: BWP " + transaction[2] +
                                "\nDate: " + transaction[3] +
                                "\nDetails: " + transaction[4]
                );

                layout.getChildren().add(details);
            }
        }

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> onBack.run());

        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Transaction History");
        stage.show();
    }
}