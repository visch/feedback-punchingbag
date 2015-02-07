package comGui;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;


public class RawDataStorage {
	//Constants
	List<String> leftOverMessages=new LinkedList<String>(); //For our left over messages
	double scalar = (24 / ((Math.pow(2, 16) / 2) - 1));
	static List<Message> accelData = new ArrayList<Message>();
	List<Message> accelDataPunchBuffer = new ArrayList<Message>();
	
	static double numOfMessages=0;

	//TODO: Possibly need checking of accelNums/and hardwareIDs here
	public void addMessage(String[] accelNum, String[] messID, int[] xVal, int[] yVal, int[] zVal) throws Exception
	{
		double xAccelAvg = 0;
		double yAccelAvg = 0;
		double zAccelAvg = 0;		
		double accelVal = 0;

		xAccelAvg = ((xVal[0]*scalar) + (xVal[1]*scalar)) / 2;
		yAccelAvg = ((yVal[0]*scalar) + (yVal[1]*scalar)) / 2;
		zAccelAvg = ((zVal[0]*scalar) + (zVal[1]*scalar)) / 2;
		
		accelVal = Math.sqrt(((Math.pow(xAccelAvg, 2)) + (Math.pow(yAccelAvg, 2)) + (Math.pow(zAccelAvg, 2))));
		
		accelData.add(new Message(Integer.parseInt(messID[0], 16), numOfMessages, accelVal));
		accelDataPunchBuffer.add(accelData.get(accelData.size()-1)); //Add the latest added message to the punch buffer;
		numOfMessages++;
	}

	//TODO: Function for writing data to database


	//Function for outputting all accelerometer data to a string with \r\n
	public static void outputAccelData() throws FileNotFoundException, UnsupportedEncodingException {
		StringBuilder formatedData1 = new StringBuilder();


		for (int i=0;i<accelData.size();i++) {
			formatedData1.append(accelData.get(i).getFromHardWareID());
			formatedData1.append("\t");
			formatedData1.append(accelData.get(i).getGeneratedID());
			formatedData1.append("\t");
			formatedData1.append(accelData.get(i).getAccelValue());
			formatedData1.append("\r\n");
		}
		
		PrintWriter accelWriter = new PrintWriter("bin/AllaccelData.txt", "UTF-8");

		accelWriter.print(formatedData1.toString());

		accelWriter.close();
	}




	public List<String> getLeftOverMessages() {
		return leftOverMessages;
	}

	public void setLeftOverMessages(List<String> leftOverMessages) {
		this.leftOverMessages = leftOverMessages;
	}

}
