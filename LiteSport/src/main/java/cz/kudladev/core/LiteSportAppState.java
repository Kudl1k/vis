package cz.kudladev.core;

import DataAccess.Connectors.TextConnectorUtils;
import DomainModels.MatchDomainModel;
import DomainModels.UserDomainModel;
import Services.UserService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
public class LiteSportAppState {
    private static final String USER_SETTINGS = "user_settings.txt";

    private UserService userService = new UserService();

    private static LiteSportAppState instance = new LiteSportAppState();
    private Boolean rememberMe = false;
    private BooleanProperty isUserLoggedIn = new SimpleBooleanProperty(false);
    private UserDomainModel loggedInUser = null;
    private String footballSelectedCategory = "";
    private MatchDomainModel selectedMatch = null;
    private MatchDomainModel[] matches = null;

    private LiteSportAppState() {
        // private constructor to prevent instantiation
        loadUserSettings();
    }

    public void setIsUserLoggedIn(boolean value) {
        isUserLoggedIn.set(value);
    }

    public void setLoggedInUser(UserDomainModel user) {
        System.out.println("User set");
        loggedInUser = user;
        isUserLoggedIn.set(true);
        if (rememberMe) {
            System.out.println("Saving user settings");
            saveUserSettings();
        }
    }

    public void logOutUser() {
        loggedInUser = null;
        isUserLoggedIn.set(false);
        Path path = TextConnectorUtils.fullFilePath(USER_SETTINGS);
        try {
            Files.writeString(path, "loggedInUser=");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserSettings() {
        // save user settings to file
        Path path = TextConnectorUtils.fullFilePath(USER_SETTINGS);
        try {
            Files.writeString(path, "loggedInUser=" + UserDomainModel.toSaveString(loggedInUser));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUserSettings() {
        // load user settings from file
        Path path = TextConnectorUtils.fullFilePath(USER_SETTINGS);
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "loggedInUser":
                            setLoggedInUser(UserDomainModel.fromString(parts[1]));
                            userService.Login(loggedInUser.getEmail(), loggedInUser.getPassword());
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LiteSportAppState getInstance() {
        return instance;
    }
}