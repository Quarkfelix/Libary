package libary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

//if contains returns true slider needs to constantly get mouseposition over giveMousePosition() function
public class Slider{
	private	int x;
	private int y;
	private int sliderlength = 200;
	private int sliderdiameter = 50;
	private int sliderposition = 0;
	private int slidervalue = 0;
	private int sliderrange = 100;
	private boolean movemode = false;
	
	private Color sliderColor = Color.WHITE;
	
// ======================================== CONSTRUCTOR ========================================
	public Slider(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
// ======================================== METHODS ============================================
	public boolean contains(int x, int y) {		
		double left = Math.pow(((double)x-(double)this.x),2)+Math.pow(((double)y-(double)this.y),2);
		double right = Math.pow(sliderdiameter/2, 2);		
		if(left <= right) {
			if(movemode) {
				movemode = false;
			} else {
				movemode = true;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void giveMousePosition(int x, int y) {
		if(x <= this.x+sliderlength) {
			if(x >= this.x) {
				sliderposition = x - this.x;
			}
		}
	}
// ======================================== GET/SET METHODS ====================================

// ======================================== PAINT-METHODS ======================================
	public void paint(Graphics2D g) {
		g.setColor(sliderColor);
		g.drawLine(x, y, x+sliderlength, y);
		
		drawSliderBall(g);
	}
	
	public void drawSliderBall(Graphics2D g) {
		g.setColor(sliderColor);
		g.fillOval(x-sliderdiameter/2+sliderposition, y-sliderdiameter/2, sliderdiameter, sliderdiameter);
	}

}
