package at.ac.fhcampuswien.fhmdb.exception;

import java.io.IOException;

public class MovieAPIException extends IOException {

    //extends RuntimeException if you want it to be unchecked
    public MovieAPIException(String message) {
        super(message);
    }

    public MovieAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
