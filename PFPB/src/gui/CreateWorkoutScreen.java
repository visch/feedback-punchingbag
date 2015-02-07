package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import backend.*;
import backend.datatypes.*;

@SuppressWarnings("serial")
public class CreateWorkoutScreen extends JFrame {
	private JPanel contentPane;
	private JTextField txtWrktName;
	private JTextField rndTimeTxt;
	private JTextField nRndsTxt;
	private JTextField brTimeTxt;
	JComboBox<String> forceAction;
	JComboBox<String> freqAction;
	JCheckBox cBxTimer;
	JCheckBox cBxCurrForce;
	JCheckBox cBxForceGoal;
	JCheckBox cBxFreqGoal;
	JCheckBox cBxCurrFreq;
	String visTime;
	String visCurForce;
	String visForceGoal;
	String visCurFreq;
	String visFreqGoal;	
	GUIController controlObj;

	/**
	 * Create the frame.
	 * @param guiController 
	 */
	public CreateWorkoutScreen(GUIController guiController) {
		controlObj = guiController;
		initialize();
	}
	public void initialize() {

		setTitle("Workout Creation");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreateWorkoutScreen.class.getResource("/Images/PFPBLogo.png")));
		setDefaultCloseOperation(CreateWorkoutScreen.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateWorkout = new JLabel("Create Workout");
		lblCreateWorkout.setFont(new Font("Segoe UI Light", Font.PLAIN, 48));
		lblCreateWorkout.setBounds(33, 11, 319, 51);
		contentPane.add(lblCreateWorkout);
		
		JLabel lblWorkoutName = new JLabel("Workout Name:");
		lblWorkoutName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblWorkoutName.setBounds(10, 77, 126, 20);
		contentPane.add(lblWorkoutName);
		
		txtWrktName = new JTextField();
		txtWrktName.setToolTipText("Type the name of the Workout to be created");
		txtWrktName.setBounds(146, 81, 225, 20);
		txtWrktName.setColumns(10);
		contentPane.add(txtWrktName);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 109, 366, 2);
		contentPane.add(separator);
		
		JLabel lblForce = new JLabel("Force:");
		lblForce.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblForce.setBounds(10, 114, 52, 25);
		contentPane.add(lblForce);
		
		JLabel lblActionToPreform = new JLabel("Action to Preform:");
		lblActionToPreform.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblActionToPreform.setBounds(10, 144, 118, 25);
		contentPane.add(lblActionToPreform);
		
		forceAction = new JComboBox<String>();
		forceAction.setModel(new DefaultComboBoxModel<String>(new String[] {"--Select An Action--", "Color Change For Box", "Music Frequency", "Music Volume", "Color And Music Volume", "Color And Music Frequency", "None"}));
		forceAction.setBounds(138, 146, 233, 20);
		contentPane.add(forceAction);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 180, 366, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 259, 366, 2);
		contentPane.add(separator_2);
		
		JLabel label = new JLabel("Action to Preform:");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		label.setBounds(10, 223, 118, 25);
		contentPane.add(label);
		
		freqAction = new JComboBox<String>();
		freqAction.setModel(new DefaultComboBoxModel<String>(new String[] {"--Select An Action--", "Color Change For Box", "Music Frequency", "Music Volume", "Color And Music Volume", "Color And Music Frequency", "None"}));
		freqAction.setBounds(138, 225, 233, 20);
		contentPane.add(freqAction);
		
		JLabel lblFrequency = new JLabel("Frequency:");
		lblFrequency.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblFrequency.setBounds(10, 193, 91, 25);
		contentPane.add(lblFrequency);
		
		JLabel lblRoundCharacteristics = new JLabel("Round Characteristics:");
		lblRoundCharacteristics.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblRoundCharacteristics.setBounds(10, 272, 185, 25);
		contentPane.add(lblRoundCharacteristics);
		
		JLabel lblRoundTime = new JLabel("Round Time:");
		lblRoundTime.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblRoundTime.setBounds(10, 300, 118, 25);
		contentPane.add(lblRoundTime);
		
		JLabel lblNumberOfRounds = new JLabel("Number of Rounds:");
		lblNumberOfRounds.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNumberOfRounds.setBounds(10, 336, 126, 25);
		contentPane.add(lblNumberOfRounds);
		
		JLabel lblBreakTime = new JLabel("Break Time:");
		lblBreakTime.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblBreakTime.setBounds(10, 372, 77, 25);
		contentPane.add(lblBreakTime);
		
		rndTimeTxt = new JTextField();
		rndTimeTxt.setToolTipText("Round Time must be in minutes");
		rndTimeTxt.setColumns(10);
		rndTimeTxt.setBounds(93, 304, 250, 20);
		contentPane.add(rndTimeTxt);
		
		nRndsTxt = new JTextField();
		nRndsTxt.setToolTipText("Number of Rounds must be an integer");
		nRndsTxt.setColumns(10);
		nRndsTxt.setBounds(138, 340, 205, 20);
		contentPane.add(nRndsTxt);
		
		brTimeTxt = new JTextField();
		brTimeTxt.setToolTipText("Type the name of the Workout to be created");
		brTimeTxt.setColumns(10);
		brTimeTxt.setBounds(93, 376, 250, 20);
		contentPane.add(brTimeTxt);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 408, 366, 2);
		contentPane.add(separator_3);
		
		JLabel lblWorkoutScreenLayout = new JLabel("Workout Screen Layout:");
		lblWorkoutScreenLayout.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblWorkoutScreenLayout.setBounds(10, 421, 200, 25);
		contentPane.add(lblWorkoutScreenLayout);
		
		cBxTimer = new JCheckBox("Timer");
		cBxTimer.setSelected(true);
		cBxTimer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cBxTimer.setBounds(10, 453, 59, 23);
		contentPane.add(cBxTimer);
		
		cBxCurrForce = new JCheckBox("Current Force");
		cBxCurrForce.setSelected(true);
		cBxCurrForce.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cBxCurrForce.setBounds(10, 479, 109, 23);
		contentPane.add(cBxCurrForce);
		
		cBxForceGoal = new JCheckBox("Force Goal");
		cBxForceGoal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cBxForceGoal.setBounds(10, 505, 91, 23);
		contentPane.add(cBxForceGoal);
		
		cBxFreqGoal = new JCheckBox("Frequency Goal");
		cBxFreqGoal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cBxFreqGoal.setBounds(135, 479, 126, 23);
		contentPane.add(cBxFreqGoal);
		
		cBxCurrFreq = new JCheckBox("Current Frequency");
		cBxCurrFreq.setSelected(true);
		cBxCurrFreq.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cBxCurrFreq.setBounds(135, 453, 139, 23);
		contentPane.add(cBxCurrFreq);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 535, 366, 2);
		contentPane.add(separator_4);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnCreate.setBounds(219, 548, 111, 36);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(forceAction.getItemAt(forceAction.getSelectedIndex()) == "--Select An Action--")
				{
					//Popup modal prompting to select action to preform for force
				}
				else if(freqAction.getItemAt(freqAction.getSelectedIndex()) == "--Select An Action--")
				{
					//Popup modal prompting to select action to preform for frequency
				}
				else {
					visTime = (cBxTimer.isSelected()) ? "TRUE" : "FALSE";
					visCurForce = (cBxCurrForce.isSelected())  ? "TRUE" : "FALSE";
					visForceGoal = (cBxForceGoal.isSelected())  ? "TRUE" : "FALSE";
					visCurFreq =  (cBxCurrFreq.isSelected())  ? "TRUE" : "FALSE";
					visFreqGoal =  (cBxFreqGoal.isSelected())  ? "TRUE" : "FALSE";
					
					Workout newlyCreatedWorkout = new Workout(txtWrktName.getText(), forceAction.getItemAt(forceAction.getSelectedIndex()), freqAction.getItemAt(freqAction.getSelectedIndex()), nRndsTxt.getText(), rndTimeTxt.getText(), brTimeTxt.getText(), visCurForce, visForceGoal, visCurFreq, visFreqGoal, visTime);
					try {
						controlObj.submitWrktCreation(newlyCreatedWorkout);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		    }
		});
		contentPane.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnCancel.setBounds(54, 548, 111, 36);
		btnCancel.addActionListener(new ActionListener() {
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
		
		JLabel lblMin = new JLabel("minutes");
		lblMin.setFont(new Font("Segoe UI Light", Font.PLAIN, 10));
		lblMin.setBounds(349, 306, 37, 14);
		contentPane.add(lblMin);
		
		JLabel lblMinutes = new JLabel("minutes");
		lblMinutes.setFont(new Font("Segoe UI Light", Font.PLAIN, 10));
		lblMinutes.setBounds(349, 379, 37, 14);
		contentPane.add(lblMinutes);
	}
}
