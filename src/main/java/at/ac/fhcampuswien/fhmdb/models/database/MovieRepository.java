package at.ac.fhcampuswien.fhmdb.models.database;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private Dao<MovieEntity,Long> dao;

    public MovieRepository() throws DatabaseException {
        dao = DatabaseManager.getInstance().getMovieDao();
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
  /*public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }*/
    /*public int removeAll() throws SQLException {
        return dao.deleteBuilder().delete();
    }*/
  /*int addAllMovies(List<Movie> movies) throws SQLException {
        return dao.create(MovieEntity.fromMovies(movies));
    }*/