package comGui;

public class Message {
	int fromHardWareID;
	double generatedID;
	double accelValue=0;
	
	public Message(int i, double genID, double aValue)
	{
		fromHardWareID=i;
		generatedID=genID;
		accelValue=aValue;
	}
	
	public int getFromHardWareID() {
		return fromHardWareID;
	}

	public void setFromHardWareID(int fromHardWareID) {
		this.fromHardWareID = fromHardWareID;
	}

	public double getGeneratedID() {
		return generatedID;
	}

	public void setGeneratedID(double generatedID) {
		this.generatedID = generatedID;
	}

	public double getAccelValue() {
		return accelValue;
	}

	public void setAccelValue(double accelValue) {
		this.accelValue = accelValue;
	}

}
