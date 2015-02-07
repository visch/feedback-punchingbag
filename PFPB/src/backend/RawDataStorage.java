package backend;

//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import backend.datatypes.*;

public class RawDataStorage {
	//Constants
	List<String> leftOverMessages=new LinkedList<String>(); //For our left over messages
	double scalar = (24 / ((Math.pow(2, 16) / 2) - 1));
	
	public List<Message> accelData = Collections.synchronizedList(new ArrayList<Message>()); //TODO Dmake this not public
	public List<Message> accelDataPunchBuffer = Collections.synchronizedList(new ArrayList<Message>());
	
	public double softwareID=0;	//Correlates to hardwareID; negates rollover
	int lastID = 0;

	//TODO: Possibly need checking of accelNums/and hardwareIDs here
	public void addMessage(String[] accelNum, String[] messID, int[] xVal, int[] yVal, int[] zVal) throws Exception
	{		
		double accelVal1 = 0;
		double accelVal2 = 0;
		double accelVal = 0;
		
		
		accelVal1 = Math.sqrt(((Math.pow(xVal[0], 2)) + (Math.pow(yVal[0], 2)) + (Math.pow(zVal[0], 2))));
		accelVal2 = Math.sqrt(((Math.pow(xVal[1], 2)) + (Math.pow(yVal[1], 2)) + (Math.pow(zVal[1], 2))));
		accelVal = ((accelVal1+accelVal2)/2)*scalar;
		
		//TODO: check right software ID is being generated correctly
		
		if(softwareID == 0) //check if first message
		{
			softwareID = 1;//first message is 1
		}
		else if((Integer.parseInt(messID[0], 16) - lastID) > 0) 		//skip happened
		{
			softwareID += (Integer.parseInt(messID[0], 16) - lastID);
		}
		else if((Integer.parseInt(messID[0], 16) - lastID) < 0)			//rollover occurred (must also check for skips)
		{
			if((Integer.parseInt(messID[0], 16) - lastID) < -65000)
			{
				//softwareID = [number of messages until rollover (max-lastID)] + [messages after rollover] +[inclusive count of 0]
				softwareID += (65535 - lastID) + Integer.parseInt(messID[0], 16) +1 ;
			}
			else
			{
				accelData.remove(accelData.size() - 1);
				accelDataPunchBuffer.remove(accelDataPunchBuffer.size() - 1);
				softwareID += (Integer.parseInt(messID[0], 16) - lastID);
			}
			
		}
		
		
		
		//set lastID to current ID; add  new message
		lastID = Integer.parseInt(messID[0], 16);
		accelData.add(new Message(Integer.parseInt(messID[0], 16), softwareID, accelVal));
		accelDataPunchBuffer.add(accelData.get(accelData.size()-1)); //Add the latest added message to the punch buffer;
	
	}

	//TODO: Function for writing data to database


	//Function for outputting all accelerometer data to a string with \r\n
//	public void outputAccelData() throws FileNotFoundException, UnsupportedEncodingException {
//		StringBuilder formatedData1 = new StringBuilder();
//
//
//		for (int i=0;i<accelData.size();i++) {
//			formatedData1.append(accelData.get(i).getFromHardWareID());
//			formatedData1.append("\t");
//			formatedData1.append(accelData.get(i).getGeneratedID());
//			formatedData1.append("\t");
//			formatedData1.append(accelData.get(i).getAccelValue());
//			formatedData1.append("\t");
//			formatedData1.append(accelData.get(i).sysTime); 
//			formatedData1.append("\r\n");
//		}
//		
//		PrintWriter accelWriter = new PrintWriter("bin/AllaccelData.txt", "UTF-8");
//
//		accelWriter.print(formatedData1.toString());
//
//		accelWriter.close();
//		
//		
//	}




	public List<String> getLeftOverMessages() {
		return leftOverMessages;
	}

	public void setLeftOverMessages(List<String> leftOverMessages) {
		this.leftOverMessages = leftOverMessages;
	}
	
	
	//TODO ADDED for the comSelection testing in the test package - DVISCh CAN BE DELETED
	public double getNumOfMessages() {
		return softwareID;
	}

}
