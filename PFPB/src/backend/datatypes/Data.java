package backend.datatypes;


public class Data{
	private int rounds_id;
	private String forceAverage;
	private String numberOfPunches;
	private int startTime; //THIS IS IN SECONDS
	private int endTime; //THIS IS IN SECONDS
	
	//ADD GETTERS AND SETTERS T
	
	
	public String generateAddDataSQL()
	{
		String sql;
		sql = "INSERT INTO Data ('Rounds_id','Force_Average','Number_Of_Punches','startTime','endTime') VALUES ("+
		"'"+this.getRounds_id()+"',"+
		"'"+this.getForceAverage()+"',"+
		"'"+this.getNumberOfPunches()+"',"+
		"'"+this.getStartTime()+"',"+
		"'"+this.getEndTime()+"')";
		return sql;	
	}

	public int getRounds_id() {
		return rounds_id;
	}

	public void setRounds_id(int rounds_id) {
		this.rounds_id = rounds_id;
	}

	public String getForceAverage() {
		return forceAverage;
	}

	public void setForceAverage(String forceAverage) {
		this.forceAverage = forceAverage;
	}

	public String getNumberOfPunches() {
		return numberOfPunches;
	}

	public void setNumberOfPunches(String numberOfPunches) {
		this.numberOfPunches = numberOfPunches;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
}
