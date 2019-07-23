package com.gmail.chrisdseiler.complexfunctions;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.gmail.chrisdseiler.complexfunctions.ComplexGraph.ComplexFunction;

public class ComplexPolarGraph {
	private ComplexFunction func;
	public ComplexPolarGraph(ComplexFunction func) {
		this.func = func;
	}
	public Image draw(int width, int height, double x_scale, double y_scale) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Polar w = toPoint(x, y, width, height, x_scale, y_scale);
				img.setRGB(x, y, func.f(w.toComplex()).toPolar().getColor(1).getRGB());
			}
		}
		return img;
	}
	private Polar toPoint(int xpix, int ypix, int width, int height, double x_scale, double y_scale) {
		double xDist = xpix - (width / 2);
		double yDist = (height / 2) - ypix;
		xDist /= 100 / x_scale;
		yDist /= 100 / y_scale;
		return new Complex(xDist, yDist).toPolar();
	}
}