package Main;

import java.awt.Color;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI {
	public JFrame jf;
	public static Draw draw;
	
	public GUI() {
		jf = new JFrame();
		draw = new Draw();
		
		jf.setSize(1500, 800);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jf.addMouseListener(new MouseHandler());
		jf.addKeyListener(new KeyHander());
		jf.addMouseMotionListener(new MouseMotionHandler());
		jf.add(draw);
		jf.requestFocus();
		jf.setVisible(true);
		
//		Icon imageIcon = new ImageIcon(this.getClass().getResource("test.gif"));
//		JLabel label = new JLabel(imageIcon);
//		label.setSize(100, 500);
//		label.setLocation(50, 50);
//		label.setVisible(true);
//		jf.getContentPane().add(label);	
//		jf.pack();
	}
	
}
