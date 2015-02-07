package backend.datatypes;


public class Round{
	private int id; 
	private int sessions_id;
	private String average_Force;
	private String average_Frequency;
	
	//ADD GETTERS AND SETTERS T
	public int getSessions_id() {
		return sessions_id;
	}

	public void setSessions_id(int sessions_id) {
		this.sessions_id = sessions_id;
	}

	public String getAverage_Force() {
		return average_Force;
	}

	public void setAverage_Force(String average_Force) {
		this.average_Force = average_Force;
	}

	public String getAverage_Frequency() {
		return average_Frequency;
	}

	public void setAverage_Frequency(String average_Frequency) {
		this.average_Frequency = average_Frequency;
	}
	
	public String generateAddRoundSQL()
	{
		String sql;
		sql = "INSERT INTO Rounds ('Sessions_id','Average_Force','Average_Frequency') VALUES ("+
		"'"+this.getSessions_id()+"',"+
		"'"+this.getAverage_Force()+"',"+
		"'"+this.getAverage_Frequency()+"')";
		return sql;	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
