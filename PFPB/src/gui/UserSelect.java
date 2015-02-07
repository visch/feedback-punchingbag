package gui;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EmptyBorder;

import backend.*;
import backend.datatypes.User;
import backend.datatypes.Workout;

import java.awt.Dimension;


@SuppressWarnings("serial")
public class UserSelect extends JFrame{

	private JPanel contentPane;
	ButtonGroup usrBtns = new ButtonGroup();
	static HashMap<String, Component> componentMap;
	static JButton btnStartWorkout;
	public JComboBox<String> userComboBox;
	public JComboBox<String> workoutComboBox;
	static JButton btnBrowse1;
	static JButton btnBrowse2;
	static JButton btnBrowse3;
	static JButton btnBrowse4;
	static JButton btnBrowse5;
	static JButton btnBrowse6;
	static JButton btnBrowse7;
	static JButton btnBrowse8;
	static JButton btnBrowse9;
	static JButton btnBrowse10;
	static JTextField songInput1;
	static JTextField songInput2;
	static JTextField songInput3;
	static JTextField songInput4;
	static JTextField songInput5;
	static JTextField songInput6;
	static JTextField songInput10;
	static JTextField songInput9;
	static JTextField songInput8;
	static JTextField songInput7;
	static JFileChooser fileChoose;
	int userIDSelected;
	int workoutIDSelected;
	public HashMap<Integer, User> users;
	public HashMap<Integer, Workout> workouts;
	GUIController controlObj;
	User currentUserSelected = null;
	Workout currentWorkoutSelected = null;

	/**
	 * Create the application.
	 * @param wrkOuts 
	 * @param usrs 
	 * @param guiController 
	 * @wbp.parser.entryPoint
	 */
	public UserSelect(HashMap<Integer, User> usrs, HashMap<Integer, Workout> wrkOuts, GUIController guiController) {
		controlObj = guiController;
		users = usrs;
		workouts = wrkOuts;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserSelect.class.getResource("/Images/PFPBLogo.png")));
		setTitle("User Selection...");
		setBounds(100, 100, 780, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ActionListener browseBtnClick = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JButton srcBtnClicked = (JButton) actionEvent.getSource();						//Get the browse button that has been clicked
				String btnName = srcBtnClicked.getName();										//Get the name of the button that was clicked
				String inputName = "songInput" + btnName.substring(9);    						//Determine the text input to write the path to
				JTextField inputToChange = (JTextField) getComponentByName(inputName);			//Get the component of the input

				int returnValue = fileChoose.showOpenDialog(srcBtnClicked);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChoose.getSelectedFile();
					inputToChange.setText(file.getAbsolutePath());
					//Save File to the database here
				}
			}
		}; 

		JLabel numSong1 = new JLabel("1.");
		numSong1.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong1.setBounds(407, 56, 21, 19);
		contentPane.add(numSong1);

		songInput1 = new JTextField();
		songInput1.setToolTipText("Click \"Browse\" to add a song...");
		songInput1.setName("songInput1");
		songInput1.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput1.setEnabled(false);
		songInput1.setBounds(428, 55, 226, 20);
		contentPane.add(songInput1);
		songInput1.setColumns(10);

		btnBrowse1 = new JButton("Browse...");
		btnBrowse1.setName("btnBrowse1");
		btnBrowse1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse1.setEnabled(false);
		btnBrowse1.setBounds(664, 54, 89, 23);
		btnBrowse1.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse1);

		JLabel numSong2 = new JLabel("2.");
		numSong2.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong2.setBounds(407, 87, 21, 20);
		contentPane.add(numSong2);

		songInput2 = new JTextField();
		songInput2.setToolTipText("Click \"Browse\" to add a song...");
		songInput2.setName("songInput2");
		songInput2.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput2.setEnabled(false);
		songInput2.setColumns(10);
		songInput2.setBounds(428, 87, 226, 20);
		contentPane.add(songInput2);

		btnBrowse2 = new JButton("Browse...");
		btnBrowse2.setName("btnBrowse2");
		btnBrowse2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse2.setEnabled(false);
		btnBrowse2.setBounds(664, 86, 89, 23);
		btnBrowse2.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse2);

		JLabel numSong3 = new JLabel("3.");
		numSong3.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong3.setBounds(407, 120, 21, 14);
		contentPane.add(numSong3);

		songInput3 = new JTextField();
		songInput3.setToolTipText("Click \"Browse\" to add a song...");
		songInput3.setName("songInput3");
		songInput3.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput3.setEnabled(false);
		songInput3.setColumns(10);
		songInput3.setBounds(428, 119, 226, 20);
		contentPane.add(songInput3);

		btnBrowse3 = new JButton("Browse...");
		btnBrowse3.setName("btnBrowse3");
		btnBrowse3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse3.setEnabled(false);
		btnBrowse3.setBounds(664, 118, 89, 23);
		btnBrowse3.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse3);

		JLabel numSong4 = new JLabel("4.");
		numSong4.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong4.setBounds(407, 152, 21, 14);
		contentPane.add(numSong4);

		songInput4 = new JTextField();
		songInput4.setToolTipText("Click \"Browse\" to add a song...");
		songInput4.setName("songInput4");
		songInput4.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput4.setEnabled(false);
		songInput4.setColumns(10);
		songInput4.setBounds(428, 151, 226, 20);
		contentPane.add(songInput4);

		btnBrowse4 = new JButton("Browse...");
		btnBrowse4.setName("btnBrowse4");
		btnBrowse4.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse4.setEnabled(false);
		btnBrowse4.setBounds(664, 150, 89, 23);
		btnBrowse4.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse4);

		JLabel numSong5 = new JLabel("5.");
		numSong5.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong5.setBounds(407, 184, 21, 14);
		contentPane.add(numSong5);

		songInput5 = new JTextField();
		songInput5.setToolTipText("Click \"Browse\" to add a song...");
		songInput5.setName("songInput5");
		songInput5.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput5.setEnabled(false);
		songInput5.setColumns(10);
		songInput5.setBounds(428, 183, 226, 20);
		contentPane.add(songInput5);

		btnBrowse5 = new JButton("Browse...");
		btnBrowse5.setName("btnBrowse5");
		btnBrowse5.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse5.setEnabled(false);
		btnBrowse5.setBounds(664, 182, 89, 23);
		btnBrowse5.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse5);

		JLabel numSong6 = new JLabel("6.");
		numSong6.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong6.setBounds(407, 216, 32, 14);
		contentPane.add(numSong6);

		songInput6 = new JTextField();
		songInput6.setToolTipText("Click \"Browse\" to add a song...");
		songInput6.setName("songInput6");
		songInput6.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput6.setEnabled(false);
		songInput6.setColumns(10);
		songInput6.setBounds(428, 215, 226, 20);
		contentPane.add(songInput6);

		btnBrowse6 = new JButton("Browse...");
		btnBrowse6.setName("btnBrowse6");
		btnBrowse6.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse6.setEnabled(false);
		btnBrowse6.setBounds(664, 214, 89, 23);
		btnBrowse6.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse6);

		JLabel numSong7 = new JLabel("7.");
		numSong7.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong7.setBounds(407, 248, 21, 14);
		contentPane.add(numSong7);

		songInput7 = new JTextField();
		songInput7.setToolTipText("Click \"Browse\" to add a song...");
		songInput7.setName("songInput7");
		songInput7.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput7.setEnabled(false);
		songInput7.setColumns(10);
		songInput7.setBounds(428, 247, 226, 20);
		contentPane.add(songInput7);

		btnBrowse7 = new JButton("Browse...");
		btnBrowse7.setName("btnBrowse7");
		btnBrowse7.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse7.setEnabled(false);
		btnBrowse7.setBounds(664, 246, 89, 23);
		btnBrowse7.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse7);

		JLabel numSong8 = new JLabel("8.");
		numSong8.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong8.setBounds(407, 280, 32, 14);
		contentPane.add(numSong8);

		songInput8 = new JTextField();
		songInput8.setToolTipText("Click \"Browse\" to add a song...");
		songInput8.setName("songInput8");
		songInput8.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput8.setEnabled(false);
		songInput8.setColumns(10);
		songInput8.setBounds(428, 279, 226, 20);
		contentPane.add(songInput8);

		btnBrowse8 = new JButton("Browse...");
		btnBrowse8.setName("btnBrowse8");
		btnBrowse8.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse8.setEnabled(false);
		btnBrowse8.setBounds(664, 278, 89, 23);
		btnBrowse8.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse8);

		JLabel numSong9 = new JLabel("9.");
		numSong9.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong9.setBounds(407, 312, 32, 14);
		contentPane.add(numSong9);

		songInput9 = new JTextField();
		songInput9.setToolTipText("Click \"Browse\" to add a song...");
		songInput9.setName("songInput9");
		songInput9.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput9.setEnabled(false);
		songInput9.setColumns(10);
		songInput9.setBounds(428, 311, 226, 20);
		contentPane.add(songInput9);

		btnBrowse9 = new JButton("Browse...");
		btnBrowse9.setName("btnBrowse9");
		btnBrowse9.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse9.setEnabled(false);
		btnBrowse9.setBounds(664, 310, 89, 23);
		btnBrowse9.addActionListener(browseBtnClick);        
		contentPane.add(btnBrowse9);

		JLabel numSong10 = new JLabel("10.");
		numSong10.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		numSong10.setBounds(400, 344, 42, 14);
		contentPane.add(numSong10);

		songInput10 = new JTextField();
		songInput10.setToolTipText("Click \"Browse\" to add a song...");
		songInput10.setName("songInput10");
		songInput10.setFont(new Font("Segoe UI Light", Font.ITALIC, 11));
		songInput10.setEnabled(false);
		songInput10.setColumns(10);
		songInput10.setBounds(428, 343, 226, 20);
		contentPane.add(songInput10);

		btnBrowse10 = new JButton("Browse...");
		btnBrowse10.setName("btnBrowse10");
		btnBrowse10.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBrowse10.setEnabled(false);
		btnBrowse10.setBounds(664, 342, 89, 23);
		btnBrowse10.addActionListener(browseBtnClick);
		contentPane.add(btnBrowse10);

		btnStartWorkout = new JButton("Start Workout!");
		btnStartWorkout.setEnabled(false);
		btnStartWorkout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnStartWorkout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(currentUserSelected != null && currentWorkoutSelected != null)
				{
					currentUserSelected.clearMusic();
					updateUserMusic();
					try {
						controlObj.usrSelectToWorkoutTrans(currentUserSelected, currentWorkoutSelected);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnStartWorkout.setBounds(195, 324, 119, 23);
		contentPane.add(btnStartWorkout);

		fileChoose = new JFileChooser();
		createComponentMap();

		JLabel lblPlaylistSelection = new JLabel("Playlist Selection:");
		lblPlaylistSelection.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
		lblPlaylistSelection.setBounds(407, 10, 226, 32);
		contentPane.add(lblPlaylistSelection);

		JLabel lblChooseAUser = new JLabel("Choose A User:");
		lblChooseAUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseAUser.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
		lblChooseAUser.setBounds(172, 56, 165, 32);
		contentPane.add(lblChooseAUser);	

		userComboBox = new JComboBox<String>();
		String userNames[];
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("--Select a User--" + "\t");
		for (Map.Entry<Integer, User> entry : users.entrySet()) {
			strBuild.append(entry.getValue().getName());
			strBuild.append("\t");
		}
		userNames = strBuild.toString().split("\t");
		userComboBox.setModel(new DefaultComboBoxModel<String>(userNames));
		userComboBox.setBounds(181, 101, 146, 32);
		userComboBox.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {				
				//get the User Name from the combo box
				@SuppressWarnings("unchecked")
				JComboBox<String> source = ((JComboBox<String>)e.getSource());
				userIDSelected = source.getSelectedIndex();
				if(userIDSelected != 0)
				{
					currentUserSelected = (User) users.values().toArray()[userIDSelected-1];
					String songPaths[] = setSongInputTxtBoxes();

					//song input test fields
					songInput1.setEnabled(true);
					songInput1.setText(songPaths[0]);
					songInput2.setEnabled(true);
					songInput2.setText(songPaths[1]);
					songInput3.setEnabled(true);
					songInput3.setText(songPaths[2]);
					songInput4.setEnabled(true);
					songInput4.setText(songPaths[3]);
					songInput5.setEnabled(true);
					songInput5.setText(songPaths[4]);
					songInput6.setEnabled(true);
					songInput6.setText(songPaths[5]);
					songInput7.setEnabled(true);
					songInput7.setText(songPaths[6]);
					songInput8.setEnabled(true);
					songInput8.setText(songPaths[7]);
					songInput9.setEnabled(true);
					songInput9.setText(songPaths[8]);
					songInput10.setEnabled(true);
					songInput10.setText(songPaths[9]);

					btnBrowse1.setEnabled(true);
					btnBrowse2.setEnabled(true);
					btnBrowse3.setEnabled(true);
					btnBrowse4.setEnabled(true);
					btnBrowse5.setEnabled(true);
					btnBrowse6.setEnabled(true);
					btnBrowse7.setEnabled(true);
					btnBrowse8.setEnabled(true);
					btnBrowse9.setEnabled(true);
					btnBrowse10.setEnabled(true);
					
					if(!(workoutComboBox.getSelectedItem().toString().contains("Select Workout")))
					{
						//Enable the Start Work out Button if a user has been selected
						btnStartWorkout.setEnabled(true);
					}
				}
				else
				{
					songInput1.setText("Click \"Browse\" to add a song...");
					songInput1.setEnabled(false);					
					songInput2.setText("Click \"Browse\" to add a song...");
					songInput2.setEnabled(false);	                
					songInput3.setText("Click \"Browse\" to add a song...");
					songInput3.setEnabled(false);	                
					songInput4.setText("Click \"Browse\" to add a song...");
					songInput4.setEnabled(false);	                
					songInput5.setText("Click \"Browse\" to add a song...");
					songInput5.setEnabled(false);	                
					songInput6.setText("Click \"Browse\" to add a song...");
					songInput6.setEnabled(false);	                
					songInput7.setText("Click \"Browse\" to add a song...");
					songInput7.setEnabled(false);	                
					songInput8.setText("Click \"Browse\" to add a song...");
					songInput8.setEnabled(false);	                
					songInput9.setText("Click \"Browse\" to add a song...");
					songInput9.setEnabled(false);	                
					songInput10.setText("Click \"Browse\" to add a song...");
					songInput10.setEnabled(false);

					btnBrowse1.setEnabled(false);
					btnBrowse2.setEnabled(false);
					btnBrowse3.setEnabled(false);
					btnBrowse4.setEnabled(false);
					btnBrowse5.setEnabled(false);
					btnBrowse6.setEnabled(false);
					btnBrowse7.setEnabled(false);
					btnBrowse8.setEnabled(false);
					btnBrowse9.setEnabled(false);
					btnBrowse10.setEnabled(false);
					currentUserSelected = null;
					btnStartWorkout.setEnabled(false);
				}
			}
		});
		contentPane.add(userComboBox);

		workoutComboBox = new JComboBox<String>();
		String workOutNames[];
		StringBuilder strBuild2 = new StringBuilder();
		strBuild2.append("--Select Workout--" + "\t");
		for (Map.Entry<Integer, Workout> entry : workouts.entrySet()) {
			strBuild2.append(entry.getValue().getName());
			strBuild2.append("\t");
		}
		workOutNames = strBuild2.toString().split("\t");
		workoutComboBox.setModel(new DefaultComboBoxModel<String>(workOutNames));
		workoutComboBox.setBounds(185, 253, 146, 32);
		workoutComboBox.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {

				//get the Work out Name from the combo box
				@SuppressWarnings("unchecked")
				JComboBox<String> source = ((JComboBox<String>)e.getSource());
				workoutIDSelected = source.getSelectedIndex();
				if(workoutIDSelected != 0)
				{
					currentWorkoutSelected = (Workout) workouts.values().toArray()[workoutIDSelected-1];
					if(!(userComboBox.getSelectedItem().toString().contains("Select a User")))
					{
						//Enable the Start Work out Button if a user has been selected
						btnStartWorkout.setEnabled(true);
					}
				}
				else {
					//we have gone back to no selection of work out so disable the start work out button again
					btnStartWorkout.setEnabled(false);
				}
			}
		});
		contentPane.add(workoutComboBox);

		JLabel lblSelectWorkout = new JLabel("Select Workout:");
		lblSelectWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectWorkout.setFont(new Font("Segoe UI Light", Font.PLAIN, 24));
		lblSelectWorkout.setBounds(180, 216, 157, 29);
		contentPane.add(lblSelectWorkout);

		JButton btnAdministration = new JButton("Administration");
		btnAdministration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controlObj.usrSelectToAdminTrans();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAdministration.setFont(new Font("Segoe UI Light", Font.PLAIN, 10));
		btnAdministration.setBounds(0, 359, 129, 23);
		contentPane.add(btnAdministration);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setVerticalTextPosition(SwingConstants.TOP);
		lblNewLabel.setMaximumSize(new Dimension(129, 340));
		lblNewLabel.setIcon(new ImageIcon(UserSelect.class.getResource("/Images/TallLogo.png")));
		lblNewLabel.setBounds(0, -16, 140, 398);
		contentPane.add(lblNewLabel);
	}

	public void createComponentMap() {
		componentMap = new HashMap<String,Component>();
		Component[] components = contentPane.getComponents();
		for (int i=0; i < components.length; i++) {
			componentMap.put(components[i].getName(), components[i]);
		}
	}

	public static Component getComponentByName(String name) {
		if (componentMap.containsKey(name)) {
			return (Component) componentMap.get(name);
		}
		else return null;
	}

	private String[] setSongInputTxtBoxes() {
		String paths[] = {"Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...","Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...", "Click \"Browse\" to add a song...", "Click \"Browse\" to add a song..."};
		int indexer = 0;
		for (String path : currentUserSelected.getMusic())
		{
			if(path != null && !path.isEmpty() && !path.trim().isEmpty())
			{
				paths[indexer] = path;
				indexer++;
			}
		}
		return paths;
	}

	private void updateUserMusic() {
		if(!(songInput1.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput1.getText().replace('\\', '/'));
		}
		if(!(songInput2.getText().equals("Click \"Browse\" to add a song...")))
		{        
			currentUserSelected.addMusic(songInput2.getText().replace('\\', '/'));                        
		}
		if(!(songInput3.getText().equals("Click \"Browse\" to add a song...")))
		{         
			currentUserSelected.addMusic(songInput3.getText().replace('\\', '/'));                   
		}
		if(!(songInput4.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput4.getText().replace('\\', '/'));                       
		}
		if(!(songInput5.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput5.getText().replace('\\', '/'));                      
		}
		if(!(songInput6.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput6.getText().replace('\\', '/'));                       
		}
		if(!(songInput7.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput7.getText().replace('\\', '/'));                     
		}
		if(!(songInput8.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput8.getText().replace('\\', '/'));                        
		}
		if(!(songInput9.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput9.getText().replace('\\', '/'));                      
		}
		if(!(songInput10.getText().equals("Click \"Browse\" to add a song...")))
		{
			currentUserSelected.addMusic(songInput10.getText().replace('\\', '/'));                        
		}
	}
}

