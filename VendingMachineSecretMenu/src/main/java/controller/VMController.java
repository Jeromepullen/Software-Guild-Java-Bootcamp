/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.VMPersistenceException;
import dto.Candy;
import dto.SecretCandy;
import java.math.BigDecimal;
import java.util.List;
import service.InsufficientFundsException;
import service.NotFoundException;
import service.OutOfStockException;
import service.VMSecretServiceLayer;
import service.VMServiceLayer;
import ui.VMNumberFormatException;
import ui.VMView;

/**
 *
 * @author jeromepullenjr
 */
public class VMController {

    VMServiceLayer service;
    VMSecretServiceLayer secret;// initialize service and view
    VMView view;

    public VMController(VMServiceLayer service, VMSecretServiceLayer secret,
            VMView view) {
        this.service = service;
        this.secret = secret;// use this instance for service and view
        this.view = view;
    }

    public void run() {
        // boolean set to true will continue to display menu even if 
        // the user types an incorrect entry
        boolean keepGoing = true;
        displayMenuBanner();
        try {
            while (keepGoing) {

                displayMenu(); // lists VM inventory if it is currently in stock
                String userEntry = continueExitBanner();
                // quit or continue
                if (userEntry.equals("Exit")) {
                    keepGoing = false;
                    break;
                } else if (userEntry.equals("exit")) {
                    keepGoing = false;
                    break;
                }
// load user's cash into machine
                BigDecimal userCash = depositUserCash();
                Candy candyChoice = null;    // uninitialized 
                String userChoice = "";     // asks for user's choice of candy

                do {
                    userChoice = getCandyChoice(); // get user's choice of candy

                    if (userChoice.equalsIgnoreCase("secret")) {

//secret menu /////////////////////////////////////////////////////// secret menu ////////////////////////////////////////////////////////////////////////
                        displaySecretMenu();

                        SecretCandy secretCandyChoice = null;    // uninitialized 

                        do {
                            userChoice = getSecretCandyChoice(); // get user's choice of secret candy

                            try {         // get candy from inventory if it is in stock
                                secretCandyChoice = getSecretCandyInventory(userChoice);
                                inSecretStockStatus(secretCandyChoice.getSecretCandyName());
                            } catch (NotFoundException e) { // catch error
                                displayTryAgainBanner();        // and try again
                                view.displayErrorBanner(e.getMessage());
                            } catch (OutOfStockException e) {
                                secretCandyChoice = null;    // if out of stock display error
                                view.displayErrorBanner(e.getMessage());
                            }
                        } while (secretCandyChoice == null);
                        // buys candy if it is in the inventory and if the user has enough cash
                        try {
                            buySecretCandyInventory(secretCandyChoice, userCash);
                        } catch (InsufficientFundsException e) { // catch error if
                            view.displayErrorBanner(e.getMessage()); // funds insuff
                        }

//                        break;
                    }
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    try {         // get candy from inventory if it is in stock
                        candyChoice = getCandyInventory(userChoice);
                        inStockStatus(candyChoice.getCandyName());
                    } catch (NotFoundException e) { // catch error
                        displayTryAgainBanner();        // and try again
                        view.displayErrorBanner(e.getMessage());
                    } catch (OutOfStockException e) {
                        candyChoice = null;    // if out of stock display error
                        view.displayErrorBanner(e.getMessage());
                    }
                } while (candyChoice == null);
                // buys candy if it is in the inventory and if the user has enough cash
                try {
                    buyCandyInventory(candyChoice, userCash);
                } catch (InsufficientFundsException e) { // catch error if
                    view.displayErrorBanner(e.getMessage()); // funds insuff
                }
            }
        } catch (VMPersistenceException e) { // catch Persistence error
            view.displayErrorBanner(e.getMessage());
        }
        displayExitBanner(); // exit
    }
    // display only the candy in stock

    private void displayMenu() throws VMPersistenceException {
        List<Candy> candyInStock = service.getOnlyCandyInStock();

        view.displayInventoryEntireList(candyInStock);
    }

    private void displaySecretMenu() throws VMPersistenceException {
        List<SecretCandy> secretCandyInStock = secret.getOnlySecretCandyInStock();

        view.displaySecretInventoryEntireList(secretCandyInStock);
    }

    // BigDecimal conversion for deposit
    private BigDecimal depositUserCash() {
        BigDecimal userCash = null;
        boolean numberFormatError = false;
        do {
            try {
                userCash = view.depositUserCash(); // deposit user cash if 
                numberFormatError = false;        // # input is in proper format
            } catch (VMNumberFormatException e) {
                numberFormatError = true;    // throw error if incorrect format
                view.displayErrorBanner(e.getMessage());
            }
        } while (numberFormatError);
        return userCash;
    }
    // Banners etc.

    private String continueExitBanner() {
        return view.continueExitBanner();
    }

    private String getCandyChoice() {
        return view.getCandyChoice();
    }

    private String getSecretCandyChoice() {
        return view.getCandyChoice();
    }

    private Candy getCandyInventory(String candy) throws VMPersistenceException, NotFoundException {
        return service.getCandyInventory(candy);
    }

    private SecretCandy getSecretCandyInventory(String secretCandy) throws VMPersistenceException, NotFoundException {
        return secret.getSecretCandyInventory(secretCandy);
    }

    private void displayTryAgainBanner() {
        view.displayTryAgainBanner();
    }

    private void inStockStatus(String candyName) throws OutOfStockException, VMPersistenceException {
        service.inStockStatus(candyName);
    }

    private void inSecretStockStatus(String secretCandyName) throws OutOfStockException, VMPersistenceException {
        secret.inSecretStockStatus(secretCandyName);
    }

    private void buyCandyInventory(Candy candy, BigDecimal userCash) throws VMPersistenceException, InsufficientFundsException {

        String usersChange = service.buyCandyInventory(candy, userCash);
        view.displayChangeBanner(candy, usersChange);
        view.displayContinueBanner();
    }

    private void buySecretCandyInventory(SecretCandy secretCandy, BigDecimal userCash) throws VMPersistenceException, InsufficientFundsException {

        String usersChange = secret.buySecretCandyInventory(secretCandy, userCash);
        view.displaySecretChangeBanner(secretCandy, usersChange);
        view.displayContinueBanner();
    }

    private void displayMenuBanner() {
        view.displayMenuBanner();
    }

    private void displayExitBanner() {
        view.displayExitBanner();
    }

}
