package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.Screenkeyboard;
import libary.Slider;


public class Surface {
	public Slider slider;
	
	public Surface() {
		slider = new Slider(200, 200);
	}
	
	public void checkButtonPress(int x, int y) {
		slider.contains(x, y);
	}
	
	public void checkButtonClick(int x, int y) {
		slider.contains(x, y);
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		slider.paint(g);
	}	
}
