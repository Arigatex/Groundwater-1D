package Groundwater1DGUI;

public class AnaliticMethod {

	double hx;

	public double calculate(double l, double hl, double h0) {
		// hl/2=a1*(L/2)+h0
		// a1=(1/L)*(hL-h0)
		// a0=h0

		hx = (1 / l) * (hl - h0) * (l / 2) + h0;
		return hx;
	}
}
