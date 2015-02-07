package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import backend.GUIController;
import backend.SQLLiteHelper;
import backend.datatypes.User;
import backend.datatypes.Workout;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ExportResultsScreen extends JFrame {

	private JPanel contentPane;
	private JRadioButton rbtnRndsByUsr;
	private JComboBox<String> comboBoxWkrtNames;
	private JComboBox<String> comboBoxUserNames;
	private JRadioButton rbtnSeshByUsr;
	private JRadioButton rdbtnSeshByWkrt;
	private JRadioButton rdbtnRndsByWrkt;
	JRadioButton rbtnAllRnds;
	JRadioButton rdbtnAllSesh;
	JRadioButton btnAllData;
	JRadioButton rbtnWrktSett;
	JRadioButton rbtnUsrSettings;
	JRadioButton rbtnByUsr;
	JRadioButton rbtnByWrkout;
	
	GUIController controlObj;
	HashMap<Integer, User> users;
	HashMap<Integer, Workout> workouts;
	SQLLiteHelper dbHelper;
	
	/**
	 * Create the frame.
	 * @param guiController 
	 * @param wkrts 
	 * @param usrs 
	 * @param dbHelperObj 
	 */
	public ExportResultsScreen(GUIController guiController, HashMap<Integer, User> usrs, HashMap<Integer, Workout> wkrts, SQLLiteHelper dbHelperObj) {
		this.controlObj = guiController;
		this.users = usrs;
		this.workouts = wkrts;
		dbHelper = dbHelperObj;
		initialize();
	}
	public void initialize() {
		setTitle("Export Results");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExportResultsScreen.class.getResource("/Images/PFPBLogo.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 498, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblExportResults = new JLabel("Export Results:");
		lblExportResults.setFont(new Font("Segoe UI Light", Font.PLAIN, 28));
		lblExportResults.setBounds(10, 11, 188, 29);
		contentPane.add(lblExportResults);
		
		JLabel lblByWorkout = new JLabel("By Workout:");
		lblByWorkout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblByWorkout.setBounds(63, 147, 76, 20);
		contentPane.add(lblByWorkout);
		
		JLabel lblByUser = new JLabel("By User:");
		lblByUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblByUser.setBounds(89, 178, 50, 20);
		contentPane.add(lblByUser);
		
		JLabel lblAllUserSettings = new JLabel("All User Settings:");
		lblAllUserSettings.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAllUserSettings.setBounds(36, 209, 103, 20);
		contentPane.add(lblAllUserSettings);
		
		JLabel lblAllWorkoutSettings = new JLabel("All Workout Settings:");
		lblAllWorkoutSettings.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAllWorkoutSettings.setBounds(10, 240, 129, 20);
		contentPane.add(lblAllWorkoutSettings);
		
		JLabel lblAllRounds = new JLabel("All Rounds:");
		lblAllRounds.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAllRounds.setBounds(71, 116, 68, 20);
		contentPane.add(lblAllRounds);
		
		JLabel lblAllSessions = new JLabel("All Sessions:");
		lblAllSessions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAllSessions.setBounds(63, 51, 76, 20);
		contentPane.add(lblAllSessions);
		
		
		JLabel lblAllData = new JLabel("All Data:");
		lblAllData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAllData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAllData.setBounds(80, 82, 59, 20);
		contentPane.add(lblAllData);
		
		btnAllData = new JRadioButton("All Dat");
		btnAllData.setHorizontalAlignment(SwingConstants.LEFT);
		btnAllData.setBounds(145, 82, 22, 23);
		contentPane.add(btnAllData);
		
		rdbtnAllSesh = new JRadioButton("");
		rdbtnAllSesh.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnAllSesh.setBounds(145, 51, 22, 23);
		contentPane.add(rdbtnAllSesh);
		
		rbtnAllRnds = new JRadioButton("");
		rbtnAllRnds.setHorizontalAlignment(SwingConstants.LEFT);
		rbtnAllRnds.setBounds(145, 113, 22, 23);
		contentPane.add(rbtnAllRnds);
		
		rbtnWrktSett = new JRadioButton("");
		rbtnWrktSett.setHorizontalAlignment(SwingConstants.LEFT);
		rbtnWrktSett.setBounds(145, 237, 22, 23);
		contentPane.add(rbtnWrktSett);
		
		rbtnUsrSettings = new JRadioButton("");
		rbtnUsrSettings.setHorizontalAlignment(SwingConstants.LEFT);
		rbtnUsrSettings.setBounds(145, 206, 22, 23);
		contentPane.add(rbtnUsrSettings);
		
		rbtnByUsr = new JRadioButton("");
		rbtnByUsr.setHorizontalAlignment(SwingConstants.LEFT);
		rbtnByUsr.setBounds(145, 175, 22, 23);
		rbtnByUsr.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(rbtnByUsr.isSelected())
		    	{
			    	comboBoxUserNames.setEnabled(true);
			    	populateComboBox(1,comboBoxUserNames);
			    	rbtnSeshByUsr.setEnabled(true);
			    	rbtnRndsByUsr.setEnabled(true);
		    	}
		    	else {
		    		comboBoxUserNames.setEnabled(false);
			    	rbtnSeshByUsr.setEnabled(false);
			    	rbtnRndsByUsr.setEnabled(false);
		    	}
		    }
		});
		contentPane.add(rbtnByUsr);
		
		rbtnByWrkout = new JRadioButton("");
		rbtnByWrkout.setHorizontalAlignment(SwingConstants.LEFT);
		rbtnByWrkout.setBounds(145, 144, 22, 23);
		rbtnByWrkout.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(rbtnByWrkout.isSelected()) {
		    		comboBoxWkrtNames.setEnabled(true);
		    		populateComboBox(2,comboBoxWkrtNames);
		    		rdbtnRndsByWrkt.setEnabled(true);
		    		rdbtnRndsByWrkt.setSelected(true);
		    		rdbtnSeshByWkrt.setEnabled(true);
		    	}
		    	else {
		    		comboBoxWkrtNames.setEnabled(false);
			    	rdbtnRndsByWrkt.setEnabled(false);
			    	rdbtnSeshByWkrt.setEnabled(false);
		    	}
		    }
		});
		contentPane.add(rbtnByWrkout);
		
		comboBoxWkrtNames = new JComboBox<String>();
		comboBoxWkrtNames.setBounds(173, 149, 153, 20);
		comboBoxWkrtNames.setEnabled(false);		
		contentPane.add(comboBoxWkrtNames);
		
		comboBoxUserNames = new JComboBox<String>();
		comboBoxUserNames.setBounds(173, 180, 153, 20);
		comboBoxUserNames.setEnabled(false);		
		contentPane.add(comboBoxUserNames);
		
		rdbtnRndsByWrkt = new JRadioButton("Rounds");
		rdbtnRndsByWrkt.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		rdbtnRndsByWrkt.setVerticalAlignment(SwingConstants.BOTTOM);
		rdbtnRndsByWrkt.setBounds(332, 151, 59, 19);
		rdbtnRndsByWrkt.setEnabled(false);
		contentPane.add(rdbtnRndsByWrkt);
		
		rdbtnSeshByWkrt = new JRadioButton("Sessions");
		rdbtnSeshByWkrt.setVerticalAlignment(SwingConstants.BOTTOM);
		rdbtnSeshByWkrt.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		rdbtnSeshByWkrt.setBounds(403, 151, 73, 19);
		rdbtnSeshByWkrt.setEnabled(false);
		contentPane.add(rdbtnSeshByWkrt);
		
		rbtnSeshByUsr = new JRadioButton("Sessions");
		rbtnSeshByUsr.setVerticalAlignment(SwingConstants.BOTTOM);
		rbtnSeshByUsr.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		rbtnSeshByUsr.setBounds(403, 182, 73, 19);
		rbtnSeshByUsr.setEnabled(false);
		contentPane.add(rbtnSeshByUsr);
		
		rbtnRndsByUsr = new JRadioButton("Rounds");
		rbtnRndsByUsr.setVerticalAlignment(SwingConstants.BOTTOM);
		rbtnRndsByUsr.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		rbtnRndsByUsr.setBounds(332, 182, 59, 19);
		rbtnRndsByUsr.setEnabled(false);
		contentPane.add(rbtnRndsByUsr);
		
		ButtonGroup byUserRadios = new ButtonGroup();
		byUserRadios.add(rbtnRndsByUsr);
		byUserRadios.add(rbtnSeshByUsr);
		
		ButtonGroup byWorkoutRadios = new ButtonGroup();
		byWorkoutRadios.add(rdbtnSeshByWkrt);
		byWorkoutRadios.add(rdbtnRndsByWrkt);
		
		ButtonGroup expoRadioBtns = new ButtonGroup();		
		expoRadioBtns.add(rbtnByWrkout);
		expoRadioBtns.add(rbtnByUsr);
		expoRadioBtns.add(rbtnUsrSettings);
		expoRadioBtns.add(rbtnWrktSett);
		expoRadioBtns.add(rbtnAllRnds);
		expoRadioBtns.add(rdbtnAllSesh);

	
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnCancel.setBounds(109, 271, 89, 23);
		btnCancel.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        try {
					controlObj.returnToAdmin();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		contentPane.add(btnCancel);
		
		JButton btnExport = new JButton("Export");
		btnExport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnExport.setBounds(302, 271, 89, 23);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> strToExport = null;
		        StringBuilder exportString = new StringBuilder();
				if(rdbtnAllSesh.isSelected())
		        {
		        	try {
						strToExport = dbHelper.allSessionsExport();
						for(String s: strToExport) {
			        		exportString.append(s);
			        	}
						controlObj.ExportAndMoveToAdmin(exportString.toString());
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		        if(rbtnAllRnds.isSelected())
		        {
		        	try {
						strToExport = dbHelper.allRoundsExport();
						for(String s: strToExport) {
			        		exportString.append(s);
			        	}
						controlObj.ExportAndMoveToAdmin(exportString.toString());
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		        if(rbtnUsrSettings.isSelected())
		        {
		        	try {
						strToExport = dbHelper.exportUsers();
						for(String s: strToExport) {
			        		exportString.append(s);
			        	}
						controlObj.ExportAndMoveToAdmin(exportString.toString());
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		        if(rbtnWrktSett.isSelected())
		        {
		        	try {
						strToExport = dbHelper.exportWorkouts();
						for(String s: strToExport) {
			        		exportString.append(s);
			        	}
						controlObj.ExportAndMoveToAdmin(exportString.toString());
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		        if (btnAllData.isSelected())
		        {
		        	try {
		        	strToExport = dbHelper.allDataExport();
		        	for (String s: strToExport)
		        	{
		        		exportString.append(s);
		        	}
		        	controlObj.ExportAndMoveToAdmin(exportString.toString());
		        	}
		        	catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		        
		        if(rbtnByWrkout.isSelected())
		        {
		        	Workout workoutSelected = (Workout)workouts.values().toArray()[comboBoxWkrtNames.getSelectedIndex() - 1];
		        	if(rdbtnRndsByWrkt.isSelected())
		        	{
		        		try {
							strToExport = dbHelper.allRoundsExportByWorkoutID(workoutSelected.getId());
							for(String s: strToExport) {
				        		exportString.append(s);
				        	}
							controlObj.ExportAndMoveToAdmin(exportString.toString());
						} catch (SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        	if(rdbtnSeshByWkrt.isSelected())
		        	{
		        		try {
							strToExport = dbHelper.allSessionsExportByWorkoutID(workoutSelected.getId());
							for(String s: strToExport) {
				        		exportString.append(s);
				        	}
							controlObj.ExportAndMoveToAdmin(exportString.toString());
						} catch (SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        }
		        if(rbtnByUsr.isSelected())
		        {
		        	User userSelected = (User)users.values().toArray()[comboBoxUserNames.getSelectedIndex() - 1];
		        	if(rbtnSeshByUsr.isSelected())
		        	{
		        		try {
							strToExport = dbHelper.allSessionsExportByUserID(userSelected.getId());
							for(String s: strToExport) {
				        		exportString.append(s);
				        	}
							controlObj.ExportAndMoveToAdmin(exportString.toString());
						} catch (SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        	if(rbtnRndsByUsr.isSelected())
		        	{
		        		try {
							strToExport = dbHelper.allRoundsExportByUserID(userSelected.getId());
							for(String s: strToExport) {
				        		exportString.append(s);
				        	}
							controlObj.ExportAndMoveToAdmin(exportString.toString());
						} catch (SQLException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        }
		    }
		});
		contentPane.add(btnExport);
		
	
	}

	private void populateComboBox(int databaseFlag, JComboBox<String> comboBoxObject) {
		switch(databaseFlag) {
			case 1:
				String userNames[];
				StringBuilder strBuild = new StringBuilder();
				strBuild.append("--Select a User--" + "\t");
				for (Map.Entry<Integer, User> entry : users.entrySet()) {
				    strBuild.append(entry.getValue().getName());
				    strBuild.append("\t");
				}
				userNames = strBuild.toString().split("\t");
				comboBoxObject.setModel(new DefaultComboBoxModel<String>(userNames));
				break;
			case 2:
				String workOutNames[];
				StringBuilder strBuild2 = new StringBuilder();
				strBuild2.append("--Select Workout--" + "\t");
				for (Map.Entry<Integer, Workout> entry : workouts.entrySet()) {
				    strBuild2.append(entry.getValue().getName());
				    strBuild2.append("\t");
				}
				workOutNames = strBuild2.toString().split("\t");
				comboBoxObject.setModel(new DefaultComboBoxModel<String>(workOutNames));
				break;
			default:
				//do nothing
				break;
		}
	}
}
