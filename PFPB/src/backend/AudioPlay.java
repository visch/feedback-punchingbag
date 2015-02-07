package backend;

import javax.media.*;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;

public  class AudioPlay
{
	private Player p;   							// player to start and adjust music
	private File s0,s1,s2,s3,s4,s5,s6,s7,s8,s9;    // files for songs
	private int next = 0;						    // check next song on list	 
	private int calledIncFrq = 0;
	private int calledDecVol = 0;
	private float frqs[] = {(float)1.3, (float)1.4, (float)1.6, (float)1.8, (float)2.0, (float)2.2, (float)2.4, (float)2.6, (float)2.8, (float)3.0}; 
	private float vols[] = {(float)0.5, (float)0.4, (float)0.3, (float)0.2, (float)0.16, (float)0.15, (float)0.14, (float)0.13, (float)0.11}; 

	private int nextModulo;

	public void startPlayer() {
		p.start();
	}
	
	public void createInitPLayer()
	{
		try {
			p=Manager.createRealizedPlayer(s0.toURI().toURL());			

		} catch (IOException | MediaException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	}

	public void terminate() {
		p.stop();
		p.close();
		p.deallocate();
		p = null;
	}
	
	public void stop()
	{
		p.stop();
	}

	public void chgVolume() {
		if(calledDecVol <= 8)
		{
			p.getGainControl().setLevel(vols[calledDecVol]);
		}
		else
		{
			p.getGainControl().setLevel(vols[8]);
		}
		calledDecVol++;
	}

	public void restoreVol() {
		p.getGainControl().setLevel(1);
		calledDecVol = 0;
	}

	public void chgFreq() {
		if(calledIncFrq <= 9)
		{
			p.setRate(frqs[calledIncFrq]);
		}
		else
		{
			p.setRate(frqs[9]);
		}
		calledIncFrq++;
	}
	public void restoreFreq() {
		p.setRate(1);
		calledIncFrq = 0;
	}

	/* Constructor: takes in play list creates Player(pointing to first song) */	 
	public AudioPlay(List<String> list) throws MalformedURLException, IOException, MediaException, InterruptedException
	{
		//create audio files
		if(list.size() == 1)
		{
			s0=new File(list.get(0).toString());


		}
		if(list.size() == 2)
		{		 
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());

		}
		if(list.size() == 3)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
		}
		if(list.size() == 4)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
		}
		if(list.size() == 5)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
			s4=new File(list.get(4).toString());
		}
		if(list.size() == 6)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
			s4=new File(list.get(4).toString());
			s5=new File(list.get(5).toString());
		}
		if(list.size() == 7)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
			s4=new File(list.get(4).toString());
			s5=new File(list.get(5).toString());
			s6=new File(list.get(6).toString());
		}
		if(list.size() == 8)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
			s4=new File(list.get(4).toString());
			s5=new File(list.get(5).toString());
			s6=new File(list.get(6).toString());
			s7=new File(list.get(7).toString());
		}
		if(list.size() == 9)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
			s4=new File(list.get(4).toString());
			s5=new File(list.get(5).toString());
			s6=new File(list.get(6).toString());
			s7=new File(list.get(7).toString());
			s8=new File(list.get(8).toString());
		}
		if(list.size() == 10)
		{
			s0=new File(list.get(0).toString());
			s1=new File(list.get(1).toString());
			s2=new File(list.get(2).toString());
			s3=new File(list.get(3).toString());
			s4=new File(list.get(4).toString());
			s5=new File(list.get(5).toString());
			s6=new File(list.get(6).toString());
			s7=new File(list.get(7).toString());
			s8=new File(list.get(8).toString());
			s9=new File(list.get(9).toString());
		}

		nextModulo = list.size();
	}

	/* set player to next song in play list loop indefinitely */
	private void  next() throws MediaException, CannotRealizeException, MalformedURLException, IOException
	{
		next = ++next % nextModulo; //Switch this before we go into the switch statement to make sure we go to the next song
		switch(next)
		{
		case 0: 
			p=Manager.createRealizedPlayer(s0.toURI().toURL());
			break;

		case 1: 
			p=Manager.createRealizedPlayer(s1.toURI().toURL());
			break;

		case 2: 
			p=Manager.createRealizedPlayer(s2.toURI().toURL());
			break;

		case 3: 
			p=Manager.createRealizedPlayer(s3.toURI().toURL());
			break;

		case 4: 
			p=Manager.createRealizedPlayer(s4.toURI().toURL());
			break;

		case 5: 
			p=Manager.createRealizedPlayer(s5.toURI().toURL());
			break;

		case 6: 
			p=Manager.createRealizedPlayer(s6.toURI().toURL());
			break;

		case 7: 
			p=Manager.createRealizedPlayer(s7.toURI().toURL());
			break;

		case 8: 
			p=Manager.createRealizedPlayer(s8.toURI().toURL());
			break;

		case 9: 
			p=Manager.createRealizedPlayer(s9.toURI().toURL());
			break;

		default:
			break;
		}


		
	}


	public void checkplayer()
	{
		if(p.getState() != Controller.Started) //check if current song has stopped
		{
			try {
				next();
				p.start();
			} catch (MediaException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			//do nothing song is still playing
		}
	}




}//end class