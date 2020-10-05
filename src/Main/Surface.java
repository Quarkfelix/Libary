package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.*;


public class Surface {
	private Screenkeyboard keyboard;
	
	public Surface() {
		keyboard = new Screenkeyboard(100, 200, 800, 250);
	}
	
	public void checkButtonPress(int x, int y) {
		keyboard.checkPress(x, y);
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		keyboard.paint(g);
	}	
}
