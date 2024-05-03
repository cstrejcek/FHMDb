package at.ac.fhcampuswien.fhmdb.exception;

import java.sql.SQLException;

public class DatabaseException extends SQLException {

    //extends RuntimeException if you want it to be unchecked
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

