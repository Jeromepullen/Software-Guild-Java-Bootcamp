/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Scanner;

/**
 *
 * @author jeromepullenjr
 */
public class UserIOConsoleImpl implements UserIO {   //implements interface

    Scanner sc = new Scanner(System.in);
    // Implement Override Annotations parsing scanner input

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        String input = sc.next();
        double inputDouble = Double.parseDouble(input);

        return inputDouble;

    }

    @Override
    public double readDouble(String prompt, double min, double max) {

        double valueDouble = Double.parseDouble(prompt);
        while (true) {
            if (valueDouble < min || valueDouble > max) {
                System.out.println("Please enter a value between " + min + " - " + max + ".");
                prompt = sc.next();
                valueDouble = Double.parseDouble(prompt);
            } else if (valueDouble > min && valueDouble < max) {
                return valueDouble;
            }
        }
    }

    @Override
    public float readFloat(String prompt) {
        float valueFloat = Float.parseFloat(prompt);
        return valueFloat;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float valueFloat = Float.parseFloat(prompt);
        while (true) {
            if (valueFloat < min || valueFloat > max) {
                System.out.println("Please enter a value between " + min + " - " + max + ".");
                prompt = sc.next();
                valueFloat = Float.parseFloat(prompt);
            } else if (valueFloat > min && valueFloat < max) {
                return valueFloat;
            }
        }
    }

    @Override
    public int readInt(String prompt) {
        int valueInt = Integer.parseInt(prompt);
        return valueInt;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        String inputInt = sc.nextLine();
        int valueInt = Integer.parseInt(inputInt);

        while (true) {
            if (valueInt >= min && valueInt <= max) {
                return valueInt;
            }
        }
    }

    @Override
    public long readLong(String prompt) {
        long valueLong = Long.parseLong(prompt);
        return valueLong;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String stringRead = sc.nextLine();
        return stringRead;
    }
}
