package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHander implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		Draw.surface.tif.setText(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
