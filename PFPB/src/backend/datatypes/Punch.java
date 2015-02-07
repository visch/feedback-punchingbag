package backend.datatypes;

import java.util.ArrayList;
import java.util.List;

 
public class Punch {
	private List<Message> messagesForPunch = new ArrayList<Message>();
	Message lastMessage = null;
	Message FirstMessage = null;	
	
	double accel;
	double ID;
	final double massOfBag = 45.4; //kg


	public Message getFirstMessage() {
		return FirstMessage;
	}


	public Punch(Message message)
	{
		AddMessage(message);
		findMaxAccel(); //Find the max accel TODO we don't need this call
		FirstMessage = message;
	}
	
	public void AddMessage(Message message)
	{
		messagesForPunch.add(message); 
		lastMessage = message;
		findMaxAccel();
	}
	
	int accelerationsForPunchSize()
	{
		return messagesForPunch.size();
	}
	
	public Message getLastMessage() {
		return lastMessage;
	}
	
	public void findMaxAccel()
	{
		
		for(int i = 0; i < messagesForPunch.size(); i++)
		{
			if(accel < messagesForPunch.get(i).accelValue)	//get acceleration from list index
			{
				accel = messagesForPunch.get(i).accelValue;
				ID = messagesForPunch.get(i).getGeneratedID();
			}
		}
		
	}
	
	public double getForce()
	{

		return  massOfBag * (accel * 9.8); //Convert g's to m/s^2 multiply by mass


	}
	
	public double getID()
	{
		return ID;
	}
	

	
}
