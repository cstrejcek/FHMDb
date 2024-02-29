package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    private FilteredList<Movie> filteredMovies;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredMovies = loadMovies(filteredMovies);

        // initialize UI stuff
        movieListView.setItems(filteredMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending
                HomeController.sortMovies(filteredMovies, true);
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                HomeController.sortMovies(filteredMovies, false);
                sortBtn.setText("Sort (asc)");
            }
        });


    }

    public static FilteredList<Movie> loadMovies(FilteredList<Movie> filteredMovies) {
        List<Movie> allMovies = Movie.initializeMovies();
        filteredMovies = new FilteredList<>(FXCollections.observableArrayList(allMovies));
        return filteredMovies;
    }

    public static void sortMovies(FilteredList<Movie> filteredMovies, boolean ascending) {
        if (ascending) {
            filteredMovies.getSource().sort(Comparator.comparing(Movie::getTitle));
        } else {
            filteredMovies.getSource().sort(Comparator.comparing(Movie::getTitle).reversed());
        }
    }
}
