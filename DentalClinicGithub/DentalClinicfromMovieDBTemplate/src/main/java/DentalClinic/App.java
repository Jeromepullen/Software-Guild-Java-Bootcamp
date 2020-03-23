package DentalClinic;

import Controller.Controller;
import Dao.AppointmentDaoException;
import Dao.AppointmentDaoImpl;
import Dao.CustomerDaoImpl;
import Dao.ProfessionalDaoImpl;
import UI.UserIO;
import UI.View;

public class App {

    public static void main(String[] args) throws AppointmentDaoException {

        UserIO io = new UserIO();
        View view = new View(io);

        AppointmentDaoImpl dao = new AppointmentDaoImpl();
        CustomerDaoImpl daoCust = new CustomerDaoImpl();
        ProfessionalDaoImpl daoPro = new ProfessionalDaoImpl();
        Controller controller = new Controller(view, dao, daoCust, daoPro);

        controller.run();
    }
//    public static void main(String[] args) throws AppointmentDaoException {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Controller controller = ac.getBean("Controller", Controller.class);
//
//        controller.run();
//    }
}
