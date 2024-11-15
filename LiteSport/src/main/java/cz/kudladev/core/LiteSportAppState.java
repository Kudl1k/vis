package cz.kudladev.core;

import DomainModels.UserDomainModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiteSportAppState {
    private static LiteSportAppState instance = new LiteSportAppState();
    private BooleanProperty isUserLoggedIn = new SimpleBooleanProperty(false);
    private UserDomainModel loggedInUser = null;
    private String footballSelectedCategory = "";

    private LiteSportAppState() {
        // private constructor to prevent instantiation
    }

    public void setIsUserLoggedIn(boolean value) {
        isUserLoggedIn.set(value);
    }

    public void setLoggedInUser(UserDomainModel user) {
        System.out.println("User set");
        loggedInUser = user;
        isUserLoggedIn.set(true);
    }

    public static LiteSportAppState getInstance() {
        return instance;
    }
}