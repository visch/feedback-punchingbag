import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class testRunner {
	public static void main(String args[]) throws Exception
	{
		SQLLiteHelper sqlrunner = new SQLLiteHelper("C:/wamp/www/pfpb.db");
	
		String sqlInsert = "INSERT INTO Users (\"id\",\"Name\",\"Age\",\"Weight\",\"Arm_Span\",\"Height\",\"Music_id\") VALUES (NULL,'Test1','22','123','132','123','2')";
		 
//		List<String> multipleSql = new ArrayList<String>();
//		for (int i=0; i<10000; i++)
//		{
//			multipleSql.add(sqlInsert);
//		}
//		sqlrunner.InsertMultipleRecords(multipleSql);
		//sqlrunner.InsertMultipleRecords(multipleSql);
		//System.out.println(sqlrunner.Execute(sqlInsert));

		
//		//Working test code for Users with music
//		for (User value : sqlrunner.grabUsers().values())
//		{
//			if (value.getMusic().size() != 0)
//			System.out.println(value.getMusic().toString());
//		}
//		sqlrunner.Close(); 
//		
	User writeMe = new User();
	writeMe.addMusic("SONGHERE.txt");
	writeMe.addMusic("ANOTHERSONG.txt");
	writeMe.setName("HelloMyFriend"); 
	sqlrunner.writeNewUser(writeMe);
	
		for (Workout value : sqlrunner.grabWorkouts().values())
		{
			System.out.println(value.getName());
			System.out.println(value.getActionCutOff_Force());
			System.out.println(value.getActionCutOff_Frequency());
			System.out.println(value.getBreakTime());
			System.out.println(value.getId());
			System.out.println(value.getNumberOfRounds());
			System.out.println(value.getRoundTime());
			System.out.println(value.getVisibility_CurrentForce());
			System.out.println(value.getVisibility_CurrentFrequency());
			System.out.println(value.getVisibility_GoalForce());
			System.out.println(value.getVisibility_GoalFrequency());
			System.out.println(value.getVisibility_SessionHighest());
		}
	}
	
}
