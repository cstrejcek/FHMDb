package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    public void movies_are_sorted_in_ascending_order() {
        //Given --> Conditions for the Test
        List<Movie> movies = Movie.initializeMovies().subList(0, Math.min(5, Movie.initializeMovies().size())); // Test with the first 5 movies

        // When --> actions executed during the test
        FilteredList<Movie> actual = HomeController.sortMovies(new FilteredList<>(FXCollections.observableArrayList(movies)), true);

        //Then --> ensure that the expected behavior has occurred after the action in the "When" section
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
    // Given
    List<Movie> testMovies = Movie.initializeMovies();

    //When
    String genreFilter = Genre.FAMILY.name(); // Genre to filter
    FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), null, genreFilter);

    // Then
    //assertTrue(filteredMovies.size() > 0); //Filtered list should contain at least one movie
    assertTrue(filteredMovies.stream().allMatch(movie -> movie.getGenres().contains(genreFilter))); //All movies in the filtered list should have the specified genre
}

    @Test
    void movies_filtered_by_genre_and_search_text() {
        // Given
        List<Movie> testMovies = Movie.initializeMovies();

        // When
        String genreFilter = Genre.DRAMA.name();
        String searchText = "dead";
        FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), searchText, genreFilter);

        // Then
        assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getGenres().contains(genreFilter) && movie.getTitle().toLowerCase().contains(searchText.toLowerCase()))); //Filtered list should contain movies that match the search text and genre.
    }

    @Test
    void movies_are_filtered_by_genres_filter_is_deleted_afterwards() {
        // Given
        List<Movie> testMovies = Movie.initializeMovies();

        // 1. When
        String genreFilter = Genre.ACTION.name();
        FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), null, genreFilter);

        // 1. Then
        long expectedActionMoviesCount = testMovies.stream().filter(movie -> movie.getGenres().contains(Genre.ACTION.name())).count(); // Verify the filtered list size after applying the genre filter
        assertEquals(expectedActionMoviesCount, filteredMovies.size()); //Filtered list should contain only movies with genre ACTION

        //2. When
        HomeController.filterMovies(filteredMovies, null, null); // Reset the filter by applying null for both parameters

        // 2.Then
        assertEquals(testMovies.size(), filteredMovies.size()); //Filtered list should contain all movies after resetting the filter
    }

    @Test
    void filter_is_empty_and_should_show_all_movies() {
        // Given
        List<Movie> testMovies = Movie.initializeMovies();

        // When
        // Calling filterMovies with null values to simulate no filtering
        FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), null, null);

        // Then
        assertEquals(testMovies.size(), filteredMovies.size()); //All movies should be displayed when filter is empty.
    }

    @Test
    void movies_filtered_by_search_with_partial_text() {
        // Given
        List<Movie> testMovies = Movie.initializeMovies();

        // When
        String searchText = "grump";
        FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), searchText, null);

        // Then
        assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getTitle().toLowerCase().contains(searchText.toLowerCase()))); //Filtered list should contain movies with 'grump' in the title
    }

    @Test
    void movies_filtered_by_search_in_description() {
        // Given
        List<Movie> testMovies = Movie.initializeMovies();

        // When
        String searchText = "thief";
        FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), searchText, null);

        // Then
        assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getDescription().toLowerCase().contains(searchText.toLowerCase()))); //Filtered list should contain movies with 'thief' in the description
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
        // Given
        List<Movie> testMovies = Movie.initializeMovies();

        // When
        String searchText = "inception";
        // Using filterMovies method to apply the search filter
        FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), searchText, null);

        // Then
        assertTrue(filteredMovies.isEmpty()); //Filtered list should be empty when no movies match the search criteria
    }

        @Test
    void movies_filtered_by_search_case_insensitive() {
            // Given
            List<Movie> testMovies = Movie.initializeMovies();

            // When
            String searchText = "JUmaNjI"; // Case-insensitive search text
            FilteredList<Movie> filteredMovies = HomeController.filterMovies(new FilteredList<>(FXCollections.observableArrayList(testMovies)), searchText, null);

            // Then
            assertTrue(filteredMovies.stream().anyMatch(movie -> movie.getTitle().equalsIgnoreCase("Jumanji"))); //Filtered list should contain 'Jumanji' regardless of case
    }
}