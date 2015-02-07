package backend.datatypes;

//TODO: Workout's ID needs to be something SQL should generate,
//or it should be handled in the background. GUI level will not
//know what ID should be next.
public class Workout {
	private int id;
	private String Name;
	private String ActionCutOff_Force;
	private String ActionCutOff_Frequency;
	private String NumberOfRounds;
	private String RoundTime;
	private String BreakTime;
	private String Visibility_CurrentForce;
	private String Visibility_GoalForce;
	private String Visibility_CurrentFrequency;
	private String Visibility_GoalFrequency; 
	private String Visibility_Timer;
	
	//Workout Defaults
	public Workout() {
//		this.Name = "Workout Name Here";
//		this.ActionCutOff_Force = "Both";
//		this.ActionCutOff_Frequency = "Both";
//		this.NumberOfRounds = "10";
//		this.RoundTime = "15";
//		this.BreakTime = "3";
//		this.Visibility_CurrentForce = "TRUE";
//		this.Visibility_GoalForce = "FALSE";
//		this.Visibility_CurrentFrequency = "TRUE";
//		this.Visibility_GoalFrequency = "FALSE";
//		this.Visibility_Timer = "TRUE";
	}
	
	public Workout(String name, String forceAction, String freqCutoff, String NumRnds, String rndTime, String brTime, String visCurrForce, String visGoalForce, String visCurrFreq, String visGoalFreq, String visTimer) {
		this.Name = name;
		this.ActionCutOff_Force = forceAction;
		this.ActionCutOff_Frequency = freqCutoff;
		this.NumberOfRounds = NumRnds;
		this.RoundTime = rndTime;
		this.BreakTime = brTime;
		this.Visibility_CurrentForce = visCurrForce;
		this.Visibility_GoalForce = visGoalForce;
		this.Visibility_CurrentFrequency = visCurrFreq;
		this.Visibility_GoalFrequency = visGoalFreq;
		this.Visibility_Timer = visTimer;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getActionCutOff_Force() {
		return ActionCutOff_Force;
	}
	public void setActionCutOff_Force(String actionCutOff_Force) {
		ActionCutOff_Force = actionCutOff_Force;
	}
	public String getActionCutOff_Frequency() {
		return ActionCutOff_Frequency;
	}
	public void setActionCutOff_Frequency(String actionCutOff_Frequency) {
		ActionCutOff_Frequency = actionCutOff_Frequency;
	}
	public String getNumberOfRounds() {
		return NumberOfRounds;
	}
	public void setNumberOfRounds(String numberOfRounds) {
		NumberOfRounds = numberOfRounds;
	}
	public String getRoundTime() {
		return RoundTime;
	}
	public void setRoundTime(String roundTime) {
		RoundTime = roundTime;
	}
	public String getBreakTime() {
		return BreakTime;
	}
	public void setBreakTime(String breakTime) {
		BreakTime = breakTime;
	}
	public String getVisibility_CurrentForce() {
		return Visibility_CurrentForce;
	}
	public void setVisibility_CurrentForce(String visibility_CurrentForce) {
		Visibility_CurrentForce = visibility_CurrentForce;
	}
	public String getVisibility_GoalForce() {
		return Visibility_GoalForce;
	}
	public void setVisibility_GoalForce(String visibility_GoalForce) {
		Visibility_GoalForce = visibility_GoalForce;
	}
	public String getVisibility_CurrentFrequency() {
		return Visibility_CurrentFrequency;
	}
	public void setVisibility_CurrentFrequency(String visibility_CurrentFrequency) {
		Visibility_CurrentFrequency = visibility_CurrentFrequency;
	}
	public String getVisibility_GoalFrequency() {
		return Visibility_GoalFrequency;
	}
	public void setVisibility_GoalFrequency(String visibility_GoalFrequency) {
		Visibility_GoalFrequency = visibility_GoalFrequency;
	}
	public String getVisibility_Timer() {
		return Visibility_Timer;
	}
	public void setVisibility_Timer(String visibility_SessionHighest) {
		Visibility_Timer = visibility_SessionHighest;
	}
	
	public String generateAddWorkoutSQL()
	{
		String workoutSQL;
		workoutSQL = "INSERT INTO Workouts ('Name','ActionCutOff_Force','ActionCutOff_Frequency','NumberOfRounds'," +
				"'RoundTime','BreakTime','Visibility_CurrentForce','Visibility_GoalForce','Visibility_CurrentFrequency'," +
				"'Visibility_GoalFrequency','Visibility_Timer') VALUES ("+
		"'"+this.getName()+"',"+
		"'"+this.getActionCutOff_Force()+"',"+
		"'"+this.getActionCutOff_Frequency()+"',"+
		"'"+this.getNumberOfRounds()+"',"+
		"'"+this.getRoundTime()+"',"+
		"'"+this.getBreakTime()+"',"+
		"'"+this.getVisibility_CurrentForce()+"',"+
		"'"+this.getVisibility_GoalForce()+"',"+
		"'"+this.getVisibility_CurrentFrequency()+"',"+
		"'"+this.getVisibility_GoalFrequency()+"',"+
		"'"+this.getVisibility_Timer()+"')";
		return workoutSQL;	
	}
	
}
