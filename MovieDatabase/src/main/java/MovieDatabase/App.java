package MovieDatabase;

import MovieDatabase.Controler.Controller;
import MovieDatabase.Dao.MovieDatabaseDaoException;
import MovieDatabase.Dao.MovieDatabaseDaoImpl;
import MovieDatabase.UI.UserIO;
import MovieDatabase.UI.View;

public class App {

    public static void main(String[] args) throws MovieDatabaseDaoException {

        UserIO io = new UserIO();
        View view = new View(io);

        MovieDatabaseDaoImpl dao = new MovieDatabaseDaoImpl();
        Controller controller = new Controller(view, dao);

        controller.run();
    }
}
