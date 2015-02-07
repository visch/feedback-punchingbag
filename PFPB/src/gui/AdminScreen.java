  package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import backend.*;
import backend.datatypes.*;

@SuppressWarnings("serial")
public class AdminScreen extends JFrame {
	private JPanel contentPane;
	private JTextField nameTField;
	private JTextField ageTField;
	private JTextField weightTField;
	private JTextField heightTField;
	private JTextField armspanTField;
	private JTextField userLevelTField;
	private JTextField forceGoalMin;
	private JTextField freqGoalMin;
	private JTextField forceGoalMax;
	private JTextField freqGoalMax;
	private JButton btnSubmitChanges;
	public JComboBox<String> editUsrDropdown;
	User currentUserSetDisplayed;
	public HashMap<Integer, User> users;
	HashMap<Integer, Workout> workouts;
	GUIController controlObj;
	private JTextField avgForceLastRndTxtField;
	private JTextField avgFreqLastRndTxtField;

	/**
	 * Create the frame.
	 * @param guiController 
	 * @param users 
	 */
	public AdminScreen(HashMap<Integer, User> usrs, HashMap<Integer, Workout> wkrts, GUIController guiController) {
		this.controlObj = guiController;
		this.users = usrs;
		this.workouts = wkrts;
		initialize();
	}
	
	public void initialize() {
		setTitle("Administration");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminScreen.class.getResource("/Images/PFPBLogo.png")));
		setDefaultCloseOperation(AdminScreen.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 475, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        
        JLabel lblAministration = new JLabel("Administration");
        lblAministration.setBounds(77, 11, 305, 46);
		lblAministration.setFont(new Font("Segoe UI Light", Font.PLAIN, 48));
		contentPane.add(lblAministration);
		
		JButton btnCreateNewUser = new JButton("Create New User");
		btnCreateNewUser.setBounds(17, 82, 131, 41);
		btnCreateNewUser.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnCreateNewUser.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent actionEvent) {
        		controlObj.adminToCreateUsrTrans();
        	}
        });
		contentPane.add(btnCreateNewUser);
		
		JButton btnNewWorkout = new JButton("New Workout");
		btnNewWorkout.setBounds(165, 82, 131, 41);
		btnNewWorkout.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnNewWorkout.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent actionEvent) {
        		controlObj.adminToCreateWorkTrans();
        	}
        });
		contentPane.add(btnNewWorkout);
		
		JButton btnExportResults = new JButton("Export Results");
		btnExportResults.setBounds(313, 82, 131, 41);
		btnExportResults.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnExportResults.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent actionEvent) {
        		controlObj.adminToExportTrans(users, workouts);
        	}
        });
		contentPane.add(btnExportResults);
		
		btnSubmitChanges = new JButton("Submit Changes");
		btnSubmitChanges.setEnabled(false);
		btnSubmitChanges.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnSubmitChanges.setBounds(302, 484, 142, 31);
		btnSubmitChanges.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent actionEvent) {		
        		currentUserSetDisplayed.setName(nameTField.getText());
        		currentUserSetDisplayed.setAge(ageTField.getText());
        		currentUserSetDisplayed.setWeight(weightTField.getText());
        		currentUserSetDisplayed.setHeight(heightTField.getText());
        		currentUserSetDisplayed.setArmSpan(armspanTField.getText());
        		currentUserSetDisplayed.setUserThoughtLevel(userLevelTField.getText());
        		currentUserSetDisplayed.setMinGoalForce(forceGoalMin.getText());
        		currentUserSetDisplayed.setMaxGoalForce(forceGoalMax.getText());
        		currentUserSetDisplayed.setMinGoalFrequency(freqGoalMin.getText());
        		currentUserSetDisplayed.setMaxGoalFrequency(freqGoalMax.getText());
    
        		
				try {
					users = controlObj.saveChangesToUser(currentUserSetDisplayed);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		String userNames[];
        		StringBuilder strBuild = new StringBuilder();
        		
        		
        		strBuild.append("--Select a User--" + "\t");
        		
        		//Enable text boxes to be editted
				nameTField.setEditable(false);
        		ageTField.setEditable(false);
        		weightTField.setEditable(false);
        		heightTField.setEditable(false);
        		armspanTField.setEditable(false);
        		userLevelTField.setEditable(false);
				forceGoalMin.setEditable(false);
				forceGoalMax.setEditable(false);
				freqGoalMax.setEditable(false);
				freqGoalMin.setEditable(false);
        		
        		
				nameTField.setText("");
				ageTField.setText("");
				weightTField.setText("");
				heightTField.setText("");
				armspanTField.setText("");
        		userLevelTField.setText("");
        		forceGoalMax.setText("");
        		forceGoalMin.setText("");
        		freqGoalMax.setText("");
        		freqGoalMin.setText("");
        		

        		//Parse through the current list of Users
				
        		for (User entry : users.values()) {
        		    strBuild.append(entry.getName());
        		    strBuild.append("\t");
        		}
        		
        		userNames = strBuild.toString().split("\t");    
        		
        		editUsrDropdown.setModel(new DefaultComboBoxModel<String>(userNames));
        	}
        });
		contentPane.add(btnSubmitChanges);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 134, 443, 8);
		contentPane.add(separator);
		
		JLabel lblEditUserSettings = new JLabel("Edit User Settings:");
		lblEditUserSettings.setBounds(10, 139, 181, 30);
		lblEditUserSettings.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		contentPane.add(lblEditUserSettings);
		
		editUsrDropdown = new JComboBox<String>();
		editUsrDropdown.setBounds(17, 180, 153, 30);
		String userNames[];
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("--Select a User--" + "\t");
		
		for (User entry : users.values()) {
		    strBuild.append(entry.getName());
		    strBuild.append("\t");
		}
		
		
		userNames = strBuild.toString().split("\t");
		editUsrDropdown.setModel(new DefaultComboBoxModel<String>(userNames));
		editUsrDropdown.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {				
				//get the User Name from the combo box
				@SuppressWarnings("unchecked")
				JComboBox<String> source = ((JComboBox<String>)e.getSource());
				int selectedIndex=source.getSelectedIndex(); 
				
				if (selectedIndex!=0) //We do not want to select anything if we are at the default selection
				{
					User selectedUser = (User) users.values().toArray()[selectedIndex-1];
					int userIDSelected = selectedUser.getId(); //Grab the latest ID
					currentUserSetDisplayed = users.get(userIDSelected);
					
					//Enable text boxes to be edited
					nameTField.setEditable(true);
	        		ageTField.setEditable(true);
	        		weightTField.setEditable(true);
	        		heightTField.setEditable(true);
	        		armspanTField.setEditable(true);
	        		userLevelTField.setEditable(true);
	        		forceGoalMin.setEditable(true);
	        		forceGoalMax.setEditable(true);
	        		freqGoalMin.setEditable(true);
	        		freqGoalMax.setEditable(true);
	        		
					nameTField.setText(currentUserSetDisplayed.getName());
					ageTField.setText(currentUserSetDisplayed.getAge());
					weightTField.setText(currentUserSetDisplayed.getWeight());
					heightTField.setText(currentUserSetDisplayed.getHeight());
					armspanTField.setText(currentUserSetDisplayed.getArmSpan());
					userLevelTField.setText(currentUserSetDisplayed.getUserThoughtLevel());
					forceGoalMin.setText(String.format("%.1f", (float)currentUserSetDisplayed.getMinGoalForce()));
					forceGoalMax.setText(String.format("%.1f", (float)currentUserSetDisplayed.getMaxGoalForce()));
					freqGoalMin.setText(String.format("%.1f", (float)currentUserSetDisplayed.getMinGoalFrequency()));
					freqGoalMax.setText(String.format("%.1f", (float)currentUserSetDisplayed.getMaxGoalFrequency()));
					avgForceLastRndTxtField.setText(String.format("%.1f", (float)currentUserSetDisplayed.getAverageForceFromLastSessionRounds()));
					avgFreqLastRndTxtField.setText(String.format("%.1f", (float)currentUserSetDisplayed.getAverageFrequencyFromLastSessionRounds()));
					
					btnSubmitChanges.setEnabled(true);
				}
				else {
					nameTField.setText("");
					ageTField.setText("");
					weightTField.setText("");
					heightTField.setText("");
					armspanTField.setText("");
	        		userLevelTField.setText("");
	        		avgForceLastRndTxtField.setText("");
	        		avgFreqLastRndTxtField.setText("");
	        		forceGoalMax.setText("");
	        		forceGoalMin.setText("");
	        		freqGoalMax.setText("");
	        		freqGoalMin.setText("");
	        		
	        		//Enable text boxes to be edited
					nameTField.setEditable(false);
	        		ageTField.setEditable(false);
	        		weightTField.setEditable(false);
	        		heightTField.setEditable(false);
	        		armspanTField.setEditable(false);
	        		userLevelTField.setEditable(false);
	        		avgForceLastRndTxtField.setEditable(false);
	        		avgFreqLastRndTxtField.setEditable(false);
					forceGoalMin.setEditable(false);
					forceGoalMax.setEditable(false);
					freqGoalMax.setEditable(false);
					freqGoalMin.setEditable(false);
				}
			}
		});
		contentPane.add(editUsrDropdown);
		
		nameTField = new JTextField();
		nameTField.setEditable(false);
		nameTField.setBounds(277, 175, 167, 20);
		nameTField.setColumns(10);
		contentPane.add(nameTField);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(231, 178, 46, 14);
		lblName.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		contentPane.add(lblName);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(241, 203, 28, 20);
		lblAge.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		contentPane.add(lblAge);
		
		ageTField = new JTextField();
		ageTField.setEditable(false);
		ageTField.setBounds(277, 204, 167, 20);
		ageTField.setColumns(10);
		contentPane.add(ageTField);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(222, 234, 47, 20);
		lblWeight.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		contentPane.add(lblWeight);
		
		weightTField = new JTextField();
		weightTField.setEditable(false);
		weightTField.setBounds(277, 235, 167, 20);
		weightTField.setColumns(10);
		contentPane.add(weightTField);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(223, 265, 46, 20);
		lblHeight.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		contentPane.add(lblHeight);
		
		heightTField = new JTextField();
		heightTField.setEditable(false);
		heightTField.setBounds(277, 266, 167, 20);
		heightTField.setColumns(10);
		contentPane.add(heightTField);
		
		JLabel lblArmspan = new JLabel("Armspan:");
		lblArmspan.setBounds(211, 296, 58, 20);
		lblArmspan.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		contentPane.add(lblArmspan);
		
		armspanTField = new JTextField();
		armspanTField.setEditable(false);
		armspanTField.setBounds(277, 297, 167, 20);
		armspanTField.setColumns(10);
		contentPane.add(armspanTField);
		
		//TODO Why don't we make a drop-down for this (i.e. Beginner, Intermediate, Expert)
		JLabel lblUserThoughtLevel = new JLabel("User Thought Level:"); 	
		lblUserThoughtLevel.setBounds(143, 328, 126, 20);
		lblUserThoughtLevel.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		contentPane.add(lblUserThoughtLevel);
		
		userLevelTField = new JTextField();
		userLevelTField.setEditable(false);
		userLevelTField.setColumns(10);
		userLevelTField.setBounds(277, 328, 167, 20);
		contentPane.add(userLevelTField);
		
		JLabel lblUserForceGoal = new JLabel("User Force Goal:");
		lblUserForceGoal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUserForceGoal.setBounds(169, 420, 100, 20);
		contentPane.add(lblUserForceGoal);
		
		JLabel lblUserFrequencyGoal = new JLabel("User Frequency Goal:");
		lblUserFrequencyGoal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUserFrequencyGoal.setBounds(138, 451, 131, 20);
		contentPane.add(lblUserFrequencyGoal);
		
		forceGoalMin = new JTextField();
		forceGoalMin.setEditable(false);
		forceGoalMin.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		forceGoalMin.setColumns(10);
		forceGoalMin.setBounds(306, 421, 46, 20);
		contentPane.add(forceGoalMin);
		
		freqGoalMin = new JTextField();
		freqGoalMin.setEditable(false);
		freqGoalMin.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		freqGoalMin.setColumns(10);
		freqGoalMin.setBounds(306, 452, 46, 20);
		contentPane.add(freqGoalMin);
		
		forceGoalMax = new JTextField();
		forceGoalMax.setEditable(false);
		forceGoalMax.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		forceGoalMax.setColumns(10);
		forceGoalMax.setBounds(392, 421, 46, 20);
		contentPane.add(forceGoalMax);
		
		freqGoalMax = new JTextField();
		freqGoalMax.setEditable(false);
		freqGoalMax.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		freqGoalMax.setColumns(10);
		freqGoalMax.setBounds(392, 453, 46, 20);
		contentPane.add(freqGoalMax);
		
		JLabel lblMin = new JLabel("Min");
		lblMin.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblMin.setBounds(282, 423, 20, 14);
		contentPane.add(lblMin);
		
		JLabel lblMin2 = new JLabel("Min");
		lblMin2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblMin2.setBounds(282, 456, 20, 14);
		contentPane.add(lblMin2);
		
		JLabel lblMax = new JLabel("Max");
		lblMax.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblMax.setBounds(365, 456, 30, 14);
		contentPane.add(lblMax);
		
		JLabel lblMax2 = new JLabel("Max");
		lblMax2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblMax2.setBounds(365, 423, 30, 14);
		contentPane.add(lblMax2);
		
		JLabel lblAverageForceFrom = new JLabel("Average Force From Last Round:");
		lblAverageForceFrom.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblAverageForceFrom.setBounds(66, 359, 202, 20);
		contentPane.add(lblAverageForceFrom);
		
		avgForceLastRndTxtField = new JTextField();
		avgForceLastRndTxtField.setEditable(false);
		avgForceLastRndTxtField.setColumns(10);
		avgForceLastRndTxtField.setBounds(276, 360, 167, 20);
		contentPane.add(avgForceLastRndTxtField);
		
		JLabel lblAverageFrequencyFrom = new JLabel("Average Frequency From Last Round:");
		lblAverageFrequencyFrom.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblAverageFrequencyFrom.setBounds(36, 391, 233, 20);
		contentPane.add(lblAverageFrequencyFrom);
		
		avgFreqLastRndTxtField = new JTextField();
		avgFreqLastRndTxtField.setEditable(false);
		avgFreqLastRndTxtField.setColumns(10);
		avgFreqLastRndTxtField.setBounds(277, 392, 167, 20);
		contentPane.add(avgFreqLastRndTxtField);
	}
}
