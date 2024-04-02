package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
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
import java.util.*;
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
    public TextField releaseYearField;
    @FXML
    public TextField ratingField;

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
        genreComboBox.getItems().addAll(Arrays.stream(Genre.values()).map(Enum::name).collect(Collectors.toList()));
        //genreComboBox.getSelectionModel().select("ALL");

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(event -> filterMovies());

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
    public void filterMovies() {
        String searchText = searchField.getText().toLowerCase();
        String selectedGen = (String) genreComboBox.getSelectionModel().getSelectedItem();
        String yearText = releaseYearField.getText();
        String ratingText = ratingField.getText();

        Predicate<Movie> textPredicate = movie -> searchText.isEmpty()
                || movie.getTitle().toLowerCase().contains(searchText)
                || movie.getDescription().toLowerCase().contains(searchText)
                || movie.getMainCast().stream().anyMatch(cast -> cast.toLowerCase().contains(searchText))
                || movie.getDirectors().stream().anyMatch(director -> director.toLowerCase().contains(searchText))
                || movie.getWriters().stream().anyMatch(writer -> writer.toLowerCase().contains(searchText));

        /*Predicate<Movie> genrePredicate = movie -> "ALL".equals(selectedGen)
                || (movie.getGenres() != null && movie.getGenres().stream().anyMatch(genre -> genre.toString().equals(selectedGen)));
*/
        Predicate<Movie> genrePredicate = movie -> {
            //Fall: wo kein Genre ausgewählt wurde oder "ALL" ausgewählt wurde
            if (selectedGen == null || selectedGen.isEmpty() || "ALL".equals(selectedGen)) {
                return true; // Akzeptiere alle Filme
            } else {
                return movie.getGenres() != null && movie.getGenres().stream().anyMatch(genre -> genre.toString().equals(selectedGen));
            }
        };

        Predicate<Movie> yearPredicate = movie -> yearText.isEmpty()
                || String.valueOf(movie.getReleaseYear()).equals(yearText);

        Predicate<Movie> ratingPredicate = movie -> ratingText.isEmpty()
                || (movie.getRating() >= Double.parseDouble(ratingText));

        Predicate<Movie> combinedPredicate = textPredicate.and(genrePredicate).and(yearPredicate).and(ratingPredicate);

        this.filteredMovies.setPredicate(combinedPredicate);
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

    int getLongestMovieTitle(List<Movie> movies) {
        int maxLength = movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)// es wird in int umgewandelt
                .max()//größter Wert
                .orElse(0); //Falls die Liste leer ist

        return maxLength;
    }

    long countMoviesFrom(List<Movie> movies, String director) {
        long count = movies.stream()
                .filter(movie -> director.equals(movie.getDirectors()))
                .count(); //zählt die gefilterten Filme

        return count; //Anzahl der Filme wird zurückgegeben
    }

    List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        List<Movie> filteredMovies = movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList()); //Sammeln der Filme in einer Lister

        return filteredMovies;
    }
 }

