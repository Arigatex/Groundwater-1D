package Groundwater1DGUI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Eqsolver {

    public static List<Node> Solve(double[][] A, double[] left, double[] right, double n, double x, double w0, double wl, double k1) {

        int dimension = (int) n;
        List<Node> nodeList = new ArrayList<Node>();
        DecimalFormat format1 = new DecimalFormat("0.00");

        double[] vector = new double[dimension];
        int[] permutations = new int[dimension];
        int rmax = 0;
        int r, c, k;

		/* max value in each row */

        for (r = 0; r < dimension; r++) {
            double big = 0.0;
            for (c = 0; c < dimension; c++)
                if (Math.abs(A[r][c]) > big)
                    big = Math.abs(A[r][c]);
            if (big == 0.0)
                System.out.println("Error: Matrix is singular!");
            vector[r] = 1.0 / big;
        }

		/* LU decomposition of matrix with pivot element and permutation */

        for (c = 0; c < dimension; c++) {
            for (r = 0; r < c; r++) {
                double sum = A[r][c];
                for (k = 0; k < r; k++)
                    sum -= A[r][k] * A[k][c];
                A[r][c] = sum;
            }
            double big = 0.0;
            for (r = c; r < dimension; r++) {
                double sum = A[r][c];
                for (k = 0; k < c; k++)
                    sum -= A[r][k] * A[k][c];
                A[r][c] = sum;
                if (vector[r] * Math.abs(sum) >= big) {
                    big = vector[r] * Math.abs(sum);
                    rmax = r;
                }
            }

			/* permutation */

            if (c != rmax) {
                for (k = 0; k < dimension; k++) {
                    double dum = A[rmax][k];
                    A[rmax][k] = A[c][k];
                    A[c][k] = dum;
                }
                vector[rmax] = vector[c];
            }
            permutations[c] = rmax;
            if (A[c][c] == 0.0)
                System.out.println("Error: Matrix is singular!");

            if (c != dimension) {
                double dum = 1.0 / A[c][c];
                for (r = c + 1; r < dimension; r++)
                    A[r][c] *= dum;
            }
        }

		/* copy righ vector to left vector */

        for (r = 0; r < dimension; r++)
            left[r] = right[r];

		/* forward substitution */

        int flag = -1;
        for (r = 0; r < dimension; r++) {
            double sum = left[permutations[r]];
            left[permutations[r]] = left[r];
            if (flag >= 0)
                for (c = flag; c < r; c++)
                    sum -= A[r][c] * left[c];
            else if (sum != 0.0)
                flag = r;
            left[r] = sum;
        }

		/* backward substitution */

        for (r = dimension - 1; r >= 0; r--) {
            double sum = left[r];
            for (c = r + 1; c < dimension; c++)
                sum -= A[r][c] * left[c];
            left[r] = sum / A[r][r];
        }

		/* copy result to left vector and building the node table*/

        System.out.println("Through Finite Difference Method:");

        //Initializing nodes on list and setting parameters for each node
        for (r = 0; r < dimension; r++) {
            System.out.print("h" + r + ": " + format1.format(left[r]) + "\t");
            nodeList.add(new Node());
            nodeList.get(r).setN(r+1);
            nodeList.get(r).setH(Double.parseDouble(format1.format(left[r])));
            nodeList.get(r).setX(Double.parseDouble(format1.format(r * x)));
            nodeList.get(r).setW(Double.parseDouble(format1.format(w0 + wl * r * x)));
            if (r != 0) {
                nodeList.get(r).setQ(Double.parseDouble(format1.format((-k1 / x) * (left[r] - left[r - 1]))));
            } else {
                nodeList.get(r).setQ(Double.parseDouble(format1.format((-k1 / x) * left[r])));
            }

        }

        System.out.println("\n");
        return nodeList;
    }
}
