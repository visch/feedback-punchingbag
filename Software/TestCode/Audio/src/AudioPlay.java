import javax.media.*;
import java.io.*;
import java.net.MalformedURLException;


 class AudioPlay implements Runnable
{
	/*
	 * Global Class variables
	 * 
	 */
	 static Player p;   							//player to start and adjust music
	 private static File s0,s1,s2,s3,s4,s5,s6,s7,s8,s9;		//files for songs
	 private static int next;								// check next song on list
	 
	 
	  //temp variables delete after complete
	 static float performanceGoal;
	 static float threshold;
	 static boolean volumeFlag;
	 static boolean FreqFlag;
	 
	 
	 /*
	  * COnstructor: 
	  * takes in playlist creates Player(pointing to first song)
	  */
	 
	 
	 public AudioPlay(String playlist []) throws NoPlayerException, CannotRealizeException, MalformedURLException, IOException
	 {
		 //create audio files
		  s0=new File(playlist[0]);
		  s1=new File(playlist[1]);
		  s2=new File(playlist[2]);
		  s3=new File(playlist[3]);
		  s4=new File(playlist[4]);
		  s5=new File(playlist[5]);
		  s6=new File(playlist[6]);
		  s7=new File(playlist[7]);
		  s8=new File(playlist[8]);
		  s9=new File(playlist[9]);
		  
		  p = Manager.createRealizedPlayer(s0.toURI().toURL()); //set player to first song
	 }
	 
	 
	  static String testSongs[] = {"H:/music/The xx - Coexist (2012)/01 Angels.mp3",
							 "H:/music/The xx - Coexist (2012)/02 Chained.mp3",
							 "H:/music/The xx - Coexist (2012)/03 Fiction.mp3",
							 "H:/music/The xx - Coexist (2012)/04 Try.mp3",
							 "H:/music/The xx - Coexist (2012)/05 Reunion.mp3",
							 "H:/music/The xx - Coexist (2012)/06 Sunset.mp3",
							 "H:/music/The xx - Coexist (2012)/07 Missing",
							 "H:/music/The xx - Coexist (2012)/08 Tides.mp3",
							 "H:/music/The xx - Coexist (2012)/09 Unfold.mp3",
							 "H:/music/The xx - Coexist (2012)/10 Swept Away.mp3"};
	
 public static void main(String args[]) throws Exception
 {
	
	AudioPlay audio = new AudioPlay(testSongs);
	int roundTimer = 100000000; //some shit set in Workout
	
	while(roundTimer != 1)
	{
		
		if(AudioPlay.p.getState() != Controller.Started)
		{
			audio.next();
			audio.p.start();
			Thread.sleep(200);	//short delay to make sure state change happens before next check
		}
		
		if((performanceGoal < threshold) && volumeFlag == true)  //if below threshold and want volume feedback
		{
			audio.p.getGainControl().setLevel((float)(performanceGoal/threshold));
		}
		else //set volume back to normal if at or above threshold
			audio.p.getGainControl().setLevel(1);
		
		if((performanceGoal < threshold) && FreqFlag == true)  //if below threshold and want frequency feedback
		{
			audio.p.setRate(performanceGoal/threshold);
		}
		else //set Frequency back to normal if at or above threshold
			audio.p.getGainControl().setLevel(1);
		
		roundTimer--;
	}
	
	
  }


 
 
 /*
  * set player to next song in playlist
  * loop indefinitely
  */
 private void next() throws MediaException, CannotRealizeException, MalformedURLException, IOException
 {
	
	 switch(next)
	 {
	 	case 0: p=Manager.createRealizedPlayer(s0.toURI().toURL());
	 	break;
	 	
	 	case 1: p=Manager.createRealizedPlayer(s1.toURI().toURL());
	 	break;
	 	
	 	case 2: p=Manager.createRealizedPlayer(s2.toURI().toURL());
	 	break;
	 	
	 	case 3: p=Manager.createRealizedPlayer(s3.toURI().toURL());
	 	break;
	 	
	 	case 4: p=Manager.createRealizedPlayer(s4.toURI().toURL());
	 	break;
	 	
	 	case 5: p=Manager.createRealizedPlayer(s5.toURI().toURL());
	 	break;
	 	
	 	case 6: p=Manager.createRealizedPlayer(s6.toURI().toURL());
	 	break;
	 	
	 	case 7: p=Manager.createRealizedPlayer(s7.toURI().toURL());
	 	break;
	 	
	 	case 8: p=Manager.createRealizedPlayer(s8.toURI().toURL());
	 	break;
	 	
	 	case 9: p=Manager.createRealizedPlayer(s9.toURI().toURL());
	 	break;
	 	
	 	default:
	 	break;
	 }
	 next = ++next % 10;
		
 }
 
 
  
 
 
@Override
public void run() {
	// TODO Auto-generated method stub
	
}







}//end class