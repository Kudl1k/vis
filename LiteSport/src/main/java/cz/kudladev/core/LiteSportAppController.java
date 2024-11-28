package cz.kudladev.core;

import DomainModels.LeagueDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.TeamDomainModel;
import cz.kudladev.LiteSportApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LiteSportAppController implements Initializable {


    //Football
    @FXML
    private HBox LiteSportHeader;
    @FXML HBox SoccerHeader;
    @FXML
    private ChoiceBox<LeagueDomainModel> FootballLeagueChoiceBox;
    @FXML
    private ChoiceBox<TeamDomainModel> FootballTeamChoiceBox;

    //Table

    @FXML
    private TableView<MatchDomainModel> footballView;
    @FXML
    private TableColumn<MatchDomainModel, String> footballID;
    @FXML
    private TableColumn<MatchDomainModel, String> footballLeague;
    @FXML
    private TableColumn<MatchDomainModel, String> footballStadium;
    @FXML
    private TableColumn<MatchDomainModel, String> footballStart;
    @FXML
    private TableColumn<MatchDomainModel, String> footballEnd;
    @FXML
    private TableColumn<MatchDomainModel, String> footballHomeTeam;
    @FXML
    private TableColumn<MatchDomainModel, String> footballAwayTeam;
    @FXML
    private TableColumn<MatchDomainModel, Integer> footballHomeScore;
    @FXML
    private TableColumn<MatchDomainModel, Integer> footballAwayScore;




    private Football football;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LiteSportAppState appState = LiteSportAppState.getInstance();
        this.football = new Football();

        initializeFootballTable();

        appState.getIsUserLoggedIn().addListener((observable, oldValue, newValue) -> {
            LiteSportHeader.getChildren().clear();
            if (newValue) {
                LoggedButtonLayout();
            } else {
                System.out.println("User is not logged in");
                DefaultButtonLayout();
            }
        });
        // Initial check
        if (!appState.getIsUserLoggedIn().getValue()) {
            LiteSportHeader.getChildren().clear();
            DefaultButtonLayout();
        } else {
            LoggedButtonLayout();
        }

        ObservableList<LeagueDomainModel> leagueList = FXCollections.observableArrayList(football.getLeagues());
        FootballLeagueChoiceBox.setItems(leagueList);

        FootballLeagueChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected league: " + newValue.getName());
            if (newValue != null) {
                ObservableList<TeamDomainModel> teamList = FXCollections.observableArrayList(football.LoadTeams(newValue));
                FootballTeamChoiceBox.setItems(teamList);
            }
        });
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

    private void LoggedButtonLayout(){
        System.out.println("User is logged in");
        Button logoutButton = new Button("Logout");
        logoutButton.setId("logoutButton");
        logoutButton.setOnAction(e -> LiteSportAppState.getInstance().logOutUser());
        Label usernameLabel = new Label(LiteSportAppState.getInstance().getLoggedInUser().getName() + " " + LiteSportAppState.getInstance().getLoggedInUser().getSurname());
        LiteSportHeader.getChildren().addAll(usernameLabel, logoutButton);
        if (LiteSportAppState.getInstance().getLoggedInUser().getRole().equals("admin")) {
            Button adminButton = new Button("Admin");
            adminButton.setId("adminButton");
            adminButton.setOnAction(e -> LiteSportApp.openWindow("Admin", 800, 600));
            LiteSportHeader.getChildren().add(adminButton);
        }
    }

    private void initializeFootballTable(){
        footballID.setCellValueFactory(new PropertyValueFactory<>("id"));
        footballLeague.setCellValueFactory(new PropertyValueFactory<>("league"));
        footballStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        footballEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        footballStadium.setCellValueFactory(new PropertyValueFactory<>("stadium"));
        footballHomeTeam.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
        footballAwayTeam.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));
        footballHomeScore.setCellValueFactory(new PropertyValueFactory<>("homeScore"));
        footballAwayScore.setCellValueFactory(new PropertyValueFactory<>("awayScore"));

        this.footballView.setItems(FXCollections.observableArrayList(football.LoadMatches(null)));

        FootballLeagueChoiceBox.addEventHandler(ActionEvent.ACTION, event -> {
            this.footballView.setItems(FXCollections.observableArrayList(football.LoadMatches(FootballLeagueChoiceBox.getValue())));
        });

        this.footballView.setOnMouseClicked(this::handleRowClick);
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double-click detected
            System.out.println("Double-click detected");
            MatchDomainModel selectedMatch = footballView.getSelectionModel().getSelectedItem();
            if (selectedMatch != null) {
                LiteSportAppState.getInstance().setSelectedMatch(selectedMatch);
                LiteSportApp.openWindow("MatchInfo", 600, 400);
            }
        }
    }


}