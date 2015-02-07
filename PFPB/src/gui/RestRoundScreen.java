package gui;

import javax.media.MediaException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import backend.*;
import backend.datatypes.*;

@SuppressWarnings("serial")
public class RestRoundScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtminsec;
	GUIController controlObj;
	Workout currentWorkout;
	int timerCheck;
	Timer roundLoopTimer;
	
	/**
	 * Create the frame.
	 * @param guiController 
	 */
	public RestRoundScreen(GUIController guiController, Workout currWkrt) {
		controlObj = guiController;
		this.currentWorkout = currWkrt;
		
		setTitle("Resting Round...");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RestRoundScreen.class.getResource("/Images/PFPBLogo.png")));
		setBounds(new Rectangle(100, 100, 780, 407));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Rest Round");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 57));
		lblNewLabel.setBounds(233, 11, 298, 68);
		contentPane.add(lblNewLabel);
		
		txtminsec = new JTextField();
		txtminsec.setHorizontalAlignment(SwingConstants.CENTER);
		txtminsec.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtminsec.setEditable(false);
		txtminsec.setFont(new Font("Segoe UI Light", Font.PLAIN, 50));
		txtminsec.setText("0min 00sec");
		txtminsec.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtminsec.setBackground(new Color(255, 255, 204));
		txtminsec.setBounds(196, 131, 392, 132);
		txtminsec.setColumns(10);
		contentPane.add(txtminsec);

		runScreen();
	}
	
	public void runScreen()
	{
		timerCheck = (int)(Double.parseDouble(currentWorkout.getBreakTime())*60);

		//Main loop to execute every second
		roundLoopTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerCheck--;
				if(timerCheck <= 0)
				{
					//Stop the timer from executing further, and dispose work out frame
					roundLoopTimer.setRepeats(false);
					roundLoopTimer.stop();
					try {
						controlObj.restRndToWorkoutTrans();
					} catch (IOException | MediaException
							| InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					//Update the Round Timer
					if((timerCheck / 60) == 0)
					{
						//Only seconds left in the round
						txtminsec.setText((timerCheck % 60) + "sec");
					}
					else 
					{
						//Minutes and seconds left in the round
						txtminsec.setText((timerCheck / 60) + "min " + (timerCheck % 60) + "sec");
					}
					
				}
			}
		});
		
		roundLoopTimer.setRepeats(true);
		roundLoopTimer.start();	
	}
	
}
