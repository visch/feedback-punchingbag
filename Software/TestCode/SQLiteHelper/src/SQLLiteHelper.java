import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class SQLLiteHelper {
	Connection c = null;
	Statement stmp = null;
	
	public SQLLiteHelper(String pathToDb)
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
		String sql = "SELECT * from Users";
		ResultSet usernames = Select(sql);
		int userID;
		String path; 
		HashMap<Integer, User> users = new HashMap<Integer, User>();
		while (usernames.next())
		{
		User tempUser = new User();
		tempUser.setId(usernames.getInt("id"));
		tempUser.setName(usernames.getString("Name"));
		tempUser.setAge(usernames.getString("Age"));
		tempUser.setWeight(usernames.getString("Weight"));
		tempUser.setArmSpan(usernames.getString("Arm_Span"));
		tempUser.setHeight(usernames.getString("Height"));
		users.put(tempUser.getId(), tempUser); //Put our user in the ID location for this map
		}
		String musicSQL = "SELECT U.id," +
				"M.Path " +
				"from Users as U " +
				"JOIN Music as M " +
				"ON M.User_id=U.id";
		
		ResultSet music = Select(musicSQL);
		
		while (music.next())
		{
			userID=music.getInt("id");
			path=music.getString("Path");
			users.get(userID).addMusic(path); //Add the song to the appropriate user
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
			tempWorkout.setVisibility_SessionHighest(workout.getString("Visibility_SessionHighest"));
			
			workouts.put(tempWorkout.getId(), tempWorkout);
		}
		
		return workouts;
	}
	
	//
	public void writeNewUser(User userObjectMan) throws Exception
	{
		Insert(userObjectMan.generateUserSQL());
		
		ResultSet findUserId=this.Select("SELECT seq from sqlite_sequence where name=\"Users\"");
		userObjectMan.setId(findUserId.getInt("seq"));
		
		InsertMultipleRecords(userObjectMan.generateMusicSQL());
		
			
	}

	
	
	
	public void Close() throws SQLException
	{
	stmp.close();
	c.close();
	}
}

