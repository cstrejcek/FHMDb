package at.ac.fhcampuswien.fhmdb.models.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private Dao<WatchlistMovieEntity,Long> dao;
    public WatchlistRepository(){
        dao = DatabaseManager.getInstance().getWatchlistDao();
    }
    public List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }
    public int addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        return dao.create(movie);
    }
    public int removeFromWatchlist(String apiId) throws SQLException {
        return dao.delete(dao.queryForEq("apiId",apiId));
    }
}
