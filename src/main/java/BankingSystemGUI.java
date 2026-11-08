
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankingSystemGUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("BANKING MANAGEMENT SYSTEM");
        title.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #ffffff;"
        );

        Label subtitle = new Label("Welcome to your banking system");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Button exitButton = new Button("Exit");

        loginButton.setMaxWidth(200);
        registerButton.setMaxWidth(200);
        exitButton.setMaxWidth(200);

        exitButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #14213d;");

        layout.getChildren().addAll(
            title, subtitle, loginButton, registerButton, exitButton
        );

        Scene scene = new Scene(layout, 600, 400);

        stage.setTitle("Banking Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
