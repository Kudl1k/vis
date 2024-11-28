module cz.kudladev.LiteSport {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires DomainLayer;
    requires DataLibrary;
    requires static lombok;
    requires datetime.picker.javafx;

    opens cz.kudladev to javafx.fxml;
    opens cz.kudladev.core to javafx.fxml;
    opens cz.kudladev.auth.login to javafx.fxml;
    opens cz.kudladev.auth.register to javafx.fxml;
    opens cz.kudladev.admin to javafx.fxml;
    exports cz.kudladev;
}