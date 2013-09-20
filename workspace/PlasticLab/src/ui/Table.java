package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import server.Utils;

public class Table extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public final int tableNumber;
    final int R = 66;
    final int G = 194;
    final int B = 110;
    
    final Paint p;
    
	public Table(int number) {
		super();
		tableNumber = number;

		Dimension size = Utils.getTableSize();
		p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 100),
	    		size.width, size.height, new Color(R, G, B, 200), true);
		
	}
	
	
	public void paintComponent(Graphics g){
		Dimension size = Utils.getTableSize();
    	Graphics2D g2d = (Graphics2D) g;
		int d = Math.min(size.width, size.height); 
	    int x = (size.width - d)/2;
	    int y = (size.height - d)/2;
	    
    	 
    	g2d.setColor(new Color(41,141,208));
    	Shape shape = new Ellipse2D.Float(100.0f, 100.0f, 100.0f, 100.0f);
    	g2d.draw(shape);
	}

}
