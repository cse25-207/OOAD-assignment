import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class OpenAccountView {

    private AccountController accountController;
    private Runnable onBack;

    public OpenAccountView(AccountController accountController,
                           Runnable onBack) {
        this.accountController = accountController;
        this.onBack = onBack;
    }

    public void display(Stage stage, Customer customer) {

        Label heading = new Label("OPEN NEW ACCOUNT");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label accountTypeLabel = new Label("Select Account Type:");
        Label accountNumberLabel = new Label("Enter Account Number:");
        Label branchLabel = new Label("Select Branch:");
        Label initialDepositLabel = new Label("Initial Deposit (BWP):");

        ComboBox<String> accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll(
                "Savings", "Investment", "Cheque"
        );
        accountTypeBox.setPromptText("Choose account type");

        TextField accountNumberField = new TextField();
        accountNumberField.setPromptText("e.g. S002");

        TextField branchField = new TextField();
        branchField.setPromptText("e.g. Gaborone");

        TextField initialDepositField = new TextField();
        initialDepositField.setPromptText("Enter opening deposit");

        Label employerNameLabel =
                new Label("Employer Company Name:");

        TextField employerNameField = new TextField();
        employerNameField.setPromptText("Enter employer name");

        Label employerAddressLabel =
                new Label("Employer Company Address:");

        TextField employerAddressField = new TextField();
        employerAddressField.setPromptText("Enter employer address");

        Button createAccountButton = new Button("Create Account");
        Button backButton = new Button("Back to Dashboard");

        Label message = new Label();

        createAccountButton.setOnAction(e -> {
            try {
                String accountNumber =
                        accountNumberField.getText().trim();

                String accountType = accountTypeBox.getValue();

                String branch = branchField.getText().trim();

                if (accountNumber.isEmpty()
                        || accountType == null
                        || branch.isEmpty()
                        || initialDepositField.getText().trim().isEmpty()) {

                    message.setText(
                            "Please complete all required fields."
                    );
                    return;
                }

                double initialDeposit = Double.parseDouble(
                        initialDepositField.getText().trim()
                );

                boolean success = accountController.openAccount(
                        customer,
                        accountType,
                        accountNumber,
                        initialDeposit,
                        branch,
                        employerNameField.getText().trim(),
                        employerAddressField.getText().trim()
                );

                if (success) {
                    message.setText("Account opened successfully!");
                } else {
                    message.setText(
                            "Account number already exists. Please use another number."
                    );
                }

            } catch (NumberFormatException ex) {
                message.setText("Please enter a valid deposit amount.");
            } catch (IllegalArgumentException ex) {
                message.setText(ex.getMessage());
            }
        });

        backButton.setOnAction(e -> onBack.run());

        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        layout.getChildren().addAll(
                heading,
                accountTypeLabel,
                accountTypeBox,
                accountNumberLabel,
                accountNumberField,
                branchLabel,
                branchField,
                initialDepositLabel,
                initialDepositField,
                employerNameLabel,
                employerNameField,
                employerAddressLabel,
                employerAddressField,
                createAccountButton,
                backButton,
                message
        );

        stage.setTitle("Open New Account");
        stage.setScene(new Scene(layout, 600, 700));
        stage.show();
    }
}