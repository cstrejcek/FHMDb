package at.ac.fhcampuswien.fhmdb.ui.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;
import java.util.List;

public class AscendingSortState implements MovieSortState {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
        return movies;
    }

    @Override
    public MovieSortState next() {
        return new DescendingSortState();
    }
}
