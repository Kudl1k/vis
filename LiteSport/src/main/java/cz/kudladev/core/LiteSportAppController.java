package cz.kudladev.core;

import DomainModels.LeagueDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.TeamDomainModel;
import Services.CategoryService;
import Services.LeagueService;
import Services.MatchService;
import Services.TeamService;
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

    private LeagueService leagueService = new LeagueService();
    private MatchService matchService = new MatchService();
    private CategoryService categoryService = new CategoryService();
    private TeamService teamService = new TeamService();

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

    private static ObservableList<MatchDomainModel> footballMatches = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LiteSportAppState appState = LiteSportAppState.getInstance();

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

        ObservableList<LeagueDomainModel> leagueList = FXCollections.observableArrayList(leagueService.GetLeagues(categoryService.GetCategory("Football")));
        FootballLeagueChoiceBox.setItems(leagueList);

        FootballLeagueChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected league: " + newValue.getName());
            if (newValue != null) {
                ObservableList<TeamDomainModel> teamList = FXCollections.observableArrayList(teamService.GetTeamsByLeague(newValue));
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

        Button notificationsButton = new Button("Notifications");
        notificationsButton.setId("notificationsButton");
        notificationsButton.setOnAction(this::openNotifications);

        Label usernameLabel = new Label(LiteSportAppState.getInstance().getLoggedInUser().getName() + " " + LiteSportAppState.getInstance().getLoggedInUser().getSurname());
        LiteSportHeader.getChildren().addAll(usernameLabel,notificationsButton, logoutButton);
        if (LiteSportAppState.getInstance().getLoggedInUser().getRole().equals("admin")) {
            Button adminButton = new Button("Admin");
            adminButton.setId("adminButton");
            adminButton.setOnAction(e -> LiteSportApp.openWindow("Admin", 300, 400));
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

        footballID.prefWidthProperty().bind(footballView.widthProperty().multiply(0.05));
        footballLeague.prefWidthProperty().bind(footballView.widthProperty().multiply(0.10));
        footballStart.prefWidthProperty().bind(footballView.widthProperty().multiply(0.15));
        footballEnd.prefWidthProperty().bind(footballView.widthProperty().multiply(0.15));
        footballStadium.prefWidthProperty().bind(footballView.widthProperty().multiply(0.10));
        footballHomeTeam.prefWidthProperty().bind(footballView.widthProperty().multiply(0.15));
        footballAwayTeam.prefWidthProperty().bind(footballView.widthProperty().multiply(0.15));
        footballHomeScore.prefWidthProperty().bind(footballView.widthProperty().multiply(0.075));
        footballAwayScore.prefWidthProperty().bind(footballView.widthProperty().multiply(0.075));

        footballMatches.setAll(matchService.GetMatches());

        this.footballView.setItems(footballMatches);

        FootballLeagueChoiceBox.addEventHandler(ActionEvent.ACTION, event -> {
            LeagueDomainModel selectedLeague = FootballLeagueChoiceBox.getSelectionModel().getSelectedItem();
            if (selectedLeague == null) {
                footballMatches.setAll(matchService.GetMatches());
            } else {
                footballMatches.setAll(matchService.GetMatchesByLeague(selectedLeague));
            }
            this.footballView.setItems(footballMatches);
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

    public void updateMatches(){
        MatchDomainModel[] matches = matchService.GetMatches();
        footballMatches.setAll(matches);
    }


    public void openNotifications(ActionEvent actionEvent) {
        LiteSportApp.openWindow("Notifications", 600, 400);
    }

}