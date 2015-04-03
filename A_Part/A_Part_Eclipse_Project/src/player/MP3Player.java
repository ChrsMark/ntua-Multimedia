package player;

import org.tritonus.share.sampled.AudioUtils;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
* This class implements an mp3Player, providing Playing, Pausing, Rsuming
* and getting Metada methods.
* @author Christos Markou 
* @version 1
*/

public class MP3Player {
	private AudioInputStream audioInputStream, decodedInputStream;
	private AudioFormat decodedAudioFormat, baseAudioFormat;
	private SourceDataLine line;
	private DataLine.Info info;

	static String Status;
	static String Skip;
	

	long Duration;
	int BytesDyr;
	static long Progress;
	static String Time;
/**
   * This is the costractor of the Class.
   */
	public MP3Player(File f) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(f);
			baseAudioFormat = audioInputStream.getFormat();
			decodedAudioFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseAudioFormat.getSampleRate(), 16,
					baseAudioFormat.getChannels(),
					baseAudioFormat.getChannels() * 2,
					baseAudioFormat.getSampleRate(), false);
			decodedInputStream = AudioSystem.getAudioInputStream(
					decodedAudioFormat, audioInputStream);
			info = new DataLine.Info(SourceDataLine.class, decodedAudioFormat);
			System.out.println(info.toString());
			line = (SourceDataLine) AudioSystem.getLine(info);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
/**
   * This method play the File f song by decodeing,reading and writing it to the speakers.
   */
	public void start(File song) {
		int TotalBytes = 0;
		long TotalDur = 0;
		long counter = 0;
		File Mp3song = song;

		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		long duration = (long) 0;
		int SkipBytes = (int) AudioUtils.millis2Bytes(500, decodedAudioFormat);
		try {
			try {
				baseFileFormat = AudioSystem.getAudioFileFormat(Mp3song);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();

			String key_duration = "duration";
			duration = (Long) properties.get(key_duration);

		}

		if (line != null && audioInputStream != null) {
			try {
				line.open(decodedAudioFormat);
				line.start();
				int nBytesRead = 0;

				byte[] abData = new byte[1024];
				while (nBytesRead != -1) {

					// ********************************************
					if (Skip == "Skip") {
						//System.out.println("i am skipping2");
						Skip = "Dummy";
						decodedInputStream.skip(SkipBytes);
						TotalBytes = TotalBytes + SkipBytes;

						//System.out.println("i am skipping3");

						TotalDur = AudioUtils.bytes2Millis(TotalBytes,
								decodedAudioFormat);

					} else if (Status == "Paused") {
						try {
							System.out.println("i am waiting");
							ThreadB myThread = myClass.getThread();
							myThread.wait();
							System.out.println("i leave from am waiting");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/*
						 * while(Status=="Paused"){ System.out.println(Status);
						 * if(Status=="Playing"){break;} }
						 */
					}
					if (Status == "Playing") {
						nBytesRead = decodedInputStream.read(abData, 0,
								abData.length);
						TotalBytes = TotalBytes + nBytesRead;

						TotalDur = AudioUtils.bytes2Millis(TotalBytes,
								decodedAudioFormat);
					}
					int mili = (int) (duration / 1000);
					Progress = 100 * TotalDur / mili;

					// System.out.println("Progress:" + Progress +"%");
					int sec = (int) ((TotalDur / 1000) % 60);
					int min = (int) ((TotalDur / 1000) / 60);
					// System.out.println("Duration:" + min + ":" + sec+"");
					Time = ("Time elapsed: "+min+":"+sec+"");

					if (nBytesRead >= 0) {
						int nBytesWritten = line.write(abData, 0, nBytesRead);
					}
				}
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				line.drain();
				line.close();
			}

		}
		//System.out.println(Status);

		Status="Done";
		while(true);
		//System.out.println(Status);
	}
/**
   * This method provides the metaData of the Song which FullPath is String song .
   */
	public void info(String song) {

		File Mp3song = new File(song);

		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		long duration = (long) 5000;

		try {
			try {
				baseFileFormat = AudioSystem.getAudioFileFormat(Mp3song);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();

			String key_duration = "duration";
			duration = (Long) properties.get(key_duration);

		}

		String Author = getArtist(song);

		String Title = getTitle(song);

		String Album = getAlbum(song);

		// Long Duration = getDuration(song);

		int mili = (int) (duration / 1000);
		int sec = (mili / 1000) % 60;
		int min = (mili / 1000) / 60;

		System.out.println("Welcome!!!");
		System.out.println("Title:" + Title);
		System.out.println("Author:" + Author);
		System.out.println("Album:" + Album);
		System.out.println("Duration:" + +min + ":" + sec + "");

	}
/**
   * This method returns the ArtistName of the Song which FullPath is String song .
   */
	public static String getArtist(String song) {
		File Mp3song = new File(song);

		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		String author = "Name";

		try {
			try {
				baseFileFormat = AudioSystem.getAudioFileFormat(Mp3song);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();
			String key = "author";
			author = (String) properties.get(key);

		}

		return author;
	}
/**
   * This method returns the Album Name of the Song which FullPath is String song .
   */
	public static String getAlbum(String song) {
		File Mp3song = new File(song);

		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		String album = "Album";

		try {
			try {
				baseFileFormat = AudioSystem.getAudioFileFormat(Mp3song);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();
			String key = "album";
			album = (String) properties.get(key);

		}

		return album;
	}
/**
   * This method returns the title Name of the Song which FullPath is String song .
   */
	public static String getTitle(String song) {
		File Mp3song = new File(song);

		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		String title = "Title";

		try {
			try {
				baseFileFormat = AudioSystem.getAudioFileFormat(Mp3song);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof TAudioFileFormat) {
			Map properties = ((TAudioFileFormat) baseFileFormat).properties();
			String key = "title";
			title = (String) properties.get(key);

		}

		return title;
	}
/**
   * This method pausing the playing by setting the global variable Status to "Paused" .
   */
	static void Pause()  {
		
	
		Status = "Paused";
		// return Status;
	}
/**
   * This method  setting the global variable Status to "Playing" .
   */
	static void Playing() {
		Status = "Playing";
		// return Status;
	}
/**
   * This method skipping the playing by setting the global variable Status to "Skip" .
   */
	static void Skip() {
		if (Status == "Playing") {
			Skip = "Skip";
		}
		// return Status;
	}
/**
   * This method resuming the playing by setting the global variable Status to "Playing"
   * and notifying the thread that playing the track and is currently in wait mode   .
   */
	static public void Resume() {
		if (Status == "Paused") {
			Status = "Playing";
			ThreadB myThread = myClass.getThread();
			synchronized (myThread) {
				System.out.println("i am resuming from MP3 Resume");
				myThread.notify();
			}

		}

	}
/**
   * This method returns status of playing {Playing,Paused,Skip}.
   */
	static public String getStatus() {
		return Status;
	}
	/**
   * This method returns the progress of playing culculating while the song file is reading and playing.
   */
	static public long getProgress(){
		System.out.println("update Prog   from : getProgress");
		return Progress;
	}
	/**
   * This method returns the time of playing culculating while the song file is reading and playing.
   */
	static public String getTime(){
		System.out.println("update Time  from getTime");
		return Time;
	}

	

}