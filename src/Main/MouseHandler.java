package Main;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Draw.surface.checkButtonPress(e.getX(), e.getY() - 26);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Draw.surface.checkButtonClick(e.getX(), e.getY() - 26);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
