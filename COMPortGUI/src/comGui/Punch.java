package comGui;

import java.util.ArrayList;
import java.util.List;
 
public class Punch {
	private List<Message> accelerationsForPunch = new ArrayList<Message>();
	Message lastMessage = null;	

	public Punch(Message message)
	{
		AddPunch(message);
		
	}
	
	public void AddPunch(Message message)
	{
		accelerationsForPunch.add(message); 
		lastMessage = message;
	}
	
	int accelerationsForPunchSize()
	{
		return accelerationsForPunch.size();
	}
	
	public Message getLastMessage() {
		return lastMessage;
	}
	
	
	public double findMaxAccel()
	{
		double max = 0;
		for(int i = 0; i < accelerationsForPunchSize(); i++)
		{
			if(max < accelerationsForPunch.get(i).accelValue)	//get acceleration from list index
			{
				max = accelerationsForPunch.get(i).accelValue;
			}
		}
		return max;
		
	}
	
	//TODO: create function to 
	
}
