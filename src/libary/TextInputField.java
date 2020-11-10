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

/*
	Information wie man diese Klasse benutzt
	- Erstelle ein TextInputField
	- Bei jedem KeyInput muss der Methode setText das KeyEvent übergeben werden. Dadurch wird automatisch der buchstabe in das textfeld geschrieben.
	- des inhalt kann man dann einfach mit gettext aus dem feld entnehmen.
	- zusätzlich muss noch die contains methode an den mouseinput angebunden werden. 
*/

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
	private double roundnessX = 0.15;
	private double roundnessY = 1;
	private Style style = Style.round;
	private Design design = Design.raw;

	// text
	private String text = "";
	
	private Color textcolor = Color.WHITE;
	private Font font;
	private String fontname = "Copperplate Gothic Bold";
	private int fontSize = 500;
	private int textWidth = 0;
	private int textHeight = 500;

	private BufferedImage searchbarImage;
	private CurserAnimation curserAnim;

//Constructor ------------------------------------------------------------------------------------------
	public TextInputField(int x, int y) {
		this.x = x;
		this.y = y;
		setImage("lupe_rechtsschauend.png");
		curserAnim = new CurserAnimation(this);
	}

	public TextInputField(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setImage("lupe_rechtsschauend.png");
		curserAnim = new CurserAnimation(this);
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

	public void reset() {
		this.selected = false;
		this.text = "";
	}

//getter-setter ----------------------------------------------------------------------------------------
	public void setRoundnessX(double roundness) {
		this.roundnessX = roundness;
	}
	
	public void setRoundnessY(double roundness) {
		this.roundnessY = roundness;
	}
	
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
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					this.setSelected(false);
				} else {
					text = text + e.getKeyChar();
				}

				textWidth = fMetric.stringWidth(text);
				int diff = (int) ((x + width - (width * distanceTextToLeft)) - (x + (width * distanceTextToLeft)));
				if (textWidth >= diff) {
					text = textSave;
				}
			} catch (StringIndexOutOfBoundsException exept) {
			}
		}
	}
	
	public void setTextFont(Font font) {
		this.font = font;
		this.fontname = font.getName();
		this.fontSize = font.getSize();
	}
	
	public void setTextFont(String name) {
		this.fontname = name;
	}

	public void setTextFontSize(int fontsize) {
		this.fontSize = fontsize;
	}

	public double getDistanceTextToBottom() {
		return distanceTextToBottom * 10;
	}

	public void setDistanceTextToBottom(double distanceTextToBottom) {
		this.distanceTextToBottom = distanceTextToBottom / 100;
	}

	public void setDistanceTextToLeft(double distanceTextToLeft) {
		this.distanceTextToLeft = distanceTextToLeft / 100;
	}

	public void setSelected(boolean state) {
		this.selected = state;
	}

	public boolean isSelected() {
		return selected;
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

	public int getTextWidth() {
		return textWidth;
	}

	public int getX() {
		return x;
	}

	public int getHeight() {
		return this.height;
	}

	public int getY() {
		return this.y;
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

	public void setTextColor(Color color) {
		this.textcolor = color;
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
			curserAnim.paint(g);
		}
	}

	private void drawField() {
		// custom stuff
		switch (style) {
		case round:
			g.setColor(backgroundColor);
			g.fillRoundRect(x, y, width, height, (int) (width * roundnessX), (int) (height * roundnessY));
			break;
		case edgy:
			g.setColor(backgroundColor);
			g.fillRect(x, y, width, height);
			break;
		default:
			break;
		}

		switch (design) {
		case design1:
			g.drawImage(searchbarImage, (int) (x + width * 0.83), (int) (y + height * 0.2), (int) (height * 0.6),
					(int) (height * 0.6), null);

			if (!selected && this.text == "") {
				g.setColor(Color.LIGHT_GRAY);
				font = new Font(this.fontname, Font.PLAIN, fontSize);
				fMetric = g.getFontMetrics(font);
				g.setFont(font);
				g.drawString("Search", (int) (x + width * 0.10), (int) (y + height * 0.75));
			}
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
		font = new Font(fontname, Font.PLAIN, fontSize);
		fMetric = g.getFontMetrics(font);
		g.setFont(font);
		textWidth = fMetric.stringWidth(text);
		textHeight = fMetric.getMaxAscent();

		g.drawString(text, (int) ((width * distanceTextToLeft) + x),
				(int) (y + height - (height * (distanceTextToBottom + 0.12))));
	}

}

class CurserAnimation implements Runnable {
	private Thread t;
	private TextInputField tif;
	private int curserBlinkInterval = 500;
	private boolean draw = false;

	public CurserAnimation(TextInputField tif) {
		this.tif = tif;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (tif.isSelected()) {
				if (draw == true) {
					draw = false;
				} else {
					draw = true;
				}
				try {
					Thread.sleep(curserBlinkInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				draw = false;
			}

		}

	}

	public void paint(Graphics2D g) {
		if (draw) {
			g.setColor(Color.BLACK);
			int x;
			if (tif.getTextWidth() < 7) {
				x = tif.getX() + tif.getTextWidth() + 7;
			} else {
				x = tif.getX() + tif.getTextWidth();
			}

			g.fillRect(x + 21, (int) (tif.getY() + (tif.getHeight() * 0.2)), 2, (int) (tif.getHeight() * 0.6));
		}
	}

}
