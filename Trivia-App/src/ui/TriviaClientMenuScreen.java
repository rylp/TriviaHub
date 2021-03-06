package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class TriviaClientMenuScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientMenuScreen frame = new TriviaClientMenuScreen();
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
	public TriviaClientMenuScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(216, 191, 216));
		menuBar.setBounds(0, 0, 984, 22);
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
				JOptionPane.showMessageDialog(contentPane, "Email us at: rohanlimaye20@gmail.com");
			}
		});
		myMenu.add(myMenuItem_ContactUs);
		
		JLabel lblChooseTask = new JLabel("What do you want to do?");
		lblChooseTask.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 28));
		lblChooseTask.setBounds(308, 59, 356, 61);
		contentPane.add(lblChooseTask);
		
		JButton btnAddTrivia = new JButton("Add Trivia");
		btnAddTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_add) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientAddTriviaScreen add_trivia=new TriviaClientAddTriviaScreen();
				add_trivia.setVisible(true);
			}
		});
		btnAddTrivia.setForeground(Color.DARK_GRAY);
		btnAddTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnAddTrivia.setBackground(Color.WHITE);
		btnAddTrivia.setBounds(277, 160, 151, 51);
		contentPane.add(btnAddTrivia);
		
		JButton btnDeleteTrivia = new JButton("Delete Trivia");
		btnDeleteTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_delete) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientDeleteTriviaScreen del_trivia=new TriviaClientDeleteTriviaScreen();
				del_trivia.setVisible(true);
			}
		});
		btnDeleteTrivia.setForeground(Color.DARK_GRAY);
		btnDeleteTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnDeleteTrivia.setBackground(Color.WHITE);
		btnDeleteTrivia.setBounds(490, 160, 174, 51);
		contentPane.add(btnDeleteTrivia);
		
		JButton btnViewTrivia = new JButton("View Trivia");
		btnViewTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_view) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientViewTriviaScreen view_trivia=new TriviaClientViewTriviaScreen();
				view_trivia.setVisible(true);
			}
		});
		btnViewTrivia.setForeground(Color.DARK_GRAY);
		btnViewTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnViewTrivia.setBackground(Color.WHITE);
		btnViewTrivia.setBounds(391, 233, 174, 51);
		contentPane.add(btnViewTrivia);
		
		JButton btnLikeDislikeTrivia = new JButton("Like/Dislike Trivia");
		btnLikeDislikeTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_like) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientLikeTriviaScreen like_trivia=new TriviaClientLikeTriviaScreen();
				like_trivia.setVisible(true);
			}
		});
		btnLikeDislikeTrivia.setForeground(Color.DARK_GRAY);
		btnLikeDislikeTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnLikeDislikeTrivia.setBackground(Color.WHITE);
		btnLikeDislikeTrivia.setBounds(351, 311, 254, 51);
		contentPane.add(btnLikeDislikeTrivia);
		
		JButton btnFeedback = new JButton("Give Us Feedback?");
		btnFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_feedback) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientFeedbackScreen feedback=new TriviaClientFeedbackScreen();
				feedback.setVisible(true);
				
			}
		});
		btnFeedback.setForeground(Color.DARK_GRAY);
		btnFeedback.setFont(new Font("Georgia", Font.BOLD, 18));
		btnFeedback.setBackground(Color.WHITE);
		btnFeedback.setBounds(376, 472, 212, 42);
		contentPane.add(btnFeedback);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_logout) 
			{
				
				int sure=JOptionPane.showConfirmDialog(contentPane,"Do you really want to log out?", "Confirm Log Out",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				
				if(sure==JOptionPane.YES_OPTION)
				{
					//Logging Out
					ui.Constants.myEmail=null;
					ui.Constants.myKey=null;
					ui.Constants.myUserId=-1;
					
					JOptionPane.showMessageDialog(contentPane, "Logged Out!");
					
					contentPane.setVisible(false);
					dispose();
					
					TriviaClientLoginScreen screen=new TriviaClientLoginScreen();
					screen.setVisible(true);
				}
			}
		});
		btnLogOut.setForeground(Color.DARK_GRAY);
		btnLogOut.setFont(new Font("Georgia", Font.BOLD, 20));
		btnLogOut.setBackground(Color.WHITE);
		btnLogOut.setBounds(406, 387, 151, 51);
		contentPane.add(btnLogOut);
	}
}
