package Groundwater1DGUI;

import java.util.Scanner;

public class ReadUserInput {

	Scanner input = new Scanner(System.in);
	double l = 0;
	double k = 0;
	double n = 0;
	double h0 = 0;
	double hl = 0;
	double q0 = 0;
	double ql = 0;
	double w0 = 0;
	double wl = 0;
	int switch0 = 0;
	boolean invalid0 = true;
	int switchl = 0;
	boolean invalid1 = true;
	double[][] matrix = new double[5][5];
	double[] vectorleft = new double[5];
	double[] vectorright = new double[5];
	boolean exceptiontop = false;
	boolean exceptionbot = false;

	////////////////////// Reading the Input//////////////////////////////

	public void readInput() {

		String read;
		Validate validate;
		String arg;

		// Length
		System.out.println("Enter the system length: ");
		read = input.next();
		arg = "Enter the system length: ";
		validate = new Validate(read, input, arg);
		l = validate.getValue();

		// Conductivity
		System.out.println("Enter the system conductivity: ");
		read = input.next();
		arg = "Enter the system conductivity: ";
		validate = new Validate(read, input, arg);
		k = validate.getValue();

		// Number of nodes
		System.out.println("Enter the number of nodes: ");
		read = input.next();
		arg = "Enter the number of nodes: ";
		validate = new Validate(read, input, arg);
		n = validate.getValue();

		// H0 - Q0
		System.out.println("H0 or Q0?: 1/2");
		h0 = 0;

		while (invalid0 == true) {
			invalid0 = false;
			switch0 = input.nextInt();
			switch (switch0) {
			case (1): // H0
				System.out.println("Enter H0: ");
				h0 = input.nextInt();
				q0 = 0;
				invalid0 = false;
				break;

			case (2): // Q0
				System.out.println("Enter Q0: ");
				q0 = input.nextDouble();
				h0 = 0;
				invalid0 = false;
				break;

			default: // Otherwise
				System.out.println("Invalid input");
				invalid0 = true;
			}
		}

		// Hl -Ql
		System.out.println("Hl or Ql?: 1/2");
		hl = 0;

		while (invalid1 == true) {
			invalid1 = false;
			switchl = input.nextInt();
			switch (switchl) {
			case (1): // Hl
				System.out.println("Enter Hl: ");
				hl = input.nextInt();
				ql = 0;
				invalid1 = false;
				break;

			case (2): // Ql
				System.out.println("Enter Ql: ");
				ql = input.nextDouble();
				hl = 0;
				invalid1 = false;
				break;

			default: // Otherwise
				System.out.println("Invalid input");
				invalid1 = true;
			}
		}

		// W0
		System.out.println("Enter W0: ");
		read = input.next();
		arg = "Enter W0: ";
		validate = new Validate(read, input, arg);
		w0 = validate.getValue();

		// Wl
		System.out.println("Enter Wl: ");
		read = input.next();
		arg = "Enter W0: ";
		validate = new Validate(read, input, arg);
		w0 = validate.getValue();

		System.out.println("\n" + "Data Input Complete!" + "\n");
	}
}
