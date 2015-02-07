package backend.datatypes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import backend.*;

@SuppressWarnings("unused")
public class Punches {
	//TODO Add max punch values???
	private double minPunchThreshold= 1.4; //Acceleration minimum for a punch
	private int minMessagesPerPunch=3; //Minimum number of messages per punch
	private int maxTicksBetweenPunches=5; //Maximum time that acceleration needs to be below the minPunchThresold to stop being counted as a punch
	private int minTicksBetweenPunch=336/4; //166ms is the fastest punch divide by 4 for me (3 punches per second)
	private List<Punch> allPunches = new ArrayList<Punch>();
	private Punch maybeAPunch; 
	public RawDataStorage theRawDataStore;
	private double lastMessageID;
	private int forceIndex = 0;
	public final int timePerID = 3;
	final int numberOfIDsPerPeriod = 5 * 1000/3; //5 sec gap
	
	int startTime = 0;
	int endTime = 5;

	public Punches(RawDataStorage rawDat) {
		//TODO: Punches Class: Finish writing the constructor
		this.theRawDataStore = rawDat;
	}

	//Get Average Force and frequency for GUI [run in realtime after x sec]
	public double [] getForceFreqGUI() 
	{
		double array[] = {0,0};
		int start =  forceIndex;
		int end = allPunches.size()-1;


		if(end >= start)
		{
			double average = 0;
			for(int i = start; i <= end; i++)
			{
				allPunches.get(i).findMaxAccel();
				average += allPunches.get(i).getForce();
			}

			average = average/(end-start +1);//inclusive

			array[0] = average;				//store average
			array[1] = (end-start + 1);		//store number of punches
			forceIndex = end + 1; 			//set punchIndex to next element to be added to list
			return array;
		}
		else
		{
			return array;
		}

	}//end getForceFreqGUI


	//returns average force and freq in set time period(given in seconds)
	public double []  getRoundResults(int startTime, int endTime)
	{
		double array[] = {0,0};
		int startID = (startTime * 1000 / timePerID);	//startTime(sec) * 10000(ms) /4(#ms/ID)
		int endID	= (endTime * 1000 / timePerID);
		double punches = 0;
		double avgForce = 0;
		
		System.out.println("Start: "+startID+" Stop: "+ endID);
		for(int i = 0; i < allPunches.size(); i++)
		{
			//OLD LOGIC: allPunches.get(i).getID() >= startID && allPunches.get(i).getID() < endID
			if((allPunches.get(i).getID() >= theRawDataStore.softwareID - numberOfIDsPerPeriod)  && (allPunches.get(i).getID() < theRawDataStore.softwareID))
			{
				punches++;
				avgForce += allPunches.get(i).getForce();
				System.out.println(avgForce);
				System.out.println("Average Force: "+avgForce + " Punches: "+punches);
				System.out.println("Punches size: "+allPunches.size());
			}
		}

		if(avgForce != 0)
		{
			avgForce = avgForce/punches;
		}
		array[0] = avgForce;
		array[1] = punches;

		return array;
	}
	
	public double []  getRoundResultsForDB(int startTime, int endTime)
	{
		double array[] = {0,0};
		int startID = (startTime * 1000 / timePerID);	//startTime(sec) * 10000(ms) /4(#ms/ID)
		int endID	= (endTime * 1000 / timePerID);
		double punches = 0;
		double avgForce = 0;
		
		System.out.println("Start: "+startID+" Stop: "+ endID);
		for(int i = 0; i < allPunches.size(); i++)
		{
			//OLD LOGIC: allPunches.get(i).getID() >= startID && allPunches.get(i).getID() < endID
			if(allPunches.get(i).getID() >= startID && allPunches.get(i).getID() < endID)
			{
				punches++;
				avgForce += allPunches.get(i).getForce();
				System.out.println(avgForce);
				System.out.println("Average Force: "+avgForce + " Punches: "+punches);
				System.out.println("Punches size: "+allPunches.size());
			}
		}

		if(avgForce != 0)
		{
			avgForce = avgForce/punches;
		}
		array[0] = avgForce;
		array[1] = punches;

		return array;
	}
	
	public double[] grabEntireRoundResults()
	{
		double punchForce=0;
		double punchFreq=0;
		double retVal[]=new double[2];
		collectPunches(); //Make sure we have the latest punches collected
		for (Punch punch : allPunches) {
			punchForce += punch.getForce();
		}
		punchForce=punchForce/allPunches.size(); 
		retVal[0]=punchForce;
		retVal[1]=allPunches.size(); 
		
		return retVal; 
	}

/*	public double[] calcRoundOverallStats(int roundTimeInSec) {
		double forceFreq[] = {0,0};
		double force = 0;
		for (int i=0; i<allPunches.size(); i++)
		{
			force += allPunches.get(i).getForce();
		}
		forceFreq[0] = force / allPunches.size();
		forceFreq[1] = (double)(allPunches.size() / roundTimeInSec);	
		
		return forceFreq;
	}*/

	//Store Punch Data for round to the DB function??
	public boolean storeToDataTable() {
		//TODO: Punches Class: add logic for the storeToDataTable function
		//Alex:--We should keep all of the DB handeling in the GUIController, via the SQLLiteHelper
		return true; //If successful on writing to the DB
		//return false if not successful on writing to the DB
	}

	//Calculate the number of punches
	private List<Message>filterOnlyMessagesOverThreshold(List<Message> buffer)
	{
		double punchVal;
		List<Message>messagesOverThreshold=new ArrayList<Message>(); 

		//First just grab all of the punches that are over some sort of threshold
		while((theRawDataStore.accelDataPunchBuffer.size() != 0) && (!theRawDataStore.accelDataPunchBuffer.isEmpty())) 
		{
			List<Message> test=theRawDataStore.accelDataPunchBuffer;
			Message test2=theRawDataStore.accelDataPunchBuffer.get(0);
			//System.out.println(test.size());
			punchVal = theRawDataStore.accelDataPunchBuffer.get(0).getAccelValue(); //Grab the first written accelerometer to the list (Earliest read)

			if (punchVal > minPunchThreshold)
			{
				messagesOverThreshold.add(theRawDataStore.accelDataPunchBuffer.get(0));
			}
			theRawDataStore.accelDataPunchBuffer.remove(0); //Remove this now that we're done with it
		}
		return messagesOverThreshold;
	}
	
	
	public List<Data> outputPunchData(Double roundLengthSeconds, int roundID)
	{
		int startTime = 0;			
		int endTime = 5;
		double array [] = {0, 0};
		List<Data> data = new ArrayList<Data>();
		
		while(endTime <= roundLengthSeconds)
		{
			array = getRoundResultsForDB( startTime,  endTime);
			Data punch = new Data();
			punch.setStartTime(startTime);
			punch.setEndTime(endTime);
			punch.setForceAverage(String.valueOf(array[0]));
			punch.setNumberOfPunches(String.valueOf(array[1]));
			punch.setRounds_id(roundID);
			
			data.add(punch);
	
			startTime += 5;
			endTime += 5;
		}
		return data;
			
	}
		
	
		
		
		

	//Calculate the number of punches
	public void collectPunches()
	{
		double punchVal;
		List<Message>messagesOverThreshold=new ArrayList<Message>(); 
		//List<Message>accel1Messages=new ArrayList<Message>(); //Accel1Messages using class to format data
		//Filter Punches
		messagesOverThreshold=filterOnlyMessagesOverThreshold(theRawDataStore.accelDataPunchBuffer);

		//Now lets look at where the distinct punches are located and add them to a List of Punches

		//Add the first read punch 
		//Make a new punch and pass in its message list 
		//Only add of maybeAPunch is not null as we could have data from a previous read
		if (maybeAPunch==null && messagesOverThreshold.size() != 0)
		{
			maybeAPunch=new Punch(messagesOverThreshold.get(0));
			messagesOverThreshold.remove(0); //Don't need that message anymore as we already added it
		}
		//		
		//Make sure this is not the first read and there is messages over a threshold.
		while(messagesOverThreshold.size() != 0)
		{			
			Message checkmes = messagesOverThreshold.get(0); 
			messagesOverThreshold.remove(0); //Remove last punch from list
			//For this function to get here maybe a punch must not be null remember this
			
			//Add the next punch if possible
			//Also make sure that the checkMessage punch is not within our minimum if it is throw it away by not touching it
			if (maybeAPunch == null || !(checkmes.getGeneratedID()-lastMessageID > minTicksBetweenPunch)) { 
				maybeAPunch=new Punch(checkmes); 
				continue; 
			}

			
			//Check if this punch fits in our maximum "Gap" with the last punch
			if ( (checkmes.getGeneratedID()-maybeAPunch.getLastMessage().getGeneratedID()) <= maxTicksBetweenPunches)
			{
				//We are within our MaxTicks between punches 
				maybeAPunch.AddMessage(checkmes); //Add this to the end of our last punch as it fits the trend
			}

			else 
			{	
				//We were not inside our max ticks between punches therefore we need to check to see if the last Punch qualifies
				//Also check to be sure that at least 122 ms has occurred between the last punch
				//Qualify the punches array
				if (maybeAPunch.accelerationsForPunchSize() >= minMessagesPerPunch)
				{
					//We have verified the last Punch therefore leave it in our Punches list and add the next message to the list
					lastMessageID=maybeAPunch.lastMessage.getGeneratedID();
					allPunches.add(maybeAPunch);
					
					//TODO deletet m im for testing
					System.out.println("Verified last punch at "+maybeAPunch.FirstMessage.getGeneratedID()+" Last ID in list: "+theRawDataStore.accelData.get(theRawDataStore.accelData.size()-1).getGeneratedID());
					
					maybeAPunch = null; 

					
					
					//System.out.println(allPunches.get(allPunches.size()-1).getForce());
					//System.out.println(allPunches.get(allPunches.size()-1).getID());
				}
				else 
				{
					//Throw away the punch as it didn't qualify and start again
					//Add the last punch to the message to check it against what we have
					maybeAPunch=null;
					maybeAPunch=new Punch(checkmes); //Add the last punch from this list to the new punch
				}
			}

		}


	}


}
