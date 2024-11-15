package cz.kudladev.admin;

import DomainModels.CategoryDomainModel;
import DomainModels.LeagueDomainModel;
import Services.CategoryService;
import Services.LeagueService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private CategoryService categoryService;
    private LeagueService leagueService;

    @FXML
    private ChoiceBox<String> LeagueCategoryCombobox;

    @FXML
    private TextField LeagueNameTextField;

    @FXML
    private TextField LeagueLanguageCodeTextField;

    @FXML
    private Label LeagueErrorText;

    private boolean clickedCreate = false;

    public AdminController() {
        this.categoryService = new CategoryService();
        this.leagueService = new LeagueService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Admin controller initialized");
        CategoryDomainModel[] categories = categoryService.GetCategories();
        for (CategoryDomainModel category : categories) {
            LeagueCategoryCombobox.getItems().add(category.getName());
        }
        LeagueCategoryCombobox.addEventHandler(ActionEvent.ACTION, event -> resetError());
        LeagueNameTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
        LeagueLanguageCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> resetError());
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

    private void resetError() {
        if (clickedCreate) {
            LeagueErrorText.setText("");
            LeagueErrorText.setStyle("-fx-text-fill: red");
            clickedCreate = false;
        }
    }



}
