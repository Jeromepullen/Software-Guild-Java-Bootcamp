/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Run;

import controller.Controller;
import dao.DaoPersistanceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.DataValidationException;

/**
 *
 * @author jeromepullenjr
 */
public class App {

    public static void main(String[] args) throws DaoPersistanceException, DataValidationException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller controller = ac.getBean("controller", Controller.class);

        controller.run();
    }
}
