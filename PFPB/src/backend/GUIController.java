package backend;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.media.MediaException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import backend.datatypes.*;
import gui.*;

public class GUIController {
	/* GUI Screens */
	SQLLiteHelper dbHelperObj = new SQLLiteHelper();
	AdminScreen administrationScreen = null;
	BaselineScreen baselineScreen = null;
	CreateUserScreen createUsrScreen = null;
	CreateWorkoutScreen createWrkScreen = null;
	ExportResultsScreen exprtScreen = null;
	RestRoundScreen rstRndScreen = null;
	UserSelect usrSelScreen = null;
	WorkoutScreen wrktScreen = null;
	AudioPlay musicRunnable;
	static WelcomeScreen welcomeScreen = null;	
	static comSelection comSelectorScreen = null;
	
	/* Global Control Variables */
	String comPortName;
	Workout currentWorkout;
	Session sezzion; 
	Round currentRound; 
	User currentUser;
	User userBeingCreated;
	int numOfRnds, crntRnd, rndLength, restRndLength;
	
	public static void main(String[] args) throws FontFormatException, IOException {
		beginExecutionOfSWSuite();
	}
	
	public static void beginExecutionOfSWSuite() throws FontFormatException, IOException {
		//Create new welcome screen object
		welcomeScreen = new WelcomeScreen();
		welcomeScreen.setVisible(true);
		
		//Change back to 3 seconds before submission
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				welcomeScreen.dispose();
				comSelectionOpenClose();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	public static void comSelectionOpenClose() {
		comSelectorScreen = new comSelection();
		comSelectorScreen.setVisible(true);
	}
	
	public void comSelectToUseSelectTrans(String portName) throws SQLException {
		this.comPortName = portName;		
		comSelectorScreen.dispose(); //Remove the com select screen as we don't need it anymore
		HashMap<Integer, User> users = dbHelperObj.grabUsers();
		HashMap<Integer, Workout> wrkOuts = dbHelperObj.grabWorkouts();
		usrSelScreen = new UserSelect(users, wrkOuts, this);
		usrSelScreen.setVisible(true);		
	}
	
	public void usrSelectToAdminTrans() throws SQLException {
		//usrSelScreen.dispose();
		HashMap<Integer, User> users = dbHelperObj.grabUsers();
		HashMap<Integer, Workout> workouts = dbHelperObj.grabWorkouts();
		administrationScreen = new AdminScreen(users, workouts, this);
		administrationScreen.setVisible(true);
	}
	
	public void usrSelectToWorkoutTrans(User currUsr, Workout currWkrt) throws Exception {
		dbHelperObj.writeUser(currUsr);
		
		if(administrationScreen == null)
		{
			performUsrSelectToWorkoutTrans(currUsr, currWkrt);
		}
		else
		{
			if(!(administrationScreen.isVisible()))
			{
				performUsrSelectToWorkoutTrans(currUsr, currWkrt);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please Close the Admin Screen Before Moving Forward", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void performUsrSelectToWorkoutTrans(User currUsr, Workout currWkrt) throws Exception 
	{
		usrSelScreen.dispose();
		this.currentUser = currUsr;
		this.currentWorkout = currWkrt;
		
		//Create a new session
		sezzion = new Session(); 
		sezzion.setForceCutOffHigh(""+this.currentUser.getMaxGoalForce());
		sezzion.setForceCutOffLow(""+this.currentUser.getMinGoalForce());
		sezzion.setFrequencyCutOffHigh(""+this.currentUser.getMaxGoalFrequency());
		sezzion.setFrequencyCutOffLow(""+this.currentUser.getMinGoalFrequency());
		sezzion.setWorkouts_id(currWkrt.getId());
		sezzion.setUsers_id(currUsr.getId());
		sezzion=dbHelperObj.writeNewSession(sezzion);
		//Set current Round number
		this.crntRnd = 1;
		
		//Account for user not inputing any paths to songs in user select screen.
		if(currentUser.getMusic().size() == 0)
		{
			currentUser.addMusic("./data/music/defaultSong0.mp3");
		}
		
		
		if(currentWorkout.getActionCutOff_Force().equals("Color And Music Frequency") | currentWorkout.getActionCutOff_Force().equals("Music Frequency") | currentWorkout.getActionCutOff_Frequency().equals("Color And Music Frequency") | currentWorkout.getActionCutOff_Frequency().equals("Music Frequency") | currentWorkout.getActionCutOff_Force().equals("Color And Music Volume") | currentWorkout.getActionCutOff_Force().equals("Music Volume") | currentWorkout.getActionCutOff_Frequency().equals("Color And Music Volume") | currentWorkout.getActionCutOff_Frequency().equals("Music Volume"))
		{
			/************************STARTING THE MUSIC PLAYER*****************************************/
			musicRunnable = new AudioPlay(currentUser.getMusic());
			musicRunnable.createInitPLayer();
			//musicRunnable.startPlayer();
		}
		wrktScreen = new WorkoutScreen(this, currentUser, currentWorkout, comPortName, musicRunnable);
		wrktScreen.setVisible(true);
	}
	
	//TODO: Add parameters to pass in the data from the round to do a write to the DB here
	public void workoutToRestRndTrans(double[] fullRes) throws Exception {
		currentRound = new Round();
		double freq=0;
		double[] doubleman = wrktScreen.punches.grabEntireRoundResults(); 
		currentRound.setSessions_id(sezzion.getId());
		currentRound.setAverage_Force(""+doubleman[0]);
		freq=doubleman[1]/(Double.parseDouble(currentWorkout.getRoundTime())*60);
		currentRound.setAverage_Frequency(""+freq);
		currentRound=dbHelperObj.writeNewRound(currentRound);
		
		dbHelperObj.writeDataPoints(wrktScreen.punches.outputPunchData(Double.parseDouble(currentWorkout.getRoundTime())*60, currentRound.getId()));
		
		wrktScreen.dispose();
		if(musicRunnable != null)
		{
			musicRunnable.stop();
		}
		
		
		if(crntRnd >= Integer.parseInt(currentWorkout.getNumberOfRounds()))
		{
			
			completeWorkoutTrans(fullRes);
		}
		else
		{
			crntRnd++; //Increase curent round time
			rstRndScreen = new RestRoundScreen(this, currentWorkout);
			rstRndScreen.setVisible(true);
			
		}
	}
	
	public void restRndToWorkoutTrans() throws MalformedURLException, IOException, MediaException, InterruptedException {
		rstRndScreen.dispose();
		//Save the current round statistics for the currentUser object in the global variable here
		
		wrktScreen = new WorkoutScreen(this, currentUser, currentWorkout, comPortName, musicRunnable);
		wrktScreen.setVisible(true);
	
		
	}
	
	public void completeWorkoutTrans(double[] fullRes) throws SQLException {
		
		wrktScreen.dispose();
		if(musicRunnable != null)
		{
			musicRunnable.terminate();
		}
		//Save session statistics here for the currentUser object in the global variable		
		//dbHelperObj.something(something parameters)????????
		
		HashMap<Integer, User> users = dbHelperObj.grabUsers();
		HashMap<Integer, Workout> wrkOuts = dbHelperObj.grabWorkouts();
		currentUser.generateDefaultGoals();
		try {
			dbHelperObj.writeUser(currentUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Set the User back to the database with their new goals
		
		usrSelScreen = new UserSelect(users, wrkOuts, this);
		usrSelScreen.setVisible(true);		
	}
	
	/* Administration Control Methods */
	public void adminToCreateUsrTrans() {
		//administrationScreen.setEnabled(false);
		createUsrScreen = new CreateUserScreen(this);
		createUsrScreen.setVisible(true);
	}
	
	public void createUsrToBaseLineTrans(String uName, int age, int weight, int armSpan, int height, String level) {
		createUsrScreen.dispose();
		userBeingCreated = new User(uName, Integer.toString(age), Integer.toString(weight), Integer.toString(armSpan), Integer.toString(height), level);
		
		try {
			dbHelperObj.writeUser(userBeingCreated); //Write the user in order to grab its ID
			baselineScreen = new BaselineScreen(this, userBeingCreated, comPortName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		baselineScreen.setVisible(true);
	}
	
	public void submitBaselineSession(User newUserInCreation) throws Exception {
		//store this data to the database
		dbHelperObj.writeUser(newUserInCreation);
		newUserInCreation=dbHelperObj.grabUsers().get(newUserInCreation.getId()); //Had to grab the users to calculate what the average force and frequency is for the user now
		newUserInCreation.generateDefaultGoals();
		dbHelperObj.writeUser(newUserInCreation); //Set the User back to the database with their new goals
		
		baselineScreen.dispose();
		returnToAdmin();
	}
	
	public void adminToCreateWorkTrans() {
		//administrationScreen.setEnabled(false);
		createWrkScreen = new CreateWorkoutScreen(this);
		createWrkScreen.setVisible(true);
	}
	
	public void submitWrktCreation(Workout workoutToCreate) throws Exception {
		//write new work out type to the database
		dbHelperObj.writeNewWorkout(workoutToCreate);
		createWrkScreen.dispose();
		returnToAdmin();
	}
	
	public void adminToExportTrans(HashMap<Integer, User> usrs, HashMap<Integer, Workout> wkrts) {
		//administrationScreen.setEnabled(false);
		exprtScreen = new ExportResultsScreen(this, usrs, wkrts, dbHelperObj);
		exprtScreen.setVisible(true);
	}	
	
	public void submitExprtResults() throws SQLException {
		//do the exporting of results to a file in the output directory
		returnToAdmin();
	}
	
	public HashMap<Integer, User> saveChangesToUser(User newUserSettings) throws Exception
	{
		//Write to DB here with the new updated user object here
		dbHelperObj.writeUser(newUserSettings);
		
		//Get and return the new list of users from the DB to update the drop down Box for edit user settings on administration screen
		return dbHelperObj.grabUsers();
	}
	
	//used after submitting methods of various administration screens as well as to cancel on any of those screens as well
	public void returnToAdmin() throws SQLException {
		HashMap<Integer, User> users = dbHelperObj.grabUsers();
		HashMap<Integer, Workout> workouts = dbHelperObj.grabWorkouts();
		String userNames[];
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("--Select a User--" + "\t");
		
		for (User entry : users.values()) {
		    strBuild.append(entry.getName());
		    strBuild.append("\t");
		}	
		
		userNames = strBuild.toString().split("\t");
		administrationScreen.editUsrDropdown.setModel(new DefaultComboBoxModel<String>(userNames));
		administrationScreen.users = users;
		usrSelScreen.userComboBox.setModel(new DefaultComboBoxModel<String>(userNames));
		usrSelScreen.users = users;
		
		String wrkts[];
		StringBuilder strBuild2 = new StringBuilder();
		strBuild2.append("--Select a Workout--" + "\t");
		
		for (Workout entry : workouts.values()) {
		    strBuild2.append(entry.getName());
		    strBuild2.append("\t");
		}	
		
		wrkts = strBuild2.toString().split("\t");
		usrSelScreen.workoutComboBox.setModel(new DefaultComboBoxModel<String>(wrkts));
		usrSelScreen.workouts = workouts;
		
		if(createUsrScreen != null)
		{
			createUsrScreen.dispose();
		}
		if (baselineScreen != null)
		{
			baselineScreen.dispose();
		}
		if (createWrkScreen != null)
		{
			createWrkScreen.dispose();
		}
		if (exprtScreen != null)
		{
			exprtScreen.dispose();
		}
		
		administrationScreen.setEnabled(true);
		administrationScreen.toFront();
	}

	public void ExportAndMoveToAdmin(String exportData) throws IOException, SQLException 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		Calendar calender = Calendar.getInstance();
		String dateTime = dateFormat.format(calender.getTime()); 
		
		String fileName = dateTime+"-PFPBExportData"+ ".csv";

	    //Attempt to write as a CSV file 	 
		File dummyFile = new File(fileName);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(dummyFile);
        
        int returnValue = fileChooser.showSaveDialog(null);        

        File csvFile = fileChooser.getSelectedFile();
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            FileWriter out = new FileWriter(csvFile);
            
            out.write(exportData);

            //Close the connection
            out.close();
        }
        else {
        	JOptionPane.showMessageDialog(null, "Error Exporting the data to that file location,.........Sorry", "Error", JOptionPane.ERROR_MESSAGE);
        }
	        
        returnToAdmin();
	}
	
	public SQLLiteHelper getDbHelperObj() {
		return dbHelperObj;
	}
}
