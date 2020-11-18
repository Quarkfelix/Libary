package libary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class MusicController implements Runnable {
	private HashMap<String, MusicPlayer> musicPlayer = new HashMap<String, MusicPlayer>();

//constructor------------------------------------------------------------------------------------------------------------
	public MusicController() {
		Thread t = new Thread(this);
		t.start();
	}

//run Method------------------------------------------------------------------------------------------------------------	
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
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

	public void play(String playlist, boolean loop, boolean single) {
		musicPlayer.get(playlist).play(loop, single);
	}
	
	public void play(String playlist, int musicindex, boolean loop, boolean single) {
		musicPlayer.get(playlist).setMusicpointer(musicindex);
		musicPlayer.get(playlist).play(loop, single);
	}

	public void stop(String playlist) {
		musicPlayer.get(playlist).stopPlaying();
	}

	public void nextSong(String playlist) {
		musicPlayer.get(playlist).nextSong();
	}
	
	public void mute() {
		for (Entry<String, MusicPlayer> e : musicPlayer.entrySet()) {
			if (e.getValue().getVolume() != 0.0f) {
				e.getValue().setVolume(0.0f);
				System.out.println("muted" + e.getValue().getVolume());
			} else {
				System.out.println("unmuted" + e.getValue().getVolume());
				e.getValue().setVolume(1.0f);
			}
		}
	}
//getter-and-setter------------------------------------------------------------------------------------------------------

	public ArrayList<String> getAllPlaylists() {
		ArrayList<String> list = new ArrayList<>();
		for (Entry<String, MusicPlayer> e : musicPlayer.entrySet()) {
			list.add(e.getKey());
		}
		return list;
	}

	public void setVolume(String player, double volume) {
		musicPlayer.get(player).setVolume((float) volume);
	}

	

}
