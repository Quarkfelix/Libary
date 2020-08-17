package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.*;


public class Surface {
	private Checkbox cb;
	public TextInputField tif;
	
	
	public Surface() {
		tif = new TextInputField(200,200, 250, 30);
		tif.setTextFontSize(100);
	}
	
	public void checkButtonPress(int x, int y) {
		
		
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
//		System.out.println("paintrequest");
		tif.paint(g);
	}	
}
