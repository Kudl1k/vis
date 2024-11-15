package cz.kudladev.core;

import Services.UserService;
import cz.kudladev.LiteSportApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LiteSportAppController implements Initializable {

    @FXML
    private HBox LiteSportHeader;

    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();
        if (LiteSportAppState.getInstance().isUserLoggedIn()) {
            System.out.println("User is logged in");
        } else {
            System.out.println("User is not logged in");
            Button loginButton = new Button("Login");
            loginButton.setId("loginButton");
            loginButton.setOnAction(e -> LiteSportApp.openWindow("Login", 400, 400));
            Button registerButton = new Button("Register");
            registerButton.setId("registerButton");
            registerButton.setOnAction(e -> LiteSportApp.openWindow("Register", 400, 400));
            LiteSportHeader.getChildren().addAll(loginButton, registerButton);
        }
    }
}