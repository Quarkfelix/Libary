package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TextArea {
	private int x, y;
	private int thiccness = 10;
	private int width = 200;
	private int height = 100;

	// outline
	private boolean drawOutline = true;
	private Color outlineColor = Color.black;
	private Color backgroundColor = Color.white;
	private boolean background = true;
	//ende outline
	
	// text
	private int fontSize = 40;
	private Font font;
	private Color textColor = Color.black;
	private String text = "";
	private String alignment = "zentriert";
	private String verticalAlignment = "center";
	private int verticalAlignmentInt = 0;
	private double textWidth;
	private double textHeight;
	// ende text

	//konstruktoren
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
		this.text = text;
	}
	public TextArea(int x, int y, int width, int height, String text, String alignment) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.alignment = alignment;
	}
	public TextArea(int x, int y, int width, int height, String text, String alignment, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.alignment = alignment;
		this.textColor = color;
	}
	public TextArea(int x, int y, int width, int height, String text, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.textColor = color;
	}
	//ende Konstruktoren
	
	// text�nderungen
	public void setText(String text) {
		this.text = text;
	}
	
	public void setText(int text) {
		this.text = text + "";
	}

	public void setTextFont(Font font) {
		this.font = font;
	}

	/*linksb�ndig rechtsb�ndig zentriert*/
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
		this.background = false;
	}
	public void setThiccness(int ThicnessInPixeln) {
		thiccness = ThicnessInPixeln;
	}
	public void setFramingColor(Color color) {
		this.outlineColor = color;
	}
	//ende textArea �nderungen
	
	//  *paint bereich* //
	public void paint(Graphics2D g) {
		//drawBackground
		if(this.background) {
			g.setColor(backgroundColor);
			g.fillRect(x, y, width, height);
		}
		
		g.setColor(textColor);
		font = new Font("TimesRoman", Font.PLAIN, fontSize);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);
		this.textWidth = fMetric.stringWidth(text);
		this.textHeight = fMetric.getHeight();
		switch (verticalAlignment) {
		case "top":
			this.verticalAlignmentInt = (int)(y + textHeight/2 + 3);
			break;
		case "bottom":
			this.verticalAlignmentInt = y + height;
			break;
		case "center":
			verticalAlignmentInt = (int) (y + height/2 + textHeight/3 - 4) ;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}
		switch (alignment) {
		case "linksbuendig":
			g.drawString(text, x, verticalAlignmentInt);
			break;
		case "rechtsbuendig":
			g.drawString(text, (int)(x + (width - textWidth)) , verticalAlignmentInt);
			break;
		case "zentriert":
			g.drawString(text, (int)(x - textWidth/2 + width/2), verticalAlignmentInt);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}
		
		if(this.drawOutline) {
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
