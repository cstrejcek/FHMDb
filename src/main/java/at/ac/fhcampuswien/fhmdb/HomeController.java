package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        try {
            filteredMovies = loadMovies(filteredMovies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // initialize UI stuff
        movieListView.setItems(filteredMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("ALL");
        genreComboBox.getItems().addAll(Genre.values());

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(event -> {
            Object selectedObject = genreComboBox.getValue(); // Get the selected genre
            String selectedGenre = selectedObject instanceof Genre ? ((Genre) selectedObject).toString() : "";

            filterMovies(filteredMovies, searchField.getText(), selectedGenre);

        });

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

    public static FilteredList<Movie> loadMovies(FilteredList<Movie> filteredMovies) throws IOException {
        List<Movie> allMovies = Movie.initializeMovies();
        filteredMovies = new FilteredList<>(FXCollections.observableArrayList(allMovies));
        return filteredMovies;
    }

    public static FilteredList<Movie> sortMovies(FilteredList<Movie> filteredMovies, boolean ascending) {
        if (ascending) {
            filteredMovies.getSource().sort(Comparator.comparing(Movie::getTitle));
        } else {
            filteredMovies.getSource().sort(Comparator.comparing(Movie::getTitle).reversed());
        }
        return filteredMovies;
    }

    public static FilteredList<Movie> filterMovies(FilteredList<Movie> filteredMovies, String selectedText, String selectedGenre) {
         Predicate<Movie> containsTitle = movie -> selectedText == null || movie.getTitle() != null && movie.getTitle().toLowerCase().contains(selectedText.toLowerCase());
         Predicate<Movie> containsDescription = movie -> selectedText == null || movie.getDescription() != null && movie.getDescription().toLowerCase().contains(selectedText.toLowerCase());
         Predicate<Movie> queryFilter = containsTitle.or(containsDescription);

         Predicate<Movie> containsGenre = movie -> selectedGenre == null || movie.getGenres().contains(selectedGenre);
         Predicate<Movie> filter = containsGenre.and(queryFilter);

         filteredMovies.setPredicate(filter);

         return filteredMovies;
     }
    String getMostPopularActor(List<Movie> movies){
        Map<String, Long> actorCounts = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream()) //stream of whole cast
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // group and count entries

        String mostPopularActor = actorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Vergleiche nach der Anzahl der Vorkommen
                .map(Map.Entry::getKey) // Extrahiere den Schauspieler
                .orElse(null); // Falls kein Schauspieler gefunden wurde

        return mostPopularActor;
    }
 }

