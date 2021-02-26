package libary;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//checkboxheight has to be done before entries are added

public class DropDownMenu {
	private int x;
	private int y;
	private int width = 100;
	private int height = 30;
	private int radius = 0;
	private int marginCheckboxes = 10;
	private boolean unfolded = false;
	private int checkBoxHeight = 30;

	private Button initialButton;
	private ArrayList<Checkbox> entries = new ArrayList<>();
	private int selectedCheckBox = 0;
	private boolean selectionMarker = true;

	// Settings
	private Color backgroundColor = new Color(67, 160, 199);
	private Color selectionColor = new Color(255, 38, 23, 255);
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
		this.checkBoxHeight = height;
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
		} else if (oneCheckMode) { // ->
			for (Checkbox entrie : entries) {
				if (entrie.contains(x, y)) {
					for (int j = 0; j < entries.size(); j++) {
						if (entrie != entries.get(j)) {
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
			selectedCheckBox = 0;
			unfolded = false;
			return null; // <- teil davor ist noch verbesserungswürdig aber er funktioniert erstmal
		} else {
			for (Checkbox entrie : entries) {
				if (entrie.contains(x, y)) {
					if (entrie.isChecked()) {
						selectedCheckBox = entries.indexOf(entrie);
						return entrie.getText();
					} else {
						return entrie.getText() + " unchecked";
					}
				}
			}
			selectedCheckBox = 0;
			unfolded = false;
			return null;
		}
	}

	public void checkButtonPress(KeyEvent e) {
		if (unfolded) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				if (entries.size() > selectedCheckBox + 1) {
					selectedCheckBox++;
				}
				break;
			case KeyEvent.VK_UP:
				if (0 <= selectedCheckBox - 1) {
					selectedCheckBox--;
				}
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER:
				if (!entries.get(selectedCheckBox).isChecked()) {
					if (oneCheckMode) {
						for (Checkbox entrie : entries) {
							entrie.setChecked(false);
						}
					}
					entries.get(selectedCheckBox).setChecked(true);
				} else {
					entries.get(selectedCheckBox).setChecked(false);
				}
				break;
			case KeyEvent.VK_ESCAPE:
				unfolded = false;
				selectedCheckBox = 0;
				break;
			default:
				System.out.println("DropDownMenu key not defined");
				return;
			}
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
			entries.add(new Checkbox(x, y + 5, width, checkBoxHeight, name));
		} else {
			entries.add(new Checkbox(x, entries.get(entries.size() - 1).getY()
					+ entries.get(entries.size() - 1).getHeight() + marginCheckboxes, width, checkBoxHeight, name));
		}
		entries.get(entries.size() - 1).setBackgroundActive(false);
	}

	public void setCheckBoxHeight(int height) {
		if (entries.size() >= 1) {
			entries.get(0).setHeight(height);
			for (int i = 1; i < entries.size(); i++) {
				entries.get(i).setHeight(height);
				entries.get(i).setY(entries.get(i - 1).getY() + height);
			}
		}
		checkBoxHeight = height;
	}
	
	public void setSelectionMarkerOn(boolean state) {
		this.selectionMarker = state;
	}

	public boolean getSelectionMarkerOn() {
		return this.selectionMarker;
	}
	
	public void setCheckboxDesign(Design design) {
		for (Checkbox entrie : entries) {
			entrie.setDesign(design);
		}
	}

	public void setCheckboxTextFontSize(int size) {
		for (Checkbox entrie : entries) {
			entrie.setTextFontSize(size);
		}
	}

	public void setCheckboxTextColor(Color color) {
		for (Checkbox entrie : entries) {
			entrie.setTextColor(color);
		}
	}

	public void setCheckboxCheckmarkColor(Color color) {
		for (Checkbox entrie : entries) {
			entrie.setCheckmarkColor(color);
		}
	}

	public void setCheckboxBackgroundColor(Color color) {
		for (Checkbox entrie : entries) {
			entrie.setBackgroundColor(color);
		}
	}

	public void setCheckboxBackgroundActive(boolean active) {
		for (Checkbox entrie : entries) {
			entrie.setBackgroundActive(active);
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

	public void setUnfolded(boolean state) {
		unfolded = state;
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
		initialButton.setColor(backgroundColor);
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
			if(selectionMarker) {
				drawSelectionMarker(g);
			}
		}
	}

	public void drawBackground(Graphics2D g) {
		g.setColor(backgroundColor);
		g.fillRoundRect(x, y, width, (checkBoxHeight + marginCheckboxes) * entries.size() - marginCheckboxes + 10,
				radius, radius);
	}

	private void drawSelectionMarker(Graphics2D g) {
		int x, y, width, height;
		x = entries.get(selectedCheckBox).getX();
		y = entries.get(selectedCheckBox).getY();
		width = entries.get(selectedCheckBox).getWidth();
		height = entries.get(selectedCheckBox).getHeight();
		g.setColor(selectionColor);
		g.drawLine(x, y, x + width, y);
		g.drawLine(x, y + height, x + width, y + height);

	}

}