package at.ac.fhcampuswien.fhmdb.models.database;

import at.ac.fhcampuswien.fhmdb.exception.DatabaseException;
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

    private DatabaseManager() throws DatabaseException{
        try{
            createConnectionSource();
            movieDao = DaoManager.createDao(connectionSource,MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource,WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException e){
            throw new DatabaseException(e.getMessage(), e);
        }
    }
    public static DatabaseManager getInstance() throws DatabaseException{
        if(instance ==null) {
            try {
                instance = new DatabaseManager();
            } catch (SQLException se){
                throw new DatabaseException(se.getMessage(), se);
            }
        }
        return instance;
    }

    private static void createConnectionSource() throws DatabaseException {
        try {
            connectionSource = new JdbcConnectionSource(DB_URL,user,password);
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }
    public static ConnectionSource getConnectionSource(){
        return connectionSource;
    }

    private static void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException se) {
            throw new DatabaseException(se.getMessage(), se);
        }
    }

    Dao<WatchlistMovieEntity,Long> getWatchlistDao(){
        return watchlistDao;
    }
    Dao<MovieEntity,Long> getMovieDao(){
        return movieDao;
    }
}

    /*private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL,user,password);

    }*/
   /*private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }*/