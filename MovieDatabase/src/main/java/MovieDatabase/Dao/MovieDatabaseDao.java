package MovieDatabase.Dao;

import MovieDatabase.Model.Movie;
import java.util.List;

public interface MovieDatabaseDao {

    //add new movie
    Movie create(Movie movie) throws MovieDatabaseDaoException;

    //deletes movie
    boolean delete(int movieId) throws MovieDatabaseDaoException;

    //finds all movies
    List<Movie> findAll() throws MovieDatabaseDaoException;

    //finds movie that matches Id
    Movie findById(int movieId) throws MovieDatabaseDaoException;

    //updates(edits) movie details
    boolean update(Movie movie) throws MovieDatabaseDaoException;

    //searches through the movie list
    List<Movie> searchMovies(String input) throws MovieDatabaseDaoException;

    //displays movie from the Id inputted by the user
    Movie displayOne(int movieId) throws MovieDatabaseDaoException;

}
