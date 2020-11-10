package libary;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



import java.awt.Robot;

public class Screenkeyboard {
	public int width = 1920;
	public int height = 300;
	public int x = 0;
	public int y = 0;
	public boolean fullscreen = false;
	private boolean active = true;
	public int cornerRadius = 70;

	// numpad
	private boolean numActive = false;

	// background
	private boolean backgroundActive = true;
	private int backgroundCornerRadius = 0;
	private int backgroundOpacity = 255; // 0-255
	private Color backgroundColor = new Color(26, 26, 26, backgroundOpacity);

	// text
	private int fontSize = 50;
	private Font font = new Font("Copperplate Gothic Bold", Font.PLAIN, fontSize);
	private Color textColor = Color.WHITE;

	// keys
	public Button[][] keys = new Button[13][4];

//Constructor ------------------------------------------------------------------------------------------
	public Screenkeyboard() {
		setupKeys();
	}

	public Screenkeyboard(int width, int height) {
		this.width = width;
		this.height = height;
		setupKeys();
	}

	public Screenkeyboard(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setupKeys();
	}

//methods -----------------------------------------------------------------------------------------------
	public void checkPress(int x, int y) {
		if (active) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < keys.length; j++) {
					if (keys[j][i].contains(x, y)) {
						int key = stringToKey(keys[j][i].getText());
						if (key == 500) {
							numpad();
						} else if (key == 501) {
							close();
						} else if (key != 0) {
							try {
								Robot robot = new Robot();
								robot.keyPress(key);
								robot.keyRelease(key);
							} catch (AWTException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	public boolean contains(int x, int y) {
		if (active) {
			if (x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height) {
				return true;
			}
		}
		return false;
	}

	private void setupKeys() {
		createKeys();
		customizeLayout();
		declareButtonNames();
	}

	/* switches between numbers and letters */
	public void numpad() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < keys.length; j++) {
				keys[j][i].setText("");
			}
		}
		if (!numActive) {
			numActive = true;
			try {
				keys[11][2].setImg(ImageIO.read(this.getClass().getResource("Textpad.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			declareButtonNumbers();
		} else {
			numActive = false;
			try {
				keys[11][2].setImg(ImageIO.read(this.getClass().getResource("numpad.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			declareButtonNames();
		}

		keys[11][2].setImageWidth(25);
		keys[11][2].setImageX(50);
		keys[11][2].setImageY(50);
	}

	public void close() {
		active = false;
		if(numActive)
			numpad();
	}
	
	public void open() {
		active = true;
	}

	private int stringToKey(String stringKey) {
		if (stringKey.toUpperCase().equals("Ö")) {
			return 79;
		} else if (stringKey.toUpperCase().equals("Ü")) {
			return 85;
		} else if (stringKey.toUpperCase().equals("Ä")) {
			return 65;
		} else if (stringKey.toUpperCase() == "") {
			return 0;
		} else if (stringKey.toUpperCase().equals("NUMB")) {
			return 500;
		} else if (stringKey.toUpperCase().equals("EXIT")) {
			return 501;
		}
		try {
			return KeyEvent.class.getField("VK_" + stringKey.toUpperCase()).getInt(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * creates four rows of equally distributed keys. Some rows are slightly pushed
	 */
	private void createKeys() {
		int ydiff = (int) (height / 4);
		int xdiff = (int) (width / keys.length);
		int xbutton = x + (int) ((width % keys.length) / 2);
		int ybutton = y + (int) ((ydiff * 0.10) / 2);
		int widthbutton = (int) (xdiff - xdiff * 0.10);
		int heightbutton = (int) (ydiff - ydiff * 0.10);

		// row 1
		for (int j = 0; j < keys.length; j++) {
			keys[j][0] = new Button(xbutton, ybutton, widthbutton, heightbutton);
			xbutton += xdiff;
		}
		// row 2
		ybutton += ydiff;
		xbutton = x + (int) ((width % keys.length) / 2 + widthbutton * 0.40);
		for (int j = 0; j < keys.length; j++) {
			keys[j][1] = new Button(xbutton, ybutton, widthbutton, heightbutton);
			xbutton += xdiff;
		}
		// row 3
		ybutton += ydiff;
		xbutton = x + (int) ((width % keys.length) / 2 + widthbutton * 0.15);
		for (int j = 0; j < keys.length; j++) {
			keys[j][2] = new Button(xbutton, ybutton, widthbutton, heightbutton);
			xbutton += xdiff;
		}
		// row 4
		ybutton += ydiff;
		xbutton = x + (int) ((width % keys.length) / 2);
		for (int j = 0; j < keys.length; j++) {
			keys[j][3] = new Button(xbutton, ybutton, widthbutton, heightbutton);
			xbutton += xdiff;
		}
	}

	private void customizeLayout() {
		int xdiff = (int) (width / keys.length);
		int widthbutton = (int) (xdiff - xdiff * 0.10);

		// layout----------------------------------------
		// Backspace
		keys[11][0].setActive(false);
		keys[12][0].setX(keys[11][0].getX());
		keys[12][0].setWidth(widthbutton + xdiff);
		keys[12][0].setTextActive(false);
		try {
			keys[12][0].setImg(ImageIO.read(this.getClass().getResource("backspace hohl white with x-cutout.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keys[12][0].setTrueImageWidth(40);
		keys[12][0].setTrueImageHeight(30);
		keys[12][0].setImageX(15);
		keys[12][0].setImageY(25);
		// enter
		keys[11][1].setActive(false);
		keys[12][1].setX(keys[11][1].getX());
		keys[12][1].setWidth((int) (widthbutton + xdiff - widthbutton * 0.40));
		keys[12][1].setTextActive(false);
		try {
			keys[12][1].setImg(ImageIO.read(this.getClass().getResource("enter.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keys[12][1].setImageWidth(37);
		keys[12][1].setImageX(20);
		keys[12][1].setImageY(25);
		// numb/3rd row
		keys[0][2].setActive(false);
		keys[12][2].setActive(false);
		keys[11][2].setTextActive(false);
		try {
			keys[11][2].setImg(ImageIO.read(this.getClass().getResource("numpad.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keys[11][2].setImageWidth(25);
		keys[11][2].setImageX(50);
		keys[11][2].setImageY(50);
		// space
		for (int i = 1; i < 9; i++)
			keys[i][3].setActive(false);
		for (int i = 10; i < 12; i++)
			keys[i][3].setActive(false);
		keys[9][3].setX(keys[3][3].getX());
		keys[9][3].setWidth(widthbutton + 6 * xdiff);
		keys[9][3].setTextActive(false);
		// keyboard down
		keys[0][3].setTextActive(false);
		keys[0][3].setWidth((int) (widthbutton + widthbutton * 0.5));
		keys[12][3].setTextActive(false);
		keys[12][3].setWidth((int) (widthbutton + widthbutton * 0.5));
		keys[12][3].setX((int) (keys[12][3].getX() - widthbutton * 0.5));
		try {
			keys[12][3].setImg(ImageIO.read(this.getClass().getResource("keyboard down.png")));
			keys[0][3].setImg(ImageIO.read(this.getClass().getResource("keyboard down.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keys[12][3].setImageWidth(32);
		keys[12][3].setImageX(50);
		keys[12][3].setImageY(50);
		keys[0][3].setImageWidth(32);
		keys[0][3].setImageX(50);
		keys[0][3].setImageY(50);

		// optics-----------------------------------------
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < keys.length; i++) {
				keys[i][j].setBorderActive(false);
				keys[i][j].setCornerRadius((width + height) / cornerRadius);
				keys[i][j].setTextFontSize((width + height) / fontSize);
				keys[i][j].setTextFont(font);
				keys[i][j].setTextColor(textColor);
			}
		}
	}

	private void declareButtonNames() {
		keys[0][0].setText("Q");
		keys[1][0].setText("W");
		keys[2][0].setText("E");
		keys[3][0].setText("R");
		keys[4][0].setText("T");
		keys[5][0].setText("Z");
		keys[6][0].setText("U");
		keys[7][0].setText("I");
		keys[8][0].setText("O");
		keys[9][0].setText("P");
		keys[10][0].setText("Ü");
		keys[11][0].setText("");
		keys[12][0].setText("BACKSPACE"); // 11
		keys[0][1].setText("A");
		keys[1][1].setText("S");
		keys[2][1].setText("D");
		keys[3][1].setText("F");
		keys[4][1].setText("G");
		keys[5][1].setText("H");
		keys[6][1].setText("J");
		keys[7][1].setText("K");
		keys[8][1].setText("L");
		keys[9][1].setText("Ö");
		keys[10][1].setText("Ä");
		keys[11][1].setText("");
		keys[12][1].setText("ENTER"); // 22
		keys[0][2].setText("");
		keys[1][2].setText("Y");
		keys[2][2].setText("X");
		keys[3][2].setText("C");
		keys[4][2].setText("V");
		keys[5][2].setText("B");
		keys[6][2].setText("N");
		keys[7][2].setText("M");
		keys[8][2].setText("");
		keys[9][2].setText("");
		keys[10][2].setText("");
		keys[11][2].setText("numb");
		keys[12][2].setText(""); // 33
		keys[0][3].setText("exit");
		keys[1][3].setText("");
		keys[2][3].setText("");
		keys[3][3].setText("");
		keys[4][3].setText("");
		keys[5][3].setText("");
		keys[6][3].setText("");
		keys[7][3].setText("");
		keys[8][3].setText("");
		keys[9][3].setText("SPACE");
		keys[10][3].setText("");
		keys[11][3].setText("");
		keys[12][3].setText("exit"); // 44
	}

	private void declareButtonNumbers() {
		keys[0][0].setText("1");
		keys[1][0].setText("2");
		keys[2][0].setText("3");
		keys[3][0].setText("4");
		keys[4][0].setText("5");
		keys[5][0].setText("6");
		keys[6][0].setText("7");
		keys[7][0].setText("8");
		keys[8][0].setText("9");
		keys[9][0].setText("0");
		keys[10][0].setText("");
		keys[11][0].setText("");
		keys[12][0].setText("BACKSPACE"); // 11
		keys[0][1].setText("");
		keys[1][1].setText("");
		keys[2][1].setText("");
		keys[3][1].setText("");
		keys[4][1].setText("");
		keys[5][1].setText("");
		keys[6][1].setText("");
		keys[7][1].setText("");
		keys[8][1].setText("");
		keys[9][1].setText("");
		keys[10][1].setText("");
		keys[11][1].setText("");
		keys[12][1].setText("ENTER"); // 22
		keys[0][2].setText("");
		keys[1][2].setText("");
		keys[2][2].setText("");
		keys[3][2].setText("");
		keys[4][2].setText("");
		keys[5][2].setText("");
		keys[6][2].setText("");
		keys[7][2].setText("");
		keys[8][2].setText("");
		keys[9][2].setText("");
		keys[10][2].setText("");
		keys[11][2].setText("numb");
		keys[12][2].setText(""); // 33
		keys[0][3].setText("exit");
		keys[1][3].setText("");
		keys[2][3].setText("");
		keys[3][3].setText("");
		keys[4][3].setText("");
		keys[5][3].setText("");
		keys[6][3].setText("");
		keys[7][3].setText("");
		keys[8][3].setText("");
		keys[9][3].setText("SPACE");
		keys[10][3].setText("");
		keys[11][3].setText("");
		keys[12][3].setText("exit"); // 44
	}

//getter-setter ----------------------------------------------------------------------------------------
	public boolean isOpen() {
		return active;
	}	
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public void setClickAnimationLength(int animLengthInMiliseconds) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < keys.length; j++) {
				keys[j][i].setAnimLength(animLengthInMiliseconds);
			}
		}
	}
	
	// background
	public void setBackgroundActive(boolean state) {
		this.backgroundActive = state;
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), backgroundOpacity);
	}

	public void setBackgroundOpacity(int opacity) {
		backgroundOpacity = opacity;
		backgroundColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(),
				opacity);
	}

	public void setBackgroundCornerRadius(int radius) {
		this.backgroundCornerRadius = radius;
	}

	// text
	public void setTextFontSize(int size) {
		this.fontSize = size;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < keys.length; j++) {
				keys[j][i].setTextFontSize(size);
			}
		}
	}

	public void setTextFont(Font font) {
		this.font = font;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < keys.length; j++) {
				keys[j][i].setTextFont(font);
			}
		}
	}

	public void setTextColor(Color color) {
		this.textColor = color;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < keys.length; j++) {
				keys[j][i].setTextColor(textColor);
			}
		}
	}

//paint ------------------------------------------------------------------------------------------------
	public void paint(Graphics2D g) {
		if (active) {
			if (backgroundActive) {
				drawBackground(g);
			}
			drawKeys(g);
		}

	}

	private void drawBackground(Graphics2D g) {
		g.setColor(backgroundColor);
		if (fullscreen) {
			g.fillRoundRect(0, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height), width, height,
					backgroundCornerRadius, backgroundCornerRadius);
		} else {
			g.fillRoundRect(x, y, width, height, backgroundCornerRadius, backgroundCornerRadius);
		}
	}

	private void drawKeys(Graphics2D g) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < keys.length; j++) {
				try {
					keys[j][i].paint(g);
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
				}
			}

		}
	}
}
