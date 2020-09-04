package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.*;


public class Surface {
	private Checkbox cb;
	public TextInputField tif;
	
	
	public Surface() {
		tif = new TextInputField(200,200, 200, 40);
		tif.setTextFontSize(100);
		tif.setBackgroundColor(Color.WHITE);
		tif.setTextLineActive(false);
		tif.setStyle(Style.round);
		tif.setDesign(Design.design1);
	}
	
	public void checkButtonPress(int x, int y) {
		tif.contains(x,y);
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
//		System.out.println("paintrequest");
		tif.paint(g);
	}	
}
