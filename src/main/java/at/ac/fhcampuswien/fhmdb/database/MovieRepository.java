package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private Dao<MovieEntity,Long> dao;

    private MovieRepository() throws DatabaseException {
        dao = DatabaseManager.getInstance().getMovieDao();
    }
    public static MovieRepository getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }
    public List<MovieEntity> getAllMovies() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }
    public int removeAll() throws DatabaseException {
        try {
            return dao.deleteBuilder().delete();
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }
    public MovieEntity getMovie(){
        return null;
    }
    public int addAllMovies(List<Movie> movies) throws DatabaseException {
        try {
            return dao.create(MovieEntity.fromMovies(movies));
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }

    }
}