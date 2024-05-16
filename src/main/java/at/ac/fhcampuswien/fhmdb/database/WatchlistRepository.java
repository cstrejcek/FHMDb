package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private static WatchlistRepository instance;
    private Dao<WatchlistMovieEntity,Long> dao;
    private WatchlistRepository() throws DatabaseException {
        dao = DatabaseManager.getInstance().getWatchlistDao();
    }
    public static WatchlistRepository getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
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
