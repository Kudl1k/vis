package cz.kudladev.admin;

import DomainModels.*;
import Services.*;
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
    private PlayerService playerService;
    private PlayerHistoryService playerHistoryService;

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
    private TextField MatchStadiumTextField;
    @FXML
    private Label MatchErrorText;
    @FXML
    private Button MatchCreateButton;


    //Player
    @FXML
    private TextField PlayerNameTextField;
    @FXML
    private TextField PlayerSurnameTextField;
    @FXML
    private DatePicker PlayerBirthDatePicker;
    @FXML
    private ChoiceBox<CategoryDomainModel> PlayerCategoryCombobox;
    @FXML
    private Label PlayerErrorText;
    @FXML
    private Button PlayerCreateButton;

    //Transfer
    @FXML
    private ChoiceBox<PlayerDomainModel> TransferPlayerCombobox;
    @FXML
    private ChoiceBox<TeamDomainModel> TransferFromTeamCombobox;
    @FXML
    private ChoiceBox<TeamDomainModel> TransferToTeamCombobox;
    @FXML
    private DatePicker TransferStartDatePicker;
    @FXML
    private DatePicker TransferEndDatePicker;
    @FXML
    private Label TransferErrorText;
    @FXML
    private Button TransferCreateButton;



    private boolean clickedCreate = false;

    public AdminController() {
        this.categoryService = new CategoryService();
        this.leagueService = new LeagueService();
        this.teamService = new TeamService();
        this.matchService = new MatchService();
        this.playerService = new PlayerService();
        this.playerHistoryService = new PlayerHistoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryDomainModel[] categories = categoryService.GetCategories();
        ObservableList<CategoryDomainModel> categoryList = FXCollections.observableArrayList(categories);
        PlayerDomainModel[] players = playerService.GetPlayers();
        ObservableList<PlayerDomainModel> playerList = FXCollections.observableArrayList(players);


        LeagueCategoryCombobox.setItems(categoryList);
        TeamCategoryCombobox.setItems(categoryList);
        MatchCategoryCombobox.setItems(categoryList);
        PlayerCategoryCombobox.setItems(categoryList);

        TransferPlayerCombobox.setItems(playerList);

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
        TransferPlayerCombobox.addEventHandler(ActionEvent.ACTION, event -> {
            CheckPlayerHistory();
        });
        TransferFromTeamCombobox.addEventHandler(ActionEvent.ACTION, event -> {
            fillTransferToTeamCombobox(TransferFromTeamCombobox.getValue());
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
        String stadium = MatchStadiumTextField.getText();
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
            DataParser.parseAndFormatDateAndTime(dateTime),
            "",
            0,
            stadium,
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

    @FXML
    private void PlayerCreateButton() {
        String name = PlayerNameTextField.getText();
        String surname = PlayerSurnameTextField.getText();
        String birthDate = PlayerBirthDatePicker.getValue().toString();
        CategoryDomainModel category = PlayerCategoryCombobox.getValue();
        clickedCreate = true;
        if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || category == null) {
            PlayerErrorText.setText("Please fill all fields");
            return;
        }
        PlayerDomainModel player = new PlayerDomainModel(
            name,
            surname,
            birthDate,
            category
        );
        if (playerService.CreatePlayer(player)) {
            PlayerErrorText.setText("Player created");
            PlayerErrorText.setStyle("-fx-text-fill: #09c909");
        } else {
            PlayerErrorText.setText("Error creating player");
        }
    }

    @FXML
    private void TransferCreateButton() {
        PlayerDomainModel player = TransferPlayerCombobox.getValue();
        TeamDomainModel team = TransferToTeamCombobox.getValue();
        String startDate = TransferStartDatePicker.getValue().toString();
        String endDate = TransferEndDatePicker.getValue().toString();
        clickedCreate = true;
        if (player == null || team == null || startDate.isEmpty()) {
            TransferErrorText.setText("Please fill all fields");
            return;
        }
        PlayerHistoryDomainModel transfer = new PlayerHistoryDomainModel(
            startDate,
            endDate,
            player,
            team
        );
        if (playerHistoryService.CreatePlayerHistory(transfer)) {
            TransferErrorText.setText("Transfer created");
            TransferErrorText.setStyle("-fx-text-fill: #09c909");
        } else {
            TransferErrorText.setText("Error creating transfer");
        }
    }

    @FXML
    private void CheckPlayerHistory(){
        PlayerHistoryDomainModel[] playerHistory = playerHistoryService.GetPlayerHistory(TransferPlayerCombobox.getValue());

        if(playerHistory.length == 0){
            TeamDomainModel[] teams = teamService.GetTeamsByCategory(TransferPlayerCombobox.getValue().getCategory());
            ObservableList<TeamDomainModel> teamList = FXCollections.observableArrayList(teams);
            TransferFromTeamCombobox.setItems(teamList);
        } else {
            TeamDomainModel[] teams = new TeamDomainModel[1];
            teams[0] = playerHistory[playerHistory.length - 1].getTeam();
            ObservableList<TeamDomainModel> teamList = FXCollections.observableArrayList(teams);
            TransferFromTeamCombobox.setItems(teamList);
            TransferFromTeamCombobox.setValue(teams[0]);
            fillTransferToTeamCombobox(teams[0]);
        }
    }

    private void fillTransferToTeamCombobox(TeamDomainModel team) {
        TeamDomainModel[] teams = teamService.GetTeamsByCategory(TransferPlayerCombobox.getValue().getCategory());
        teams = Arrays.stream(teams).filter(t -> t.getId() != team.getId()).toArray(TeamDomainModel[]::new);
        ObservableList<TeamDomainModel> teamList = FXCollections.observableArrayList(teams);
        TransferToTeamCombobox.setItems(teamList);
    }

}
