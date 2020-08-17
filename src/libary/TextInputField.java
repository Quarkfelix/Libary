package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import Main.Draw;
import javafx.scene.input.KeyCode;

public class TextInputField {
	int x = 100;
	int y = 100;
	int width = 100;
	int height = 40;

	private Graphics2D g;
	private FontMetrics fMetric;
	private boolean lock = false;
	
	private Color backgroundColor = Color.GRAY;
	private Color textLineColor = Color.BLACK;
	private double distanceTextToBottom = 0.15; // in percent
	private double distanceTextToLeft = 0.05; // in percent

	// text
	private String text = "";
	private Color textcolor = Color.WHITE;
	private Font font;
	private int fontSize = 500;
	private int textWidth = 0;
	private int textHeight = 500;

//Constructor ------------------------------------------------------------------------------------------
	public TextInputField(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public TextInputField(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}

//methods ----------------------------------------------------------------------------------------------

	private void establishFontSize() {		
		try {
			int maxHeight = (int) ((height - (height * distanceTextToBottom))*1.10);
			while (textHeight >= maxHeight) {
				font = new Font("TimesRoman", Font.PLAIN, fontSize);
				fMetric = g.getFontMetrics(font);
				textHeight = fMetric.getMaxAscent();
				fontSize--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//getter-setter ----------------------------------------------------------------------------------------
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText(KeyEvent e) {
		// rechts rausschreiben sperre
		try {
			String textSave = text;
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				// Shift soll nicht angezeigt werden
			} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				text = text.substring(0, text.length() - 1);
			} else {
				text = text + e.getKeyChar();
			}

			textWidth = fMetric.stringWidth(text);
			int diff = (int) ((x + width - (width * distanceTextToLeft)) - (x + (width * distanceTextToLeft)));
			if (textWidth >= diff) {
				text = textSave;
			}
		} catch (StringIndexOutOfBoundsException exept) {
			System.out.println("textfeld zu oft backspace gedrückt");
		}
	}

	public void setTextFontSize(int fontsize) {
		this.fontSize = fontsize;
	}

	public double getDistanceTextToBottom() {
		return distanceTextToBottom * 10;
	}

	public void setDistanceTextToBottom(double distanceTextToBottom) {
		this.distanceTextToBottom = distanceTextToBottom / 10;
	}

	public void setDistanceTextToLeft(double distanceTextToLeft) {
		this.distanceTextToLeft = distanceTextToLeft / 10;
	}

	public Color getTextLineColor() {
		return textLineColor;
	}

	public void setTextLineColor(Color textLineColor) {
		this.textLineColor = textLineColor;
	}

//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		this.g = g;
		drawField();
		drawText();
		if(!lock) {
			establishFontSize();
		}
	}

	private void drawField() {
		g.setColor(backgroundColor);
		g.fillRect(x, y, width, height);

		g.setColor(textLineColor);
		g.drawLine((int) (x + (width * distanceTextToLeft)), (int) (y + height - (height * distanceTextToBottom)),
				(int) (x + width - (width * distanceTextToLeft)), (int) (y + height - (height * distanceTextToBottom)));
	}

	private void drawText() {
		g.setColor(textcolor);
		font = new Font("TimesRoman", Font.PLAIN, fontSize);
		fMetric = g.getFontMetrics(font);
		g.setFont(font);
		textWidth = fMetric.stringWidth(text);
		textHeight = fMetric.getMaxAscent();

		g.drawString(text, (int) ((width * distanceTextToLeft) + x),
				(int) (y + height - (height * (distanceTextToBottom + 0.04))));
	}

}
