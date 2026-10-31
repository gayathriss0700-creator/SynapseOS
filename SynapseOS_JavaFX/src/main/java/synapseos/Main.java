
package synapseos;

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

public class Main extends Application {

    private Stage stage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        Label title = new Label("SynapseOS");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label subtitle = new Label("Login to your account");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(280);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(280);

        Button loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setMaxWidth(280);
        loginButton.setOnAction(event -> login());

        messageLabel = new Label();
        messageLabel.setWrapText(true);

        VBox root = new VBox(
                14,
                title,
                subtitle,
                usernameField,
                passwordField,
                loginButton,
                messageLabel
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        stage.setTitle("SynapseOS Login");
        stage.setScene(new Scene(root, 420, 400));
        stage.setResizable(false);
        stage.show();
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        String role = authenticateUser(username, password);

        if (role == null) {
            messageLabel.setText("Invalid username or password.");
            passwordField.clear();
            return;
        }

        messageLabel.setText("");
        passwordField.clear();

        // Hide login while the role menu is open.
        stage.hide();

        try {
            ConsoleMenu.openRoleMenu(role);
        } finally {
            // Return to the login page after logout.
            usernameField.clear();
            passwordField.clear();
            messageLabel.setText("You have logged out.");
            stage.show();
        }
    }

    private String authenticateUser(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            return "admin";
        }

        if (username.equals("manager") && password.equals("manager123")) {
            return "manager";
        }

        if (username.equals("employee") && password.equals("employee123")) {
            return "employee";
        }

        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
