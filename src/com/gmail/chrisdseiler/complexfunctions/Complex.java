package com.gmail.chrisdseiler.complexfunctions;
public class Complex {
	public static final Complex I = new Complex(0, 1);
	public final double a, b;
	public Complex(double a, double b) {
		this.a = a;
		this.b = b;
	}
	public Complex plus(Complex z) {
		return new Complex(this.a + z.a, this.b + z.b);
	}
	public Complex plus(double d) {
		return new Complex(this.a + d, this.b);
	}
	public Complex plus(double a, double b) {
		return new Complex(this.a + a, this.b + b);
	}
	public Complex minus(Complex z) {
		return new Complex(this.a - z.a, this.b - z.b);
	}
	public Complex minus(double d) {
		return new Complex(this.a - d, this.b);
	}
	public Complex minus(double a, double b) {
		return new Complex(this.a - a, this.b - b);
	}
	public Complex times(Complex z) {
		// (a+bi)(c+di) = ac + adi + bci - bd
		// = (ac - bd) + (ad + bc)i
		return new Complex(this.a * z.a - this.b * z.b, this.a * z.b + this.b * z.a);
	}
	public Complex times(double d) {
		return new Complex(a*d, b*d);
	}
	public Complex times(double a, double b) {
		return times(new Complex(a, b));
	}
	public Complex reciprocal() {
		// 1/(a+bi) = (a-bi)/((a+bi)(a-bi)) = (a-bi)/(a^2 + b^2)
		double fact = a*a + b*b;
		return new Complex(a / fact, -b / fact);
	}
	public Complex divide(Complex z) {
		return times(z.reciprocal());
	}
	public Complex divide(double d) {
		return new Complex(a/d, b/d);
	}
	public Complex divide(double a, double b) {
		return divide(new Complex(a, b));
	}
	public Complex conjugate() {
		return new Complex(a, -b);
	}
	public String toString() {
		return a + " + " + b + "i";
	}
	public double rad() {
		return Math.sqrt(a*a+b*b);
	}
	public double arg() {
		return Math.atan2(b, a);
	}
	public Complex pow(Complex z) {
		return pow(z.a).times(exp(new Complex(0, z.b).times(ln(this))));
	}
	public Complex pow(double d) {
		return exp(ln(this).times(d));
	}
	public Complex pow(double a, double b) {
		return pow(new Complex(a, b));
	}
	public Polar toPolar() {
		return new Polar(this);
	}
	public static Complex exp(Complex z) {
		// e^(a+bi) = (e^a)(cosb + isinb)
		double ea = Math.exp(z.a);
		return new Complex(ea * Math.cos(z.b), ea * Math.sin(z.b));
	}
	public static Complex ln(Complex z) {
		// https://en.wikipedia.org/wiki/Complex_logarithm
		// since there are an infinite number of possibilites, I take k=0
		return new Complex(Math.log(z.rad()), z.arg());
	}
	public static Complex log(Complex b, Complex z) {
		return ln(z).divide(ln(b));
	}
	public static Complex sin(Complex z) {
		return new Complex(Math.sin(z.a)*Math.cosh(z.b), Math.cos(z.a)*Math.sinh(z.b));
	}
	public static Complex cos(Complex z) {
		return new Complex(Math.cos(z.a)*Math.cosh(z.b), -Math.sin(z.a)*Math.sinh(z.b));
	}
	public static Complex tan(Complex z) {
		return sin(z).divide(cos(z));
	}
	public static Complex arcsin(Complex z) {
		return I.times(-1).times(ln(I.times(z).plus((z.times(z).times(-1).plus(1).pow(0.5)))));
	}
}
