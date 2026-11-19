import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankingSystemGUI extends Application {

    private List<Customer> customers = new ArrayList<>();

    private AuthenticationService authenticationService =
            new AuthenticationService();

    private TransactionManager transactionManager =
            new TransactionManager();

    private LoginController loginController =
            new LoginController(authenticationService);

    private AccountController accountController =
            new AccountController(transactionManager);

    private Customer demoCustomer =
            new Customer(1, "Gaborone", "customer1", "12345");
    private SavingsAccount savings =
            new SavingsAccount("S001", 1000.00, "Gaborone");

    private InvestmentAccount investment =
            new InvestmentAccount("I001", 2000.00, "Gaborone");

    @Override
    public void start(Stage stage) {
        database.DatabaseInitializer.initializeDatabase();
        database.CustomerDAO customerDAO =
                new database.CustomerDAO();

        for (String[] record : customerDAO.getAllCustomerRecords()) {

            int customerId = Integer.parseInt(record[0]);

            if ("Individual".equalsIgnoreCase(record[1])) {

                IndividualCustomer customer =
                        new IndividualCustomer(
                                customerId,
                                record[2],
                                record[3],
                                record[4],
                                record[5],
                                record[6],
                                record[7],
                                record[8]
                        );

                customers.add(customer);

            } else if ("Company".equalsIgnoreCase(record[1])) {

                CompanyCustomer customer =
                        new CompanyCustomer(
                                customerId,
                                record[2],
                                record[3],
                                record[4],
                                record[9],
                                record[10]
                        );

                customers.add(customer);
            }
        }

        if (!customers.contains(demoCustomer)) {
            customers.add(demoCustomer);
        }
        demoCustomer.addAccount(savings);
        demoCustomer.addAccount(investment);
        database.AccountDAO accountDAO =
                new database.AccountDAO();

        for (String[] record : accountDAO.getAllAccountRecords()) {

            int customerId = Integer.parseInt(record[1]);

            for (Customer customer : customers) {

                if (customer.getCustomerId() == customerId) {

                    Account account = null;

                    switch (record[2]) {

                        case "Savings":
                            account = new SavingsAccount(
                                    record[0],
                                    Double.parseDouble(record[3]),
                                    record[4]
                            );
                            break;

                        case "Investment":
                            account = new InvestmentAccount(
                                    record[0],
                                    Double.parseDouble(record[3]),
                                    record[4]
                            );
                            break;

                        case "Cheque":
                            account = new ChequeAccount(
                                    record[0],
                                    Double.parseDouble(record[3]),
                                    record[4],
                                    record[5],
                                    record[6]
                            );
                            break;
                    }

                    if (account != null) {
                        customer.addAccount(account);
                    }

                    break;
                }
            }
        }

        Label heading = new Label("BANKING MANAGEMENT SYSTEM");
        heading.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Label subtitle = new Label("Welcome to your Banking System");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Button exitButton = new Button("Exit");

        loginButton.setPrefWidth(180);
        registerButton.setPrefWidth(180);
        exitButton.setPrefWidth(180);

        loginButton.setOnAction(e -> showLoginScreen(stage));
        registerButton.setOnAction(e -> showRegistrationScreen(stage));
        exitButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading, subtitle,
                loginButton, registerButton, exitButton
        );

        stage.setTitle("Banking Management System");
        stage.setScene(new Scene(layout, 600, 400));
        stage.show();
    }

    private void showLoginScreen(Stage stage) {

        Label heading = new Label("CUSTOMER LOGIN");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(250);

        Label message = new Label();

        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Customer matchingCustomer = null;

            for (Customer customer : customers) {
                if (customer.verifyCredentials(username, password)) {
                    matchingCustomer = customer;
                    break;
                }
            }

            boolean successful = matchingCustomer != null
                    && loginController.login(matchingCustomer, username, password);

            if (successful) {
                showDashboard(stage);
            } else {
                message.setText("Invalid username or password.");
            }
        });

        backButton.setOnAction(e -> start(stage));

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading,
                usernameField,
                passwordField,
                loginButton,
                message,
                backButton
        );

        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Customer Login");
    }
    private void showDashboard(Stage stage) {

        Label heading = new Label("CUSTOMER DASHBOARD");
        heading.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        Customer currentCustomer = loginController.getLoggedInCustomer();

        Label welcome = new Label(
                "Welcome, Customer " + currentCustomer.getCustomerId()
        );

        Button openAccount = new Button("Open New Account");
        openAccount.setPrefWidth(220);
        openAccount.setOnAction(e -> showOpenAccountScreen(stage));
        Button accounts = new Button("1. View My Accounts");
        accounts.setPrefWidth(220);
        accounts.setOnAction(e -> showAccounts(stage));
        Button deposit = new Button("2. Deposit Money");
        deposit.setPrefWidth(220);
        deposit.setOnAction(e -> showDepositScreen(stage));
        Button withdraw = new Button("3. Withdraw Money");
        withdraw.setPrefWidth(220);
        withdraw.setOnAction(e -> showWithdrawScreen(stage));
        Button balance = new Button("4. Check Account Balance");
        balance.setPrefWidth(220);
        balance.setOnAction(e -> showBalanceScreen(stage));
        Button history = new Button("5. Transaction History");
        history.setPrefWidth(200);
        history.setOnAction(e -> showTransactionHistory(stage));
        Button interestButton = new Button("Pay Monthly Interest");
        interestButton.setOnAction(e -> showInterestScreen(stage));

        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(e -> {
            loginController.logout();
            start(stage);
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading,
                welcome,
                openAccount,
                accounts,
                deposit,
                withdraw,
                balance,
                history,
                interestButton,
                logoutButton
        );

        stage.setTitle("Customer Dashboard");
        stage.setScene(new Scene(layout, 600, 500));
    }
    private void showInterestScreen(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        InterestView interestView =
                new InterestView(
                        accountController,
                        () -> showDashboard(stage)
                );

        interestView.display(stage, currentCustomer);
    }


    private void showOpenAccountScreen(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        OpenAccountView openAccountView =
                new OpenAccountView(
                        accountController,
                        () -> showDashboard(stage)
                );

        openAccountView.display(stage, currentCustomer);
    }


    private void showAccounts(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        AccountView accountView =
                new AccountView(accountController,
                        () -> showDashboard(stage));

        accountView.displayAccounts(stage, currentCustomer);
    }

    private void showDepositScreen(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        DepositView depositView =
                new DepositView(
                        accountController,
                        () -> showDashboard(stage)
                );

        depositView.display(stage, currentCustomer);
    }

    private void showWithdrawScreen(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        WithdrawalView withdrawalView =
                new WithdrawalView(
                        accountController,
                        () -> showDashboard(stage)
                );

        withdrawalView.display(stage, currentCustomer);
    }
    private void showBalanceScreen(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        BalanceView balanceView =
                new BalanceView(
                        accountController,
                        () -> showDashboard(stage)
                );

        balanceView.display(stage, currentCustomer);
    }

    private void showTransactionHistory(Stage stage) {

        Customer currentCustomer =
                loginController.getLoggedInCustomer();

        TransactionView transactionView =
                new TransactionView(
                        accountController,
                        () -> showDashboard(stage)
                );

        transactionView.display(stage, currentCustomer);
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void showRegistrationScreen(Stage stage) {

        Label heading = new Label("Customer Registration");

        Button individualButton =
                new Button("Register as Individual");

        Button companyButton =
                new Button("Register as Company");

        Button backButton =
                new Button("Back");

        individualButton.setPrefWidth(220);
        companyButton.setPrefWidth(220);
        backButton.setPrefWidth(220);

        individualButton.setOnAction(
                e -> showIndividualRegistration(stage)
        );

        companyButton.setOnAction(
                e -> showCompanyRegistration(stage)
        );

        backButton.setOnAction(e -> start(stage));

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.getChildren().addAll(
                heading,
                individualButton,
                companyButton,
                backButton
        );

        stage.setTitle("Customer Registration");
        stage.setScene(new Scene(layout, 600, 400));
        stage.show();
    }
    private void showIndividualRegistration(Stage stage) {

        Label heading = new Label("Individual Customer Registration");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label message = new Label();

        Button registerButton = new Button("Create Account");
        Button backButton = new Button("Back");

        registerButton.setOnAction(e -> {

                    if (firstNameField.getText().trim().isEmpty()
                            || surnameField.getText().trim().isEmpty()
                            || addressField.getText().trim().isEmpty()
                            || emailField.getText().trim().isEmpty()
                            || phoneField.getText().trim().isEmpty()
                            || usernameField.getText().trim().isEmpty()
                            || passwordField.getText().isEmpty()) {

                        message.setText("Please complete all fields.");

                    } else {

                        database.CustomerDAO customerDAO =
                                new database.CustomerDAO();

                        int customerId = customerDAO.getNextCustomerId();

                        if (customerId == -1) {
                            message.setText("Could not generate customer ID.");
                            return;
                        }

                        IndividualCustomer customer =
                                new IndividualCustomer(
                                        customerId,
                                        addressField.getText().trim(),
                                        usernameField.getText().trim(),
                                        passwordField.getText(),
                                        firstNameField.getText().trim(),
                                        surnameField.getText().trim(),
                                        emailField.getText().trim(),
                                        phoneField.getText().trim()
                                );


                        boolean saved = customerDAO.saveCustomer(
                                customerId,
                                "Individual",
                                addressField.getText().trim(),
                                usernameField.getText().trim(),
                                passwordField.getText(),
                                firstNameField.getText().trim(),
                                surnameField.getText().trim(),
                                emailField.getText().trim(),
                                phoneField.getText().trim(),
                                null,
                                null
                        );

                        if (saved) {
                            customers.add(customer);
                            message.setText("Registration successful!");
                        } else {
                            message.setText(
                                    "Registration failed. Username may already exist."
                            );
                        }
                    }
                });

        backButton.setOnAction(e -> showRegistrationScreen(stage));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                heading,
                firstNameField,
                surnameField,
                addressField,
                emailField,
                phoneField,
                usernameField,
                passwordField,
                registerButton,
                message,
                backButton
        );

        Scene scene = new Scene(layout, 500, 650);
        stage.setTitle("Individual Registration");
        stage.setScene(scene);
        stage.show();
    }

    private void showCompanyRegistration(Stage stage) {

        Label heading = new Label("Company Registration");

        TextField companyNameField = new TextField();
        companyNameField.setPromptText("Company Name");

        TextField registrationNumberField = new TextField();
        registrationNumberField.setPromptText("Registration Number");

        TextField addressField = new TextField();
        addressField.setPromptText("Company Address");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label message = new Label();

        Button registerButton = new Button("Create Account");
        Button backButton = new Button("Back");

        registerButton.setOnAction(e -> {

                    if (companyNameField.getText().trim().isEmpty()
                            || registrationNumberField.getText().trim().isEmpty()
                            || addressField.getText().trim().isEmpty()
                            || usernameField.getText().trim().isEmpty()
                            || passwordField.getText().isEmpty()) {

                        message.setText("Please complete all fields.");

                    } else {

                        database.CustomerDAO customerDAO =
                                new database.CustomerDAO();

                        int customerId = customerDAO.getNextCustomerId();

                        if (customerId == -1) {
                            message.setText("Could not generate customer ID.");
                            return;
                        }

                        CompanyCustomer customer =
                                new CompanyCustomer(
                                        customerId,
                                        addressField.getText().trim(),
                                        usernameField.getText().trim(),
                                        passwordField.getText(),
                                        companyNameField.getText().trim(),
                                        registrationNumberField.getText().trim()
                                );


                        boolean saved = customerDAO.saveCustomer(
                                customerId,
                                "Company",
                                addressField.getText().trim(),
                                usernameField.getText().trim(),
                                passwordField.getText(),
                                null,
                                null,
                                null,
                                null,
                                companyNameField.getText().trim(),
                                registrationNumberField.getText().trim()
                        );

                        if (saved) {
                            customers.add(customer);
                            message.setText("Company registration successful!");
                        } else {
                            message.setText(
                                    "Registration failed. Username may already exist."
                            );
                        }
                    }
                });

        backButton.setOnAction(e -> showRegistrationScreen(stage));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                heading,
                companyNameField,
                registrationNumberField,
                addressField,
                usernameField,
                passwordField,
                registerButton,
                message,
                backButton
        );

        Scene scene = new Scene(layout, 500, 550);
        stage.setTitle("Company Registration");
        stage.setScene(scene);
        stage.show();
    }
}
