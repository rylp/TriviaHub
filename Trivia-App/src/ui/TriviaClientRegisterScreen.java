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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TriviaClientRegisterScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtRegEmail;
	private JTextField txtRegFirstName;
	private JTextField txtRegLastName;
	private JTextField txtRegAge;
	private JPasswordField txtRegPass;
	private JPasswordField txtRegVerifyPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientRegisterScreen frame = new TriviaClientRegisterScreen();
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
	public TriviaClientRegisterScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginEnterInfo = new JLabel("Enter your details");
		lblLoginEnterInfo.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblLoginEnterInfo.setBounds(260, 30, 175, 60);
		contentPane.add(lblLoginEnterInfo);
		
		JLabel lblLoginUserEmail = new JLabel("Enter Email");
		lblLoginUserEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLoginUserEmail.setBounds(130, 109, 140, 25);
		contentPane.add(lblLoginUserEmail);
		
		JLabel lblEnterFirstName = new JLabel("Enter First Name");
		lblEnterFirstName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEnterFirstName.setBounds(130, 170, 140, 25);
		contentPane.add(lblEnterFirstName);
		
		JLabel lblEnterLastName = new JLabel("Enter Last Name");
		lblEnterLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEnterLastName.setBounds(130, 218, 140, 25);
		contentPane.add(lblEnterLastName);
		
		JLabel lblEnterAge = new JLabel("Enter Age");
		lblEnterAge.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEnterAge.setBounds(127, 273, 143, 25);
		contentPane.add(lblEnterAge);
		
		JLabel lblEnterPassword = new JLabel("Enter Password");
		lblEnterPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEnterPassword.setBounds(130, 326, 143, 25);
		contentPane.add(lblEnterPassword);
		
		txtRegEmail = new JTextField();
		txtRegEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRegEmail.setColumns(10);
		txtRegEmail.setBounds(300, 100, 150, 40);
		contentPane.add(txtRegEmail);
		
		txtRegFirstName = new JTextField();
		txtRegFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRegFirstName.setColumns(10);
		txtRegFirstName.setBounds(300, 160, 150, 40);
		contentPane.add(txtRegFirstName);
		
		txtRegLastName = new JTextField();
		txtRegLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRegLastName.setColumns(10);
		txtRegLastName.setBounds(300, 210, 150, 40);
		contentPane.add(txtRegLastName);
		
		txtRegAge = new JTextField();
		txtRegAge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRegAge.setColumns(10);
		txtRegAge.setBounds(300, 265, 150, 40);
		contentPane.add(txtRegAge);
		
		txtRegPass = new JPasswordField();
		txtRegPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRegPass.setBounds(300, 320, 150, 40);
		contentPane.add(txtRegPass);
		
		txtRegVerifyPass = new JPasswordField();
		txtRegVerifyPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRegVerifyPass.setBounds(300, 375, 150, 40);
		contentPane.add(txtRegVerifyPass);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password");
		lblReenterPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblReenterPassword.setBounds(130, 384, 140, 25);
		contentPane.add(lblReenterPassword);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_register) 
			{
				String MSG_TYPE="REGISTER";
				String Email=null;
				String Fname=null;
				String Lname=null;
				String password=null;
				String verify_password=null;
				int age;
				
				try
				{
					Email=txtRegEmail.getText();
					Fname=txtRegFirstName.getText();
					Lname=txtRegLastName.getText();
					age=Integer.parseInt(txtRegAge.getText());
					password=String.valueOf(txtRegPass.getPassword());
					verify_password=String.valueOf(txtRegVerifyPass.getPassword());
					
					if(password.equals(verify_password))
					{
						Gson gson=new Gson();
						
						JsonDataContract jdc=new JsonDataContract();
						
						jdc.setMessageType(MSG_TYPE);
						
						jdc.setFirstName(Fname);
						jdc.setLastName(Lname);
						jdc.setEmail(Email);
						jdc.setAge(String.valueOf(age));
						jdc.setPassword(password);
						
						jdc.setClientIp(client.Constants.clientIp);
						jdc.setClientPort(String.valueOf(client.Constants.clientPort));
						
						String clientData=gson.toJson(jdc,JsonDataContract.class);
						
						int result=sendData(clientData);
						
						if(result==1)//Correct Credentials
						{
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientTopicSelectionScreen select_topics=new TriviaClientTopicSelectionScreen();
							select_topics.setVisible(true);
							
						}
						else if(result==-1)//Email Exists
						{
							JOptionPane.showMessageDialog(null, "Email Already Exists");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Error in Registration!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Passwords don't match!");
					}
					
				}
				catch(Exception e)
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
				
				client.ClientCommunication clientComm=new client.ClientCommunication();
				
				client.ClientCommunication.Transmitter tr=clientComm.new Transmitter();
				
				client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socketDetails);
				
				if(jdc1.getErrorValue().isEmpty())//CORRECT CREDENTIALS
				{
					ui.Constants.myEmail=jdc1.getEmail();
				}
				else if(jdc1.getErrorValue().equals(ui.Constants.EMAIL_EXISTS))//EMAIL EXISTS
				{
					System.out.println("Email Already Exists");
					
					//TODO: Give login option here if wanted
					return -1;
				}
				return 1;
			}
		});
		btnRegister.setForeground(Color.DARK_GRAY);
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setBounds(260, 440, 100, 30);
		contentPane.add(btnRegister);
		
		JLabel lblRegAlreadyAUser = new JLabel("Already a User? Login");
		lblRegAlreadyAUser.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e_alreadyAUser) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientLoginScreen login_user=new TriviaClientLoginScreen();
				login_user.setVisible(true);
			}
		});
		lblRegAlreadyAUser.setForeground(Color.BLUE);
		lblRegAlreadyAUser.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblRegAlreadyAUser.setBounds(240, 485, 140, 20);
		contentPane.add(lblRegAlreadyAUser);
	}
}
