package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class TriviaClientOpeningScreen {

	private JFrame frame;
	JButton btnHomeLogin=null;
	JButton btnHomeRegister=null;
	JLabel lblHostIP=null;
	JLabel lblHostPort=null;
	JLabel lblDisplayHostIP=null;
	JLabel lblDisplayHostPort=null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientOpeningScreen window = new TriviaClientOpeningScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TriviaClientOpeningScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_connect) {
				
				boolean result=Client.communicationEndpoint();
				
				if(result)
				{
					btnHomeLogin.setEnabled(true);
					btnHomeRegister.setEnabled(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Can't Connect to Server! "+"IP: "+lblDisplayHostIP.getText()+" Port: "+lblDisplayHostPort.getText());
				}
			}
		});
		btnConnect.setBackground(Color.WHITE);
		btnConnect.setForeground(Color.DARK_GRAY);
		btnConnect.setFont(new Font("Georgia", Font.BOLD, 20));
		btnConnect.setBounds(370, 328, 157, 50);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblWelcome = new JLabel("Welcome to TriviaHub");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setForeground(Color.BLACK);
		lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblWelcome.setBounds(273, 50, 399, 80);
		frame.getContentPane().add(lblWelcome);
		
		lblHostIP = new JLabel("Host IP");
		lblHostIP.setHorizontalAlignment(SwingConstants.CENTER);
		lblHostIP.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHostIP.setBounds(328, 159, 110, 45);
		frame.getContentPane().add(lblHostIP);
		lblHostIP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblHostPort = new JLabel("Host Port");
		lblHostPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblHostPort.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHostPort.setBounds(328, 215, 110, 50);
		frame.getContentPane().add(lblHostPort);
		lblHostPort.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblDisplayHostIP = new JLabel("127.0.0.1");
		lblDisplayHostIP.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayHostIP.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblDisplayHostIP.setBounds(448, 159, 157, 45);
		frame.getContentPane().add(lblDisplayHostIP);
		lblDisplayHostIP.setBorder(new LineBorder(Color.BLACK));
		
		lblDisplayHostPort = new JLabel("9997");
		lblDisplayHostPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayHostPort.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblDisplayHostPort.setBounds(448, 215, 157, 50);
		frame.getContentPane().add(lblDisplayHostPort);
		lblDisplayHostPort.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnHomeLogin = new JButton("Login");
		btnHomeLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_login) {
				
				frame.dispose();
				
				TriviaClientLoginScreen login_user=new TriviaClientLoginScreen();
				login_user.setVisible(true);
			}
		});
		btnHomeLogin.setEnabled(false);
		btnHomeLogin.setForeground(Color.DARK_GRAY);
		btnHomeLogin.setFont(new Font("Georgia", Font.BOLD, 20));
		btnHomeLogin.setBackground(Color.WHITE);
		btnHomeLogin.setBounds(370, 432, 157, 44);
		frame.getContentPane().add(btnHomeLogin);
		
		btnHomeRegister = new JButton("Register");
		btnHomeRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_register) 
			{	
				frame.dispose();
				
				TriviaClientRegisterScreen reg_user=new TriviaClientRegisterScreen();
				reg_user.setVisible(true);
			}
		});
		btnHomeRegister.setEnabled(false);
		btnHomeRegister.setForeground(Color.DARK_GRAY);
		btnHomeRegister.setFont(new Font("Georgia", Font.BOLD, 20));
		btnHomeRegister.setBackground(Color.WHITE);
		btnHomeRegister.setBounds(370, 501, 157, 44);
		frame.getContentPane().add(btnHomeRegister);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(216, 191, 216));
		menuBar.setBounds(0, 0, 984, 22);
		frame.getContentPane().add(menuBar);
		
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
	}
}
