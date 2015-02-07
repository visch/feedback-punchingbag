package comGui;

import java.util.ArrayList;
import java.util.List;

public class Punches {
	//TODO Add max punch values???
	private double minPunchThreshold=1.4; //Acceleration minimum for a punch
	private int minMessagesPerPunch=3; //Minimum number of messages per punch
	private int maxTicksBetweenPunches=5; //Maximum time that acceleration needs to be below the minPunchThresold to stop being counted as a punch
	public List<Punch> allPunches = new ArrayList<Punch>();
	public RawDataStorage theRawDataStore;
	
	private static int forceIndex = 0; 	//Global used to keep track of last punch read from function getAverageForce
	private static int freqIndex = 0; 	//Global used to keep track of last punch read from function getFreq
	
	public Punches(RawDataStorage rawDat) {
		//TODO: Punches Class: Finish writing the constructor
		this.theRawDataStore = rawDat;
	}
	
	//Get Average Force function
	public double getAverageForce() 
	{
		int start =  forceIndex;
		int end = allPunches.lastIndexOf(allPunches);
		 
		
		if(end >= start)
		{
			double average = 0;
			for(int i = start; i <= end; i++)
			{
				//TODO: convert to force/create function in punch to do it
				average += allPunches.get(i).accelerationsForPunchSize();
			}
			
			average = average/(end-start +1);//inclusive
			forceIndex = end + 1; //set punchIndex to next element to be added to list
			return average;
		}
		else
		{
			return 0;
		}
		
	}//end getAverageForce
	
	//TODO: how accurate is java timer? DO we need to calculate Freq based on hardware ID? 
	public double getFreq(int timeSec)
	{
		int start =  freqIndex;
		int end = allPunches.lastIndexOf(allPunches);
		
		if(end >= start)
		{
			freqIndex = end + 1; //set freqIndex to next element to be added to list
			return (end - start + 1)/timeSec; //calculate number of punches per second
		}
		else
		{
			return 0;
		}
		
		
		
		
		
	}
	
	//Store Punch Data for round to the DB function??
	public boolean storeToDataTable() {
		//TODO: Punches Class: add logic for the storeToDataTable function
		return true; //If successful on writing to the DB
		//return false if not successful on writing to the DB
	}
	
	//Calcuate the number of punches
	public void collectPunches()
	{
		double punchVal;
		//List<Message>accel1Messages=new ArrayList<Message>(); //Accel1Messages using class to format data
		List<Message>punchesOverThreshold=new ArrayList<Message>(); 


		//First just grab all of the punches that are over some sort of threshold
		while((theRawDataStore.accelDataPunchBuffer.size() != 0) && (!theRawDataStore.accelDataPunchBuffer.isEmpty())) 
		{
			List<Message> test=theRawDataStore.accelDataPunchBuffer;
			
			Message test2=theRawDataStore.accelDataPunchBuffer.get(0);
			//System.out.println(test.size());
			punchVal=theRawDataStore.accelDataPunchBuffer.get(0).getAccelValue(); //Grab the first written accelerometer to the list (Earliest read)
			
			
			if (punchVal>minPunchThreshold)
			{
				punchesOverThreshold.add(theRawDataStore.accelDataPunchBuffer.get(0));
			}
			theRawDataStore.accelDataPunchBuffer.remove(0); //Remove this now that we're done with it
		}

		//Now lets look at where the distinct punches are located and add them to a List of Punches

		//Add the first read punch 
		//Make a new punch and pass in its message list 
		
		if (punchesOverThreshold.size() != 0 && allPunches.size()==0)
		{
		allPunches.add(new Punch(punchesOverThreshold.get(0)));
		punchesOverThreshold.remove(0); // We're done with it delete it
		}
		
		while(punchesOverThreshold.size() != 0)
		{
			Message checkmes = punchesOverThreshold.get(0); 
			punchesOverThreshold.remove(0); //Remove last punch from list

			Punch lastPunch = allPunches.get(allPunches.size()-1); //Grab the latest punch

			//Check for 16bit rollover from uC if so deal with it
			//TODO NEED to check the main message ID just in case we roll over a short even though it's taken longer than that for a punch
			if (lastPunch.getLastMessage().getFromHardWareID() > checkmes.getFromHardWareID())
			{
				checkmes.setFromHardWareID(checkmes.getFromHardWareID()+65535);
			}

			//Check if this punch fits in our maximum "Gap" with the last punch
			if ( (checkmes.getFromHardWareID()-lastPunch.getLastMessage().getFromHardWareID()) <= maxTicksBetweenPunches)
			{
				//We are within our MaxTicks between punches 
				lastPunch.AddPunch(checkmes); //Add this to the end of our last punch as it fits the trend
			}


			else
			{
				//We were not inside our max ticks between punches therefore we need to check to see if the last Punch qualifies
				//Qualify the last punch
				if (lastPunch.accelerationsForPunchSize() >= minMessagesPerPunch)
				{
					//We have verified the last Punch therefore leave it in our Punches list and add the next message to the list
					System.out.println("Verified last punch");
					//Add the next punch if possible
					if (punchesOverThreshold.size()!=0) 
					{	
						allPunches.add(new Punch(checkmes)); //Add this into the next new punch
					}
				}
			}

		}


	}
	
	
}
