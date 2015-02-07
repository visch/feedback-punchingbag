import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.media.MediaException;


public class AudioTesterWithList {

	public static void main(String[] args) throws MalformedURLException, IOException, MediaException, InterruptedException {
		// TODO Auto-generated method stub
		List<String> songPaths = new ArrayList<String>();
		songPaths.add("C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3");
		songPaths.add("C:\\Users\\Public\\Music\\Sample Music\\Maid with the Flaxen Hair.mp3");
		songPaths.add("C:\\Users\\Public\\Music\\Sample Music\\Sleep Away.mp3");
		
		for(String path: songPaths) {
			path = path.replace('\\', '/');
		}
		
		AudioPlay musicPlayer = new AudioPlay(songPaths);
		musicPlayer.startPlayer();
		
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.chgFreq((float)0.35);
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.restoreFreq();
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.chgFreq((float)1.50);
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.restoreFreq();
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.chgVolume((float)0.50);
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.restoreVol();
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.chgVolume((float)0.125);
		TimeUnit.SECONDS.sleep(3);
		musicPlayer.restoreVol();
	}
}
