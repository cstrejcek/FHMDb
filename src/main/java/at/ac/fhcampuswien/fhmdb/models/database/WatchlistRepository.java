package at.ac.fhcampuswien.fhmdb.models.database;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private Dao<WatchlistMovieEntity,Long> dao;
    public WatchlistRepository() throws DatabaseException {
        dao = DatabaseManager.getInstance().getWatchlistDao();
    }
    public List<WatchlistMovieEntity> getWatchlist() throws DatabaseException {
        try {
            return dao.queryForAll();
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }
    public int addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try {
            return dao.create(movie);
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }
    public int removeFromWatchlist(String apiId) throws DatabaseException {
        try {
            return dao.delete(dao.queryForEq("apiId",apiId));
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }
}
