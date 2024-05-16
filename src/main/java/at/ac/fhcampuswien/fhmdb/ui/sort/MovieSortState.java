package at.ac.fhcampuswien.fhmdb.ui.sort;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.List;

public interface MovieSortState {
    List<Movie> sort(List<Movie> movies);
    MovieSortState next();
}
