package MovieDatabase.Dao;

public class MovieDatabaseDaoException extends Exception {

    public MovieDatabaseDaoException(String message) {
        super(message);
    }

    public MovieDatabaseDaoException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
