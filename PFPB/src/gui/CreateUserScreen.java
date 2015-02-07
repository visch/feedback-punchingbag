package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import backend.GUIController;

@SuppressWarnings("serial")
public class CreateUserScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldName;
	private JTextField tFieldAge;
	private JTextField tFieldWeight;
	private JTextField tFieldArm;
	private JComboBox<String> lvlCombBox;
	private JButton btnStartBaselineSession;
	private CreateUserScreen myFrame = this;
	private String userName;
	private int userAge;
	private int userWeight;
	private int userArmSpan;
	private int userHeight;
	private String userLevel;
	GUIController controlObj;
	private JTextField tFieldHeight;

	/**
	 * Create the frame.
	 * @param guiController 
	 */
	public CreateUserScreen(GUIController guiController) {
		controlObj = guiController;
		initialize();
	}
	
	@SuppressWarnings("static-access")
	public void initialize() {
		setFont(new Font("Segoe UI Light", Font.PLAIN, 32));
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreateUserScreen.class.getResource("/Images/PFPBLogo.png")));
		setTitle("Create User");
		setDefaultCloseOperation(myFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 464, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateUser = new JLabel("Create User");
		lblCreateUser.setFont(new Font("Segoe UI Light", Font.PLAIN, 48));
		lblCreateUser.setBounds(106, 11, 235, 47);
		contentPane.add(lblCreateUser);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblName.setBounds(126, 83, 51, 25);
		contentPane.add(lblName);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblAge.setBounds(141, 118, 36, 25);
		contentPane.add(lblAge);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblWeight.setBounds(114, 154, 63, 25);
		contentPane.add(lblWeight);
		
		JLabel lblArmspan = new JLabel("Armspan:");
		lblArmspan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblArmspan.setBounds(101, 190, 76, 25);
		contentPane.add(lblArmspan);
		
		JLabel lblBeginningUserLevel = new JLabel("Beginning \r\nUser Level:");
		lblBeginningUserLevel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblBeginningUserLevel.setBounds(10, 262, 167, 25);
		contentPane.add(lblBeginningUserLevel);
		
		txtFieldName = new JTextField();
		txtFieldName.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		txtFieldName.setColumns(10);
		txtFieldName.setBounds(187, 82, 251, 26);
		txtFieldName.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(!(txtFieldName.getText().equals(""))) {
					userName = txtFieldName.getText();
					tFieldAge.setEnabled(true);
				}
			}
		});
		contentPane.add(txtFieldName);
		
		
		tFieldAge = new JTextField();
		tFieldAge.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		tFieldAge.setColumns(10);
		tFieldAge.setBounds(187, 118, 251, 26);
		tFieldAge.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(!(tFieldAge.getText().equals(""))) {
					userAge = Integer.parseInt(tFieldAge.getText());
					tFieldWeight.setEnabled(true);
				}
			}
		});
		contentPane.add(tFieldAge);
		
		tFieldWeight = new JTextField();
		tFieldWeight.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		tFieldWeight.setColumns(10);
		tFieldWeight.setBounds(187, 154, 251, 26);
		tFieldWeight.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(!(tFieldWeight.getText().equals(""))) {
					userWeight = Integer.parseInt(tFieldWeight.getText());
					tFieldArm.setEditable(true);
				}
			}
		});
		contentPane.add(tFieldWeight);
		
		tFieldArm = new JTextField();
		tFieldArm.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		tFieldArm.setColumns(10);
		tFieldArm.setBounds(187, 190, 251, 26);
		tFieldArm.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(!(tFieldArm.getText().equals(""))) {
					userArmSpan = Integer.parseInt(tFieldArm.getText());
					tFieldHeight.setEnabled(true);
				}
			}
		});
		contentPane.add(tFieldArm);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setToolTipText("User height in inches");
		lblHeight.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblHeight.setBounds(101, 226, 76, 25);
		contentPane.add(lblHeight);
		
		tFieldHeight = new JTextField();
		tFieldHeight.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		tFieldHeight.setColumns(10);
		tFieldHeight.setBounds(187, 226, 251, 26);
		tFieldHeight.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(!(tFieldArm.getText().equals(""))) {
					userHeight = Integer.parseInt(tFieldHeight.getText());
					lvlCombBox.setEnabled(true);
				}
			}
		});
		contentPane.add(tFieldHeight);
		
		lvlCombBox = new JComboBox<String>();
		lvlCombBox.setEditable(true);
		lvlCombBox.setModel(new DefaultComboBoxModel<String>(new String[] {"---Select Level---", "Beginner", "Intermediate", "Expert", "Unknown"}));
		lvlCombBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		lvlCombBox.setBounds(187, 263, 141, 25);
		lvlCombBox.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(!(lvlCombBox.getSelectedItem().toString().equals("---Select Level---")))
				{
					userLevel = lvlCombBox.getSelectedItem().toString();
					btnStartBaselineSession.setEnabled(true);
				}
			}
		});
		contentPane.add(lvlCombBox);
		
		btnStartBaselineSession = new JButton("Start Baseline Session");
		btnStartBaselineSession.setEnabled(false);
		btnStartBaselineSession.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		btnStartBaselineSession.setBounds(248, 298, 150, 23);
		btnStartBaselineSession.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				userName = txtFieldName.getText();
				userAge = Integer.parseInt(tFieldAge.getText());
				userWeight = Integer.parseInt(tFieldWeight.getText());
				userArmSpan = Integer.parseInt(tFieldArm.getText());
				userHeight = Integer.parseInt(tFieldHeight.getText());
				userLevel = lvlCombBox.getSelectedItem().toString();
				controlObj.createUsrToBaseLineTrans(userName, userAge, userWeight, userArmSpan, userHeight, userLevel);
			}
		});
		contentPane.add(btnStartBaselineSession);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		btnCancel.setBounds(49, 299, 150, 23);
		btnCancel.addActionListener(new ActionListener () {
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
	}
}
