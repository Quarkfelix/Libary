package libary;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TextWriter {
	
	public void drawText(Graphics2D g, int x, int y, String absatz, Font font, String text) {
		g.setColor(Color.BLACK);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);
		int textWidth = fMetric.stringWidth(text);
		int textHeight = fMetric.getHeight();
		switch (absatz) {
		case "linksb�ndig":
			g.drawString(text, x, y);
			break;
		case "zentriert":
			g.drawString(text, (int)x - textWidth/2, (int)y + textHeight/3);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + absatz);
		}
		
		
	}
	
	public void drawText(Graphics2D g, int x, int y, String absatz, Font font, Color color, String text) {
		g.setColor(color);
		FontMetrics fMetric = g.getFontMetrics(font);
		g.setFont(font);
		int textWidth = fMetric.stringWidth(text);
		int textHeight = fMetric.getHeight();
		switch (absatz) {
		case "linksb�ndig":
			g.drawString(text, x, y);
			break;
		case "zentriert":
			g.drawString(text, (int)x - textWidth/2, (int)y + textHeight/3);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + absatz);
		}
	}
}
