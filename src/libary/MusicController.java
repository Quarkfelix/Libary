package libary;

import java.util.ArrayList;
import java.util.HashMap;

/*�ber music controller werden lieder abgespielt. wenn ein lied/sound abgespielt werden soll wird ein neuer MusicPlayer 
erstellt welcher dann seinen sound apspielt.
im controller wird auch festgelegt ob loop alle sounds oder nur einen.
die lautst�rkensteuerung muss noch auf einzelne lieder umgestellt weden */
public class MusicController extends Thread{ 
	private HashMap<String, MusicPlayer> musicPlayer = new HashMap<String, MusicPlayer>();

//constructor------------------------------------------------------------------------------------------------------------
	public MusicController() {
	}
	
//run Method------------------------------------------------------------------------------------------------------------	
	public void run() {
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//methods---------------------------------------------------------------------------------------------------------------
	public void newPlayer(String name, String url) {
		musicPlayer.put(name, new MusicPlayer(url));
	}
	
	public void newPlaylist(String name, String[] urls) {
		musicPlayer.put(name, new MusicPlayer(urls));
	}
	
	public void startLooping(String name) {
		musicPlayer.get(name).setLoop(true);;
	}
	
	public void playSound(String playlistName, String soundName) {
		musicPlayer.get(playlistName).playSound(soundName);
	}

	
	
	
	
	
//getter-and-setter------------------------------------------------------------------------------------------------------
	
	//l�sst einen music Thread alle songs loopen
	public void setLoop(String player, boolean state) {
		musicPlayer.get(player).setLoop(state);
	}
	
	public void setVolume(String player, double volume) {
		musicPlayer.get(player).setVolume((float)volume);
	}
	
}




