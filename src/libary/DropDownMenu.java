package libary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DropDownMenu {
	private int x;
	private int y;
	private int width = 100;
	private int height = 30;
	private int marginCheckboxes = 10;
	private boolean unfolded = false;

	private Button initialButton;
	private ArrayList<Checkbox> entries = new ArrayList<>();

	private Color backgroundColor = Color.GRAY;

//Constructor ------------------------------------------------------------------------------------------
	public DropDownMenu(int x, int y) {
		this.x = x;
		this.y = y;
		buttonSetup();
	}

	public DropDownMenu(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		buttonSetup();
	}

//methods ----------------------------------------------------------------------------------------------
	public void buttonSetup() {
		initialButton = new Button(x, y, width, height);
		initialButton.setBorderActive(false);
	}
	
	//if drop menu not open it opens if button pressed
	//if drop menu open you can select and it returns name of checkbox
	public String contains(int x, int y) {
		if (!unfolded) {
			if(initialButton.contains(x, y)) {
				unfolded = true;
				return "unfolded";
			}
			return "";
		} else {
			for (Checkbox entrie : entries) {
				if (entrie.contains(x, y)) {
					return entrie.getText();
				}
			}
			unfolded = false;
			return "folded";
		}
	}
	

//getter-setter ----------------------------------------------------------------------------------------
	public void addEntrie(String name) {
		if (entries.size() == 0) {
			entries.add(new Checkbox(x, y, width, height, name));
		} else {
			entries.add(new Checkbox(x, entries.get(entries.size()-1).getY() + entries.get(entries.size()-1).getHeight() + marginCheckboxes, width, height, name));
		}
		entries.get(entries.size()-1).setBackgroundActive(false);
	}

	public void setCheckboxDesign(Design design) {
		for (Checkbox entrie : entries) {
			entrie.setDesign(design);
		}
	}
	
	
	// clolor
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		if (!unfolded) {
			initialButton.paint(g);
		} else {
			drawBackground(g);
			for (Checkbox entrie : entries) {
				entrie.paint(g);
			}
		}
	}
	
	public void drawBackground(Graphics2D g) {
		g.setColor(backgroundColor);
		g.fillRect(x, y-5, width, (height + marginCheckboxes) * entries.size() - marginCheckboxes + 10);
	}

}
