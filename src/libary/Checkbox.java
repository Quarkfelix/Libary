package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Checkbox {
	private int x, y;
	private int width = 120;
	private int height = 50;
	private int radius = 0;
	private int checkBoxThicness = 3;
	
	// general
	private boolean background = true;
	private Color backgroundcolor = Color.BLACK;
	private Color checkboxcolor = Color.WHITE;
	private Color checkmarkColor = Color.WHITE;
	private int distanceCheckoxAndText = 10;
	private int distanceCheckboxAndBorderLeft = 10;
	private int checkboxdimension = 25;
	private boolean checked = false;
	
	private Design design = Design.raw;

	// Text
	private String text = "";
	private Color textcolor = Color.WHITE;
	private String fontname = "Copperplate Gothic Bold";
	private Font font;
	private int fontSize = 30;

//Constructor ------------------------------------------------------------------------------------------
	public Checkbox(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Checkbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Checkbox(int x, int y, int width, int height, String name) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = name;
	}

//methods ----------------------------------------------------------------------------------------------
	// checkt ob uebergebener punkt enthalten ist
//	public boolean contains(int x, int y) {
//		if (x >= (this.x + this.distanceCheckboxAndBorderLeft) && y >= (this.y + 0.5 * (height - checkboxdimension))
//				&& x <= (this.x + this.distanceCheckboxAndBorderLeft + checkboxdimension)
//				&& y <= (this.y + 0.5 * (height - checkboxdimension) + checkboxdimension)) {
//			// new CheckboxAnimation(this);
//			if (checked) {
//				checked = false;
//			} else {
//				checked = true;
//			}
//			return true;
//		}
//		return false;
//	}

	public boolean contains(int x, int y) {
		if (x >= this.x && y >= this.y && x <= (this.x + this.width) && y <= (this.y + this.height)) {
			// new CheckboxAnimation(this);
			if (checked) {
				checked = false;
			} else {
				checked = true;
			}
			return true;
		}
		return false;
	}
	
	public void reset() {
		this.checked = false;
	}
	
//getter-setter ----------------------------------------------------------------------------------------
	// Text
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setTextFontSize(int size) {
		this.fontSize = size;
	}

	public void setTextColor(Color color) {
		this.textcolor = color;
	}

	public void setDistanceCheckoxAndText(int distance) {
		this.distanceCheckoxAndText = distance;
	}
	
	public void setTextFont(Font font) {
		this.font = font;
		this.fontname = font.getName();
		this.fontSize = font.getSize();
	}
	
	public void setTextFont(String name) {
		this.fontname = name;
	}
	// Text ende

	// sizes
	public void setCheckboxdimension(int checkboxdimension) {
		this.checkboxdimension = checkboxdimension;
	}

	public void setDistanceCheckboxAndBorderLeft(int distance) {
		this.distanceCheckboxAndBorderLeft = distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	// sizes ende
	
	//color
	public void setCheckboxColor(Color color) {
		this.checkboxcolor = color;
	}

	public void setCheckmarkColor(Color color) {
		this.checkmarkColor = color;
	}
	//color ende
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean state) {
		checked = state;
	}

	public void setBackgroundActive(boolean state) {
		this.background = state;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundcolor = color;
	}

	public void setDesign(Design design) {
		this.design = design;
	}

//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		if (background) {
			g.setColor(backgroundcolor);
			g.fillRect(x, y, width, height);
		}
		drawCheckbox(g);
		drawText(g);
		if (checked) {
			drawCheckmark(g);
		}
	}

	private void drawCheckmark(Graphics2D g) {
		g.setColor(checkmarkColor);

		switch (design) {
		case raw:
			g.drawLine((int) (x + this.distanceCheckboxAndBorderLeft), (int) (y + 0.5 * (height - checkboxdimension)),
					(int) (x + this.distanceCheckboxAndBorderLeft + checkboxdimension),
					(int) (y + 0.5 * (height - checkboxdimension) + checkboxdimension));
			g.drawLine((int) (x + this.distanceCheckboxAndBorderLeft + checkboxdimension),
					(int) (y + 0.5 * (height - checkboxdimension)), (int) (x + this.distanceCheckboxAndBorderLeft),
					(int) (y + 0.5 * (height - checkboxdimension) + checkboxdimension));
			break;
		case design1:
			g.fillRect((int) (x + this.distanceCheckboxAndBorderLeft), (int) (y + 0.5 * (height - checkboxdimension)),
					checkboxdimension + 1, checkboxdimension + 1);
			break;
		default:
			break;
		}

	}

	private void drawCheckbox(Graphics2D g) {
		int x = (int) (this.x + this.distanceCheckboxAndBorderLeft);
		int y = (int) (this.y + 0.5 * (height - checkboxdimension));
		
		g.setColor(checkboxcolor);
		for (int i = 0; i < this.checkBoxThicness; i++) {
			g.drawRoundRect(x + i, y + i, checkboxdimension - i * 2, checkboxdimension - i * 2, radius, radius);
		}
	}

	private void drawText(Graphics2D g) {
		g.setColor(textcolor);
		font = new Font(fontname, Font.PLAIN, fontSize);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);
		int textWidth = fMetric.stringWidth(text);
		int textHeight = fMetric.getMaxAscent();

		g.drawString(text, (int) (x + distanceCheckboxAndBorderLeft + checkboxdimension + distanceCheckoxAndText),
				(int) (y + textHeight / 3 + height / 2));
	}
}

class CheckboxAnimation implements Runnable {
	private Checkbox cb;

	public CheckboxAnimation(Checkbox cb) {
		this.cb = cb;
	}

	@Override
	public void run() {

	}

}
