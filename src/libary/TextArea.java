package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class TextArea {
	private int x, y;
	private int thiccness = 10;
	private int width = 200;
	private int height = 100;

	// outline
	private boolean drawOutline = true;
	private Color outlineColor = Color.RED;
	private Color backgroundColor = Color.white;
	private boolean background = true;
	// ende outline

	// text
	private int fontSize = 40;
	private Font font;
	private String fontName = "Copperplate Gothic Bold";
	private Color textColor = Color.black;
	private ArrayList<String> text = new ArrayList<>();
	private String alignment = "linksbuendig";
	private String verticalAlignment = "top";
	private int verticalAlignmentInt = 0;
	private double textWidth;
	private double textHeight;
	private FontMetrics fMetric;
	private int linedistance = 45;
	// ende text

	// konstruktoren
	public TextArea(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public TextArea(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public TextArea(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text.add(text);
	}

	public TextArea(int x, int y, int width, int height, String text, String alignment) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text.add(text);
		this.alignment = alignment;
	}

	public TextArea(int x, int y, int width, int height, String text, String alignment, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text.add(text);
		this.alignment = alignment;
		this.textColor = color;
	}

	public TextArea(int x, int y, int width, int height, String text, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text.add(text);
		this.textColor = color;
	}
	// ende Konstruktoren

	// methods
	public void checkNewLine() {
		int rows = 1;
		if (text.size() > 0) {
			for (int j = 0; j < rows; j++) {
				for (int i = 0; i < text.get(j).length(); i++) {

					if (fMetric.stringWidth(text.get(j).substring(0, i)) >= this.width) {
						text.add(text.get(j).substring(0, i - 1));
						text.add(text.get(j).substring(i - 1, text.get(j).length()));
						text.remove(j);
						rows++;
					}
				}
			}
		}
	}
	// end methods

	// textänderungen
	public void setText(String text) {
		if (!this.text.isEmpty()) {
			this.text = new ArrayList<String>();
		}
		this.text.add(text);
	}

	public void setText(int text) {
		this.text.add(text + "");
	}

	public void setTextFont(Font font) {
		this.font = font;
	}

	public void setTextFont(String fontName) {
		this.fontName = fontName;
	}

	/* linksbï¿½ndig rechtsbï¿½ndig zentriert */
	public void setTextAlignment(String alignment) {
		this.alignment = alignment;
	}

	public void setTextAlignmentVertical(String alignment) {
		this.verticalAlignment = alignment;
	}

	public void setTextFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
	}
	// ende textaenderungen

	// textArea aenderungen
	public void setOutlineActive(boolean state) {
		drawOutline = state;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

	public void setBackgroundActive(boolean state) {
		this.background = state;
	}

	public void setThiccness(int ThicnessInPixeln) {
		thiccness = ThicnessInPixeln;
	}

	public void setFramingColor(Color color) {
		this.outlineColor = color;
	}
	// ende textArea ï¿½nderungen

	// *paint bereich* //
	public void paint(Graphics2D g) {
		// drawBackground
		if (this.background) {
			g.setColor(backgroundColor);
			g.fillRect(x, y, width, height);
		}

		g.setColor(textColor);
		font = new Font(fontName, Font.PLAIN, fontSize);
		fMetric = g.getFontMetrics(font);
		g.setFont(font);
		String textasString = "";
		for (String string : text) {
			textasString = textasString + string;
		}
		this.textWidth = fMetric.stringWidth(textasString);
		this.textHeight = fMetric.getHeight();
		checkNewLine();

		switch (verticalAlignment) {
		case "top":
			this.verticalAlignmentInt = (int) (y + textHeight / 2 + 3);
			break;
		case "bottom":
			this.verticalAlignmentInt = y + height;
			break;
		case "center":
			verticalAlignmentInt = (int) (y + height / 2 + textHeight / 4);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}
		switch (alignment) {
		case "linksbuendig":
			for (int i = 0; i < text.size(); i++) {
				g.drawString(text.get(i), x, (int) (verticalAlignmentInt + linedistance * (i)));
			}
			break;
		case "rechtsbuendig":
//			g.drawString(text, (int) (x + (width - textWidth)), verticalAlignmentInt);
			break;
		case "zentriert":
			for (int i = 0; i < text.size(); i++) {
				g.drawString(text.get(i), (int) (x - fMetric.stringWidth(text.get(i)) / 2 + width / 2), (int) (verticalAlignmentInt + linedistance * (i)));
			}
//			g.drawString(text.toString(), (int) (x - textWidth / 2 + width / 2), verticalAlignmentInt);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}

		if (this.drawOutline) {
			this.drawOutline(g);
		}
	}

	private void drawOutline(Graphics2D g) {
		g.setColor(outlineColor);
		g.fillRect(x, y, thiccness, height);
		g.fillRect(x, y, width, thiccness);
		g.fillRect(x + width - thiccness, y, thiccness, height);
		g.fillRect(x, y + height - thiccness, width, thiccness);
		g.drawRect(x, y, width, height);
	}
}