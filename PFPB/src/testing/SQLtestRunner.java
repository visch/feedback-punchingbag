package testing;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import backend.SQLLiteHelper;
import backend.datatypes.User;
import backend.datatypes.Workout;


@SuppressWarnings("unused")
public class SQLtestRunner {
	public static void main(String args[]) throws Exception
	{
		SQLLiteHelper sqlrunner = new SQLLiteHelper();
	
		//String sqlInsert = "INSERT INTO Users (\"id\",\"Name\",\"Age\",\"Weight\",\"Arm_Span\",\"Height\",\"Music_id\") VALUES (NULL,'Test1','22','123','132','123','2')";
		 
		//sqlrunner.grabUsers()
//		List<String> multipleSql = new ArrayList<String>();
//		for (int i=0; i<10000; i++)
//		{
//			multipleSql.add(sqlInsert);
//		}
//		sqlrunner.InsertMultipleRecords(multipleSql);
		//sqlrunner.InsertMultipleRecords(multipleSql);
		//System.out.println(sqlrunner.Execute(sqlInsert));
		
		//Write user
		User writeMe = new User();
		writeMe.setName("HelloMyFriend"); 
		writeMe.setAge("22");
		writeMe.setArmSpan("48");
		writeMe.setHeight("39");
		writeMe.setWeight("195");
		writeMe.addMusic("SONGHERE.txt");
		writeMe.addMusic("ANOTHERSONG.txt");
		writeMe.setUserThoughtLevel("HAPPY ME");
		
		
		
		sqlrunner.writeUser(writeMe);
		writeMe.setName("HelloMySecondFriend"); //Do an update to the User
		sqlrunner.writeUser(writeMe);
		//Working test code for Users with music
		for (User value : sqlrunner.grabUsers().values())
		{
			System.out.println("Name:"+value.getName()+" ID: "+value.getId() + " Music: "+value.getMusic() + " Thought Level: " +value.getUserThoughtLevel());
		}
		
		
		//TESTING Write workout Commented out for myself, does work
		
//		Workout testWorkout = new Workout();
//		
//		testWorkout.setName("Test");
//		sqlrunner.writeNewWorkout(testWorkout);
//		
//		
//		//List all workouts
//		for (Workout value : sqlrunner.grabWorkouts().values())
//		{
//			System.out.println(value.getName());
//			System.out.println(value.getActionCutOff_Force());
//			System.out.println(value.getActionCutOff_Frequency());
//			System.out.println(value.getBreakTime());
//			System.out.println(value.getId());
//			System.out.println(value.getNumberOfRounds());
//			System.out.println(value.getRoundTime());
//			System.out.println(value.getVisibility_CurrentForce());
//			System.out.println(value.getVisibility_CurrentFrequency());
//			System.out.println(value.getVisibility_GoalForce());
//			System.out.println(value.getVisibility_GoalFrequency());
//			System.out.println(value.getVisibility_SessionHighest());
//		}
		
		//Test the average force frequency thing
		double[] forcefreq=sqlrunner.grabAverageForceHighandLowFromLastSessionsRounds(12);
		System.out.println("Average Force: "+forcefreq[0]+" Avg Freq:"+forcefreq[1]);

		//TODO Check the force and freq numbers
		List<String> exportData=sqlrunner.allSessionsExport();
		List<String> exportDataa=sqlrunner.allDataExport();
		List<String> exportDatad=sqlrunner.allDataExportByUserID(2);
		List<String> exportDataf=sqlrunner.allDataExportByWorkoutID(1);
		List<String> exportDatag=sqlrunner.allRoundsExport();
		List<String> exportDatadd=sqlrunner.allRoundsExportByUserID(1);
		List<String> exportDatadf=sqlrunner.allRoundsExportByWorkoutID(1);
		List<String> exportDatadg=sqlrunner.allSessionsExport();
		sqlrunner.Close(); 
	}
}
//		
//	User writeMe = new User();
//	writeMe.addMusic("SONGHERE.txt");
//	writeMe.addMusic("ANOTHERSONG.txt");
//	
//	writeMe.setName("HelloMyFriend"); 
//	sqlrunner.writeNewUser(writeMe);
//	

	

