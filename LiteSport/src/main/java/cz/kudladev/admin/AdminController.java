package cz.kudladev.admin;

import DomainModels.CategoryDomainModel;
import DomainModels.LeagueDomainModel;
import DomainModels.TeamDomainModel;
import Services.CategoryService;
import Services.LeagueService;
import Services.TeamService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private CategoryService categoryService;
    private LeagueService leagueService;
    private TeamService teamService;

    @FXML
    private ChoiceBox<String> LeagueCategoryCombobox;
    @FXML
    private TextField LeagueNameTextField;
    @FXML
    private TextField LeagueLanguageCodeTextField;
    @FXML
    private Label LeagueErrorText;

    @FXML
    private ChoiceBox<String> TeamCategoryCombobox;
    @FXML
    private ChoiceBox<String> TeamLeagueCombobox;
    @FXML
    private TextField TeamNameTextField;
    @FXML
    private TextField TeamLanguageCodeTextField;
    @FXML
    private Label TeamErrorText;


    private boolean clickedCreate = false;

    public AdminController() {
        this.categoryService = new CategoryService();
        this.leagueService = new LeagueService();
        this.teamService = new TeamService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Admin controller initialized");
        CategoryDomainModel[] categories = categoryService.GetCategories();
        for (CategoryDomainModel category : categories) {
            LeagueCategoryCombobox.getItems().add(category.getName());
        }
        LeagueCategoryCombobox.addEventHandler(ActionEvent.ACTION, event -> resetLeagueError());
        LeagueNameTextField.textProperty().addListener((observable, oldValue, newValue) -> resetLeagueError());
        LeagueLanguageCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> resetLeagueError());

        for (CategoryDomainModel category : categories) {
            TeamCategoryCombobox.getItems().add(category.getName());
        }
        TeamCategoryCombobox.addEventHandler(ActionEvent.ACTION, event -> {
            resetTeamError();
            fillTeamLeagueCombobox(TeamCategoryCombobox.getValue());
        });
    }

    @FXML
    public void LeagueCreateButton(){
        String name = LeagueNameTextField.getText();
        String languageCode = LeagueLanguageCodeTextField.getText();
        String category = LeagueCategoryCombobox.getValue();
        clickedCreate = true;
        if(name.isEmpty() || languageCode.isEmpty() || category.isEmpty()){
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
        String category = TeamCategoryCombobox.getValue();
        String league = TeamLeagueCombobox.getValue();
        clickedCreate = true;
        if(name.isEmpty() || languageCode.isEmpty() || category.isEmpty() || league.isEmpty()){
            TeamErrorText.setText("Please fill all fields");
            return;
        }
        TeamDomainModel team = new TeamDomainModel(
            name,
            languageCode,
            new LeagueDomainModel(league, "", ""),
            new CategoryDomainModel(category)
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

    private void fillTeamLeagueCombobox(String category) {
        LeagueDomainModel[] leagues = leagueService.GetLeagues(category);
        TeamLeagueCombobox.getItems().clear();
        for (LeagueDomainModel league : leagues) {
            TeamLeagueCombobox.getItems().add(league.getName());
        }
    }



}
