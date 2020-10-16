package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class TriviaClientViewTriviaScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientViewTriviaScreen frame = new TriviaClientViewTriviaScreen();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TriviaClientViewTriviaScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 140, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(216, 191, 216));
		menuBar.setBounds(0, 0, 974, 22);
		getContentPane().add(menuBar);
		
		JMenu myMenu = new JMenu("File");
		myMenu.setHorizontalAlignment(SwingConstants.CENTER);
		myMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		menuBar.add(myMenu);
		
		JMenuItem myMenuItem_Settings = new JMenuItem("Settings");
		myMenu.add(myMenuItem_Settings);
		
		JMenuItem myMenuItem_ContactUs = new JMenuItem("Contact Us");
		myMenuItem_ContactUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_contact) 
			{
				JOptionPane.showMessageDialog(null, "Email us at: rohanlimaye20@gmail.com");
			}
		});
		myMenu.add(myMenuItem_ContactUs);
		
		JLabel lblSelectTopicForView = new JLabel("Select Topic for which you want to view trivia");
		lblSelectTopicForView.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTopicForView.setForeground(Color.YELLOW);
		lblSelectTopicForView.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 22));
		lblSelectTopicForView.setBounds(238, 33, 480, 54);
		contentPane.add(lblSelectTopicForView);
		
		JRadioButton rdbtnTopic1 = new JRadioButton("");
		rdbtnTopic1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic1.setBounds(370, 94, 216, 34);
		contentPane.add(rdbtnTopic1);
		
		JRadioButton rdbtnTopic2 = new JRadioButton("");
		rdbtnTopic2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic2.setBounds(370, 134, 216, 34);
		contentPane.add(rdbtnTopic2);
		
		JRadioButton rdbtnTopic3 = new JRadioButton("");
		rdbtnTopic3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic3.setBounds(370, 180, 216, 34);
		contentPane.add(rdbtnTopic3);
		
		JButton btnTopicForView = new JButton("Ok");
		btnTopicForView.setForeground(Color.DARK_GRAY);
		btnTopicForView.setFont(new Font("Georgia", Font.BOLD, 20));
		btnTopicForView.setBackground(Color.WHITE);
		btnTopicForView.setBounds(416, 221, 107, 34);
		contentPane.add(btnTopicForView);
		
		JButton btnShowTrivia = new JButton("Show Trivia");
		btnShowTrivia.setForeground(Color.DARK_GRAY);
		btnShowTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnShowTrivia.setBackground(Color.WHITE);
		btnShowTrivia.setBounds(370, 284, 216, 46);
		contentPane.add(btnShowTrivia);
	}
}
