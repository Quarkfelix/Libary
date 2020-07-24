package Main;

public class RepaintThread extends Thread{

	public RepaintThread() {
		super.start();
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GUI.draw.repaint();
		}
	}
}
