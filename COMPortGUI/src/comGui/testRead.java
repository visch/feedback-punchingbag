package comGui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import jssc.*;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class testRead extends JFrame {

	private JPanel contentPane;
	testRead frame = this;
	SerialPortReaderWrapper comReader;
	StringBuilder paneText;
	private String portName = "COM7";
	JTextArea outputTPane;
	StyledDocument doc;
	SimpleAttributeSet keyWord;
	RawDataStorage messageLists;
	Punches punches;
	int comPortFlag = 0;


	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//final testRead frame = new testRead("COM8");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public testRead(String COMPortName) {
		portName = COMPortName;

		setTitle("COM Port Read Out");
		setIconImage(Toolkit.getDefaultToolkit().getImage(testRead.class.getResource("/Images/PFPBLogo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		outputTPane = new JTextArea();
		outputTPane.setColumns(20);
		outputTPane.setRows(1000);
		outputTPane.setEditable(false);
		outputTPane.setBounds(10, 46, 333, 205);
		outputTPane.setText("dd");
		contentPane.add(outputTPane);

		JLabel lblDataFromSerial = new JLabel("Data From Serial Port:");
		lblDataFromSerial.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblDataFromSerial.setBounds(10, 11, 309, 28);
		contentPane.add(lblDataFromSerial);

		JButton btnClosePort = new JButton("Close Port");
		btnClosePort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					comReader.serialPort.closePort();//Close serial port
					
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.dispose();
			}
		});

		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.BLACK);
		StyleConstants.setBackground(keyWord, Color.WHITE);
		StyleConstants.setBold(keyWord, false);

		btnClosePort.setBounds(361, 228, 89, 23);
		contentPane.add(btnClosePort);
		
		frame.setVisible(true);
		
		//Start reading through our reader
		loopReader(); //Calls our loop reader function
		//testRead.main(null);
	}

	public void loopReader() {	
		messageLists = new RawDataStorage();
		punches = new Punches(messageLists); 
		
		/************************STARTING THE SERIAL PORT HANDLER THREAD***********************************/
		Thread serialPortSerivce = new Thread(new SerialPortReaderWrapper(portName, frame, messageLists));		
		serialPortSerivce.start();
		
		//TODO Figure out what timing we actually want here
		Timer timer = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(comPortFlag == 1)
				//{
					if(RawDataStorage.numOfMessages != 0)
					{						
						try {
							RawDataStorage.outputAccelData();
							punches.collectPunches();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					//comPortFlag = 0;
					//comReader.setEventHand();
				//}
			}
		});
		
		timer.setRepeats(true);
		timer.start();
	}
	//Getter
	public int getComPortFlag(){
		return this.comPortFlag;
	}
	//Setter
	public void setComPortFlag(int value) {
		this.comPortFlag = value;
	}
}

