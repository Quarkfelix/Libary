package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.scene.input.KeyCode;

public class TextInputField {
	int x = 100;
	int y = 100;
	int width = 100;
	int height = 40;

	private Graphics2D g;
	private FontMetrics fMetric;
	private boolean lock = false;
	private boolean selected = false;
	private boolean active = true;

	private Color backgroundColor = Color.GRAY;
	private Color textLineColor = Color.BLACK;
	private boolean textLineActive = true;
	private double distanceTextToBottom = 0.15; // in percent
	private double distanceTextToLeft = 0.05; // in percent
	private Style style = Style.round;
	private Design design = Design.raw;

	// text
	private String text = "";
	private Color textcolor = Color.WHITE;
	private Font font;
	private int fontSize = 500;
	private int textWidth = 0;
	private int textHeight = 500;

	private BufferedImage searchbarImage;

//Constructor ------------------------------------------------------------------------------------------
	public TextInputField(int x, int y) {
		this.x = x;
		this.y = y;
		setImage("lupe_rechtsschauend.png");
	}

	public TextInputField(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setImage("lupe_rechtsschauend.png");
	}
	
//methods ----------------------------------------------------------------------------------------------
	
	private void establishFontSize() {
		try {
			int maxHeight = (int) ((height - (height * distanceTextToBottom)) * 1.10);
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

	// checkt ob uebergebener punkt enthalten ist
	public boolean contains(int x, int y) {
		if (active) {
			if (x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height) {
				selected = true;
				return true;
			}
		}
		selected = false;
		return false;
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
		if (selected) {
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
				System.out.println("textfeld zu oft backspace gedr�ckt");
			}
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

	public void setSelected(boolean state) {
		this.selected = state;
	}

	public void setTextLineActive(boolean state) {
		this.textLineActive = state;
	}
	
	public void setDesign(Design design) {
		this.design = design;
	}
	
	public void setStyle(Style style) {
		this.style = style;
	}
	
	public void setImage(String source) {
		try {
			searchbarImage = ImageIO.read(this.getClass().getResource(source));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// color
	public Color getTextLineColor() {
		return textLineColor;
	}

	public void setTextLineColor(Color textLineColor) {
		this.textLineColor = textLineColor;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		this.g = g;
		if (active) {
			drawField();
			drawText();
			if (!lock) {
				establishFontSize();
			}
		}

	}

	private void drawField() {
		// custom stuff
		switch (style) {
		case round:
			g.setColor(backgroundColor);
			g.fillRoundRect(x, y, width, height, (int) (width * 0.2), (int) (height));
			break;
		case edgy:
			g.fillRect(x, y, width, height);
			break;
		default:
			break;
		}

		switch (design) {
		case design1:
			g.drawImage(searchbarImage, (int)(x + width*0.80), (int)(y+height*0.15), (int)(height*0.70), (int)(height*0.70), null);
			break;
		default:
			break;
		}

		// general stuff
		if (textLineActive) {
			g.setColor(textLineColor);
			g.drawLine((int) (x + (width * distanceTextToLeft)), (int) (y + height - (height * distanceTextToBottom)),
					(int) (x + width - (width * distanceTextToLeft)),
					(int) (y + height - (height * distanceTextToBottom)));
		}

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
