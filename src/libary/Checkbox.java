package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Checkbox {
	private int x, y;
	private int width = 120;
	private int height = 50;

	// general
	private boolean background = true;
	private Color backgroundcolor = Color.BLACK;
	private Color checkboxcolor = Color.WHITE;
	private Color checkmarkColor = Color.RED;
	private int distanceCheckoxAndText = 10;
	private int distanceCheckboxAndBorderLeft = 10;
	private int checkboxdimension = 25;
	private boolean checked = false;
	

	// Text
	private String text = "";
	private Color textcolor = Color.WHITE;
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

//methods ----------------------------------------------------------------------------------------------
	// checkt ob uebergebener punkt enthalten ist
	public boolean contains(int x, int y) {
		if (x >= (this.x + this.distanceCheckboxAndBorderLeft) && y >= (this.y + 0.5 * (height - checkboxdimension))
				&& x <= (this.x + this.distanceCheckboxAndBorderLeft + checkboxdimension)
				&& y <= (this.y + 0.5 * (height - checkboxdimension) + checkboxdimension)) {
			new CheckboxAnimation(this);
			if(checked) {
				checked = false;
			} else {
				checked = true;
			}
			return true;
		}
		return false;
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

	// Text ende
	public void setCheckboxColor(Color color) {
		this.checkboxcolor = color;
	}
	
	public void setCheckmarkColor(Color color) {
		this.checkmarkColor = color;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean state) {
		checked = state;
	}
	
	public void setCheckboxdimension(int checkboxdimension) {
		this.checkboxdimension = checkboxdimension;
	}

	public void setBackgroundActive(boolean state) {
		this.background = state;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundcolor = color;
	}

	public void setDistanceCheckboxAndBorderLeft(int distance) {
		this.distanceCheckboxAndBorderLeft = distance;
	}

//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		if (background) {
			g.setColor(backgroundcolor);
			g.fillRect(x, y, width, height);
		}
		drawCheckbox(g);
		drawText(g);
		if(checked) {
			drawCheckmark(g);
		}
	}

	private void drawCheckmark(Graphics2D g) {
		g.setColor(checkmarkColor);
		g.drawLine((int) (x + this.distanceCheckboxAndBorderLeft), (int) (y + 0.5 * (height - checkboxdimension)),
				(int) (x + this.distanceCheckboxAndBorderLeft + checkboxdimension), (int) (y + 0.5 * (height - checkboxdimension) + checkboxdimension));
		g.drawLine((int) (x + this.distanceCheckboxAndBorderLeft + checkboxdimension), (int) (y + 0.5 * (height - checkboxdimension)),
				(int) (x + this.distanceCheckboxAndBorderLeft), (int) (y + 0.5 * (height - checkboxdimension) + checkboxdimension));
	}
	
	private void drawCheckbox(Graphics2D g) {
		g.setColor(checkboxcolor);
		g.drawRect((int) (x + this.distanceCheckboxAndBorderLeft), (int) (y + 0.5 * (height - checkboxdimension)),
				checkboxdimension, checkboxdimension);
	}

	private void drawText(Graphics2D g) {
		g.setColor(textcolor);
		font = new Font("TimesRoman", Font.PLAIN, fontSize);
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
