package Main;

import java.awt.Color;
import java.awt.Graphics2D;

import libary.*;


public class Surface {
	private DropDownMenu drop;
	public static TextInputField tif = new TextInputField(500, 100, 200, 40);
	
	public Surface() {
		drop = new DropDownMenu(100, 100, 200, 30);
		drop.addEntrie("Penis");
		drop.addEntrie("Vagina");
		drop.setCheckboxDesign(Design.design1);
		drop.setRadius(10);
		drop.setOneCheckMode(true);
		
		tif.setBackgroundColor(Color.WHITE);
		tif.setDesign(Design.design1);
		tif.setStyle(Style.round);
		tif.setTextLineActive(false);
		tif.setTextColor(Color.DARK_GRAY);
	}
	
	public void checkButtonPress(int x, int y) {
		drop.contains(x, y);
		tif.contains(x, y);
	}
	
	
	
//paint--------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		drop.paint(g);
		tif.paint(g);
	}	
}
