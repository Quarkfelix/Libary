package libary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer implements Runnable {
	private ArrayList<File> musicFiles = new ArrayList<>();
	private int volume = 0;
	private int volumesave = 0;
	private Thread t;
	private Clip clip;
	private long lengthclip = 0;
	private long timeold = 0;
	private long timenew = 0;

	private boolean on = false;
	private boolean looping = false;
	private boolean all = false;
	private boolean locked = false;
	private int musicpointer = 0;

//constructor------------------------------------------------------------------------------------------------------------
	public MusicPlayer(String url) {
		musicFiles.add(new File(getClass().getClassLoader().getResource(url).getFile()));
		t = new Thread(this);
		t.start();
	}

	public MusicPlayer(String[] urls) {
		for (int i = 0; i < urls.length; i++) {
			musicFiles.add(new File(getClass().getClassLoader().getResource(urls[i]).getFile()));
		}
		t = new Thread(this);
		t.start();
	}

//run Method------------------------------------------------------------------------------------------------------------
	public void run() {
		while (true) {
			while (this.on) {
				while (looping) {
					if (all) {
						playall();
					} else {
						playsingle();
					}
				}
				if (!looping && !locked) {
					if (all) {
						playall();
					} else {
						playsingle();
					}
					locked = true;
					this.on = false;
				}
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

	// plays the song the pointer points to
	public void playsingle() {
		play(musicFiles.get(musicpointer));
		timeold = System.nanoTime();
		timenew = System.nanoTime();
		lengthclip = clip.getMicrosecondLength() * 1000;
		while (timenew - timeold <= lengthclip) {
			try {
				Thread.sleep(10);
				timenew = System.nanoTime();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clip.stop();
		clip.flush();
		clip.drain();
		clip.close();
		clip = null;
	}

	// plays every song till end of list starting with pointer
	public void playall() {
		System.out.println("playall");
		while (musicpointer < musicFiles.size()) {
			play(musicFiles.get(musicpointer));
			timeold = System.nanoTime();
			timenew = System.nanoTime();
			lengthclip = clip.getMicrosecondLength() * 1000;
			while (timenew - timeold <= lengthclip) {
				try {
					Thread.sleep(10);
					timenew = System.nanoTime();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// clip hat nach paar mal skippen rumgespackt.
			// Ich: random bullshit go!
			clip.stop();
			clip.flush();
			clip.drain();
			clip.close();
			clip = null;
			musicpointer++;
		}
		musicpointer = 0;
	}

	private void play(File file) {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volumeControl.setValue(-1 * volume);
			
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mute() {
		if(volume != 80) { //mute
			volumesave = volume;
			setVolume(80);
		} else { 			//unmute
			setVolume(volumesave);
		}		
	}

//getter-and-setter------------------------------------------------------------------------------------------------------
	public void play(boolean loop, boolean single) {
		if(loop) {
			this.looping = true;
		} else {
			this.locked = false;
			this.looping = false;
		}
		
		if(single) {
			this.all = false;
		} else {
			this.all = true;
		}
		this.on = true;	
	}

	public void stopPlaying() {
		this.on = false;
		this.looping = false;
		this.locked = true;
		musicpointer = musicFiles.size()-1;
		lengthclip = 0;
	}
	
	public void resume() {
		this.on = true;
	}

	public void nextSong() {
		lengthclip = 0;
	}

	public boolean isOn() {
		return this.on;
	}
	
	public void setMusicpointer(int musicindex) {
		this.musicpointer = musicindex;
	}
	
	// value between 0 and 100
	public void setVolume(int volume) {
		this.volume = volume;
		if(clip != null) {
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volumeControl.setValue(-1 * volume);
		}	
	}

	public float getVolume() {
		return volume;
	}
}