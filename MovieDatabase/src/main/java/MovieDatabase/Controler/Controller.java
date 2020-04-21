package MovieDatabase.Controler;

import MovieDatabase.Dao.MovieDatabaseDaoException;
import MovieDatabase.Model.Movie;
import MovieDatabase.UI.View;
import java.util.List;
import MovieDatabase.Dao.MovieDatabaseDao;

public class Controller {

    private final View view;
    private final MovieDatabaseDao dao;

    public Controller(View view, MovieDatabaseDao dao) {
        this.view = view;
        this.dao = dao;
    }

    public void run() throws MovieDatabaseDaoException {
        int selection = 0;
        do {
            selection = view.displayMenuAndSelect();
            switch (selection) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    editMovie();
                    break;
                case 3:
                    deleteMovie();
                    break;
                case 4:
                    displayAll();
                    break;
                case 5:
                    displayOne();
                    break;
                case 6:
                    searchMovies();
                    break;

            }
        } while (selection > 0);

        view.sayGoodbye();
    }

    private void addMovie() {
        //show the output for the user to begin inputting the movie
        Movie movie = view.makeMovie();
        try {
            //sends the movie info to the dao 
            dao.create(movie);

            //shows if the movie was successfully created (from the view)
            view.displayMessage(movie.getTitle() + " created!");
        } catch (MovieDatabaseDaoException ex) {

            //shows this if the movie was NOT successfully created
            view.displayException(ex);
        }

    }

    private void displayAll() {
        try {
            //retrieves the entire ArrayList of movies fro the dao
            List<Movie> all = dao.findAll();

            //shows all of the movies that were retrieved
            view.displayMovies(all);

        } catch (MovieDatabaseDaoException ex) {
            //shows this if the movies were not retrieved properly
            view.displayException(ex);
        }
    }

    private void editMovie() {
        //asks for the movieId from the user 
        int movieId = view.readMovieId("Edit Movie");

        try {

            Movie movie = dao.findById(movieId);
            //if there is no movie found it will display that it wasn't found
            if (movie == null) {
                view.displayMessage("Movie Id " + movieId + " not found.");
                return;
            }
            //calls the update method in the view which updatse each part of the movie
            movie = view.update(movie);
            if (dao.update(movie)) {

                //displays if movie was successfully updated
                view.displayMessage(movie.getTitle() + " updated!");
            } else {

                //displays if movie was not found
                view.displayMessage("Movie Id " + movieId + " not found.");
            }
        } catch (MovieDatabaseDaoException ex) {
            //if there was an error updating the movie
            view.displayException(ex);
        }

    }

    private void deleteMovie() {
        try {
            //reads the movieId input from the user
            int movieId = view.readMovieId("Delete Movie");
            if (dao.delete(movieId)) {
                //if successfully deleted
                view.displayMessage("Movie Id " + movieId + " deleted.");
            } else {
                //if not successfully deleted
                view.displayMessage("Movie Id " + movieId + " not found.");
            }
            //if movie was not found
        } catch (MovieDatabaseDaoException ex) {
            view.displayException(ex);
        }

    }

    private void displayOne() throws MovieDatabaseDaoException {

        //asks for movieID
        int movieId = view.readMovieId("Movie Info");

        //looks up the movie that matches the id inputted
        Movie movie = dao.findById(movieId);

        //displays movie info that matches the id
        view.displayMovie(movie);
    }

    private void searchMovies() throws MovieDatabaseDaoException {
        try {
            view.displaySearchBanner();

            //get the user input for what the movie starts with
            String startsWith = view.getSearch();

            // get movie data for every movie that starts with the user input
            List<Movie> All = dao.searchMovies(startsWith);

            //display movies that meet this criteria
            view.displayMovieList(All);

        } catch (MovieDatabaseDaoException ex) {
            view.displayException(ex);
        }
    }
}
