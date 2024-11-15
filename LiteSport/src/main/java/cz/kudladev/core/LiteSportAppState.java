package cz.kudladev.core;

import DomainModels.UserDomainModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiteSportAppState {
    private static LiteSportAppState instance = new LiteSportAppState();
    private boolean isUserLoggedIn = false;
    private UserDomainModel loggedInUser = null;

    private LiteSportAppState() {
        // private constructor to prevent instantiation
    }

    public static LiteSportAppState getInstance() {
        return instance;
    }
}