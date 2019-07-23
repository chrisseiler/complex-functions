package com.gmail.chrisdseiler.complexfunctions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComplexGraph extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800, HEIGHT = 500;
	public static double X_SCALE = 2, Y_SCALE = 2, DENSITY = 8;
	public static final Stroke THICK = new BasicStroke(3), NORMAL = new BasicStroke(1), MEDTHICK = new BasicStroke(2);
	private static List<Connection> cons = new ArrayList<Connection>();
	private static ComplexFunction func;
	private static double d = 0;
	public static class Connection {
		public Point a;
		public Point b;
		public boolean vert;
		public Connection(Point a, Point b, boolean vert) {
			this.a = a;
			this.b = b;
			this.vert = vert;
		}
	}
	public static abstract class ComplexFunction {
		public abstract Complex f(Complex z);
		protected static Complex C(double a, double b) {
			 return new Complex(a, b);
		}
	}
	public ComplexGraph() {
		/**
		 * PARAMETERS:
		 * 	Window:
		 * 		Width
		 * 		Height
		 *	Graph:
		 *		XScale (x intervals per 100 pixels)
		 *		YScale (y intervals per 100 pixels)
		 *		Density (amount of contour per 100 pixels)
		 *		Center Coords
		 *
		 * 
		 */
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		func = new ComplexFunction() {
			public Complex f(Complex z) {
				return C(1, 2).pow(z);
				//return new Complex(57, 0).pow(z).pow(Complex.I);
			}
		};
		d = System.currentTimeMillis();
	}
	public void paintComponent(Graphics grn) {
		super.paintComponent(grn);
		Graphics2D g = (Graphics2D) grn;
		WIDTH = getWidth();
		HEIGHT = getHeight();
		g.setStroke(THICK);

		cons.clear();
		int nXLines = (int) (WIDTH * DENSITY / 100 + DENSITY * X_SCALE);
		int nYLines = (int) (HEIGHT * DENSITY / 100 + DENSITY * Y_SCALE);
		int xOffset = (WIDTH / 2) % 100;
		int yOffset = (HEIGHT / 2) % 100;
		for(int x = 0; x < nXLines; x++) {
			for(int y = 0; y < nYLines; y++) {
				Connection con1 = new Connection(new Point((int)(xOffset - 100 + x * 100 / DENSITY), (int)(yOffset - 100 + y * 100 / DENSITY)),
						new Point((int)(xOffset - 100 + x * 100 / DENSITY - 100 / DENSITY), (int)(yOffset - 100 + y * 100 / DENSITY)), false);
				cons.add(con1);
				Connection con2 = new Connection(new Point((int)(xOffset - 100 + x * 100 / DENSITY), (int)(yOffset - 100 + y * 100 / DENSITY)),
						new Point((int)(xOffset - 100 + x * 100 / DENSITY), (int)(yOffset - 100 + y * 100 / DENSITY - 100 / DENSITY)), true);
				cons.add(con2);
			}
		}
		g.setStroke(NORMAL);
		for(Connection con : cons) {
			con.a = lerp(con.a, toXY(func.f(toPoint(con.a))), (System.currentTimeMillis()-d-1000)/6000);
			con.b = lerp(con.b, toXY(func.f(toPoint(con.b))), (System.currentTimeMillis()-d-1000)/6000);
			if(con.vert) g.setColor(Color.BLUE);
			else g.setColor(Color.DARK_GRAY);
			draw(g, con.a, con.b);
		}
		repaint();
	}
	public Complex toPoint(Point p) {
		return toPoint(p.x, p.y);
	}
	public Complex toPoint(int xpix, int ypix) {
		double xDist = xpix - (WIDTH / 2);
		double yDist = (HEIGHT / 2) - ypix;
		xDist /= 100 / X_SCALE;
		yDist /= 100 / Y_SCALE;
		return new Complex(xDist, yDist);
	}
	public Point lerp(Point a, Point b, double d) {
		if(d >= 1) return b;
		if(d <= 0) return a;
		int dx = b.x - a.x;
		int dy = b.y - a.y;
		int px = (int)(dx * d);
		int py = (int)(dy * d);
		return new Point(a.x + px, a.y + py);
	}
	public Point toXY(Complex z) {
		double xDist = z.a;
		double yDist = z.b;
		if(Double.isInfinite(xDist) || Double.isNaN(xDist) || Double.isInfinite(yDist) || Double.isNaN(yDist))
			return new Point(WIDTH/2, HEIGHT/2);
		xDist *= 100 / X_SCALE;
		yDist *= 100 / Y_SCALE;
		int xpix = (int)(xDist + WIDTH / 2);
		int ypix = (int)(HEIGHT / 2 - yDist);
		return new Point(xpix, ypix);
	}
	public void draw(Graphics2D g, Point p) {
		g.fillOval(p.x-4, p.y-4, 8, 8);
	}
	public void draw(Graphics2D g, Point a, Point b) {
		g.drawLine(a.x, a.y, b.x, b.y);
	}
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Complex graphing calculator");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new ComplexGraph();
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		// Example to draw function with polar coordinates as colors:
		/*
		ComplexFunction func = new ComplexFunction() {
			public Complex f(Complex z) {
				return Complex.arcsin(z.reciprocal()).pow(z);
			}
		};
		ComplexPolarGraph graph = new ComplexPolarGraph(func);
		try {
			ImageIO.write((RenderedImage) graph.draw(800, 500, 1, 1), "PNG", new File("res/func.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}