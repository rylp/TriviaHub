package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TriviaClientLoginScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtLoginEmail;
	private JPasswordField txtLoginPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientLoginScreen frame = new TriviaClientLoginScreen();
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
	public TriviaClientLoginScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginUserEmail = new JLabel("Enter Email");
		lblLoginUserEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginUserEmail.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblLoginUserEmail.setBounds(239, 178, 145, 35);
		contentPane.add(lblLoginUserEmail);
		
		JLabel lblLoginUserPass = new JLabel("Password");
		lblLoginUserPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginUserPass.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblLoginUserPass.setBounds(239, 238, 145, 39);
		contentPane.add(lblLoginUserPass);
		
		txtLoginEmail = new JTextField();
		txtLoginEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtLoginEmail.setBounds(414, 172, 212, 44);
		contentPane.add(txtLoginEmail);
		txtLoginEmail.setColumns(10);
		
		txtLoginPass = new JPasswordField();
		txtLoginPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtLoginPass.setBounds(414, 234, 212, 44);
		contentPane.add(txtLoginPass);
		
		JLabel lblLoginEnterInfo = new JLabel("Enter your details");
		lblLoginEnterInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginEnterInfo.setFont(new Font("Palatino Linotype", Font.BOLD, 25));
		lblLoginEnterInfo.setBounds(331, 69, 265, 65);
		contentPane.add(lblLoginEnterInfo);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				String userEmail=null;
				String userPassword=null;
				String MSG_TYPE="LOGIN";
				
				try
				{
					userEmail=txtLoginEmail.getText();
					userPassword=String.valueOf(txtLoginPass.getPassword());
					
					if(!userEmail.isEmpty() && !userPassword.isEmpty())
					{
						Gson gson=new Gson();
						
						JsonDataContract jdc=new JsonDataContract();
						
						jdc.setMessageType(MSG_TYPE);
						
						jdc.setEmail(userEmail);
						jdc.setPassword(userPassword);
						
						jdc.setClientIp(client.Constants.clientIp);
						
						jdc.setClientPort(String.valueOf(client.Constants.clientPort));
						
						String clientData=gson.toJson(jdc,JsonDataContract.class);
						
						int Result=sendData(clientData);
						
						if(Result==1)//Successful
						{
							//GET TopicsKey
							ui.GetTopicKey getKey=new ui.GetTopicKey();
							int result=getKey.extractKey();
							
							if(result==1)
							{
								JOptionPane.showMessageDialog(null, "Welcome! "+ui.Constants.myEmail);
								JOptionPane.showMessageDialog(null, "Your Key: "+ui.Constants.myKey);
								
								contentPane.setVisible(false);
								dispose();
								
								TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
								menu_screen.setVisible(true);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Topics are not selected");
								JOptionPane.showMessageDialog(null, "Could not LogIn");
							}
						}
						else if(Result==-1)//Incorrect Email
						{
							JOptionPane.showMessageDialog(null, "Email DNE");
						}
						else if(Result==-2)//Incorrect Password
						{
							JOptionPane.showMessageDialog(null, "Password is Wrong!");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Error in Logging in!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Enter all details");
					}
				}
				catch(Exception e_login)
				{
					JOptionPane.showMessageDialog(null, "Enter valid details");
				}
			}

			private int sendData(String clientData) 
			{
				client.SocketDetails socketDetails=new client.SocketDetails();
				
				try {
					socketDetails.setIn(new DataInputStream(client.Constants.socket.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					socketDetails.setOut(new OutputStreamWriter(client.Constants.socket.getOutputStream(), StandardCharsets.UTF_8));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				client.ClientCommunication clComm=new client.ClientCommunication(); 
				
				client.ClientCommunication.Transmitter tr=clComm.new Transmitter();//class within class
				
				client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socketDetails);
				
				System.out.println("Got back JSON");
				
				System.out.println("Status: "+jdc1.getStatus());
				
				System.out.println("Message Type: "+jdc1.getMessageType());
				
				System.out.println("Error Value: "+jdc1.getErrorValue());
				
				if(jdc1.getErrorValue().isEmpty())//CORRECT CREDENTIALS
				{
					ui.Constants.myEmail=jdc1.getEmail();
				}
				else if(jdc1.getErrorValue().equals(ui.Constants.WRONG_EMAIL))
				{
					System.out.println("Incorrect Email");
					return -1;
				}
				else if(jdc1.getErrorValue().equals(ui.Constants.WRONG_PASSWORD))
				{
					System.out.println("Incorrect Password");
					return -2;
				}
				
				return 1;
			}
		});
		btnLogin.setForeground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("Georgia", Font.BOLD, 20));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(384, 318, 145, 44);
		contentPane.add(btnLogin);
		
		JLabel lblLoginNotAUser = new JLabel("Not a User? Register");
		lblLoginNotAUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginNotAUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e_notauser) 
			{
				contentPane.setVisible(false);
				dispose();
				TriviaClientRegisterScreen reg_user=new TriviaClientRegisterScreen();
				reg_user.setVisible(true);
			}
		});
		lblLoginNotAUser.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 20));
		lblLoginNotAUser.setForeground(Color.BLUE);
		lblLoginNotAUser.setBounds(357, 398, 218, 35);
		contentPane.add(lblLoginNotAUser);
		
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
		
	}
}
