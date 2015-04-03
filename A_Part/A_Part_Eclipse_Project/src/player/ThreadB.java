package player;

import java.io.File;

/**
* This class implements a thread that can get a song file and make of it 
  an MP3Player object. Then staritn the play of the song.
* @author Christos Markou 
* @version 1
*/


class ThreadB extends Thread {
	int total = 0;

	@Override
	public void run() {
		synchronized (this) {
			
			File MySong = myClass.getSong();
			
			MP3Player myplay = new MP3Player(MySong);
			
			myplay.start(MySong);

		}
	}
}