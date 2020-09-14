package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Draw extends JPanel{
	public static Surface surface;
	
	//constructor------------------------------------------------------------------------------------------------------------
		public Draw() {
			surface = new Surface();
		}
		
	//paint------------------------------------------------------------------------------------------------------------------
		public void paint(Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			try {
				surface.paint(g);			
			} catch (Exception e) {}
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
			g.drawString("FPS: " + Main.t.fps, 10, 47);
		}
}
