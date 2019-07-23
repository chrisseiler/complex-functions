package com.gmail.chrisdseiler.complexfunctions;

import java.awt.Color;

public class Polar {
	public final double r, th;
	public Polar(double radius, double theta) {
		this.r = radius;
		this.th = theta % (2 * Math.PI);
	}
	public Polar(Complex complx) {
		r = complx.rad();
		th = complx.arg();
	}
	public Complex toComplex() {
		return new Complex(r * Math.cos(th), r * Math.sin(th));
	}
	public Color getColor(double limit) {
		return Color.getHSBColor((float)((th % (2 * Math.PI)) / (2 * Math.PI)), 1, (float)Math.tanh(r / limit));
	}
}