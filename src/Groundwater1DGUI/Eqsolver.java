package Groundwater1DGUI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Eqsolver {

    public static List<Node> Solve(double[][] A, double[] left, double[] right, double n, double x, double w0, double wl, double k, int casenum) {

        int dimension = (int) n;
        List<Node> nodeList = new ArrayList<Node>();
        DecimalFormat format1 = new DecimalFormat("0.00");

        double[] vector = new double[dimension];
        int[] permutations = new int[dimension];
        int rmax = 0;
        int i, c, ka;

		/* max value in each row */

        for (i = 0; i < dimension; i++) {
            double big = 0.0;
            for (c = 0; c < dimension; c++)
                if (Math.abs(A[i][c]) > big)
                    big = Math.abs(A[i][c]);
            if (big == 0.0)
                System.out.println("Error: Matrix is singular!");
            vector[i] = 1.0 / big;
        }

		/* LU decomposition of matrix with pivot element and permutation */

        for (c = 0; c < dimension; c++) {
            for (i = 0; i < c; i++) {
                double sum = A[i][c];
                for (ka = 0; ka < i; ka++)
                    sum -= A[i][ka] * A[ka][c];
                A[i][c] = sum;
            }
            double big = 0.0;
            for (i = c; i < dimension; i++) {
                double sum = A[i][c];
                for (ka = 0; ka < c; ka++)
                    sum -= A[i][ka] * A[ka][c];
                A[i][c] = sum;
                if (vector[i] * Math.abs(sum) >= big) {
                    big = vector[i] * Math.abs(sum);
                    rmax = i;
                }
            }

			/* permutation */

            if (c != rmax) {
                for (ka = 0; ka < dimension; ka++) {
                    double dum = A[rmax][ka];
                    A[rmax][ka] = A[c][ka];
                    A[c][ka] = dum;
                }
                vector[rmax] = vector[c];
            }
            permutations[c] = rmax;
            if (A[c][c] == 0.0)
                System.out.println("Error: Matrix is singular!");

            if (c != dimension) {
                double dum = 1.0 / A[c][c];
                for (i = c + 1; i < dimension; i++)
                    A[i][c] *= dum;
            }
        }

		/* copy righ vector to left vector */

        for (i = 0; i < dimension; i++)
            left[i] = right[i];

		/* forward substitution */

        int flag = -1;
        for (i = 0; i < dimension; i++) {
            double sum = left[permutations[i]];
            left[permutations[i]] = left[i];
            if (flag >= 0)
                for (c = flag; c < i; c++)
                    sum -= A[i][c] * left[c];
            else if (sum != 0.0)
                flag = i;
            left[i] = sum;
        }

		/* backward substitution */

        for (i = dimension - 1; i >= 0; i--) {
            double sum = left[i];
            for (c = i + 1; c < dimension; c++)
                sum -= A[i][c] * left[c];
            left[i] = sum / A[i][i];
        }

		/* copy result to left vector and building the node table*/

        System.out.println("Through Finite Difference Method:");

        //Initializing nodes on list and setting parameters for each node
        for (i = 0; i < dimension; i++) {
            System.out.print("h" + i + ": " + format1.format(left[i]) + "\t");
            nodeList.add(new Node());
            nodeList.get(i).setN(i + 1);
            nodeList.get(i).setH(Double.parseDouble(format1.format(left[i])));
            nodeList.get(i).setX(Double.parseDouble(format1.format(i * x)));
            nodeList.get(i).setW(Double.parseDouble(format1.format(w0 + wl * i * x)));

            //Setting q values on the list
            if (i != 0 && i != dimension - 1) { //Middle Elements
                nodeList.get(i).setQ(Double.parseDouble(format1.format((-k / (2 * x)) * (left[i + 1] -  left[i - 1]))));
            } else { //Node 0
                if (i == 0) {
                    if (casenum == 2) { //q0 is given
                        nodeList.get(i).setQ(Double.parseDouble(format1.format(right[i])));
                    } else { //h0 is given
                        nodeList.get(i).setQ(Double.parseDouble(format1.format((-k / x) * (left[i+1] - left[i]))));
                    }
                } else { //Node n
                    if (casenum == 3) { //qn is given
                        nodeList.get(i).setQ(Double.parseDouble(format1.format(right[i])));
                    } else { //hn is given
                        nodeList.get(i).setQ(Double.parseDouble(format1.format((-k / x) * (left[i - 1] - left[i - 2]))));
                    }
                }
            }
        }
        System.out.println("\n");
        return nodeList;
    }
}
