package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    //TESTS FOR 4 STREAM METHODS -> 8 Tests
    @Test
    public void get_most_popular_actor_from_movies() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        String actual = HomeController.getMostPopularActor(movies);

        assertEquals("Brad Pitt", actual);
    }

    @Test
    public void get_most_popular_actor_from_movies_when_there_is_a_tie() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        String actual = HomeController.getMostPopularActor(movies);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void get_longest_movie_title_from_movies() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        int actual = HomeController.getLongestMovieTitle(movies);

        assertEquals(29,actual);
    }

    @Test
    public void count_movies_from_director() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        long actual = HomeController.countMoviesFrom(movies, "Quentin Tarantino");

        assertEquals(2, actual);
    }

    @Test
    public void count_movies_from_director_when_there_is_none() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        long actual = HomeController.countMoviesFrom(movies, "Quentin Tarantula");

        assertTrue(actual == 0.0);
    }

    @Test
    public void get_movies_between_years() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        List<Movie> actual = HomeController.getMoviesBetweenYears(movies, 1998, 2000);

        List<Movie> expected = Arrays.asList(movies.get(2), movies.get(3));
        assertEquals(expected, actual);
    }

    @Test
    public void get_movies_between_years_on_same_start_and_end_year() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );

        List<Movie> actual = HomeController.getMoviesBetweenYears(movies, 1999, 1999);

        List<Movie> expected = Arrays.asList(movies.get(2), movies.get(3));
        assertEquals(expected, actual);
    }

    @Test
    public void get_movies_between_years_with_unreasonable_end_year() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );
        assertThrows(IllegalArgumentException.class, () -> {
            HomeController.getMoviesBetweenYears(movies, 1998, 1995);
        });

    }

    //TESTS FOR FILTER AND SORTING
    @Test
    public void movies_are_sorted_in_ascending_order() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );
        List<Movie> expected = new ArrayList<>();
        expected.add(movies.get(0));
        expected.add(movies.get(3));
        expected.add(movies.get(2));
        expected.add(movies.get(1));

        List<Movie> actual = HomeController.sortMovies(movies, true);

        assertEquals(expected, actual.stream().toList());
    }

    @Test
    public void movies_are_sorted_in_descending_order() {
        List<Movie> movies = Arrays.asList(
                new Movie("Once upon a time in Hollywood","...",Arrays.asList(Genre.COMEDY,Genre.DRAMA),
                        Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino"),
                        2019,7.7,161,"",""),
                new Movie("The Matrix","...", Arrays.asList(Genre.ACTION,Genre.SCIENCE_FICTION),
                        Arrays.asList("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss"),
                        Arrays.asList("Lana Wachowski","Lilly Wachowski"),Arrays.asList("Lana Wachowski","Lilly Wachowski"),
                        1999,8.7,136,"",""),
                new Movie("Seven", "..." ,Arrays.asList(Genre.CRIME,Genre.DRAMA,Genre.MYSTERY,Genre.THRILLER),
                        Arrays.asList("Morgan Freeman", "Kevin Spacey", "Brad Pitt"),
                        Arrays.asList("Kevin Fincher"),Arrays.asList("Andrew Kevin Walker"),
                        1995,8.6,127,"",""),
                new Movie("Pulp Fiction","...", Arrays.asList(Genre.CRIME,Genre.DRAMA),
                        Arrays.asList("John Travolta", "Uma Thurman", "Samuel L. Jackson"),
                        Arrays.asList("Quentin Tarantino"),Arrays.asList("Quentin Tarantino ","Roger Avary"),
                        1999,8.7,136,"","")
        );
        List<Movie> expected = new ArrayList<>();
        expected.add(movies.get(1));
        expected.add(movies.get(2));
        expected.add(movies.get(3));
        expected.add(movies.get(0));

        List<Movie> actual = HomeController.sortMovies(movies, false);

        assertEquals(expected, actual.stream().toList());
    }

    @Test
    void movies_are_filtered_by_genre() {
        String genreFilter = Genre.FAMILY.name(); // Genre to filter
        List<Movie> actual = HomeController.filterAndSortMovies("",genreFilter,"","", Sort.UNSORTED);

        //All movies in the filtered list should have the specified genre
        assertTrue(actual.stream().allMatch(movie -> movie.getGenreString().contains(genreFilter)));
    }
    @Test
    void movies_filtered_by_genre_and_search_text() {
        String genreFilter = Genre.DRAMA.toString();
        String searchText = "the Godfather";
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,genreFilter,"","",Sort.UNSORTED);

        //Filtered list should contain movies that match the search text and genre.
        assertTrue(actual.stream().anyMatch(movie -> movie.getGenreString().contains(genreFilter) &&
                movie.getTitle().toLowerCase().contains(searchText.toLowerCase())));
    }


    @Test
    void movies_are_filtered_by_genres_filter_is_deleted_afterwards() {

        List<Movie> testMovies = null;
        try {
            testMovies = HomeController.loadMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String genreFilter = Genre.ACTION.name();
        List<Movie> filteredMovies = HomeController.filterAndSortMovies("",genreFilter,"","",Sort.UNSORTED);

        long expectedActionMoviesCount = testMovies.stream().filter(movie -> movie.getGenreString().contains(Genre.ACTION.name())).count(); // Verify the filtered list size after applying the genre filter
        assertEquals(expectedActionMoviesCount, filteredMovies.size()); //Filtered list should contain only movies with genre ACTION

        filteredMovies = HomeController.filterAndSortMovies("","","","",Sort.UNSORTED); // Reset the filter by applying null for both parameters

        assertEquals(testMovies.size(), filteredMovies.size()); //Filtered list should contain all movies after resetting the filter
    }

    @Test
    void filter_is_empty_and_should_show_all_movies() {
        List<Movie> testMovies = null;
        try {
            testMovies = HomeController.loadMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Calling filterMovies with null values to simulate no filtering
        List<Movie> filteredMovies = HomeController.filterAndSortMovies("","","","",Sort.UNSORTED);
        assertEquals(testMovies.size(), filteredMovies.size()); //All movies should be displayed when filter is empty.
    }

    @Test
    void movies_filtered_by_search_with_partial_text() {

        String searchText = "Go";
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,"","","",Sort.UNSORTED);

        //Filtered list should contain movies with 'Go' in the title
        assertTrue(actual.stream().anyMatch(movie -> movie.getTitle().toLowerCase().contains(searchText.toLowerCase())));
    }

    @Test
    void movies_filtered_by_search_in_description() {
        String searchText = "aging"; //in The Godfather
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,"","","",Sort.UNSORTED);

        //List should not contain movies with 'aging'
        assertTrue(actual.stream().noneMatch(movie -> movie.getDescription().toLowerCase().contains(searchText.toLowerCase())));
    }

    @Test
    void filter_function_can_handle_null_values_without_errors() {
        List<Movie> expected = null;
        try {
            expected = HomeController.loadMovies();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Movie> actual = HomeController.filterAndSortMovies(null,null,null,null,Sort.UNSORTED);
        // First, check if the sizes of the lists are equal
        assertEquals(expected.size(), actual.size());

        // Then, compare each element in the lists
        for (int i = 0; i < expected.size(); i++) {
            Movie expectedMovie = expected.get(i);
            Movie actualMovie = actual.get(i);
            // Check if the movies are equal
            assertTrue(expectedMovie.toString().equals(actualMovie.toString()));
        }
    }

    @Test
    void movies_filtered_by_search_with_no_results() {

        String searchText = "Harry Potter";
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,"","","",Sort.UNSORTED);

        //List should be empty when no movies match the search criteria
        assertTrue(actual.isEmpty());
    }
    @Test
    void movies_filtered_by_release_year() {

        String releaseYear = "1995";
        int releaseYearInt = Integer.parseInt(releaseYear);
        List<Movie> actual = HomeController.filterAndSortMovies("","",releaseYear,"",Sort.UNSORTED);

        //Filtered list should contain movie
        assertTrue(actual.stream().anyMatch(movie -> movie.getReleaseYear()== releaseYearInt ));
    }

    @Test
    void movies_filtered_by_rating() {

        String rating = "9.0";
        double ratingDouble =Double.parseDouble(rating);
        List<Movie> actual = HomeController.filterAndSortMovies("","","",rating,Sort.UNSORTED);

        //Filtered list should contain movie
        assertTrue(actual.stream().anyMatch(movie -> movie.getRating()== ratingDouble ));
    }

    @Test
    void movies_filtered_by_search_case_insensitive() {

        String searchText = "gOdFaThEr"; // Case-insensitive search text
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,"","","",Sort.UNSORTED);

        //List should contain 'Godfather' regardless of case
        assertTrue(actual.stream().anyMatch(movie -> movie.getTitle().equalsIgnoreCase("The Godfather")));
    }

    @Test
    void movies_filtered_and_sorted_ascending() {

        String searchText = "god";
        List<Movie> unsorted = HomeController.filterAndSortMovies(searchText,"","","",Sort.UNSORTED);
        List<Movie> expected = new ArrayList<>();
        expected.add(unsorted.get(1));
        expected.add(unsorted.get(0));
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,"","","",Sort.ASCENDING);

        for (int i = 0; i < expected.size(); i++) {
            Movie expectedMovie = expected.get(i);
            Movie actualMovie = actual.get(i);
            // Check if the movies are equal
            assertTrue(expectedMovie.toString().equals(actualMovie.toString()));
        }
    }

    @Test
    void movies_filtered_and_sorted_descending() {

        String searchText = "god";
        List<Movie> unsorted = HomeController.filterAndSortMovies(searchText,"","","",Sort.UNSORTED);
        List<Movie> expected = new ArrayList<>();
        expected.add(unsorted.get(0));
        expected.add(unsorted.get(1));
        List<Movie> actual = HomeController.filterAndSortMovies(searchText,"","","",Sort.DESCENDING);

        for (int i = 0; i < expected.size(); i++) {
            Movie expectedMovie = expected.get(i);
            Movie actualMovie = actual.get(i);
            // Check if the movies are equal
            assertTrue(expectedMovie.toString().equals(actualMovie.toString()));
        }
    }
}