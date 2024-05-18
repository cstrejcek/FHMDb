package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
import at.ac.fhcampuswien.fhmdb.ui.watchlist.WatchlistObservable;
import at.ac.fhcampuswien.fhmdb.ui.watchlist.WatchlistObserver;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements WatchlistObservable {
    private static WatchlistRepository instance;
    private Dao<WatchlistMovieEntity,Long> dao;
    private List<WatchlistObserver> observers;
    private WatchlistRepository() throws DatabaseException {
        dao = DatabaseManager.getInstance().getWatchlistDao();
        observers = new ArrayList<>();
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
            int result = dao.create(movie);
            notifyObservers("Movie successfully added to watchlist.");
            return result;
        } catch (SQLException se) {
            notifyObservers("Error adding movie to watchlist: " + se.getMessage());
            throw new DatabaseException(se.getMessage(), se);
        }
    }
    public int removeFromWatchlist(String apiId) throws DatabaseException {
        try {
            int result = dao.delete(dao.queryForEq("apiId",apiId));
            notifyObservers("Successfully removed movie to watchlist. ");
            return result;
        } catch (SQLException se) {
            notifyObservers("Error removing movie to watchlist: " + se.getMessage());
            throw new DatabaseException(se.getMessage(), se);
        }
    }

    @Override
    public void addObserver(WatchlistObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WatchlistObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (WatchlistObserver observer : observers) {
            observer.update(message);
        }
    }
}
