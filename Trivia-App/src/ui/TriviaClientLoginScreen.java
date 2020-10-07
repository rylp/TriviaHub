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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginUserEmail = new JLabel("Enter Email");
		lblLoginUserEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLoginUserEmail.setBounds(180, 180, 90, 25);
		contentPane.add(lblLoginUserEmail);
		
		JLabel lblLoginUserPass = new JLabel("Password");
		lblLoginUserPass.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLoginUserPass.setBounds(180, 226, 90, 25);
		contentPane.add(lblLoginUserPass);
		
		txtLoginEmail = new JTextField();
		txtLoginEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtLoginEmail.setBounds(280, 175, 120, 40);
		contentPane.add(txtLoginEmail);
		txtLoginEmail.setColumns(10);
		
		txtLoginPass = new JPasswordField();
		txtLoginPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtLoginPass.setBounds(280, 225, 120, 40);
		contentPane.add(txtLoginPass);
		
		JLabel lblLoginEnterInfo = new JLabel("Enter your details");
		lblLoginEnterInfo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblLoginEnterInfo.setBounds(250, 80, 175, 60);
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
						JOptionPane.showMessageDialog(null, "Welcome! "+ui.Constants.myEmail);
						
						contentPane.setVisible(false);
						dispose();
						TriviaClientOpeningScreen.main(null);	
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
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(290, 310, 100, 30);
		contentPane.add(btnLogin);
		
		JLabel lblLoginNotAUser = new JLabel("Not a User? Register");
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
		lblLoginNotAUser.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblLoginNotAUser.setForeground(Color.BLUE);
		lblLoginNotAUser.setBounds(260, 360, 140, 20);
		contentPane.add(lblLoginNotAUser);
	}
}
