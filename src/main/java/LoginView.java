import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private LoginController loginController;
    private List<Customer> customers;
    private Runnable onLoginSuccess;

    public LoginView(LoginController loginController,
                     List<Customer> customers,
                     Runnable onLoginSuccess) {
        this.loginController = loginController;
        this.customers = customers;
        this.onLoginSuccess = onLoginSuccess;
    }

    public void display(Stage stage) {

        Label heading = new Label("CUSTOMER LOGIN");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Label message = new Label();

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Customer matchingCustomer = null;

            for (Customer customer : customers) {
                if (loginController.login(customer, username, password)) {
                    matchingCustomer = customer;
                    break;
                }
            }

            if (matchingCustomer != null) {
                message.setText("Login successful!");
                onLoginSuccess.run();
            } else {
                message.setText("Invalid username or password.");
            }
        });


        VBox layout = new VBox(15);
        layout.getChildren().addAll(
                heading,
                usernameField,
                passwordField,
                loginButton,
                message
        );

        Scene scene = new Scene(layout, 400, 350);

        stage.setTitle("Banking System - Login");
        stage.setScene(scene);
        stage.show();
    }
}