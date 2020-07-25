package libary;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;



/*
 * font sachen:
 * Font.PLAIN = standart
 * Font.BOLD = Fett
 * Font.ITALIC = Kursiv
 * 
 * 
 * VerbesserungsIdeen:
 * -imgButtons
 * -Rahmendicke einstellbar //bei textarea vorhanden 
 * 	wird hier aber schwer mit den abgerundeten ecken funktionieren.
 */

public class Button implements Runnable{
	Thread t;
	
	private int x;
	private int y;
	private int width = 100;
	private int height = 50;
	private int radius = 0;
	private double angle = 0;
	private boolean clicked = false;
	private boolean repainted = true;
	private boolean oval = false;
	private boolean active = true;
	private boolean border = true;
	private Color color = Color.DARK_GRAY;
	private Color borderColor = Color.GREEN;

	//animation
	private int time = 1; //geschwidichkeit der animation -> zeit die pro iteration gewartet wird
	private int speed = 1; //anzahl der pixel die kleiner werden pro iteration
	private int size = 10; //wie oft verkleinert/vergrößert wird
	
	// text
	private int fontSize = 40;
	private Font font;
	private Color textColor = Color.RED;
	private String text = "";
	private Textalign alignment = Textalign.mittig;
	private double textWidth;
	private double textHeight;

	
	// ende text

//run---------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void run() {
		while(true) {
			if (clicked) {
				clickAnimation();
				clicked = false;
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//Constructors------------------------------------------------------------------------------------------------------------------------
	public Button(int x, int y) {
		this.x = x;
		this.y = y;
		startThread();
	}

	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		startThread();
	}

	public Button(int x, int y, int width, int height, double angle) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
		startThread();
	}

	public Button(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		startThread();
	}

	public Button(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		startThread();
	}

	public Button(int x, int y, int width, int height, Color color, Color framingColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.borderColor = framingColor;
		startThread();
	}

	public Button(int x, int y, Color color, Color framingColor) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.borderColor = framingColor;
		startThread();
	}

//methods--------------------------------------------------------------------------------------------------------------------------------
	private void startThread() {
		t = new Thread(this);
		t.start();
	}
	
	// checkt ob uebergebener punkt enthalten ist
	public boolean contains(int x, int y) {
		if (active) {
			if (x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height) {
				clicked = true;
				return true;
			}
		}
		return false;
	}
	
	public void clickAnimation() {
		for (int i = size; i > 0; i--) {
			while(!repainted) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			x += speed;
			y += speed;
			width -= speed*2;
			height -= speed*2;
			repainted = false;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = size; i > 0; i--) {
			while(!repainted) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			x -= speed;
			y -= speed;
			width += speed*2;
			height += speed*2;
			repainted = false;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
//getter-setter-------------------------------------------------------------------------------------------------------------------------	
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

//ButtonColor---------------------------------------------------------------------
	public void setBorderColor(Color color) {
		this.borderColor = color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
	}
//Buttontext----------------------------------------------------------------------
	public void setText(String text) {
		this.text = text;
	}

	public void setTextFont(Font font) {
		this.font = font;
	}

	public void setTextAlignment(Textalign alignment) {
		this.alignment = alignment;
	}

	public void setTextFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
//Miscellaneous--------------------------------------------------------------------
	public void setCornerRadius(int radius) {
		this.radius = radius;
	}
	
	public void setImg(String url) {
		File file = new File(url);	
	}

//paint-----------------------------------------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		this.drawButton(g);
		this.drawText(g);
		this.repainted = true;
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
				g.fillRoundRect(x, y, width, height, radius, radius);
				if (border) {
					g.setColor(borderColor);
					g.drawRoundRect(x, y, width, height, radius, radius);
				}
			}
		}
	}

	private void drawText(Graphics2D g) {
		g.setColor(textColor);
		font = new Font("TimesRoman", Font.PLAIN, fontSize);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);
		this.textWidth = fMetric.stringWidth(text);
		this.textHeight = fMetric.getHeight();

		switch (this.alignment) {
		case linksbuendig:
			g.drawString(text, x, (int) (y + textHeight / 3 + height / 2));
			break;
		case rechtsbuendig:
			g.drawString(text, (int) (x + (width - textWidth)), (int) (y + textHeight / 3 + height / 2));
			break;
		case mittig:
			g.drawString(text, (int) (x - textWidth / 2 + width / 2), (int) (y + textHeight / 3 + height / 2));
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + alignment);
		}
	}
}



