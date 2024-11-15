package cz.kudladev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LiteSportApp extends Application {

    private static Stage stage;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        LiteSportApp.stage = stage;
        scene = new Scene(loadFXML("LiteSport"), 800, 400);
        stage.setTitle("LiteSport");
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LiteSportApp.class.getResource("/LiteSportGUI/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void openWindow(String fxml, int width, int height) {
        try {
            Scene scene = new Scene(loadFXML(fxml), width, height);
            Stage stage = new Stage();
            stage.setTitle(fxml);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeTitle(String title) {
        stage.setTitle(title);
    }

    public static void main(String[] args) {
        launch(args);
    }
}