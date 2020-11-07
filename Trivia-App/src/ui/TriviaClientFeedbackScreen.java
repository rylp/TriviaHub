package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;

public class TriviaClientFeedbackScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JTextField textFieldName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientFeedbackScreen frame = new TriviaClientFeedbackScreen();
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
	public TriviaClientFeedbackScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, ui.Constants.SCREEN_WIDTH, ui.Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
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
				JOptionPane.showMessageDialog(contentPane, "Email us at: rohanlimaye20@gmail.com");
			}
		});
		myMenu.add(myMenuItem_ContactUs);
		
		JLabel lblFillFeedback = new JLabel("Please fill this feedback form");
		lblFillFeedback.setHorizontalAlignment(SwingConstants.CENTER);
		lblFillFeedback.setForeground(Color.BLACK);
		lblFillFeedback.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
		lblFillFeedback.setBounds(201, 56, 480, 54);
		contentPane.add(lblFillFeedback);
		
		JTextArea textAreaFeedback = new JTextArea();
		textAreaFeedback.setWrapStyleWord(true);
		textAreaFeedback.setMargin(new Insets(2, 2, 2, 2));
		textAreaFeedback.setLineWrap(true);
		textAreaFeedback.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 20));
		textAreaFeedback.setEditable(true);
		textAreaFeedback.setBackground(Color.WHITE);
		textAreaFeedback.setBounds(331, 280, 510, 205);
		contentPane.add(textAreaFeedback);
		
		JLabel lblLoginUserEmail = new JLabel("Enter Email");
		lblLoginUserEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginUserEmail.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblLoginUserEmail.setBounds(109, 134, 177, 40);
		contentPane.add(lblLoginUserEmail);
		
		JLabel lblEnterFeedbackName = new JLabel("Enter Name");
		lblEnterFeedbackName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterFeedbackName.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblEnterFeedbackName.setBounds(109, 197, 177, 40);
		contentPane.add(lblEnterFeedbackName);
		
		JLabel lblEnterFeedback = new JLabel("Enter Feedback");
		lblEnterFeedback.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterFeedback.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblEnterFeedback.setBounds(109, 329, 177, 40);
		contentPane.add(lblEnterFeedback);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(331, 134, 260, 40);
		contentPane.add(textFieldEmail);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldName.setColumns(10);
		textFieldName.setBounds(331, 197, 260, 40);
		contentPane.add(textFieldName);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_submitfeedback) 
			{
				String MSG_TYPE="FEEDBACK";
				String Email=null;
				String Name=null;
				String Feedback=null;
				
				Email=textFieldEmail.getText().toString().trim();
				Name=textFieldName.getText().toString().trim();
				Feedback=textAreaFeedback.getText().trim();
				
				if(!Email.isEmpty() && !Name.isEmpty() && !Feedback.isEmpty())
				{
					try
					{
						int response=JOptionPane.showConfirmDialog(contentPane, "Confirm Feedback Submission?","Submit Feedback",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						
						if(response==JOptionPane.YES_OPTION)
						{	
							Gson gson=new Gson();
							
							JsonDataContract jdc=new JsonDataContract();
							
							jdc.setMessageType(MSG_TYPE);
							
							jdc.setFeedbackEmail(Email);
							jdc.setFeedbackName(Name);
							jdc.setFeedbackContent(Feedback);
							
							jdc.setClientIp(client.Constants.clientIp);
							jdc.setClientPort(String.valueOf(client.Constants.clientPort));
							
							String clientData=gson.toJson(jdc,JsonDataContract.class);
							
							int result=sendData(clientData);
							
							if(result==1)
							{
								JOptionPane.showMessageDialog(contentPane, "Thank you for ur feedback");
								
								contentPane.setVisible(false);
								dispose();
								
								TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
								menu_screen.setVisible(true);
							}
							else
							{
								JOptionPane.showMessageDialog(contentPane, "Unable to submit feedback");
							}
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(contentPane, "Error in Adding Feedback");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane, "Fill up all the fields");
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
					return 1;
				}
				
				return 0;
			}
		});
		btnSubmit.setForeground(Color.DARK_GRAY);
		btnSubmit.setFont(new Font("Georgia", Font.BOLD, 20));
		btnSubmit.setBackground(Color.WHITE);
		btnSubmit.setBounds(350, 544, 140, 50);
		contentPane.add(btnSubmit);
		
		JButton btnGoToMenu = new JButton("MENU");
		btnGoToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_menu) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
				menu_screen.setVisible(true);
			}
		});
		btnGoToMenu.setFont(new Font("Tahoma", Font.ITALIC, 18));
		btnGoToMenu.setBounds(10, 56, 100, 30);
		contentPane.add(btnGoToMenu);
		
		
	}
}
