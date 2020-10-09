package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TriviaClientOpeningScreen {

	private JFrame frame;
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
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_connect) {
				
				Client.communicationEndpoint();
			}
		});
		btnConnect.setBackground(Color.WHITE);
		btnConnect.setForeground(Color.DARK_GRAY);
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnConnect.setBounds(500, 100, 100, 30);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblWelcome = new JLabel("Welcome to TriviaHub");
		lblWelcome.setForeground(Color.RED);
		lblWelcome.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblWelcome.setBounds(200, 200, 320, 50);
		frame.getContentPane().add(lblWelcome);
		
		JLabel lblHostIP = new JLabel("Host IP");
		lblHostIP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHostIP.setBounds(30, 40, 70, 20);
		frame.getContentPane().add(lblHostIP);
		lblHostIP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel lblHostPort = new JLabel("Host Port");
		lblHostPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHostPort.setBounds(30, 65, 70, 20);
		frame.getContentPane().add(lblHostPort);
		lblHostPort.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel lblDisplayHostIP = new JLabel("localhost");
		lblDisplayHostIP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDisplayHostIP.setBounds(100, 40, 70, 20);
		frame.getContentPane().add(lblDisplayHostIP);
		lblDisplayHostIP.setBorder(new EmptyBorder(0,10,0,0));
		
		JLabel lblDisplayHostPort = new JLabel("9997");
		lblDisplayHostPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDisplayHostPort.setBounds(100, 65, 70, 20);
		frame.getContentPane().add(lblDisplayHostPort);
		lblDisplayHostPort.setBorder(new EmptyBorder(0,10,0,0));
		
		JButton btnHomeLogin = new JButton("Login");
		btnHomeLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_login) {
				
				frame.dispose();
				
				TriviaClientLoginScreen login_user=new TriviaClientLoginScreen();
				login_user.setVisible(true);
			}
		});
		btnHomeLogin.setForeground(Color.DARK_GRAY);
		btnHomeLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHomeLogin.setBackground(Color.WHITE);
		btnHomeLogin.setBounds(280, 280, 100, 30);
		frame.getContentPane().add(btnHomeLogin);
		
		JButton btnHomeRegister = new JButton("Register");
		btnHomeRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_register) 
			{	
				frame.dispose();
				
				TriviaClientRegisterScreen reg_user=new TriviaClientRegisterScreen();
				reg_user.setVisible(true);
			}
		});
		btnHomeRegister.setForeground(Color.DARK_GRAY);
		btnHomeRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHomeRegister.setBackground(Color.WHITE);
		btnHomeRegister.setBounds(280, 330, 100, 30);
		frame.getContentPane().add(btnHomeRegister);
	}
}
