/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dto.Candy;
import dto.SecretCandy;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jeromepullenjr
 */
public class VMView {

    private UserIO io;

    public VMView(UserIO io) {
        this.io = io;
    }
    // stream for inventory menu

    public void displayInventoryEntireList(List<Candy> inventory) {
        inventory.stream()
                .forEach(i -> io.print(i.getCandyName()
                + "  -  $" + i.getCost()));
    }
    // BigDecimal conversion for deposit

    public void displaySecretInventoryEntireList(List<SecretCandy> secretInventory) {
        secretInventory.stream()
                .forEach(i -> io.print(i.getSecretCandyName()
                + "  -  $" + i.getCost()));
    }
    // BigDecimal conversion for deposit

    public BigDecimal depositUserCash() throws VMNumberFormatException {
        BigDecimal userCash = null;
        try {
            userCash = new BigDecimal(io.readString("Deposit Money (in a number format like: 1 or 1.00): "));
        } catch (NumberFormatException e) { // catch error
            throw new VMNumberFormatException("\tNumbers must be entered", e);
        }
        return userCash;
    }

    public String getCandyChoice() {
        return io.readString("\nEnter Your Choice: ");
    }

    public String getSecretCandyChoice() {
        return io.readString("\nEnter Your Choice: ");
    }   // display banners

    public String continueExitBanner() {
        return io.readString("\nPlease hit 'Enter' to add money\n"
                + "============= OR ==============\n"
                + "Type 'Exit' to quit the program");
    }

    public void displayMenuBanner() {
        io.print("\n==== Vending Machine Menu ====");

    }

    public void displayChangeBanner(Candy candy, String usersChange) {
        io.print(candy.getCandyName() + " has been selected");
        io.print(usersChange);
    }

    public void displaySecretChangeBanner(SecretCandy secretCandy, String usersChange) {
        io.print(secretCandy.getSecretCandyName() + " has been selected");
        io.print(usersChange);
    }

    public void displayErrorBanner(String error) {
        io.print("\n===========     ERROR     ===========\n");
        io.print(error);
        io.print("\n===========     ERROR     ===========\n");
    }

    public void displayContinueBanner() {
        io.print("\n===============================\n");
        io.readString("Please hit 'Enter' to continue");
        io.print("===============================\n");
    }

    public void displayTryAgainBanner() {
        io.print("\n=== Try Again, the selection is invalid ===\n");
    }

    public void displayExitBanner() {
        io.print("\n===============================");
        io.print("===============================");
        io.print("=         Byeeeeee!!         =");
        io.print("===============================");
        io.print("===============================\n");
    }

}
