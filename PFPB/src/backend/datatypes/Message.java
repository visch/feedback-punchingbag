package backend.datatypes;

public class Message {
	int fromHardWareID;
	double generatedID;
	Double accelValue=0.0;
	public long sysTime;
	
	public Message(int i, double genID, Double aValue)
	{
		fromHardWareID=i;
		generatedID=genID;
		
		accelValue=aValue;
		sysTime=System.currentTimeMillis(); 
	}
	
	public int getFromHardWareID() {
		return fromHardWareID;
	}

	public void setFromHardWareID(int fromHardWareID) {
		this.fromHardWareID = fromHardWareID;
	}

	//returns given Software ID
	public double getGeneratedID() {
		return generatedID;
	}

	public void setGeneratedID(double generatedID) {
		this.generatedID = generatedID;
	}

	public double getAccelValue() {
		return accelValue;
	}
	
	
	public void setAccelValue(double accelValue){
		this.accelValue = accelValue;
	}

	

}
