package cz.kudladev.info;

import DataTransferObjects.PlayerDTO;
import DomainModels.*;
import Services.FavouriteService;
import Services.GoalHistoryService;
import Services.MatchService;
import Services.PlayerService;
import cz.kudladev.core.LiteSportAppController;
import cz.kudladev.core.LiteSportAppState;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MatchInfoController implements Initializable {

    private PlayerService playerService;
    private GoalHistoryService goalHistoryService;
    private MatchService matchService;
    private FavouriteService favouriteService;

    @FXML
    private Button FavoriteButton;

    @FXML
    private Label homeTeam;
    @FXML
    private Label awayTeam;
    @FXML
    private Label homeScore;
    @FXML
    private Label awayScore;
    @FXML
    private Label stadium;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;

    @FXML
    private HBox adminPanel;
    @FXML
    private TextField minute;
    @FXML
    private ChoiceBox<TeamDomainModel> team;
    @FXML
    private ChoiceBox<PlayerDomainModel> player;
    @FXML
    private Button addGoal;

    @FXML
    private TableView<GoalHistoryDomainModel> matchInfoView;
    @FXML
    private TableColumn<GoalHistoryDomainModel, Integer> matchMinute;
    @FXML
    private TableColumn<GoalHistoryDomainModel, String> matchTeam;
    @FXML
    private TableColumn<GoalHistoryDomainModel, String> matchPlayer;
    @FXML
    private TableColumn<GoalHistoryDomainModel, String> creator;

    @FXML
    private VBox DeleteButtonVBox;


    ObservableList<GoalHistoryDomainModel> goalsList;


    public MatchInfoController(){
        playerService = new PlayerService();
        goalHistoryService = new GoalHistoryService();
        matchService = new MatchService();
        favouriteService = new FavouriteService();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        matchMinute.prefWidthProperty().bind(matchInfoView.widthProperty().multiply(0.1)); // 10% of the table width
        matchTeam.prefWidthProperty().bind(matchInfoView.widthProperty().multiply(0.3)); // 30% of the table width
        matchPlayer.prefWidthProperty().bind(matchInfoView.widthProperty().multiply(0.3)); // 30% of the table width
        creator.prefWidthProperty().bind(matchInfoView.widthProperty().multiply(0.3)); // 30% of the table width

        if (LiteSportAppState.getInstance().getSelectedMatch() != null){
            SetMatchInfo();
            GoalHistoryDomainModel[] goals = goalHistoryService.GetGoalHistories(LiteSportAppState.getInstance().getSelectedMatch());
            this.goalsList = FXCollections.observableArrayList(goals);
            SetMatchInfoView();
            SetupAdminPanel();
            if (favouriteService.GetFavouriteByUserAndMatch(LiteSportAppState.getInstance().getLoggedInUser(), LiteSportAppState.getInstance().getSelectedMatch())){
                FavoriteButton.setStyle("-fx-text-fill: green");
            } else {
                FavoriteButton.setStyle("-fx-text-fill: red");
            }
        }
    }

    private void SetupAdminPanel(){
        if (LiteSportAppState.getInstance().getLoggedInUser() == null || Objects.equals(LiteSportAppState.getInstance().getLoggedInUser().getRole(), "user")){
            adminPanel.setVisible(false);
            return;
        }
        adminPanel.setVisible(Objects.equals(LiteSportAppState.getInstance().getLoggedInUser().getRole(), "admin"));
        if (!LiteSportAppState.getInstance().getSelectedMatch().getEndTime().isBlank() && Objects.equals(LiteSportAppState.getInstance().getLoggedInUser().getRole(), "admin")){
            DeleteButtonVBox.setVisible(false);
            adminPanel.setVisible(false);
        }

        ObservableList<TeamDomainModel> teams = FXCollections.observableArrayList(LiteSportAppState.getInstance().getSelectedMatch().getHomeTeam(), LiteSportAppState.getInstance().getSelectedMatch().getAwayTeam());

        team.setItems(teams);

        PlayerDomainModel[] homePlayers = playerService.GetPlayersByTeam(LiteSportAppState.getInstance().getSelectedMatch().getHomeTeam());
        PlayerDomainModel[] awayPlayers = playerService.GetPlayersByTeam(LiteSportAppState.getInstance().getSelectedMatch().getAwayTeam());

        ObservableList<PlayerDomainModel> homePlayersList = FXCollections.observableArrayList(homePlayers);
        ObservableList<PlayerDomainModel> awayPlayersList = FXCollections.observableArrayList(awayPlayers);

        team.addEventHandler(javafx.event.ActionEvent.ACTION, event -> {
            if (team.getValue() != null){
                if (team.getValue().getId() == LiteSportAppState.getInstance().getSelectedMatch().getHomeTeam().getId()){
                    player.setItems(homePlayersList);
                } else {
                    player.setItems(awayPlayersList);
                }
            }
        });

    }

    @FXML
    private void AddGoal(){
        String minute = this.minute.getText();
        TeamDomainModel team = this.team.getValue();
        PlayerDomainModel player = this.player.getValue();
        MatchDomainModel match = LiteSportAppState.getInstance().getSelectedMatch();

        if (minute.isEmpty() || team == null || player == null || !minute.matches("\\d+")){
            return;
        }

        System.out.println("Match: " + match.toString());

        System.out.println("Adding goal: " + minute + " " + team.getName() + " " + player.getName());

        GoalHistoryDomainModel goal = new GoalHistoryDomainModel(Integer.parseInt(minute), player, team, match, LiteSportAppState.getInstance().getLoggedInUser());

        if (goalHistoryService.AddGoalHistory(goal)){
            System.out.println("Goal added");
            goalsList.add(goal);

            if (favouriteService.GetFavouriteByUserAndMatch(LiteSportAppState.getInstance().getLoggedInUser(), match)){
                System.out.println("Notification added");
                LiteSportAppState.getInstance().addNotification("New goal in match " + match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName() + " at " + minute + " minute");
            }

            SetMatchInfo();
            SetMatchInfoView();
            new LiteSportAppController().updateMatches();
        } else {
            System.out.println("Error adding goal");
        }
    }

    public void SetMatchInfoView(){

        matchMinute.setCellValueFactory(new PropertyValueFactory<>("minute"));
        matchTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        matchPlayer.setCellValueFactory(new PropertyValueFactory<>("player"));
        creator.setCellValueFactory(new PropertyValueFactory<>("creator"));

        matchInfoView.setItems(goalsList);
    }

    public void SetMatchInfo(){
        MatchDomainModel match = matchService.GetMatch(LiteSportAppState.getInstance().getSelectedMatch().getId());

        homeTeam.setText(match.getHomeTeam().getName());
        awayTeam.setText(match.getAwayTeam().getName());
        homeScore.setText(String.valueOf(match.getHomeScore()));
        awayScore.setText(String.valueOf(match.getAwayScore()));
        stadium.setText(match.getStadium());
        startTime.setText(match.getStartTime());
        endTime.setText(match.getEndTime());
    }

    @FXML
    private void onEndMatch(){
        MatchDomainModel match = LiteSportAppState.getInstance().getSelectedMatch();
        if (matchService.EndMatch(match)){
            SetMatchInfo();
            SetupAdminPanel();
            new LiteSportAppController().updateMatches();
        }
    }

    @FXML
    private void onFavorite(){
        MatchDomainModel match = LiteSportAppState.getInstance().getSelectedMatch();
        UserDomainModel user = LiteSportAppState.getInstance().getLoggedInUser();
        FavouriteDomainModel favourite = new FavouriteDomainModel(user, match);
        if (favouriteService.GetFavouriteByUserAndMatch(user, match)){
            if (favouriteService.RemoveFavourite(favourite)){
                System.out.println("Favourite removed");
                FavoriteButton.setStyle("-fx-text-fill: red");
            } else {
                System.out.println("Error removing favourite");
            }
        } else {
            if (favouriteService.AddFavourite(favourite)){
                System.out.println("Favourite added");
                FavoriteButton.setStyle("-fx-text-fill: green");
            } else {
                System.out.println("Error adding favourite");
            }
        }
    }
}
