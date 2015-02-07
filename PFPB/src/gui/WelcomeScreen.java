package gui;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class WelcomeScreen extends JFrame{

	private JPanel contentPane;

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	public WelcomeScreen() throws FontFormatException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	private void initialize() throws FontFormatException, IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeScreen.class.getResource("/Images/PFPBLogo.png")));
		setTitle("Performance Feedback Punching Bag System");
		setBounds(100, 100, 780, 407);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Performance Feedback Punching Bag System");
		lblNewLabel.setBounds(10, 12, 744, 57);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		contentPane.add(lblNewLabel);
		
		JLabel lblDerekVisch = new JLabel("Derek Visch");
		lblDerekVisch.setHorizontalAlignment(SwingConstants.CENTER);
		lblDerekVisch.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		lblDerekVisch.setBounds(637, 344, 80, 16);
		contentPane.add(lblDerekVisch);
		
		JLabel lblAlexanderMclean = new JLabel("Alexander McLean");
		lblAlexanderMclean.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlexanderMclean.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		lblAlexanderMclean.setBounds(624, 331, 106, 16);
		contentPane.add(lblAlexanderMclean);
		
		JLabel lblRyanBowman = new JLabel("Ryan Bowman");
		lblRyanBowman.setHorizontalAlignment(SwingConstants.CENTER);
		lblRyanBowman.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		lblRyanBowman.setBounds(632, 317, 90, 16);
		contentPane.add(lblRyanBowman);
		
		JLabel lblDesignCreated = new JLabel("Designed & Created By:");
		lblDesignCreated.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesignCreated.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblDesignCreated.setBounds(601, 298, 153, 20);
		contentPane.add(lblDesignCreated);
		
		JLabel lblSponsoredBy = new JLabel("Sponsored By:");
		lblSponsoredBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblSponsoredBy.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblSponsoredBy.setBounds(73, 298, 97, 16);
		contentPane.add(lblSponsoredBy);
		
		JLabel lblNeilDeochand = new JLabel("Neil Deochand");
		lblNeilDeochand.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeilDeochand.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		lblNeilDeochand.setBounds(81, 315, 80, 16);
		contentPane.add(lblNeilDeochand);
		
		JLabel lblDaleGregory = new JLabel("Dale Gregory");
		lblDaleGregory.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaleGregory.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		lblDaleGregory.setBounds(84, 329, 74, 16);
		contentPane.add(lblDaleGregory);
		
		JLabel lblDrFuqua = new JLabel("Dr. Fuqua & WMU Psychology Department");
		lblDrFuqua.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrFuqua.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		lblDrFuqua.setBounds(10, 342, 223, 16);
		contentPane.add(lblDrFuqua);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(WelcomeScreen.class.getResource("/Images/PFPBLogo.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		label.setBounds(282, 84, 200, 200);
		contentPane.add(label);
	}
}
