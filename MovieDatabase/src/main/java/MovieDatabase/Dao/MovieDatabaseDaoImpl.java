package MovieDatabase.Dao;

import MovieDatabase.Model.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseDaoImpl implements MovieDatabaseDao {

    private static final String FILE_PATH = "movies";
    private static final String DELIMITER = "::";

    @Override
    public Movie create(Movie movie) throws MovieDatabaseDaoException {
        int movieId = 0;
        List<Movie> all = findAll();
        for (Movie b : all) {
            movieId = Math.max(movieId, b.getMovieId());
        }
        movie.setMovieId(movieId + 1);
        all.add(movie);

        save(all);

        return movie;
    }

    @Override
    public boolean update(Movie movie) throws MovieDatabaseDaoException {
        List<Movie> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getMovieId() == movie.getMovieId()) {
                all.set(i, movie);
                save(all);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(int movieId) throws MovieDatabaseDaoException {
        List<Movie> all = findAll();
        for (Movie b : all) {
            if (b.getMovieId() == movieId) {
                all.remove(b);
                save(all);
                return true;
            }
        }
        return false;
    }

    private void save(List<Movie> movies) throws MovieDatabaseDaoException {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Movie b : movies) {
                writer.println(marshall(b));
            }
        } catch (IOException ex) {
            throw new MovieDatabaseDaoException(ex.getMessage(), ex);
        }
    }

    private String marshall(Movie b) {
        return b.getMovieId() + DELIMITER
                + b.getTitle() + DELIMITER
                + b.getReleaseDate() + DELIMITER
                + b.getMpaaRating() + DELIMITER
                + b.getDirectorName() + DELIMITER
                + b.getStudio() + DELIMITER
                + b.getUserRating();
    }

    private Movie unmarshall(String line) {

        String[] tokens = line.split(DELIMITER);
        if (tokens.length != 7) {
            return null;
        }

        Movie movie = new Movie();
        movie.setMovieId(Integer.parseInt(tokens[0]));
        movie.setTitle(tokens[1]);
        movie.setReleaseDate(tokens[2]);
        movie.setMpaaRating(tokens[3]);
        movie.setDirectorName(tokens[4]);
        movie.setStudio(tokens[5]);
        movie.setUserRating(tokens[6]);
        return movie;
    }

    @Override
    public Movie findById(int movieId) throws MovieDatabaseDaoException {
        for (Movie b : findAll()) {
            if (b.getMovieId() == movieId) {
                return b;
            }
        }
        return null;
    }

    @Override
    public List<Movie> findAll() throws MovieDatabaseDaoException {
        ArrayList<Movie> all = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Movie movie = unmarshall(line);
                if (movie != null) {
                    all.add(movie);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new MovieDatabaseDaoException(ex.getMessage(), ex);
        }
        return all;
    }

    @Override
    public Movie displayOne(int movieId) throws MovieDatabaseDaoException {
        for (Movie o : findAll()) {
            if (o.getMovieId() == movieId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public List<Movie> searchMovies(String input) throws MovieDatabaseDaoException {

        List<Movie> matches = new ArrayList<>();
        for (Movie movie : findAll()) {
            String title = movie.getTitle();
            if (title.contains(input)) {
                matches.add(movie);
            }
        }
        return matches;
    }

}
