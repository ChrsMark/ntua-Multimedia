package player;

import java.io.File;
import java.util.Scanner;

/**
* This class is used for connecting purposes to the MP3Player Class.
* @author Christos Markou 
* @version 1
*/

public class myClass {

	static String Path;
	static ThreadB b;
	static File MySong;

	 /**
   * This method initialize b object of Class ThreadB .
   * @param args. The iput from cosole if it is used as a console application.
   */
	public static void main(String[] args) {

		
		b = new ThreadB();

		
	}
 /**
   * This method returns the public variable Path.
   */
	public static String getPath() {
		return Path;

	}
	/**
   * This method returns the public variable MySong.
   */
	public static File getSong() {
		return MySong;

	}
	/**
   * This method creates a new ThreadB object and make it to be runned. 
   When this method it is called the File Song is to be played.
   */
	public static void Play(File Song) {
		b = new ThreadB();
		MP3Player.Playing();
		b.start();
		MySong=Song;
	}
	/**
   * This method pauses the play of the song.
   */
	public static void MyPause() {
		MP3Player.Pause();
	}
	/**
   * This method resuming the play of the song. 
   */
	public static void MyResume() {
		MP3Player.Resume();
	}
	/**
   * This method skiping the play of the song. 
   */
	public static void MySkip() {
		if (MP3Player.getStatus() == "Playing") {
			MP3Player.Skip();
		}
	}
	/**
   * This method returning the Bio of the Composer of File f song, 
   through last.fm servise. 
   */
	public static String getComposerBio(File f) {
		HandleXML myXML = new HandleXML();
		String Author = MP3Player.getArtist(f.getAbsolutePath());
		String Bio =  myXML.getXML(
				"http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist="
						+ Author + "&api_key=41c757fdc3ad80a6dc1ab4dd859f2472",
				"Author");
		return Bio;

	}
	/**
   * This method returning the URL to the Picture of the Album of File f song, 
   through last.fm servise. 
   */
	public static String getAlbumPicture(File f) {
		HandleXML myXML = new HandleXML();
		String Album = MP3Player.getAlbum(f.getAbsolutePath());
		String Author = MP3Player.getArtist(f.getAbsolutePath());
		String MyUrl = myXML.getXML(
				"http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=41c757fdc3ad80a6dc1ab4dd859f2472&artist="
						+ Author + "&album=" + Album, "Album");
		return MyUrl;

	}
/**
   * This method returning the public ThreadB  b object
   */
	public static ThreadB getThread() {
		return b;

	}
}



