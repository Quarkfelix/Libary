package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.Screenkeyboard;


public class Surface {
	private Screenkeyboard keyboard;
	
	public Surface() {
		keyboard = new Screenkeyboard(100, 200, 1000, 350);
		keyboard.setBackgroundCornerRadius(20);
	}
	
	public void checkButtonPress(int x, int y) {
		keyboard.checkPress(x, y);
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		keyboard.paint(g);
	}	
}
