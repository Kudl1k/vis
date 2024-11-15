package cz.kudladev.core;

import Services.UserService;
import cz.kudladev.LiteSportApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        LiteSportAppState appState = LiteSportAppState.getInstance();

        appState.getIsUserLoggedIn().addListener((observable, oldValue, newValue) -> {
            LiteSportHeader.getChildren().clear(); // Clear existing children
            if (newValue) {
                System.out.println("User is logged in");
                Button logoutButton = new Button("Logout");
                logoutButton.setId("logoutButton");
                logoutButton.setOnAction(e -> {
                    //userService.logout();
                    appState.setIsUserLoggedIn(false);
                });
                Label usernameLabel = new Label(appState.getLoggedInUser().getName() + " " + appState.getLoggedInUser().getSurname());
                LiteSportHeader.getChildren().addAll(usernameLabel, logoutButton);
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
        });

        // Initial check
        if (!appState.getIsUserLoggedIn().getValue()) {
            LiteSportHeader.getChildren().clear();
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