package Groundwater1DGUI;

import java.util.Scanner;

public class Validate {

    private double newinput;
    public static String casedetected;
    private static int casenum = 0;
    int validation;

    public Validate(String read, Scanner input, String arg) {

        while (!validateDouble(read)) {
            System.out.println(arg);
            read = input.next();
        }
    }

    public boolean validateDouble(String input) {
        try {
            Double.parseDouble(input);
            this.setValue(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid value");
            return false;
        }
    }

    public int validateNumbers(String read) {

        int aux = Integer.parseInt(read);

        switch (aux) {
            case (0):
                validation = 0;
                break;
            case (1):
                validation = 1;
                break;
            default:
                validation = 2;
        }
        return validation;
    }

    ////////////////////// Identifying the case/type of matrix to be
    ////////////////////// used////////////////////////////////

    public static int identifyCase(String choice0, String choicel) {

        if (choice0.equalsIgnoreCase("h0") && choicel.equalsIgnoreCase("hl")) {
            casedetected = "h0 and hl given";
            casenum = 1;
        }
        if (choice0.equalsIgnoreCase("q0") && choicel.equalsIgnoreCase("hl")) {
            casedetected = "q0 and hl given";
            casenum = 2;
        }
        if (choice0.equalsIgnoreCase("h0") && choicel.equalsIgnoreCase("ql")) {
            casedetected = "h0 and ql given";
            casenum = 3;
        }
        if (choice0.equalsIgnoreCase("q0") && choicel.equalsIgnoreCase("ql")) {
            casedetected = "Unsuitable border conditions";
            casenum = 4;
        }
        return casenum;
    }

    ////////////////////////////////// Setters and
    ////////////////////////////////// Getters/////////////////////////////////

    private void setValue(String input) {
        this.newinput = Double.parseDouble(input);
    }

    public double getValue() {
        return this.newinput;
    }

}
