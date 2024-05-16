package at.ac.fhcampuswien.fhmdb.ui.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public class UnsortedState implements MovieSortState {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        // no sorting
        return movies;
    }

    @Override
    public MovieSortState next() {
        return new AscendingSortState();
    }
}
