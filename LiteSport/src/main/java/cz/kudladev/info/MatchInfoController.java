package cz.kudladev.info;

import DataTransferObjects.PlayerDTO;
import DomainModels.GoalHistoryDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.PlayerDomainModel;
import DomainModels.TeamDomainModel;
import Services.GoalHistoryService;
import Services.PlayerService;
import cz.kudladev.core.LiteSportAppState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MatchInfoController implements Initializable {

    private PlayerService playerService;
    private GoalHistoryService goalHistoryService;

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


    public MatchInfoController(){
        playerService = new PlayerService();
        goalHistoryService = new GoalHistoryService();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LiteSportAppState.getInstance().getSelectedMatch() != null){
            homeTeam.setText(LiteSportAppState.getInstance().getSelectedMatch().getHomeTeam().getName());
            awayTeam.setText(LiteSportAppState.getInstance().getSelectedMatch().getAwayTeam().getName());
            homeScore.setText(String.valueOf(LiteSportAppState.getInstance().getSelectedMatch().getHomeScore()));
            awayScore.setText(String.valueOf(LiteSportAppState.getInstance().getSelectedMatch().getAwayScore()));
            stadium.setText(LiteSportAppState.getInstance().getSelectedMatch().getStadium());
            startTime.setText(LiteSportAppState.getInstance().getSelectedMatch().getStartTime());
            endTime.setText(LiteSportAppState.getInstance().getSelectedMatch().getEndTime());
        }
        SetupAdminPanel();
    }

    private void SetupAdminPanel(){
        if (LiteSportAppState.getInstance().getLoggedInUser() == null){
            adminPanel.setVisible(false);
            return;
        }
        adminPanel.setVisible(Objects.equals(LiteSportAppState.getInstance().getLoggedInUser().getRole(), "admin"));

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
        } else {
            System.out.println("Error adding goal");
        }

    }

}
