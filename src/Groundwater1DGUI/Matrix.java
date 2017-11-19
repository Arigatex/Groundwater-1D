package Groundwater1DGUI;

import java.text.DecimalFormat;

class Matrix {

	private DecimalFormat df = new DecimalFormat("0.00");
	private boolean exceptiontop = false;
	private boolean exceptionbot = false;
	private double[][] matrix;
	private double[] vectorRight;
	private double[] vectorLeft;

	////////////////////////////////// Constructor/////////////////////////////////

	public Matrix(int n, double l, double k, double h0, double hl, double q0, double ql, double w0, double w1,int casem) {

		double x = l / (n - 1);
		double[] vectorRight = new double[n];
		double[] vectorLeft = new double[n];
		double[][] matrix = new double[n][n];// <-----------initialize the general // vector

		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix.length; col++) {

				if (row == col) { // <----------------------Middle diagonal

					if (row == 0) {//<-------Boundary condition at 0
						switch(casem) {
							case(1):
								matrix[row][col] = 1;
								break;
							case(3):
								matrix[row][col] = 1;
								break;
							default:
								matrix[row][col] = k/x;
						}
					}
					if (row == n-1) {//<--------Boundary condition at L
						switch(casem) {
							case(1):
								matrix[row][col] = 1;
								break;
							case(2):
								matrix[row][col] = 1;
								break;
							default:
								matrix[row][col] = -k/x;
						}
					}
					if (0 < row && row < n-1) {
						matrix[row][col] = (2 * k) / (x * x);
					}

				} else { //<-------------------------NOT middle diagonal

					if (row == col + 1 || row == col - 1) { // <---Secondary diagonals
						exceptiontop = false;
						exceptionbot = false;

						if (row == 0 && col == 1) { // Filling the top left element
							switch(casem) {
								case(1):
									matrix[row][col] = 0;
									break;
								case(3):
									matrix[row][col] = 0;
									break;
								default:
									matrix[row][col] = -k/x;
							}
							exceptiontop = true;
						}

						if (row == n-1 && col == n - 2) { // Filling the bottom right element
							switch(casem) {
								case(1):
									matrix[row][col] = 0;
									break;
								case(2):
									matrix[row][col] = 0;
									break;
								default:
									matrix[row][col] = k/x;
							}
							exceptionbot = true;
						}

						if (exceptiontop == false && exceptionbot == false) { // <----Filling the 2 sub-diagonals
							matrix[row][col] = (-k) / (x * x);
						}
					} else {
						matrix[row][col] = 0; // <----------------------------------Filling the 0s. Not necessary
					}
				}
			}
		}

		// Filling the right vector
		for (int i = 0; i < vectorRight.length; i++) {
			if (i != 0 && i != n-1) {//<----------Middle positions
				vectorRight[i] = w0+w1*i*x;
			} else {
				if (i == 0) {//<------Top position
					switch(casem) {
						case(1):
							vectorRight[i] = h0;
							break;
						case(3):
							vectorRight[i] = h0;
							break;
						default:
							vectorRight[i] = q0;
					}

				} else {//<-----------Bot position
					switch(casem) {
						case(1):
							vectorRight[i] = hl;
							break;
						case(2):
							vectorRight[i] = hl;
							break;
						default:
							vectorRight[i] = ql;
					}

				}
			}
		}
		setMatrix(matrix);
		setVectorRight(vectorRight);
		setVectorLeft(vectorLeft);

	}

	/////////////////////////// Setters/Getters//////////////////////////////////////////

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public void setVectorRight(double[] vector) {
		this.vectorRight = vector;
	}

	public double[] getVectorRight() {
		return vectorRight;
	}

	public void setVectorLeft(double[] vector) {
		this.vectorLeft = vector;
	}

	public double[] getVectorLeft() {
		return vectorLeft;
	}

	//////////////////////// Matrix///////////////////////////////////////////////////////////
	//////////////////////// Printer//////////////////////////////////////////////////////////

	public void MatrixPrinter() {

		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix.length; col++) {

				System.out.print(df.format(matrix[row][col]) + "\t"); // <-----------------Printing the Matrix
			}
			System.out.println("");
		}
		System.out.println();
		System.out.print("Left vector:\t");
		VectorPrinter(vectorLeft);
		System.out.print("Right vector:\t");
		VectorPrinter(vectorRight);
		System.out.println("");
	}

	/////////////////////////// Vector
	/////////////////////////// Printer///////////////////////////////////////////////

	public void VectorPrinter(double[] vector) {

		for (int row = 0; row < vector.length; row++) {

			System.out.print(df.format(vector[row]) + "\t"); // <-----------------Printing the Vector
		}
		System.out.println("");
	}
}
