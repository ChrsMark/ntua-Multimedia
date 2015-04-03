package theGUI;

import java.io.File;
import java.util.Random;

import javax.swing.SwingWorker;

import player.MP3Player;
import player.myClass;





class Task extends SwingWorker<Void, Void> {
	int total = 0;

	
	
			
			
	

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		int MyProg=0;
		System.out.println("dsdksj");
		while(MyProg<100){
			for(long klo=0; klo<999; klo++);
			
			 MyProg = (int) MP3Player.getProgress();
			MyGui.progressBar.setValue(MyProg);
			MyGui.TimeLabel.setText(MP3Player.getTime());
		//MyGui.progressBar.setValue((int) MP3Player.getProgress());
			if(MyProg==99){
				break;
			}
		System.out.println(MyProg);
		}
		MyGui.progressBar.setValue(100);
		MyGui.progressBar.setValue(0);
		MyGui.TimeLabel.setText("");
		MyGui.lblArtistBio.setText("");
		return null;
	}

	
   
}