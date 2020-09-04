package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.*;


public class Surface {
	private DropDownMenu drop;
	
	
	public Surface() {
		drop = new DropDownMenu(100, 100, 200, 30);
		drop.addEntrie("Penis");
		drop.addEntrie("Vagina");
		drop.setCheckboxDesign(Design.design1);
	}
	
	public void checkButtonPress(int x, int y) {
		drop.contains(x, y);
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
//		System.out.println("paintrequest");
		drop.paint(g);
	}	
}
