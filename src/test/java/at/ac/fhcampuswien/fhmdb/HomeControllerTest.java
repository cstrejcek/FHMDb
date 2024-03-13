package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    public void movies_are_sorted_in_ascending_order() {
        //Given
        List<Movie> movies = Movie.initializeMovies().subList(0, Math.min(5, Movie.initializeMovies().size())); // Test with the first 5 movies

        // When
        FilteredList<Movie> actual = HomeController.sortMovies(new FilteredList<>(FXCollections.observableArrayList(movies)), true);

        //Then
        List<Movie> expected = new ArrayList<>();
        expected.add(movies.get(4));
        expected.add(movies.get(2));
        expected.add(movies.get(1));
        expected.add(movies.get(0));
        expected.add(movies.get(3));
        assertEquals(expected, actual.stream().toList());
    }


    @Test
    public void movies_are_sorted_in_descending_order() {
        //Given
        List<Movie> movies = Movie.initializeMovies().subList(0, Math.min(5, Movie.initializeMovies().size())); // Test with the first 5 movies

        // When
        FilteredList<Movie> actual = HomeController.sortMovies(new FilteredList<>(FXCollections.observableArrayList(movies)), false);

        //Then
        List<Movie> expected = new ArrayList<>();
        expected.add(movies.get(3));
        expected.add(movies.get(0));
        expected.add(movies.get(1));
        expected.add(movies.get(2));
        expected.add(movies.get(4));
        assertEquals(expected, actual.stream().toList());
    }

    @Test
    void movies_are_filtered_by_genre() {
        //Given --> Conditions for the Test
        List<Movie> testMovies = Movie.initializeMovies(); //loads data source

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies); //ObservableList is a list that allows listeners to track changes when they occur
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true); //all items from observableMovies are included in filteredMovies

        //When --> actions executed during the test
        Genre genreFilter = Genre.FAMILY;
        //This method adjusts the filtering criterion for filteredMovies
        filteredMovies.setPredicate(movie -> movie.getGenres().contains(genreFilter.name())); //The predicate is configured to check if the movie's genres include the FAMILY genre

        //Then --> ensure that the expected behavior has occurred after the action in the "When" section
        assertTrue(filteredMovies.size() > 0, "Filtered list should contain at least one movie."); //The first assertTrue checks that the filtered list is not empty
        //The second assertTrue confirms that every movie in the filtered list belongs to the FAMILY genre
        assertTrue(filteredMovies.stream().allMatch(movie -> Arrays.asList(movie.getGenres().split(",")).contains(genreFilter.name())), "All movies in the filtered list should have the FAMILY genre.");
    }

    @Test
    void movies_are_filtered_by_genre_and_search_text() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        Genre genreFilter = Genre.DRAMA;
        String searchText = "dead";
        //filters the movies by both genre and title --> ignoring case sensitivity.
        filteredMovies.setPredicate(movie ->
                movie.getGenres().contains(genreFilter.name()) &&
                        movie.getTitle().toLowerCase().contains(searchText.toLowerCase()));

        //Then
        //count the number of movies in filteredMovies that are exactly titled "Dead Man Walking", ignoring case differences.
        long count = filteredMovies.stream().filter(movie -> movie.getTitle().equalsIgnoreCase("Dead Man Walking")).count();
        //checks that there is exactly one movie in the filtered list with that title
        assertTrue(count == 1, "Filtered list should contain exactly one movie titled 'Dead Man Walking'.");
    }

    @Test
    void movies_are_filtered_by_genres_filter_is_deleted_afterwards() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //1. When
        Genre genreFilter = Genre.ACTION;
        //The predicate for filteredMovies is set to include only the movies that belong to the genre
        filteredMovies.setPredicate(movie -> movie.getGenres().contains(genreFilter.name()));

        //1. Then
        //The code calculates the number of movies in the original list (testMovies) and checks if the filtered list (filteredMovies) has the same number of movies
        long actionMovies = testMovies.stream().filter(movie -> movie.getGenres().contains(Genre.ACTION.name())).count();
        assertEquals(actionMovies, filteredMovies.size(), "Filtered list should contain only movies with genre ACTION.");

        //2. When
        filteredMovies.setPredicate(movie -> true); //The filter predicate is reset, intending to display all movies regardless of their genre.

        //2. Then
        //This assertion checks that the size of filteredMovies is equal to the size of testMovies after removing the filter --> shows all movies
        assertEquals(testMovies.size(), filteredMovies.size(), "Filtered list should contain all movies.");
    }

    @Test
    void filter_is_empty_and_should_show_all_movies() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        filteredMovies.setPredicate(movie -> true); //it's set to display all movies (it represents a clear action)

        //Then
        //all movies should be displayed, the filtered list should contain the same number of movies as the original list
        assertEquals(testMovies.size(), filteredMovies.size(), "All movies should be displayed when filter is empty.");
    }

    @Test
    void movies_filtered_by_search_with_partial_text() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        String searchText = "grump";
        //it filters the list to include only movies whose titles contain the string "grump"
        filteredMovies.setPredicate(movie -> movie.getTitle().toLowerCase().contains(searchText.toLowerCase()));

        //Then
        //ensures that the condition specified holds true --> at least one movie meets the condition, the test will pass
        assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getTitle().toLowerCase().contains(searchText.toLowerCase())), "Filtered list should contain movies with 'grump' in the title.");
    }

    @Test
    void movies_filtered_by_search_in_description() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        String searchText = "thief";
        //set to filter the movies by their description, including only those whose descriptions contain the word "thief"
        filteredMovies.setPredicate(movie -> movie.getDescription().toLowerCase().contains(searchText.toLowerCase()));

        //Then
        //there is at least one movie in the filteredMovies list whose description contains the word "thief" --> stream().anyMatch()
        assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getDescription().contains("thief")), "Filtered list should contain 'Heat' when searching in descriptions.");
    }

    @Test
    void filter_function_can_handle_null_values_without_errors() {
        //Given
        List<Movie> testMovies = new ArrayList<>();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        FilteredList<Movie> actual = HomeController.filterMovies(filteredMovies,null,null);

        //Then
        assertEquals(actual,filteredMovies);
    }

    @Test
    void movies_filtered_by_search_with_no_results() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        String searchText = "inception";
        //filter the movies by their titles, including only those whose titles contain the string
        filteredMovies.setPredicate(movie -> movie.getTitle().toLowerCase().contains(searchText.toLowerCase()));

        //Then
        assertTrue(filteredMovies.isEmpty(), "Filtered list should be empty when no movies match the search criteria."); //checks if the filteredMovies list is empty
    }

    @Test
    void movies_filtered_by_search_case_insensitive() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        String searchText = "JUmaNjI";
        filteredMovies.setPredicate(movie ->
                movie.getTitle() != null && movie.getTitle().toLowerCase().contains(searchText.toLowerCase()));

        //Then
        //checks if there is at least one movie, ignoring case differences
        assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getTitle().equalsIgnoreCase("Jumanji")), "Filtered list should contain 'Jumanji' regardless of case");
    }
}