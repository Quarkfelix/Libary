package Main;

public class RepaintThread extends Thread{
	public int fps = 0;
	private int count = 0;
	private float oldTime = 0;
	
	public RepaintThread() {
		super.start();
		oldTime = System.nanoTime();
	}
	
	public void run() {
		while(true) {
			GUI.draw.repaint();
			count++;
			if(System.nanoTime() - oldTime > 250000000) {
				fps = count * 4;
				count = 0;
				oldTime = System.nanoTime();
			}
		}
	}
}
