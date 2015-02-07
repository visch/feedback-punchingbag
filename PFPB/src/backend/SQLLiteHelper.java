package backend;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import backend.datatypes.*;
public class SQLLiteHelper {
	Connection c = null;
	Statement stmp = null;
	String pathToDb = "data/db/pfpb.db";

	public SQLLiteHelper()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+pathToDb);
			c.setAutoCommit(false);
			stmp = c.createStatement(); 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public ResultSet Select(String sql) throws SQLException
	{
		return stmp.executeQuery(sql);
	}

	//Will insert record for you : NOTE THIS IS very slow (about 10 of these per second max depending on the hard drive) Use Insert multiplle records when possible
	public void Insert(String sql) throws SQLException
	{
		stmp.executeUpdate(sql);
		c.commit();
		return;
	}

	public void InsertMultipleRecords(List<String> sqlInsertStatements) throws Exception
	{
		int numberOfAdditions=sqlInsertStatements.size();

		//Be sure that our statement Has some data in it
		if (sqlInsertStatements.size()==0)
		{
			throw new Exception("SQL Statement does not have any data inside of it");
		}


		while (sqlInsertStatements.size()!=0)
		{
			//Add all SQL Statements to our Transaction
			stmp.executeUpdate(sqlInsertStatements.get(0));
			sqlInsertStatements.remove(0);
		}
		c.commit(); //Place all statements out onto the hard disk

		//TODO Use Logger Class
		System.out.println("Wrote " + numberOfAdditions + " to database!");
		return;
	}
	

	public HashMap<Integer, User> grabUsers() throws SQLException
	{
		String sql = "SELECT * from Users ORDER BY id";
		ResultSet usernames = Select(sql);
		HashMap<Integer, User> users = new HashMap<Integer, User>();
		while (usernames.next())
		{
			double[] avgForceFreq=new double[2];
			User tempUser = new User();
			tempUser.setId(usernames.getInt("id"));
			tempUser.setName(usernames.getString("Name"));
			tempUser.setAge(usernames.getString("Age"));
			tempUser.setWeight(usernames.getString("Weight"));
			tempUser.setArmSpan(usernames.getString("Arm_Span"));
			tempUser.setHeight(usernames.getString("Height"));
			tempUser.setUserThoughtLevel(usernames.getString("userThought")); //User Thought
			tempUser.setMinGoalForce(usernames.getString("MinGoalForce"));
			tempUser.setMaxGoalForce(usernames.getString("MaxGoalForce"));
			tempUser.setMinGoalFrequency(usernames.getString("MinGoalFrequency"));
			tempUser.setMaxGoalFrequency(usernames.getString("MaxGoalFrequency"));
			String[] music = usernames.getString("Music_Paths").split(",");
			for (String songPath : music) {
				tempUser.addMusic(songPath);
			}
			SQLLiteHelper objectForMe = new SQLLiteHelper();
			avgForceFreq=objectForMe.grabAverageForceHighandLowFromLastSessionsRounds(tempUser.getId());
			objectForMe.Close();
			tempUser.setAverageForceFromLastSessionRounds(avgForceFreq[0]);
			tempUser.setAverageFrequencyFromLastSessionRounds(avgForceFreq[1]);
			users.put(tempUser.getId(), tempUser); //Put our user in the ID location for this map
			
		}

		return users; 
	}
	
	public HashMap<Integer, Workout> grabWorkouts() throws SQLException
	{
		String sql = "SELECT * from Workouts";
		ResultSet workout = Select(sql);
		HashMap<Integer, Workout> workouts = new HashMap<Integer, Workout>();

		while(workout.next())
		{
			Workout tempWorkout = new Workout();
			tempWorkout.setActionCutOff_Force(workout.getString("ActionCutOff_Force"));
			tempWorkout.setActionCutOff_Frequency(workout.getString("ActionCutOff_Frequency"));
			tempWorkout.setBreakTime(workout.getString("BreakTime"));
			tempWorkout.setId(workout.getInt("id"));
			tempWorkout.setName(workout.getString("Name"));
			tempWorkout.setNumberOfRounds(workout.getString("NumberOfRounds"));
			tempWorkout.setRoundTime(workout.getString("RoundTime"));
			tempWorkout.setVisibility_CurrentForce(workout.getString("Visibility_CurrentForce"));
			tempWorkout.setVisibility_CurrentFrequency(workout.getString("Visibility_CurrentFrequency"));
			tempWorkout.setVisibility_GoalForce(workout.getString("Visibility_GoalForce"));
			tempWorkout.setVisibility_GoalFrequency(workout.getString("Visibility_GoalFrequency"));
			tempWorkout.setVisibility_Timer(workout.getString("Visibility_Timer"));

			workouts.put(tempWorkout.getId(), tempWorkout);
		}

		return workouts;
	}
	
	
	
	public void writeNewWorkout(Workout newWorkout) throws Exception
	{
		
		Insert(newWorkout.generateAddWorkoutSQL());
		System.out.println("Added Workout Object to Database with Name: "+newWorkout.getName());
	}
	
	public Session writeNewSession(Session newSession) throws Exception
	{
		Insert(newSession.generateAddSessionSQL());
		ResultSet findSessionID=this.Select("SELECT seq from sqlite_sequence where name=\"Sessions\"");
		newSession.setId(findSessionID.getInt("seq")); //Give the sesion object an ID
		System.out.println("Added Session Object to Database with Workouts_id: "+newSession.getWorkouts_id() + " and User_ID: "+newSession.getUsers_id());
		return newSession; 
	}
	
	public Round writeNewRound(Round newRound) throws Exception
	{
	Insert(newRound.generateAddRoundSQL());
	ResultSet findSessionID=this.Select("SELECT seq from sqlite_sequence where name=\"Rounds\"");
	newRound.setId(findSessionID.getInt("seq")); //Give the sesion object an ID
	System.out.println("Added Round object to database with Session_id: "+newRound.getSessions_id());
	return newRound; 
	}
	
	public void writeDataPoints(List<Data> dataPoints) throws Exception
	{
	List<String> multipleInserts= new ArrayList<String>();
		while(dataPoints.size()!=0)
		{
			multipleInserts.add(dataPoints.get(0).generateAddDataSQL());
			dataPoints.remove(0);
		}
		InsertMultipleRecords(multipleInserts);
	}
	
	//
	public void writeUser(User updateOrAddUser) throws Exception
	{
		//Write a new user if the ID is set to -1
		if (updateOrAddUser.getId()==-1)
		{
			writeNewUser(updateOrAddUser);
		}
		
		else 
		{
			updateUser(updateOrAddUser);
		}
	}
	
	
	//****************************************************
	//Queries written for Various Tasks
	//****************************************************
	
	private double[]grabAverageForceHighandLowFromLastSessionRounds(int userId, int sessionId) throws SQLException
	{
		String sql="SELECT  " +
				"R.Average_Force, " +
				"R.Average_Frequency " +
				"FROM Sessions S " +
				"JOIN Rounds R ON " +
				"R.Sessions_id=S.id " +
				"where " +
				"S.id ="+sessionId+" AND "+
				"S.Users_id="+userId;
		
		return grabAverageForceHighAndLowWorker(sql);
		
	}
	
	public double[] grabAverageForceHighandLowFromLastSessionsRounds(int userId) throws SQLException
	{
		String sql="SELECT  " +
				"R.Average_Force, " +
				"R.Average_Frequency " +
				"FROM Rounds R " +
				" where " +
				"R.Sessions_id = ( SELECT  MAX(S.id)" +
				" FROM Sessions S " +
				"JOIN Users U ON " +
				"U.id = S.Users_id" +
				" where U.id ="+userId+")";
		return grabAverageForceHighAndLowWorker(sql);
	}
	
	private double[] grabAverageForceHighAndLowWorker(String sql) throws SQLException
	{
		ResultSet forceFreqFromRounds = this.Select(sql);
		
		List<Double>forceFromRounds=new ArrayList<Double>();
		List<Double>freqFromRounds=new ArrayList<Double>();
		double totalForce=0.0; 
		double totalFreq=0.0;
		double averageForce,averageFreq;
		double[] forceFreq= new double[2];
		
		while (forceFreqFromRounds.next())
		{
			forceFromRounds.add(Double.parseDouble(forceFreqFromRounds.getString("Average_Force")));
			freqFromRounds.add(Double.parseDouble(forceFreqFromRounds.getString("Average_Frequency")));
		}
		
		for (Double db : forceFromRounds) {
			totalForce+=db;
		}
		
		for (Double db : freqFromRounds) {
			totalFreq+=db;
		}
		
		averageForce=totalForce/forceFromRounds.size();
		averageFreq=totalFreq/freqFromRounds.size();
		
		
		forceFreq[0]=averageForce;
		forceFreq[1]=averageFreq;
		
		
		return forceFreq; 
	
	}
	//****************
	//End Custom SQL Stuff
	//******************
	
	
	//Exporting Data Form
	//All of these functions include a header line, just write all of this stuff to your file with whatever delim you want
	
	public List<String>allSessionsExport() throws SQLException
	{
		return allSessionsExportData(-1, -1); //Send the function an ID of -1 for no workout ID
	}
	public List<String>allSessionsExportByWorkoutID(int workoutID) throws SQLException
	{
		return allSessionsExportData(workoutID, -1);
	}
	public List<String>allSessionsExportByUserID(int userID) throws SQLException
	{
		return allSessionsExportData(-1, userID);
	}
	
	private List<String>allSessionsExportData(int workoutID, int userID) throws SQLException
	{
		List<String>exportSession=new ArrayList<String>();
		SQLLiteHelper objectForMe = new SQLLiteHelper(); //Used to do a query while a result set is still open
		String sql="Select U.Name UserName, " +
				"U.id UserID," +
				"S.id SessionID, " +
				"S.Workouts_id WorkoutsID, "+
				"W.Name WorkoutName, " +
				"S.Date_Started, " +
				"S.Force_CutOff_High, " +
				"S.Force_CutOff_Low, " +
				"S.Frequency_CutOff_High, " +
				"S.Frequency_CutOff_Low " +
				"from Users U " +
				"JOIN Sessions S " +
				"ON S.Users_id = U.id " +
				"JOIN Workouts W ON" +
				" S.Workouts_id = W.id ";
			if (workoutID!=-1)
			{
				sql+="where S.Workouts_id="+workoutID+" ";
			}
			if (userID != -1)
			{
				sql+="where S.Users_id="+userID+" ";
			}
			sql+="ORDER BY S.id";
		ResultSet allSessionsExportData = this.Select(sql);
		String delim=","; //Delimter
		String outputLine="User Name, WorkoutsID, SessionID, Workout Name, Session Force, Session Frequency, High Force Cut-Off, Low Force Cut-off," +
				"High Punch Frequency Cut-Off, Low Punch Frequency Cut-Off\r\n";
		
		exportSession.add(outputLine); //Add header info
		outputLine="";
		double[] avgForceavgFreq = new double[2];
		while (allSessionsExportData.next())
		{
			outputLine+=allSessionsExportData.getString("UserName")+delim;
			outputLine+=allSessionsExportData.getString("WorkoutsID")+delim;
			outputLine+=allSessionsExportData.getString("SessionID")+delim;
			outputLine+=allSessionsExportData.getString("WorkoutName")+delim;
			
			//This line will slow this function down considerably as it goes out to the db for ever single row 
			
			avgForceavgFreq=objectForMe.grabAverageForceHighandLowFromLastSessionRounds(allSessionsExportData.getInt("UserID"), allSessionsExportData.getInt("SessionID"));
			
			outputLine+=avgForceavgFreq[0]+delim;
			outputLine+=avgForceavgFreq[1]+delim;
			
			outputLine+=allSessionsExportData.getString("Force_CutOff_High")+delim;
			outputLine+=allSessionsExportData.getString("Force_CutOff_Low")+delim;
			outputLine+=allSessionsExportData.getString("Frequency_CutOff_High")+delim;
			outputLine+=allSessionsExportData.getString("Frequency_CutOff_Low");
			outputLine+="\r\n";
			exportSession.add(outputLine);
			outputLine=""; //Reset outputLine
			avgForceavgFreq[0]=0; //Set avgForceFreq to 0
			avgForceavgFreq[1]=0;
		}
		objectForMe.Close(); //Close our connection that was made to the other SQLLite object
		return exportSession; 
		
	}
	
	
	
	
	public List<String>allRoundsExport() throws SQLException
	{
		return allRoundsExportData(-1, -1); //Send the function an ID of -1 for no workout ID
	}
	public List<String>allRoundsExportByWorkoutID(int workoutID) throws SQLException
	{
		return allRoundsExportData(workoutID, -1);
	}
	public List<String>allRoundsExportByUserID(int userID) throws SQLException
	{
		return allRoundsExportData(-1, userID);
	}
	private List<String>allRoundsExportData(int workoutID, int userID) throws SQLException
	{
		List<String>exportRounds=new ArrayList<String>();
		String sql="SELECT " +
				"U.Name UserName," +
				"S.Workouts_id WorkoutsID, "+
				"R.id RoundID, " +
				"S.id SessionID, " +
				"R.Average_Force, " +
				"R.Average_Frequency, " +
				"S.Force_CutOff_High, " +
				"S.Force_CutOff_Low, " +
				"S.Frequency_CutOff_High, " +
				"S.Frequency_CutOff_Low, " +
				"S.Date_Started " +
				"from  Sessions S " +
				"JOIN Rounds R on " +
				"R.Sessions_id = S.id " +
				"JOIN Users U " +
				"on U.id = S.Users_id ";
				if (workoutID!=-1)
				{
					sql+="where S.Workouts_id="+workoutID+" ";
				}
				if (userID != -1)
				{
					sql+="where S.Users_id="+userID+" ";
				}
				sql+="ORDER BY R.id";
				
		ResultSet allRoundsExportData = this.Select(sql);
		
		String delim=","; //Delimter
		String outputLine="User Name, WorkoutID, RoundID, SessionID, RoundAverageForce, RoundAverageFrequency, High Force Cut-Off, Low Force Cut-off," +
				"High Punch Frequency Cut-Off, Low Punch Frequency Cut-Off\r\n";
		exportRounds.add(outputLine); //Add header info
		
		while (allRoundsExportData.next())
		{
			outputLine="";
			outputLine+=allRoundsExportData.getString("UserName")+delim;
			outputLine+=allRoundsExportData.getString("WorkoutsID")+delim;
			outputLine+=allRoundsExportData.getString("RoundID")+delim;
			outputLine+=allRoundsExportData.getString("SessionID")+delim;
			outputLine+=allRoundsExportData.getString("Average_Force")+delim;
			outputLine+=allRoundsExportData.getString("Average_Frequency")+delim;
			outputLine+=allRoundsExportData.getString("Force_CutOff_High")+delim;
			outputLine+=allRoundsExportData.getString("Force_CutOff_Low")+delim;
			outputLine+=allRoundsExportData.getString("Frequency_CutOff_High")+delim;
			outputLine+=allRoundsExportData.getString("Frequency_CutOff_Low")+delim;
			outputLine+="\r\n";
			
			exportRounds.add(outputLine);
		}
		
		return exportRounds;
	}
	
	
	//No filtering by workout
	public List<String>allDataExport() throws SQLException
	{
		return allDataExport(-1, -1); //Send the function an ID of -1 for no workout ID
	}
	public List<String>allDataExportByWorkoutID(int workoutID) throws SQLException
	{
		return allDataExport(workoutID, -1);
	}
	public List<String>allDataExportByUserID(int userID) throws SQLException
	{
		return allDataExport(-1, userID);
	}
	
	private List<String>allDataExport(int workoutID, int userID) throws SQLException
	{
		List<String>exportData=new ArrayList<String>();
		String sql="SELECT " +
				"U.Name UserName, " +
				"D.id DataID, " +
				"S.Workouts_id WorkoutsID, "+
				"R.id RoundID, " +
				"S.id SessionID, " +
				"D.Force_Average DataForceAverage, " +
				"D.Number_Of_Punches NumberOfPunches, " +
				"D.startTime PunchesStartTime, " +
				"D.endTime PunchesEndTime " +
				"from Data D  " +
				"join Rounds R ON " +
				"R.id = D.Rounds_id " +
				"join Sessions S ON " +
				"S.id = R.Sessions_id " +
				"join Users U ON " +
				"U.id = S.Users_id ";
				if (workoutID!=-1)
				{
					sql+="where S.Workouts_id="+workoutID+" ";
				}
				if (userID != -1)
				{
					sql+="where S.Users_id="+userID+" ";
				}
				
				sql+="ORDER BY D.id";
		ResultSet allDataExport = this.Select(sql);
		
		String delim=","; //Delimter
		String outputLine="User Name, WorkoutsID, DataID, RoundID, SessionID, DataForceAverage, NumberOfPunches, PunchesStartTime, PunchesEndTime\r\n";
		exportData.add(outputLine); //Add header info
		
		while (allDataExport.next())
		{
			outputLine="";
			outputLine+=allDataExport.getString("UserName")+delim;
			outputLine+=allDataExport.getString("WorkoutsID")+delim;
			outputLine+=allDataExport.getString("DataID")+delim;
			outputLine+=allDataExport.getString("RoundID")+delim;
			outputLine+=allDataExport.getString("SessionID")+delim;
			outputLine+=allDataExport.getString("DataForceAverage")+delim;
			outputLine+=allDataExport.getString("NumberOfPunches")+delim;
			outputLine+=allDataExport.getString("PunchesStartTime")+delim;
			outputLine+=allDataExport.getString("PunchesEndTime");
			outputLine+="\r\n";
			
			exportData.add(outputLine);
		}
		
		return exportData;
	}
	
	
	public List<String>exportUsers() throws SQLException
	{
		List<String>exportData=new ArrayList<String>();
		
		String delim=","; //Delimter
		String outputLine="UserID, UserName, Age, Weight, ArmSpan, Height, UserThoughtLevel\r\n";
		exportData.add(outputLine); //Add header info
		
		for (User value : grabUsers().values())
			{
			outputLine="";
			outputLine+=value.getId()+delim;
			outputLine+=value.getName()+delim;
			outputLine+=value.getAge()+delim;
			outputLine+=value.getWeight()+delim;
			outputLine+=value.getArmSpan()+delim;
			outputLine+=value.getHeight()+delim;
			outputLine+=value.getUserThoughtLevel();
			outputLine+="\r\n";
			
			exportData.add(outputLine);
			}
		return exportData;
	}
	
	public List<String>exportWorkouts() throws SQLException
	{
		List<String>exportData=new ArrayList<String>();
		
		String delim=","; //Delimter
		String outputLine="WorkoutID, WorkoutName, ActionOnCutOff_Force, ActionOnCutOff_Frequency, NumberOfRounds, RoundTime, BreakTime, Visibility_Currentforce," +
				"Visibility_GoalForce, Visibility_CurrentFrequency, Visibility_GoalFrequency, Visibility_Timer\r\n";
		exportData.add(outputLine); //Add header info
		
		for (Workout value : grabWorkouts().values())
			{
			outputLine="";
			outputLine+=value.getId()+delim;
			outputLine+=value.getName()+delim;
			outputLine+=value.getActionCutOff_Force()+delim;
			outputLine+=value.getActionCutOff_Frequency()+delim;
			outputLine+=value.getNumberOfRounds()+delim;
			outputLine+=value.getRoundTime()+delim;
			outputLine+=value.getBreakTime()+delim;
			outputLine+=value.getVisibility_CurrentForce()+delim;
			outputLine+=value.getVisibility_GoalForce()+delim;
			outputLine+=value.getVisibility_CurrentFrequency()+delim;
			outputLine+=value.getVisibility_GoalFrequency()+delim;
			outputLine+=value.getVisibility_Timer();
			outputLine+="\r\n";
			
			exportData.add(outputLine);
			}
		return exportData;
	}
	
	
	
	private void writeNewUser(User userObjectMan) throws Exception
	{
		
		Insert(userObjectMan.generateNewUserSQL());
		ResultSet findUserId=this.Select("SELECT seq from sqlite_sequence where name=\"Users\"");
		userObjectMan.setId(findUserId.getInt("seq")); //Give the user object an ID
		System.out.println("Added User Object to Database with ID: "+userObjectMan.getId()+" Name: "+ userObjectMan.getName());
	}

	
	private void updateUser(User updateUser) throws SQLException
	{
		Insert(updateUser.generateUserUpdateSQL()); 
		System.out.println("Updated User Object to Database with ID: "+updateUser.getId()+" Name: "+ updateUser.getName()); 
	}
		
	
	public void Close() throws SQLException
	{
		stmp.close();
		c.close();
	}
}

