package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import java.awt.Insets;

import javax.swing.JButton;

import backend.GUIController;
import backend.RawDataStorage;
import backend.SerialPortReaderWrapper;
import backend.datatypes.Punches;
import backend.datatypes.Round;
import backend.datatypes.Session;
import backend.datatypes.User;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

//TODO: Implement the average force and Frequency tracking somewhere in this code file
//TODO: Make the baseline screen disappear and merge it with the workout screen
public class BaselineScreen extends JFrame {
	JFrame thisBaselineScr;
	JPanel contentPane;
	private JTextField txtminsec;
	int timerCheck;
	int numberOfRounds = 1;
	private double forceNFreq[];
	private List<Double> BaseLineForces = new ArrayList<Double>();
	private List<Double> BaseLineFrqs = new ArrayList<Double>();
	JButton btnContinueRounds;
	JButton btnSubmitBaseline;
	Timer baseLineTimer;
	BaselineScreen self = this;
	User newUserInCreation;
	GUIController controlObj;
	RawDataStorage messagesList;
	Punches punches;
	SerialPortReaderWrapper runnable;
	Thread serialPortSerivce;
	Session sezzion;
	private int comPortFlag = 0;
	int rndTime;
	private String portName;
	double fullRes[];
	int currentTime = 0;
	private int timeChunk=5; //Time Chunk for Punches and Frequency to be counted

	/**
	 * Create the frame.
	 * @param guiController 
	 * @param userBeingCreated 
	 * @throws Exception 
	 */
	public BaselineScreen(GUIController guiController, User userBeingCreated, String comPortNm) throws Exception {		
		controlObj = guiController;
		this.newUserInCreation = userBeingCreated;
		this.portName = comPortNm;
		initialize();
	}
	
	public void initialize() throws Exception {
		//Write the user to the database
		//Setup baseline session in Database
		sezzion = new Session(); 
		sezzion.setForceCutOffHigh(""+newUserInCreation.getMaxGoalForce());
		sezzion.setForceCutOffLow(""+newUserInCreation.getMinGoalForce());
		sezzion.setFrequencyCutOffHigh(""+newUserInCreation.getMaxGoalFrequency());
		sezzion.setForceCutOffLow(""+newUserInCreation.getMinGoalFrequency());
		sezzion.setWorkouts_id(-1); //-1 to signify baseline
		sezzion.setUsers_id(newUserInCreation.getId());
		sezzion=controlObj.getDbHelperObj().writeNewSession(sezzion);
		//End writing to the database 
		
     	setIconImage(Toolkit.getDefaultToolkit().getImage(BaselineScreen.class.getResource("/Images/PFPBLogo.png")));
		setTitle("Baseline In Progress...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBaselineSession = new JLabel("Baseline Session");
		lblBaselineSession.setFont(new Font("Segoe UI Light", Font.PLAIN, 48));
		lblBaselineSession.setBounds(61, 11, 325, 54);
		contentPane.add(lblBaselineSession);
		
		txtminsec = new JTextField();
		txtminsec.setText(" ");
		txtminsec.setMargin(new Insets(2, 4, 2, 4));
		txtminsec.setHorizontalAlignment(SwingConstants.CENTER);
		txtminsec.setFont(new Font("Segoe UI", Font.PLAIN, 42));
		txtminsec.setEditable(false);
		txtminsec.setColumns(10);
		txtminsec.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtminsec.setBackground(new Color(255, 255, 204));
		txtminsec.setBounds(80, 138, 288, 84);
		contentPane.add(txtminsec);
		
		JLabel lblTimeRemaining = new JLabel("Time Remaining:");
		lblTimeRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeRemaining.setFont(new Font("Segoe UI Light", Font.PLAIN, 32));
		lblTimeRemaining.setBounds(84, 86, 279, 43);
		contentPane.add(lblTimeRemaining);
		
		btnSubmitBaseline = new JButton("Submit Baseline");
		btnSubmitBaseline.setEnabled(false);
		btnSubmitBaseline.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnSubmitBaseline.setBounds(257, 256, 129, 31);
		btnSubmitBaseline.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				try {
					double forceAvg = 0;
					double freqAvg = 0;
					for(int i=0; i<BaseLineForces.size(); i++) {
						forceAvg += BaseLineForces.get(i).doubleValue();
					}
					for(int j=0; j<BaseLineFrqs.size(); j++) {
						freqAvg += BaseLineFrqs.get(j).doubleValue();
					}
					forceAvg = forceAvg / (BaseLineForces.size() - 1);
					freqAvg = freqAvg / (BaseLineFrqs.size() - 1);
					
					newUserInCreation.setAverageForceFromLastSessionRounds(forceAvg);
					newUserInCreation.setAverageFrequencyFromLastSessionRounds(freqAvg);
					
					controlObj.submitBaselineSession(newUserInCreation);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		contentPane.add(btnSubmitBaseline);
		
		btnContinueRounds = new JButton("Continue Rounds");
		btnContinueRounds.setEnabled(false);
		btnContinueRounds.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnContinueRounds.setBounds(61, 256, 139, 31);
		btnContinueRounds.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				//disable the buttons again, and run the baseline session again
				btnContinueRounds.setEnabled(false);
				btnSubmitBaseline.setEnabled(false);
				runBaseLine();
			}
		});
		contentPane.add(btnContinueRounds);
		
		runBaseLine();
	}

	private void runBaseLine() {
		messagesList = new RawDataStorage();
		punches = new Punches(messagesList); 
		timerCheck = 1 * 60;					//3 minute baseline session rounds TODO fix this to grab from the workout
		rndTime = (1 * 60);
		currentTime = 0;

		//Initial set of the timer text in the frame
		txtminsec.setText((timerCheck / 60) + "min " + (timerCheck % 60) + "sec");
		
		/************************STARTING THE SERIAL PORT HANDLER THREAD***********************************/
		runnable = new SerialPortReaderWrapper(portName, this, messagesList);
		serialPortSerivce = new Thread(runnable);		
		serialPortSerivce.start();
		
		//Main loop to execute every second
		baseLineTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerCheck--;
				currentTime++;
				if(timerCheck <= 0)
				{
					try {
						double freq; 
						self.setComPortFlag(1);
						runnable.terminate();
						serialPortSerivce.join();
						
						punches.collectPunches();
						fullRes = punches.grabEntireRoundResults();
						BaseLineForces.add(fullRes[0]);
						BaseLineFrqs.add(fullRes[1]);
						
						//Write the round results to the database
						Round currentRound = new Round();
						currentRound.setSessions_id(sezzion.getId());
						currentRound.setAverage_Force(""+fullRes[0]);
						freq=fullRes[1]/rndTime;
						currentRound.setAverage_Frequency(""+freq);
						currentRound=controlObj.getDbHelperObj().writeNewRound(currentRound);
						controlObj.getDbHelperObj().writeDataPoints(punches.outputPunchData((double)rndTime, currentRound.getId()));
						//Finish writing the round results to the database
						
						//Set 0min/0sec left in the round
						txtminsec.setText("0min 0sec");
						btnContinueRounds.setEnabled(true);
						btnSubmitBaseline.setEnabled(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//Stop the timer from executing further, and enable option buttons
					baseLineTimer.setRepeats(false);
					baseLineTimer.stop();
				}
				else {
					//Update the Round Timer
					if((timerCheck / 60) == 0)
					{
						//Only seconds left in the round
						txtminsec.setText((timerCheck % 60) + "sec");
					}
					else {
						//Minutes and seconds left in the round
						txtminsec.setText((timerCheck / 60) + "min " + (timerCheck % 60) + "sec");
					}
					
					if(currentTime >= timeChunk)
					{
						//Get average force and frequency here
						forceNFreq = punches.getRoundResults((currentTime-timeChunk), currentTime);
					}
					else
					{
						//Get average force and frequency here
						forceNFreq = punches.getRoundResults(0, currentTime);
					}
					
					//Store the Force Value
					BaseLineForces.add(forceNFreq[0]);
					
					//Store the Frequency Value
					BaseLineFrqs.add(forceNFreq[1]);
				}
			}
		});
		
		baseLineTimer.setRepeats(true);
		baseLineTimer.start();
	
	}

	public int getComPortFlag() {
		return comPortFlag;
	}

	public void setComPortFlag(int comPortFlag) {
		this.comPortFlag = comPortFlag;
	}

}
