package at.ac.fhcampuswien.fhmdb;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            ControllerFactory controllerFactory = new ControllerFactory();
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
            fxmlLoader.setControllerFactory(controllerFactory);
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("FHMDb");
            stage.setScene(scene);
            Image icon = new Image(getClass().getResourceAsStream("film.PNG"));
            stage.getIcons().add(icon);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null); // No header
            alert.setContentText("Application could not be started. Exiting.");
            alert.showAndWait();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        try {
            launch();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null); // No header
            alert.setContentText("Application could not be started. Exiting.");
            alert.showAndWait();
            Platform.exit();
        }
    }
}