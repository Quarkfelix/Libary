package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

/*
 * font sachen:
 * Font.PLAIN = standart
 * Font.BOLD = Fett
 * Font.ITALIC = Kursiv
 * 
 * 
 * VerbesserungsIdeen:
 * -Rahmendicke einstellbar
 * 	wird hier aber schwer mit den abgerundeten ecken funktionieren.
 */

public class Button {
	private int x;
	private int y;
	private int width = 100;
	private int height = 50;
	private int radiusWidth = 0;
	private int radiusHeight = 0;
	private double angle = 0;
	private boolean oval = false;
	private boolean active = true;
	private boolean border = true;
	private Color color = Color.DARK_GRAY;
	private Color borderColor = Color.GREEN;
	private BufferedImage img = null;
	private int animLength = 100;

	// text
	private int fontSize = 40;
	private Font font;
	private String fontname = "Copperplate Gothic Bold";
	private Color textColor = Color.RED;
	private String text = "";
	private boolean textActive = true;
	private Textalign alignment = Textalign.mittig;

	// img
	private int imgX;
	private int imgY;
	private int imgwidth;
	private int imgheight;
	private double imgXfactor;
	private double imgYfactor;

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
		this.borderColor = framingColor;
	}

	public Button(int x, int y, Color color, Color framingColor) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.borderColor = framingColor;
	}

//methods--------------------------------------------------------------------------------------------------------------------------------
	// checkt ob uebergebener punkt enthalten ist
	public boolean contains(int x, int y) {
		if (active) {
			if (x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height) {
				new ButtonAnimation(this, animLength);
				return true;
			}
		}
		return false;
	}

//getter-setter-------------------------------------------------------------------------------------------------------------------------	
	public void setAnimLength(int animLengthInMiliseconds) {
		this.animLength = animLengthInMiliseconds;
	}
	
	public void setOval(boolean state) {
		oval = state;
	}

	public void setBorderActive(boolean state) {
		border = state;
	}

	public void setActive(boolean state) {
		this.active = state;
	}

	public boolean isActive() {
		return active;
	}

	public void setX(int x) {
		this.x = x;
		imgX = (int) (x + width*imgXfactor - imgwidth/2);
	}

	public void setY(int y) {
		this.y = y;
		imgY = (int) (y + height*imgYfactor - imgheight/2);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	// img
	public void setImg(BufferedImage img) {
		this.img = img;
		setImageWidth(width);
		setImageX(50);
		setImageY(50);
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public void setImageX(double xInPercentToLeftButtonborder) {
		imgXfactor = xInPercentToLeftButtonborder/100;
		imgX = (int) (x + width*imgXfactor - imgwidth/2);
	}

	public void setImageY(double yInPercentToButtontop) {
		imgYfactor = yInPercentToButtontop/100;
		imgY = (int) (y + height*imgYfactor - imgheight/2);
	}

	public void setImageWidth(int imgwidth) {
		if (img != null) {
			this.imgwidth = imgwidth;
			double resizingfactor = (double) (imgwidth) / img.getWidth();
			this.imgheight = (int) (img.getHeight() * resizingfactor);
		}
	}

	public void setImageHeight(int imgheight) {
		if (img != null) {
			this.imgheight = imgheight;
			double resizingfactor = (double) (imgheight) / img.getHeight();
			imgwidth = (int) (img.getWidth() * resizingfactor);
		}
	}

	public void setTrueImageWidth(int imgwidth) {
		this.imgwidth = imgwidth;
	}

	public void setTrueImageHeight(int imgheight) {
		this.imgheight = imgheight;
	}

	/* percent is measured in the width of the button */
	public void setImageSize(double sizeInPercentToButton) {
		double size = sizeInPercentToButton / 100;
		this.imgwidth = (int) (width * size);
		double resizingfactor = (double) (imgwidth) / img.getWidth();
		this.imgheight = (int) (img.getHeight() * resizingfactor);
	}

	// ButtonColor
	public void setBorderColor(Color color) {
		this.borderColor = color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
	}

	// Buttontext
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public void setTextFont(Font font) {
		this.font = font;
		this.fontname = font.getName();
		this.fontSize = font.getSize();
	}
	
	public void setTextFont(String name) {
		this.fontname = name;
	}

	public void setTextAlignment(Textalign alignment) {
		this.alignment = alignment;
	}

	public void setTextFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setTextActive(boolean state) {
		this.textActive = state;
	}

	// Miscellaneous
	public void setCornerRadiusWidth(int radius) {
		this.radiusWidth = radius;
	}

	public void setCornerRadiusHeight(int radius) {
		this.radiusHeight = radius;
	}

	public void setCornerRadius(int radius) {
		this.radiusHeight = radius;
		this.radiusWidth = radius;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

//paint-----------------------------------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		drawButton(g);
		if (img != null)
			drawImage(g);
		if (textActive)
			drawText(g);
	}

	private void drawButton(Graphics2D g) {
		if (active) {
			if (oval) {
				// oval button
				g.translate(x + width / 2, y + height / 2);
				g.rotate(Math.toRadians(angle));
				g.setColor(color);
				g.fillOval((int) -width / 2, (int) -height / 2, width, height);
				if (border) {
					g.setColor(borderColor);
					g.drawOval((int) -width / 2, (int) -height / 2, width, height);
				}
				g.rotate(-Math.toRadians(angle));
				g.translate(-(x + width / 2), -(y + width / 2));
			} else {
				// normal button
				g.setColor(color);
				g.fillRoundRect(x, y, width, height, radiusWidth, radiusHeight);
				
				if (border) {
					g.setColor(borderColor);
					g.drawRoundRect(x, y, width, height, radiusWidth, radiusHeight);
				}
			}
		}
	}

	private void drawImage(Graphics2D g) {
		g.drawImage(img, imgX, imgY, imgwidth, imgheight, null);
	}

	private void drawText(Graphics2D g) {
		g.setColor(textColor);
		font = new Font(fontname, Font.PLAIN, fontSize);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);

		int textWidth = fMetric.stringWidth(text);
		int textHeight = fMetric.getMaxAscent();

		switch (this.alignment) {
		case linksbuendig:
			g.drawString(text, x, (int) (y + textHeight / 3 + getHeight() / 2));
			break;
		case rechtsbuendig:
			g.drawString(text, (int) (x + (getWidth() - textWidth)), (int) (y + textHeight / 3 + getHeight() / 2));
			break;
		case mittig:
			g.drawString(text, (int) (x - textWidth / 2 + getWidth() / 2),
					(int) (y + textHeight / 3 + getHeight() / 2));
			break;
		case under:
			g.drawString(text, (int) (x - textWidth / 2 + getWidth() / 2), (int) (y + getHeight() + textHeight - 5));
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}
	}
}

//die animation in eigener runtime
class ButtonAnimation implements Runnable {
	public long animLength = 100; // in ms 1sec = 1000ms
	public long depth = 3; // tife in pixeln
	public Button b;

// Constructor---------------------------------------------------------------------------
	public ButtonAnimation(Button b) {
		this.b = b;
		Thread t = new Thread(this);
		t.start();
	}
	
	public ButtonAnimation(Button b, int animLength) {
		this.b = b;
		this.animLength = animLength;
		Thread t = new Thread(this);
		t.start();
	}

// run-method---------------------------------------------------------------------------
	@Override
	public void run() {
		// verkleinern
		for (int i = 0; i < depth; i++) {
			b.setX(b.getX() + 1);
			b.setY(b.getY() + 1);
			b.setWidth(b.getWidth() - 2);
			b.setHeight(b.getHeight() - 2);

			try {
				Thread.sleep((animLength / 2) / depth); // half time /pixel
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// vergrößern
		for (int i = 0; i < depth; i++) {
			b.setX(b.getX() - 1);
			b.setY(b.getY() - 1);
			b.setWidth(b.getWidth() + 2);
			b.setHeight(b.getHeight() + 2);

			try {
				Thread.sleep((animLength / 2) / depth); // half time /pixel
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
