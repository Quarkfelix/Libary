package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.*;


public class Surface {
	public TextInputField tif;
	
	public Surface() {
		tif = new TextInputField(200, 300, 700, 40);
		tif.setRoundnessX(5);
		tif.setRoundnessY(100);
	}
	
	public void checkButtonPress(int x, int y) {
		tif.contains(x, y);
	}
	
	public void checkButtonClick(int x, int y) {

	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		tif.paint(g);
	}	
}
