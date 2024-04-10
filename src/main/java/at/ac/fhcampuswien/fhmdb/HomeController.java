package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public Label moviesLabel;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;
    @FXML
    public JFXComboBox releaseYearComboBox;
    @FXML
    public JFXComboBox fromRatingComboBox;

    @FXML
    public JFXButton sortBtn;

    private List<Movie> movies;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            movies = loadMovies();
            moviesLabel.setText("Showing " + movies.size() + " movies");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // initialize UI stuff
        movieListView.setItems(FXCollections.observableArrayList(movies));   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("");
        genreComboBox.getItems().addAll(Arrays.stream(Genre.values()).map(Enum::name).collect(Collectors.toList()));

        releaseYearComboBox.setPromptText("Filter by Release Year");
        // Extract unique release years from the list of movies using streams
        ObservableList<Integer> releaseYears = movies.stream()
                .map(Movie::getReleaseYear)
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        releaseYearComboBox.getItems().add("");
        releaseYearComboBox.getItems().addAll(releaseYears);

        fromRatingComboBox.setPromptText("Filter by From Rating");
        //Extract unique ratings from the list of movies using streams
        ObservableList<Double> fromRatings = movies.stream()
                .map(Movie::getRating)
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        fromRatingComboBox.getItems().add("");
        fromRatingComboBox.getItems().addAll(fromRatings);


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(event -> {
            String searchText = searchField.getText().toLowerCase();
            String selectedGen = genreComboBox.getSelectionModel().getSelectedItem() == null? "" : genreComboBox.getSelectionModel().getSelectedItem().toString();
            String yearText = releaseYearComboBox.getSelectionModel().getSelectedItem() == null ? "" : releaseYearComboBox.getSelectionModel().getSelectedItem().toString();
            String ratingText = fromRatingComboBox.getSelectionModel().getSelectedItem() == null ? "" : fromRatingComboBox.getSelectionModel().getSelectedItem().toString();
            movies = filterAndSortMovies(searchText,selectedGen,yearText,ratingText,Sort.UNSORTED);
            sortBtn.setText("Sort");
            movieListView.setItems(FXCollections.observableArrayList(movies));
            moviesLabel.setText("Showing " + movies.size() + " movies");
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)") || sortBtn.getText().equals("Sort")) {
                // TODO sort observableMovies ascending
                HomeController.sortMovies(movies, true);
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                HomeController.sortMovies(movies, false);
                sortBtn.setText("Sort (asc)");
            }
            movieListView.setItems(FXCollections.observableArrayList(movies));
        });
    }

    public static List<Movie> loadMovies() throws IOException {
        return Movie.initializeMovies();
    }

    public static List<Movie> sortMovies(List<Movie> movies, boolean ascending) {
        if (ascending) {
            movies.sort(Comparator.comparing(Movie::getTitle));
        } else {
            movies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }
        return movies;
    }

    public static List<Movie> filterAndSortMovies(String query,String genre,
                                                  String releaseYear,String rating,Sort sortOrder) {
        List<Movie> movies;
        try {
            movies = MovieAPI.filterMovies(query,genre,releaseYear,rating);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(sortOrder == Sort.ASCENDING)
            movies=sortMovies(movies,true);
        else if (sortOrder == Sort.DESCENDING)
            movies=sortMovies(movies,false);
        return movies;
    }

    public static String getMostPopularActor(List<Movie> movies){
        Map<String, Long> actorCounts = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream()) // Stream of whole cast
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // Group and count entries

        // Find the maximum count
        Long maxCount = actorCounts.values().stream()
                .max(Long::compareTo)
                .orElse(0L);

        // Check if there is more than one actor with the highest count
        boolean hasTie = actorCounts.values().stream()
                .filter(count -> count.equals(maxCount))
                .count() > 1;

        // If there is a tie, return an empty string; otherwise, return the most popular actor
        if (hasTie) {
            return null;
        } else {
            String mostPopularActor = actorCounts.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(maxCount))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null); // If no actor found
            return mostPopularActor;
        }
    }

    public static int getLongestMovieTitle(List<Movie> movies) {
        int maxLength = movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)// es wird in int umgewandelt
                .max()//größter Wert
                .orElse(0); //Falls die Liste leer ist

        return maxLength;
    }

    public static long countMoviesFrom(List<Movie> movies, String director) {
        long count = movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count(); //zählt die gefilterten Filme

        return count; //Anzahl der Filme wird zurückgegeben
    }

    public static List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        if(startYear>endYear)
            throw new IllegalArgumentException("Start Year needs to be lower than or same as End Year");
        List<Movie> filteredMovies = movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList()); //Sammeln der Filme in einer Lister

        return filteredMovies;
    }
 }

