package cz.kudladev.info;

import cz.kudladev.core.LiteSportAppState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MatchInfoController implements Initializable {

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
    }
}
