package cz.kudladev.notifications;

import cz.kudladev.core.LiteSportAppState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NotificationsController implements Initializable {

    @FXML
    private ListView<String> NotificationList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> notifications = LiteSportAppState.getInstance().getNotifications();
        System.out.println("Notifications: " + notifications);
        NotificationList.getItems().addAll(notifications);
    }
}
