package MovieDatabase.UI;

import MovieDatabase.Model.Movie;
import java.util.List;

//THIS VIEW CLASS CONSISTS OF METHODS CREATED SPECIFICALLY TO DISPLAY THE INFORMATION TO THE USER
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public int displayMenuAndSelect() {

        io.print("\nMain Menu");
        io.print("==========");

        io.print("0. [Exit]");
        io.print("1. Add a movie.");
        io.print("2. Edit a movie.");
        io.print("3. Delete a movie.");
        io.print("4. Display all movies.");
        io.print("5. Display a single movie.");
        io.print("6. Search for a movie.");

        return io.readInt("Select [0-6]:", 0, 6);
    }

    public void sayGoodbye() {
        io.print("\nGoodbye...");
    }

    public Movie makeMovie() {

        io.print("\nAdd a Movie");
        io.print("============");

        Movie movie = new Movie();
        movie.setTitle(io.readRequiredString("Movie Title:"));
        movie.setReleaseDate(io.readRequiredString("Release Date:"));
        movie.setMpaaRating(io.readRequiredString("MPAA Rating:"));
        movie.setDirectorName(io.readRequiredString("Director Name:"));
        movie.setStudio(io.readRequiredString("Studio Name:"));
        movie.setUserRating(io.readRequiredString("User Rating:"));
        return movie;
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public void displayException(Exception ex) {
        io.print("\nERROR");
        io.print("=======");
        io.print(ex.toString());
    }

    public void displayMovies(List<Movie> all) {
        io.print("\nAll Movies");
        io.print("============");
        if (all.isEmpty()) {
            io.print("No movies founds.");
            return;
        }

        for (Movie b : all) {
            io.print(b.getMovieId() + ": " + b.getTitle() + ", " + b.getReleaseDate()
                    + ", " + b.getMpaaRating() + ", " + b.getDirectorName() + ", " + b.getStudio()
                    + ", " + b.getUserRating()
            );
        }
    }

    public int readMovieId(String header) {
        io.print("");
        io.print(header);
        io.print("============");
        return io.readInt("Movie Id?:");
    }

    public Movie update(Movie movie) {

        String title = io.readString(
                String.format("Movie Title (%s):", movie.getTitle()));
        if (!title.isEmpty()) {
            movie.setTitle(title);
        }

        String releaseDate = io.readString(
                String.format("Release Date (%s):", movie.getReleaseDate()));
        if (!releaseDate.isEmpty()) {
            movie.setReleaseDate(releaseDate);
        }

        String mpaaRating = io.readString(
                String.format("MPAA Rating (%s):", movie.getMpaaRating()));
        if (!mpaaRating.isEmpty()) {
            movie.setMpaaRating(mpaaRating);
        }

        String DirectorName = io.readString(
                String.format("Director Name (%s):", movie.getDirectorName()));
        if (!DirectorName.isEmpty()) {
            movie.setDirectorName(DirectorName);
        }

        String studio = io.readString(
                String.format("Studio Name (%s):", movie.getStudio()));
        if (!studio.isEmpty()) {
            movie.setStudio(studio);
        }

        String userRating = io.readString(
                String.format("User Rating (%s):", movie.getUserRating()));
        if (!userRating.isEmpty()) {
            movie.setUserRating(userRating);
        }

        return movie;
    }

    public void displaySearchBanner() {
        io.print("Search Movies");
    }

    public void displayDisplayMovieBanner() {
        io.print("=== View Movie Information ===");
    }

    public String getSearch() {
        String movieTitle = io.readString("Search for movies that start with: ");
        return movieTitle;
    }

    public void displayMovieList(List<Movie> movieList) {
        for (Movie movie : movieList) {
            io.print(movie.getTitle());
            io.print("Release Date: " + movie.getReleaseDate());
            io.print("Rated: " + movie.getMpaaRating());
            io.print("Directed by: " + movie.getDirectorName());
            io.print("Studio: " + movie.getStudio());
            io.print("User rating: " + movie.getUserRating());
            io.print("--------");
        }
    }

    public String getMovieTitleChoice() {
        String movieTitle = io.readString("Title of movie that you would "
                + "like more information about: ");
        return movieTitle;
    }

    public void displayMovie(Movie movie) {

        if (movie != null) {
            io.print(movie.getTitle());
            io.print(movie.getReleaseDate());
            io.print(movie.getMpaaRating());
            io.print(movie.getDirectorName());
            io.print(movie.getStudio());
            io.print(movie.getUserRating());
        } else {
            io.print("That movie is not in the database.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayViewMovieBanner() {
        io.print("=== View Movie Information ===");
    }

}
