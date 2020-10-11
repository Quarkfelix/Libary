package Main;

public class Main {
	public static GUI gui;
	public static RepaintThread t;
	
	public static void main(String[] args) {
		gui = new GUI();
		t = new RepaintThread();
	}
}
