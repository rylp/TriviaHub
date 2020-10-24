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
import javax.swing.SwingConstants;

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
	public TriviaClientRegisterScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginEnterInfo = new JLabel("Enter your details");
		lblLoginEnterInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginEnterInfo.setFont(new Font("Palatino Linotype", Font.BOLD, 23));
		lblLoginEnterInfo.setBounds(290, 30, 268, 61);
		contentPane.add(lblLoginEnterInfo);
		
		JLabel lblLoginUserEmail = new JLabel("Enter Email");
		lblLoginUserEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginUserEmail.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblLoginUserEmail.setBounds(200, 115, 177, 40);
		contentPane.add(lblLoginUserEmail);
		
		JLabel lblEnterFirstName = new JLabel("Enter First Name");
		lblEnterFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterFirstName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblEnterFirstName.setBounds(197, 164, 177, 40);
		contentPane.add(lblEnterFirstName);
		
		JLabel lblEnterLastName = new JLabel("Enter Last Name");
		lblEnterLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterLastName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblEnterLastName.setBounds(197, 225, 177, 40);
		contentPane.add(lblEnterLastName);
		
		JLabel lblEnterAge = new JLabel("Enter Age");
		lblEnterAge.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterAge.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblEnterAge.setBounds(197, 286, 177, 40);
		contentPane.add(lblEnterAge);
		
		JLabel lblEnterPassword = new JLabel("Enter Password");
		lblEnterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblEnterPassword.setBounds(197, 347, 186, 40);
		contentPane.add(lblEnterPassword);
		
		txtRegEmail = new JTextField();
		txtRegEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRegEmail.setColumns(10);
		txtRegEmail.setBounds(420, 120, 250, 40);
		contentPane.add(txtRegEmail);
		
		txtRegFirstName = new JTextField();
		txtRegFirstName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRegFirstName.setColumns(10);
		txtRegFirstName.setBounds(420, 170, 250, 40);
		contentPane.add(txtRegFirstName);
		
		txtRegLastName = new JTextField();
		txtRegLastName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRegLastName.setColumns(10);
		txtRegLastName.setBounds(420, 220, 250, 40);
		contentPane.add(txtRegLastName);
		
		txtRegAge = new JTextField();
		txtRegAge.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRegAge.setColumns(10);
		txtRegAge.setBounds(420, 280, 250, 40);
		contentPane.add(txtRegAge);
		
		txtRegPass = new JPasswordField();
		txtRegPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRegPass.setBounds(420, 340, 250, 40);
		contentPane.add(txtRegPass);
		
		txtRegVerifyPass = new JPasswordField();
		txtRegVerifyPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtRegVerifyPass.setBounds(420, 400, 250, 40);
		contentPane.add(txtRegVerifyPass);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password");
		lblReenterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblReenterPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblReenterPassword.setBounds(197, 408, 186, 40);
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
					
					if(!password.isEmpty()  && !verify_password.isEmpty() && !Email.isEmpty() && !Fname.isEmpty() && !Lname.isEmpty())
					{
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
					else
					{
						JOptionPane.showMessageDialog(null, "Enter all details");
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
		btnRegister.setFont(new Font("Georgia", Font.BOLD, 20));
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setBounds(371, 505, 140, 50);
		contentPane.add(btnRegister);
		
		JLabel lblRegAlreadyAUser = new JLabel("Already a User? Login");
		lblRegAlreadyAUser.setHorizontalAlignment(SwingConstants.CENTER);
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
		lblRegAlreadyAUser.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 17));
		lblRegAlreadyAUser.setBounds(339, 572, 186, 40);
		contentPane.add(lblRegAlreadyAUser);
		
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
