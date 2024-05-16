package at.ac.fhcampuswien.fhmdb.ui.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public class MovieSorter {
    private MovieSortState currentState;

    public MovieSorter() {
        currentState = new UnsortedState(); // start state
    }

    public List<Movie> sort(List<Movie> movies) {
        return currentState.sort(movies);
    }

    public void next() {
        currentState = currentState.next();
    }
}
