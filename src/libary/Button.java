package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/*
 * font sachen:
 * Font.PLAIN = standart
 * Font.BOLD = Fett
 * Font.ITALIC = Kursiv
 * 
 * 
 * VerbesserungsIdeen:
 * -Rahmendicke einstellbar //kann von TextArea genommen werden
 * -abgerundete ecken
 */

public class Button {
	private int x;
	private int y;
	private int width = 100;
	private int height = 50;
	private double angle = 0;
	private boolean oval = false;
	private boolean active = true;
	private boolean framing = true;
	private Color color = Color.DARK_GRAY;
	private Color framingColor = Color.GREEN;

	// text
	private int fontSize = 40;
	private Font font;
	private Color textColor = Color.RED;
	private String text = "";
	private String alignment = "zentriert";
	private double textWidth;
	private double textHeight;
	// ende text

//Constructors------------------------------------------------------------------------------------------------------------------------
	public Button(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Button(int x, int y, int width, int height, double angle) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
	}

	public Button(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public Button(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public Button(int x, int y, int width, int height, Color color, Color framingColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.framingColor = framingColor;
	}

	public Button(int x, int y, Color color, Color framingColor) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.framingColor = framingColor;
	}

//methods--------------------------------------------------------------------------------------------------------------------------------
	// checkt ob uebergebener punkt enthalten ist
	public boolean contains(int x, int y) {
		if (active) {
			if (x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height) {
				return true;
			}
		}
		return false;
	}
	
//getter-setter-------------------------------------------------------------------------------------------------------------------------	
	public void setOval(boolean state) {
		oval = state;
	}

	public void setFramingActive(boolean state) {
		framing = state;
	}

	public void setActive(boolean state) {
		this.active = state;
	}

	public boolean isActive() {
		return active;
	}

	// farb�nderungen
	public void setFramingColor(Color color) {
		this.framingColor = color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
	}
	// ende farb�nderungen

	// Buttontext
	public void setText(String text) {
		this.text = text;
	}

	public void setTextFont(Font font) {
		this.font = font;
	}

	public void setTextAlignment(String alignment) {
		this.alignment = alignment;
	}

	public void setTextFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	// ende Buttontext

//paint-----------------------------------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		this.drawButton(g);
		this.drawText(g);
	}

	private void drawButton(Graphics2D g) {
		if (active) {
			if (oval) {
				// oval button
				g.translate(x + width / 2, y + height / 2);
				g.rotate(Math.toRadians(angle));
				g.setColor(color);
				g.fillOval((int) -width / 2, (int) -height / 2, width, height);
				if (framing) {
					g.setColor(framingColor);
					g.drawOval((int) -width / 2, (int) -height / 2, width, height);
				}
				g.rotate(-Math.toRadians(angle));
				g.translate(-(x + width / 2), -(y + width / 2));

			} else {
				// normal button
				g.setColor(color);
				g.fillRect(x, y, width, height);
				if (framing) {
					g.setColor(framingColor);
					g.drawRect(x, y, width, height);
				}
			}
		}
	}

	public void drawText(Graphics2D g) {
		g.setColor(textColor);
		font = new Font("TimesRoman", Font.PLAIN, fontSize);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);
		this.textWidth = fMetric.stringWidth(text);
		this.textHeight = fMetric.getHeight();

		switch (alignment) {
		case "linksbuendig":
			g.drawString(text, x, (int) (y + textHeight / 3 + height / 2));
			break;
		case "rechtsbuendig":
			g.drawString(text, (int) (x + (width - textWidth)), (int) (y + textHeight / 3 + height / 2));
			break;
		case "zentriert":
			g.drawString(text, (int) (x - textWidth / 2 + width / 2), (int) (y + textHeight / 3 + height / 2));
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}
	}
}
