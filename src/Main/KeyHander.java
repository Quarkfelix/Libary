package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHander implements KeyListener {

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		Draw.surface.tif.setText(arg0);

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
