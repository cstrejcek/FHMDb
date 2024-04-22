package at.ac.fhcampuswien.fhmdb.models.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    public static final String DB_URL = "jdbc:h2:file:./db/fhmdb";
    public static final String user = "user";
    public static final String password = "password";
    private static ConnectionSource connectionSource;
    private static DatabaseManager instance;
    private Dao<MovieEntity,Long> movieDao;
    private Dao<WatchlistMovieEntity,Long> watchlistDao;
    private DatabaseManager(){
        try{
            createConnectionSource();
            movieDao = DaoManager.createDao(connectionSource,MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource,WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static DatabaseManager getInstance(){
        if(instance ==null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL,user,password);

    }
    public static ConnectionSource getConnectionSource(){
        return connectionSource;
    }
    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }
    Dao<WatchlistMovieEntity,Long> getWatchlistDao(){
        return watchlistDao;
    }
    Dao<MovieEntity,Long> getMovieDao(){
        return movieDao;
    }
}
