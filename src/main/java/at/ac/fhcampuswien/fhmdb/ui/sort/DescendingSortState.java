package at.ac.fhcampuswien.fhmdb.ui.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class DescendingSortState implements MovieSortState {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle).reversed());
        return movies;
    }

    @Override
    public MovieSortState next() {
        return new AscendingSortState();
    }
}
