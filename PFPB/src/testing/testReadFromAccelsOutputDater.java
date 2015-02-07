package testing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JSeparator;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jssc.*;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class testReadFromAccelsOutputDater extends JFrame {

	private JPanel contentPane;
	static JButton btnNewButton;
	static JComboBox<String> comboBox;
	static String[] portNames;
	static String comPortSelected = " ";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final testReadFromAccelsOutputDater frame = new testReadFromAccelsOutputDater();
					frame.setVisible(true);

					Timer timer = new Timer(4000, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String[] tempPortNames = SerialPortList.getPortNames();
							if(tempPortNames != portNames) {
								portNames = tempPortNames;
								comboBox.removeAllItems();
								for(int i = 0; i < portNames.length; i++){
									comboBox.addItem(portNames[i]);
								}
							}
						}
					});
					timer.setRepeats(true);
					timer.start();

					ActionListener subBtnClick = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent actionEvent) {
							comPortSelected = comboBox.getSelectedItem().toString();
							testRead outputWindow = new testRead(comPortSelected);
							outputWindow.setVisible(true);
							frame.dispose();


						}
					};

					btnNewButton.addActionListener(subBtnClick);

				} 
				
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public testReadFromAccelsOutputDater() {
		setTitle("COM Port Selection Tool");
		setIconImage(Toolkit.getDefaultToolkit().getImage(testReadFromAccelsOutputDater.class.getResource("/Images/PFPBLogo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblChooseTheCom = new JLabel("Choose the COM Port that the PFPB is connected to:");
		lblChooseTheCom.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblChooseTheCom.setBounds(21, 91, 414, 25);
		contentPane.add(lblChooseTheCom);

		JLabel lblNewLabel = new JLabel("PFPB Connection Setup");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 42));
		lblNewLabel.setBounds(18, 0, 420, 64);
		contentPane.add(lblNewLabel);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(21, 127, 414, 25);


		portNames = SerialPortList.getPortNames();
		for(int i = 0; i < portNames.length; i++){
			comboBox.addItem(portNames[i]);
		}

		contentPane.add(comboBox);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 78, 437, 2);
		contentPane.add(separator);

		btnNewButton = new JButton("OK");
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton.setBounds(173, 169, 115, 23);
		contentPane.add(btnNewButton);
	}
}
