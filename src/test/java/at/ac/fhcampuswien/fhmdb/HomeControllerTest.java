package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    void movies_filtered_by_genre() {
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
    void movies_filtered_by_genre_and_search_text() {
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
    void movies_filtered_by_genres_and_back_to_all_movies() {
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
    void if_filter_is_empty_it_should_show_all_movies() {
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
    void search_with_partial_text() {
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
    void search_in_description() {
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
    void filter_function_handles_null_values() {
        //Given
        List<Movie> testMovies = Movie.initializeMovies();
        testMovies.add(new Movie(null, null, Arrays.asList(Genre.DRAMA)));

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        //When
        String searchText = "alien";
        //avoiding null pointer exceptions by checking if the title and description are not null before calling methods on them
        filteredMovies.setPredicate(movie ->
                (movie.getTitle() != null && movie.getTitle().toLowerCase().contains(searchText.toLowerCase())) ||
                        (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(searchText.toLowerCase())));

        //Then
        //ensure that the filter function can handle null values in movie titles or descriptions without causing errors --> assertDoesNotThrow
        assertDoesNotThrow(() -> filteredMovies.size(), "Filter function should handle null values without throwing an exception.");
    }

    @Test
    void search_with_no_results() {
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
    void search_case_insensitive() {
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
/*
   @Test
    public void testExample() {
        String message = "Test";
        System.out.println(message);
        assertEquals(message, "Test");
    }
 */

/*
    @Test
    void movies_filtered_by_genre_and_search_text() {
        List<Movie> testMovies2 = Arrays.asList(
          new Movie("The Godfather", "The aging patriarch of an organized crime dynasty ...", Arrays.asList(Genre.CRIME, Genre.DRAMA)),
          new Movie("The Dark Knight", "When the menace known as the Joker wreaks havoc ...", Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA)),
          new Movie("Coming to America", "An extremely pampered African prince travels to Queens, New York ...", Arrays.asList(Genre.COMEDY, Genre.ROMANCE)),
          new Movie("Casablanca", "A cynical expatriate American cafe owner struggles ...", Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.WAR))
        );

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies2);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        Genre genreFilter = Genre.DRAMA;
        String searchText = "Godfather";
        filteredMovies.setPredicate(movie ->
                movie.getGenres().contains(genreFilter.name()) &&
                movie.getTitle().toLowerCase().contains(searchText.toLowerCase()));

        assertTrue(filteredMovies.contains(testMovies2.get(0)), "Filtered list should contain The Godfather.");
        assertFalse(filteredMovies.contains(testMovies2.get(1)), "Filtered list should not contain The Dark Knight, doesn't match with the search text.");
        assertFalse(filteredMovies.contains(testMovies2.get(2)), "Filtered list should not contain Coming to America, doesn't match with the search text.");
        assertFalse(filteredMovies.contains(testMovies2.get(3)), "Filtered list should not contain Casablanca, doesn't match with the search text.");

        assertEquals(1, filteredMovies.size(), "Filtered list should contain one movie.");
    }
 */

/*
    @Test
    void movies_filtered_by_genres_and_back_to_all_movies() {
        List<Movie> testMovies3 = Arrays.asList(
          new Movie("Catch Me If You Can", "Barely 17 yet, Frank is skilled forger ...", Arrays.asList(Genre.BIOGRAPHY, Genre.CRIME, Genre.DRAMA)),
          new Movie("Blow", "The story of how George Jung. along with the Medellín Cartel headed by Pablo Escobar ...", Arrays.asList(Genre.BIOGRAPHY, Genre.CRIME, Genre.DRAMA)),
          new Movie("The Pianist", "During WWII, acclaimed Polish musician Wladyslaw faces various struggles ...", Arrays.asList(Genre.BIOGRAPHY, Genre.DRAMA)),
          new Movie("Alien", "The crew of a commercial spacecraft encounters a deadly lifeform ...", Arrays.asList(Genre.HORROR, Genre.SCIENCE_FICTION))
        );

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies3);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        Genre genreFilter = Genre.BIOGRAPHY;
        filteredMovies.setPredicate(movie -> movie.getGenres().contains(genreFilter.name()));

        assertTrue(filteredMovies.contains(testMovies3.get(0)), "Filtered list should contain Catch Me If You Can.");
        assertTrue(filteredMovies.contains(testMovies3.get(1)), "Filtered list should contain Blow.");
        assertTrue(filteredMovies.contains(testMovies3.get(2)), "Filtered list should contain The Pianist.");
        assertFalse(filteredMovies.contains(testMovies3.get(3)), "Filtered list should not contain Alien.");

        filteredMovies.setPredicate(movie -> true);

        assertTrue(filteredMovies.contains(testMovies3.get(0)), "Filtered list should contain Catch Me If You Can.");
        assertTrue(filteredMovies.contains(testMovies3.get(1)), "Filtered list should contain Blow.");
        assertTrue(filteredMovies.contains(testMovies3.get(2)), "Filtered list should contain The Pianist.");
        assertTrue(filteredMovies.contains(testMovies3.get(3)), "Filtered list should contain Alien.");

        assertEquals(4, filteredMovies.size(), "Filtered list should contain all movies.");
    }
 */

/*
    @Test
     void filter_function_handles_null_values() {
        List<Movie> testMovies = Arrays.asList(
            new Movie("Inception", "A thief with the rare ability...", Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)),
            new Movie(null, "No title, but has a description.", Arrays.asList(Genre.DRAMA)),
            new Movie("No Description Movie", null, Arrays.asList(Genre.COMEDY))
        );

        ObservableList<Movie> observableMovies = FXCollections.observableArrayList(testMovies);
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, p -> true);

        String searchText = "inception";
        filteredMovies.setPredicate(movie ->
            (movie.getTitle() != null && movie.getTitle().toLowerCase().contains(searchText.toLowerCase())) ||
            (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(searchText.toLowerCase())));

        assertDoesNotThrow(() -> filteredMovies.size(), "Filter function should handle null values without throwing an exception.");
    }
*/