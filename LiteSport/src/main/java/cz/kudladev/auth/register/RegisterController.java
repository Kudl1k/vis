package cz.kudladev.auth.register;

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

public class RegisterController implements Initializable {


    @FXML
    public TextField NameRegisterTextField;
    @FXML
    public TextField SurnameRegisterTextField;
    @FXML
    public TextField EmailRegisterTextField;
    @FXML
    public TextField PasswordRegisterTextField;
    @FXML
    public TextField PasswordConfirmRegisterTextField;
    @FXML
    public CheckBox AdminCheckbox;
    @FXML
    public Label ErrorMessageLabel;
    @FXML
    public Button RegisterButton;

    private boolean clickedRegister = false;

    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();
        System.out.println("RegisterController initialized");
        NameRegisterTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        SurnameRegisterTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        EmailRegisterTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        PasswordRegisterTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        PasswordConfirmRegisterTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
    }

    @FXML
    public void onRegisterButtonClicked() {
        System.out.println("Register button clicked");
        String name = NameRegisterTextField.getText();
        String surname = SurnameRegisterTextField.getText();
        String email = EmailRegisterTextField.getText();
        String password = PasswordRegisterTextField.getText();
        String passwordConfirm = PasswordConfirmRegisterTextField.getText();


        boolean isPasswordValid = password.equals(passwordConfirm);
        boolean isEmailValid = email.contains("@");
        boolean isNameValid = !name.isEmpty();
        boolean isSurnameValid = !surname.isEmpty();

        if (isPasswordValid && isEmailValid && isNameValid && isSurnameValid) {
            System.out.println("Registering user");
            boolean result = userService.CreateUser(name, surname, email, password, AdminCheckbox.isSelected() ? "admin" : "user");
            if (result) {
                UserDomainModel user = userService.Login(email, password);
                LiteSportAppState.getInstance().setLoggedInUser(user);
                System.out.println("User registered: " + user.getName() + " " + user.getSurname() + " " + user.getEmail());
                Stage stage = (Stage) RegisterButton.getScene().getWindow();
                stage.close();
            } else {
                ErrorMessageLabel.setText("Error registering user");
            }
        } else if (!isPasswordValid) {
            ErrorMessageLabel.setText("Passwords do not match");
        } else if (!isEmailValid) {
            ErrorMessageLabel.setText("Invalid email");
        } else if (!isNameValid) {
            ErrorMessageLabel.setText("Name cannot be empty");
        } else if (!isSurnameValid) {
            ErrorMessageLabel.setText("Surname cannot be empty");
        } else {
            ErrorMessageLabel.setText("Unknown error");
        }
        clickedRegister = true;
    }

    private void resetError(){
        if (clickedRegister){
            ErrorMessageLabel.setText("");
        }
        clickedRegister = false;
    }
}
