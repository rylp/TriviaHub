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
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		btnConnect.setBounds(295, 268, 157, 50);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblWelcome = new JLabel("Welcome to TriviaHub");
		lblWelcome.setForeground(Color.BLACK);
		lblWelcome.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 28));
		lblWelcome.setBounds(236, 15, 308, 74);
		frame.getContentPane().add(lblWelcome);
		
		lblHostIP = new JLabel("Host IP");
		lblHostIP.setHorizontalAlignment(SwingConstants.CENTER);
		lblHostIP.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHostIP.setBounds(265, 131, 98, 34);
		frame.getContentPane().add(lblHostIP);
		lblHostIP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblHostPort = new JLabel("Host Port");
		lblHostPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblHostPort.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblHostPort.setBounds(265, 176, 98, 35);
		frame.getContentPane().add(lblHostPort);
		lblHostPort.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lblDisplayHostIP = new JLabel("127.0.0.1");
		lblDisplayHostIP.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayHostIP.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblDisplayHostIP.setBounds(373, 131, 110, 34);
		frame.getContentPane().add(lblDisplayHostIP);
		lblDisplayHostIP.setBorder(new LineBorder(Color.BLACK));
		
		lblDisplayHostPort = new JLabel("9997");
		lblDisplayHostPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayHostPort.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblDisplayHostPort.setBounds(373, 176, 110, 34);
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
		btnHomeLogin.setBounds(295, 382, 157, 44);
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
		btnHomeRegister.setBounds(295, 437, 157, 44);
		frame.getContentPane().add(btnHomeRegister);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(343, 546, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(20, 0, 764, 22);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Settings");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Contact Us");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_contact) 
			{
				JOptionPane.showMessageDialog(null, "Email us at: rohanlimaye20@gmail.com");
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
	}
}
