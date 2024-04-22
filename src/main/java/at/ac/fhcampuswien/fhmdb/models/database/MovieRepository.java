package at.ac.fhcampuswien.fhmdb.models.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private Dao<MovieEntity,Long> dao;

    public MovieRepository(){
        dao = DatabaseManager.getInstance().getMovieDao();
    }
    public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }
    public int removeAll() throws SQLException {
        return dao.deleteBuilder().delete();
    }
    public MovieEntity getMovie(){
        return null;
    }
    int addAllMovies(List<Movie> movies) throws SQLException {
        return dao.create(MovieEntity.fromMovies(movies));
    }
}
