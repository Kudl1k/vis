package cz.kudladev.auth.login;

import DomainModels.UserDomainModel;
import Services.UserService;
import cz.kudladev.core.LiteSportAppState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private UserService userService;

    @FXML
    private TextField UsernameLoginTextField;
    @FXML
    private TextField PasswordLoginTextField;
    @FXML
    private CheckBox RememberMeCheckBox;
    @FXML
    private Label ErrorMessageLabel;
    @FXML
    private Button LoginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();
        System.out.println("LoginController initialized");
        UsernameLoginTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        PasswordLoginTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        RememberMeCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            LiteSportAppState.getInstance().setRememberMe(newValue);
        });
    }
    private boolean clickedLogin = false;

    @FXML
    private void onLoginClicked() {
        String username = UsernameLoginTextField.getText();
        String password = PasswordLoginTextField.getText();
        UserDomainModel user = userService.Login(username, password);
        if (user != null) {
            LiteSportAppState.getInstance().setLoggedInUser(user);
            System.out.println("Logged in user: " + user.toString());
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.close();
            System.out.println("Login successful");
        } else {
            ErrorMessageLabel.setText("Invalid username or password");
        }
        clickedLogin = true;
    }

    private void resetError(){
        if(clickedLogin){
            ErrorMessageLabel.setText("");
        }
        clickedLogin = false;
    }
}
