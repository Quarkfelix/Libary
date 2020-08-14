package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.Button;
import libary.Checkbox;
import libary.Textalign;


public class Surface {
	private Checkbox cb;
	
	public Surface() {
		cb = new Checkbox(100,100, 300, 50);
		
		cb.setBackgroundColor(Color.GRAY);
		cb.setText("Herkunft");
	}
	
	public void checkButtonPress(int x, int y) {
		cb.contains(x, y);
		
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
//		System.out.println("paintrequest");
		cb.paint(g);
	}	
}
