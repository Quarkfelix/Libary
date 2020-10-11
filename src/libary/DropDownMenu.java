package libary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DropDownMenu {
	private int x;
	private int y;
	private int width = 100;
	private int height = 30;
	private int radius = 0;
	private int marginCheckboxes = 10;
	private boolean unfolded = false;

	private Button initialButton;
	private ArrayList<Checkbox> entries = new ArrayList<>();

	// Settings
	private Color backgroundColor = new Color(67, 160, 199);
	private boolean oneCheckMode = false;

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
	private void buttonSetup() {
		initialButton = new Button(x, y, width, height);
		initialButton.setBorderActive(false);
		initialButton.setColor(new Color(67, 160, 199));
		initialButton.setText("drop down");
		initialButton.setTextColor(Color.WHITE);
		initialButton.setTextFontSize(26);
	}

	// if drop menu not open it opens if button pressed
	// if drop menu open you can select and it returns name of checkbox or
	// "name(variable) unchecked"
	public String contains(int x, int y) {
		if (!unfolded) {
			if (initialButton.contains(x, y)) {
				unfolded = true;
				return null;
			}
			return null;
		} else if (oneCheckMode) {															// ->
			for (Checkbox entrie : entries) {
				if (entrie.contains(x, y)) {
					for (int j = 0; j < entries.size(); j++) {
						if(entrie != entries.get(j)) {
							entries.get(j).setChecked(false);
						}
					}
					if (!entrie.isChecked()) {
						entrie.setChecked(false);
					} else {
						entrie.setChecked(true);
					}
					return entrie.getText();
				}
			}
			unfolded = false;
			return null;																	// <- teil davor ist noch verbesserungswürdig aber er funktioniert erstmal
		} else {
			for (Checkbox entrie : entries) {
				if (entrie.contains(x, y)) {
					if (entrie.isChecked()) {
						return entrie.getText();
					} else {
						return entrie.getText() + " unchecked";
					}
				}
			}
			unfolded = false;
			return null;
		}
	}
	
	public void reset() {
		for (Checkbox entrie : entries) {
			entrie.reset();
		}
		this.unfolded = false;
	}

//getter-setter ----------------------------------------------------------------------------------------
	public void addEntrie(String name) {
		if (entries.size() == 0) {
			entries.add(new Checkbox(x, y + 5, width, height, name));
		} else {
			entries.add(new Checkbox(x, entries.get(entries.size() - 1).getY()
					+ entries.get(entries.size() - 1).getHeight() + marginCheckboxes, width, height, name));
		}
		entries.get(entries.size() - 1).setBackgroundActive(false);
	}

	public void setCheckboxDesign(Design design) {
		for (Checkbox entrie : entries) {
			entrie.setDesign(design);
		}
	}
	
	public void setTextFont(String fontName) {
		for (Checkbox entrie : entries) {
			entrie.setTextFont(fontName);
		}
		initialButton.setTextFont(fontName);
	}
	
	public void setTextFontSize(int size) {
		for (Checkbox entrie : entries) {
			entrie.setTextFontSize(size);
		}
		initialButton.setTextFontSize(size);
	}


	// initialButton
	public Button getInitialButton() {
		return initialButton;
	}

	public void setTitle(String text) {
		initialButton.setText(text);
	}
	// ende initialButton

	public boolean isUnfolded() {
		return unfolded;
	}

	public void setOneCheckMode(boolean state) {
		this.oneCheckMode = state;
	}

	public ArrayList<Checkbox> getCheckedBoxes() {
		ArrayList<Checkbox> checkedBoxes = new ArrayList<>();
		for (Checkbox entrie : entries) {
			if (entrie.isChecked()) {
				checkedBoxes.add(entrie);
			}
		}
		return checkedBoxes;
	}

	// size
	public void setRadius(int radius) {
		this.radius = radius;
		initialButton.setCornerRadius(radius);
	}
	// size ende

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
		g.fillRoundRect(x, y, width, (height + marginCheckboxes) * entries.size() - marginCheckboxes + 10, radius,
				radius);
	}

}