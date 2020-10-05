package libary;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.glass.events.KeyEvent;

import java.awt.Robot;

public class Screenkeyboard {
	public int width = 1920;
	public int height = 300;
	public int x = 0;
	public int y = 0;
	public boolean fullscreen = false;
	public int fontSize = 27;

	// keys
	public Button[] letterkeys = new Button[33];
	public Button[] specialkeys = new Button[3];

//Constructor ------------------------------------------------------------------------------------------
	public Screenkeyboard() {
		createkeys();
	}

	public Screenkeyboard(int width, int height) {
		this.width = width;
		this.height = height;
		createkeys();
	}

	public Screenkeyboard(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createkeys();
	}

//methods -----------------------------------------------------------------------------------------------
	
	public void checkPress(int x, int y) {
		for (int i = 0; i < letterkeys.length; i++) {
			if (letterkeys[i].contains(x, y)) {
				try {
					Robot robot = new Robot();
					int key = stringToKey(letterkeys[i].getText());
					robot.keyPress(key);
					robot.keyRelease(key);			
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}
	
	private int stringToKey(String stringKey) {
		if(stringKey.toUpperCase().equals("Ö")) {
			return 79;
		} else if(stringKey.toUpperCase().equals("Ü")) {
			return 85;
		} else if(stringKey.toUpperCase().equals("Ä")) {
			return 65;
		}
		
		try {
			return KeyEvent.class.getField("VK_" + stringKey.toUpperCase()).getInt(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private void createkeys() {
		int ydiff = (int) (height / 4); // y point at witch first row starts etc.
		int xdiff = (int) (width / 11); // x diff for first row

		int xbutton = x + (int) ((width % 11) / 2);
		int ybutton = y + (int) ((ydiff * 0.10) / 2);
		int widthbutton = (int) (xdiff - xdiff * 0.10);
		int heightbutton = (int) (ydiff - ydiff * 0.10);

		// setup rows -----------------------------------------------------------------------------
		for (int i = 0; i < 33; i++) {
			letterkeys[i] = new Button(xbutton, ybutton, widthbutton, heightbutton);
			xbutton += xdiff;
			if (i == 10 || i == 21 || i == 32) {
				ybutton += ydiff;
				xbutton = x + (int) ((width % 11) / 2);
			}
		}
		//special keys -----------------------------------------------------------------------------
		specialkeys[0] = new Button(xbutton + 3*xdiff, ybutton , (int) (5*xdiff - xdiff*0.10), heightbutton);
		specialkeys[1] = new Button(xbutton + 9*xdiff, ybutton , widthbutton+xdiff, heightbutton);
		specialkeys[2] = new Button(xbutton + 9*xdiff, ybutton - ydiff , widthbutton+xdiff, heightbutton);
		
		// optics Keys -----------------------------------------------------------------------------
		for (int i = 0; i < letterkeys.length; i++) {
			letterkeys[i].setBorderActive(false);
			letterkeys[i].setCornerRadius(20);
			letterkeys[i].setTextFontSize((width+height)/fontSize);
			letterkeys[i].setTextColor(new Color(36, 110, 255));
		}
		for (int i = 0; i < specialkeys.length; i++) {
			specialkeys[i].setBorderActive(false);
			specialkeys[i].setCornerRadius(20);
			specialkeys[i].setTextFontSize((width+height)/fontSize);
			specialkeys[i].setTextColor(new Color(36, 110, 255));
		}

		// button names -----------------------------------------------------------------------------
		letterkeys[0].setText("Q");
		letterkeys[1].setText("W");
		letterkeys[2].setText("E");
		letterkeys[3].setText("R");
		letterkeys[4].setText("T");
		letterkeys[5].setText("Z");
		letterkeys[6].setText("U");
		letterkeys[7].setText("I");
		letterkeys[8].setText("O");
		letterkeys[9].setText("P");
		letterkeys[10].setText("Ü");
		letterkeys[11].setText("A");
		letterkeys[12].setText("S");
		letterkeys[13].setText("D");
		letterkeys[14].setText("F");
		letterkeys[15].setText("G");
		letterkeys[16].setText("H");
		letterkeys[17].setText("J");
		letterkeys[18].setText("K");
		letterkeys[19].setText("L");
		letterkeys[20].setText("Ö");
		letterkeys[21].setText("Ä");
		letterkeys[22].setText("");
		letterkeys[23].setText("");
		letterkeys[24].setText("Y");
		letterkeys[25].setText("X");
		letterkeys[26].setText("C");
		letterkeys[27].setText("V");
		letterkeys[28].setText("B");
		letterkeys[29].setText("N");
		letterkeys[30].setText("M");
		letterkeys[31].setText("");
		letterkeys[32].setText("");
		
		
		//special -----------------------------------------------------------------------------
		specialkeys[0].setText("SPACE");
		specialkeys[1].setText("ENTER");
		specialkeys[2].setText("BACKSPACE");
		specialkeys[0].setTextActive(false); //spacebar notext
		specialkeys[0].setTextActive(false); //enter notext
		specialkeys[2].setTextActive(false); //backspace notext
		try {
			specialkeys[2].setImg(ImageIO.read(this.getClass().getResource("backspace.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//getter-setter ----------------------------------------------------------------------------------------
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		for (int i = 0; i < letterkeys.length; i++) {
			letterkeys[i].setTextFontSize(fontSize);
		}
		for (int i = 0; i < specialkeys.length; i++) {
			specialkeys[i].setTextFontSize(fontSize);
		}
	}
//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		drawBackground(g);
		drawKeys(g);
	}

	private void drawBackground(Graphics2D g) {
		g.setColor(Color.GRAY);
		if (fullscreen) {
			g.fillRect(0, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height), width, height);
		} else {
			g.fillRect(x, y, width, height);
		}
	}

	private void drawKeys(Graphics2D g) {
		for (int i = 0; i < letterkeys.length; i++) {
			try {
				letterkeys[i].paint(g);
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
			}
		}
		for (int i = 0; i < specialkeys.length; i++) {
			try {
				specialkeys[i].paint(g);
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
			}
		}
	}
}
