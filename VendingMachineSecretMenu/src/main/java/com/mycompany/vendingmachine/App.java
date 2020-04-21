/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine;

import controller.VMController;
import dao.VMDao;
import dao.VMDaoFileImpl;
import dao.VMSecretDao;
import dao.VMSecretDaoFileImpl;
import service.VMSecretServiceLayer;
import service.VMSecretServiceLayerImpl;
import service.VMServiceLayer;
import service.VMServiceLayerImpl;
import ui.UserIO;
import ui.UserIOConsoleImpl;
import ui.VMView;

/**
 *
 * @author jeromepullenjr
 */
public class App {

    public static void main(String[] args) {
        UserIO io = new UserIOConsoleImpl();
        VMView view = new VMView(io);
        VMDao dao = new VMDaoFileImpl();
        VMSecretDao secretDao = new VMSecretDaoFileImpl();
        VMSecretServiceLayer secret = new VMSecretServiceLayerImpl(secretDao);
//        VMDao dao = new JSONDaoFileImpl();
        VMServiceLayer service = new VMServiceLayerImpl(dao);
        VMController controller = new VMController(service, secret, view);
        controller.run();
//
//        ApplicationContext ctx
//                = new ClassPathXmlApplicationContext("applicationContext.xml");
//        VMController controller
//                = ctx.getBean("controller", VMController.class);
//        controller.run();
    }

}
