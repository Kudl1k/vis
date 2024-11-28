package cz.kudladev.admin;

import DomainModels.CategoryDomainModel;
import DomainModels.LeagueDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.TeamDomainModel;
import Services.CategoryService;
import Services.LeagueService;
import Services.MatchService;
import Services.TeamService;
import com.browniebytes.javafx.control.DateTimePicker;
import cz.kudladev.core.LiteSportAppState;
import cz.kudladev.util.DataParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private CategoryService categoryService;
    private LeagueService leagueService;
    private TeamService teamService;
    private MatchService matchService;

    //FXML elements

    //League
    @FXML
    private ChoiceBox<CategoryDomainModel> LeagueCategoryCombobox;
    @FXML
    private TextField LeagueNameTextField;
    @FXML
    private TextField LeagueLanguageCodeTextField;
    @FXML
    private Label LeagueErrorText;

    //Team
    @FXML
    private ChoiceBox<CategoryDomainModel> TeamCategoryCombobox;
    @FXML
    private ChoiceBox<LeagueDomainModel> TeamLeagueCombobox;
    @FXML
    private TextField TeamNameTextField;
    @FXML
    private TextField TeamLanguageCodeTextField;
    @FXML
    private Label TeamErrorText;


    //Match
    @FXML
    private ChoiceBox<CategoryDomainModel> MatchCategoryCombobox;
    @FXML
    private ChoiceBox<LeagueDomainModel> MatchLeagueCombobox;
    @FXML
    private ChoiceBox<TeamDomainModel> MatchHomeTeamCombobox;
    @FXML
    private ChoiceBox<TeamDomainModel> MatchAwayTeamCombobox;
    @FXML
    private DateTimePicker MatchDateTimePicker;
    @FXML
    private Label MatchErrorText;
    @FXML
    private Button MatchCreateButton;

    private boolean clickedCreate = false;

    public AdminController() {
        this.categoryService = new CategoryService();
        this.leagueService = new LeagueService();
        this.teamService = new TeamService();
        this.matchService = new MatchService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryDomainModel[] categories = categoryService.GetCategories();
        ObservableList<CategoryDomainModel> categoryList = FXCollections.observableArrayList(categories);

        LeagueCategoryCombobox.setItems(categoryList);
        TeamCategoryCombobox.setItems(categoryList);
        MatchCategoryCombobox.setItems(categoryList);

        LeagueCategoryCombobox.addEventHandler(ActionEvent.ACTION, event -> resetLeagueError());
        LeagueNameTextField.textProperty().addListener((observable, oldValue, newValue) -> resetLeagueError());
        LeagueLanguageCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> resetLeagueError());


        TeamCategoryCombobox.addEventHandler(ActionEvent.ACTION, event -> {
            resetTeamError();
            fillTeamLeagueCombobox(TeamCategoryCombobox.getValue());
        });
        MatchCategoryCombobox.addEventHandler(ActionEvent.ACTION, event -> {
            resetMatchError();
            fillMatchLeagueCombobox(MatchCategoryCombobox.getValue());
        });
        MatchLeagueCombobox.addEventHandler(ActionEvent.ACTION, event -> {
            resetMatchError();
            fillMatchTeamsCombobox(MatchLeagueCombobox.getValue());
        });
    }

    @FXML
    public void LeagueCreateButton(){
        String name = LeagueNameTextField.getText();
        String languageCode = LeagueLanguageCodeTextField.getText();
        CategoryDomainModel category = LeagueCategoryCombobox.getValue();
        clickedCreate = true;
        if(name.isEmpty() || languageCode.isEmpty() || category == null){
            LeagueErrorText.setText("Please fill all fields");
            return;
        }
        if(leagueService.CreateLeague(new LeagueDomainModel(name, languageCode, category))){
            //change text to green
            LeagueErrorText.setText("League created");
            LeagueErrorText.setStyle("-fx-text-fill: #09c909");
        } else {
            LeagueErrorText.setText("Error creating league");
        }
    }

    private void resetLeagueError() {
        if (clickedCreate) {
            LeagueErrorText.setText("");
            LeagueErrorText.setStyle("-fx-text-fill: red");
            clickedCreate = false;
        }
    }

    @FXML
    public void TeamCreateButton(){
        String name = TeamNameTextField.getText();
        String languageCode = TeamLanguageCodeTextField.getText();
        CategoryDomainModel category = TeamCategoryCombobox.getValue();
        LeagueDomainModel league = TeamLeagueCombobox.getValue();
        clickedCreate = true;
        if(name.isEmpty() || languageCode.isEmpty() || category == null || league == null){
            TeamErrorText.setText("Please fill all fields");
            return;
        }
        TeamDomainModel team = new TeamDomainModel(
            name,
            languageCode,
            league,
            category
        );
        if(teamService.CreateTeam(team)){
            //change text to green
            TeamErrorText.setText("Team created");
            TeamErrorText.setStyle("-fx-text-fill: #09c909");
        } else {
            TeamErrorText.setText("Error creating team");
        }
    }

    private void resetTeamError() {
        if (clickedCreate) {
            TeamErrorText.setText("");
            TeamErrorText.setStyle("-fx-text-fill: red");
            clickedCreate = false;
        }
    }

    private void fillTeamLeagueCombobox(CategoryDomainModel category) {
        LeagueDomainModel[] leagues = leagueService.GetLeagues(category);
        ObservableList<LeagueDomainModel> leagueList = FXCollections.observableArrayList(leagues);
        TeamLeagueCombobox.setItems(leagueList);
    }

    @FXML
    public void MatchCreateButton() {
        CategoryDomainModel category = MatchCategoryCombobox.getValue();
        LeagueDomainModel league = MatchLeagueCombobox.getValue();
        TeamDomainModel homeTeam = MatchHomeTeamCombobox.getValue();
        TeamDomainModel awayTeam = MatchAwayTeamCombobox.getValue();
        String dateTime = MatchDateTimePicker.getTime().toString();
        clickedCreate = true;
        if (category == null || league == null || homeTeam == null || awayTeam == null || dateTime.isEmpty()) {
            MatchErrorText.setText("Please fill all fields");
            return;
        }
        MatchDomainModel match = new MatchDomainModel(
            homeTeam,
            awayTeam,
            0,
            0,
            DataParser.parseAndFormatDate(dateTime),
            "",
            0,
            "",
            category,
            league,
            LiteSportAppState.getInstance().getLoggedInUser()
        );
        if (matchService.CreateMatch(match)) {
            MatchErrorText.setText("Match created");
            MatchErrorText.setStyle("-fx-text-fill: #09c909");
        } else {
            MatchErrorText.setText("Error creating match");
        }
    }

    private void resetMatchError() {
        if (clickedCreate) {
            MatchErrorText.setText("");
            MatchErrorText.setStyle("-fx-text-fill: red");
            clickedCreate = false;
        }
    }

    private void fillMatchLeagueCombobox(CategoryDomainModel category) {
        LeagueDomainModel[] leagues = leagueService.GetLeagues(category);
        ObservableList<LeagueDomainModel> leagueList = FXCollections.observableArrayList(leagues);
        MatchLeagueCombobox.setItems(leagueList);
    }

    private void fillMatchTeamsCombobox(LeagueDomainModel league) {
        TeamDomainModel[] teams = teamService.GetTeamsByLeague(league);
        ObservableList<TeamDomainModel> teamList = FXCollections.observableArrayList(teams);
        MatchHomeTeamCombobox.setItems(teamList);
        MatchAwayTeamCombobox.setItems(teamList);
    }

}
