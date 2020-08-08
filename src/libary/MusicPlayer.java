package libary;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer extends Thread{
	private HashMap<String, File> musicFiles = new HashMap<String, File>();
	private boolean loop = false;
	private int playCount = 0;
	private String activeFile = "File 1";
	private float volume = (float) 0.0;
	
//constructor------------------------------------------------------------------------------------------------------------
	public MusicPlayer(String url) {
		musicFiles.put("File " + musicFiles.size() + 1, new File(url));
		super.start();
	}	
	
	public MusicPlayer(String[] urls) {
		for (int i = 0; i < urls.length; i++) {
			musicFiles.put("File " + (musicFiles.size()+1), new File(urls[i]));
		}
		super.start();
	}	
//run Method------------------------------------------------------------------------------------------------------------
	public void run() {
		while (true) {
			for (int i = playCount; i > 0; i--) {
				playCount--;
				play(musicFiles.get(activeFile));
			}
			while (loop) {
				musicFiles.forEach((k,v) -> play(v));
			}
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//methods---------------------------------------------------------------------------------------------------------------
	private void play(File file) {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volumeControl.setValue(volume);
			Thread.sleep(clip.getMicrosecondLength() / 1000);
			
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playSound(String soundName) {
		activeFile = soundName;
		playCount++;
	}
	
	public void playSound(String soundName, int timesToPlay) {
		activeFile = soundName;
		playCount += timesToPlay;
	}
//getter-and-setter------------------------------------------------------------------------------------------------------
	public void setLoop(boolean state) {
		loop = state;
	}
	
	//value between 0 and 1
	public void setVolume(float numb) {
		volume = (float)(Math.log(numb) / Math.log(10.0) * 20.0);
	}
}