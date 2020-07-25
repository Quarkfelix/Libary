package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.Button;
import libary.Textalign;


public class Surface {
	Button b = new Button(100,100, 200, 150);
	
	public Surface() {
		b.setBorderColor(Color.CYAN);
		b.setCornerRadius(10);
		
		b.setText("Test");
		b.setTextColor(Color.CYAN);
		b.setTextAlignment(Textalign.mittig);
		
	}
	
	public void checkButtonPress(int x, int y) {
		if(b.contains(x, y)) {
			System.out.println("button pressed");
		}
		
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
//		System.out.println("paintrequest");
		b.paint(g);
	}	
}
