package cz.kudladev.core;

import DomainModels.LeagueDomainModel;
import cz.kudladev.LiteSportApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LiteSportAppController implements Initializable {

    @FXML
    private HBox LiteSportHeader;

    @FXML HBox SoccerHeader;
    @FXML
    private ChoiceBox<String> FootballLeagueChoiceBox;
    @FXML
    private ChoiceBox<String> FootballTeamChoiceBox;




    private Football football;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LiteSportAppState appState = LiteSportAppState.getInstance();
        this.football = new Football();


        appState.getIsUserLoggedIn().addListener((observable, oldValue, newValue) -> {
            LiteSportHeader.getChildren().clear();
            if (newValue) {
                System.out.println("User is logged in");
                Button logoutButton = new Button("Logout");
                logoutButton.setId("logoutButton");
                logoutButton.setOnAction(e -> {
                    appState.setIsUserLoggedIn(false);
                });
                Label usernameLabel = new Label(appState.getLoggedInUser().getName() + " " + appState.getLoggedInUser().getSurname());
                LiteSportHeader.getChildren().addAll(usernameLabel, logoutButton);
                if (appState.getLoggedInUser().getRole().equals("admin")) {
                    Button adminButton = new Button("Admin");
                    adminButton.setId("adminButton");
                    adminButton.setOnAction(e -> LiteSportApp.openWindow("Admin", 800, 600));
                    LiteSportHeader.getChildren().add(adminButton);
                }

            } else {
                System.out.println("User is not logged in");
                DefaultButtonLayout();
            }
        });
        // Initial check
        if (!appState.getIsUserLoggedIn().getValue()) {
            LiteSportHeader.getChildren().clear();
            DefaultButtonLayout();
        }
        for (LeagueDomainModel league : football.getLeagues()) {
            FootballLeagueChoiceBox.getItems().add(league.getName());
        }
    }
    private void DefaultButtonLayout() {
        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        loginButton.setOnAction(e -> LiteSportApp.openWindow("Login", 400, 400));
        Button registerButton = new Button("Register");
        registerButton.setId("registerButton");
        registerButton.setOnAction(e -> LiteSportApp.openWindow("Register", 400, 400));
        LiteSportHeader.getChildren().addAll(loginButton, registerButton);
    }
}