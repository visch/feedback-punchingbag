package backend.datatypes;

public class Session {
	private int id; 
	private int workouts_id;
	private int users_id;
	private String forceCutOffHigh;
	private String forceCutOffLow;
	private String frequencyCutOffHigh;
	private String frequencyCutOffLow;
	
	
	//Make me getters and setters
	
	
	public String generateAddSessionSQL()
	{
	String sql;
			sql = "INSERT INTO Sessions ('Workouts_id','Users_id','Force_CutOff_High','Force_CutOff_Low'," +
				"'Frequency_CutOff_High','Frequency_CutOff_Low') VALUES ("+
		"'"+this.getWorkouts_id()+"',"+
		"'"+this.getUsers_id()+"',"+
		"'"+this.getForceCutOffHigh()+"',"+
		"'"+this.getForceCutOffLow()+"',"+
		"'"+this.getFrequencyCutOffHigh()+"',"+
		"'"+this.getFrequencyCutOffLow()+"')";
		return sql;	
	}


	public int getWorkouts_id() {
		return workouts_id;
	}


	public void setWorkouts_id(int workouts_id) {
		this.workouts_id = workouts_id;
	}


	public int getUsers_id() {
		return users_id;
	}


	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}


	public String getForceCutOffHigh() {
		return forceCutOffHigh;
	}


	public void setForceCutOffHigh(String forceCutOffHigh) {
		this.forceCutOffHigh = forceCutOffHigh;
	}


	public String getForceCutOffLow() {
		return forceCutOffLow;
	}


	public void setForceCutOffLow(String forceCutOffLow) {
		this.forceCutOffLow = forceCutOffLow;
	}


	public String getFrequencyCutOffHigh() {
		return frequencyCutOffHigh;
	}


	public void setFrequencyCutOffHigh(String frequencyCutOffHigh) {
		this.frequencyCutOffHigh = frequencyCutOffHigh;
	}


	public String getFrequencyCutOffLow() {
		return frequencyCutOffLow;
	}


	public void setFrequencyCutOffLow(String frequencyCutOffLow) {
		this.frequencyCutOffLow = frequencyCutOffLow;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	
	
}
