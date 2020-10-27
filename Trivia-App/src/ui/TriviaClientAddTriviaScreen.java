package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TriviaClientAddTriviaScreen extends JFrame {

	private JPanel contentPane;
	private String myTopic=null;
	private int myTopicSelectNumber=-1;
	
	JTextArea textAreaTriviaContent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientAddTriviaScreen frame = new TriviaClientAddTriviaScreen();
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
	public TriviaClientAddTriviaScreen() {
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Constants con=new Constants();
		
		//declaring 3 topics
		String firstTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(0))));
		String secondTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(1))));
		String thirdTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(2))));
		
		JLabel lblSelectTopictoAdd = new JLabel("Select Topic for which you want to add trivia");
		lblSelectTopictoAdd.setForeground(Color.BLACK);
		lblSelectTopictoAdd.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 22));
		lblSelectTopictoAdd.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTopictoAdd.setBounds(225, 55, 480, 62);
		contentPane.add(lblSelectTopictoAdd);
		
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
				JOptionPane.showMessageDialog(null, "Email us at: rohanlimaye20@gmail.com");
			}
		});
		myMenu.add(myMenuItem_ContactUs);
		
		JRadioButton rdbtnTopic1 = new JRadioButton("");
		rdbtnTopic1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic1.setBounds(375, 124, 216, 34);
		rdbtnTopic1.setText(firstTopic);
		contentPane.add(rdbtnTopic1);
		
		JRadioButton rdbtnTopic2 = new JRadioButton("");
		rdbtnTopic2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic2.setBounds(375, 171, 216, 34);
		rdbtnTopic2.setText(secondTopic);
		contentPane.add(rdbtnTopic2);
		
		JRadioButton rdbtnTopic3 = new JRadioButton("");
		rdbtnTopic3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic3.setBounds(375, 218, 216, 34);
		rdbtnTopic3.setText(thirdTopic);
		contentPane.add(rdbtnTopic3);
		
		ButtonGroup btnGroupTopic=new ButtonGroup();
		btnGroupTopic.add(rdbtnTopic1);
		btnGroupTopic.add(rdbtnTopic2);
		btnGroupTopic.add(rdbtnTopic3);
		
		JButton btnTopicForAdd = new JButton("Ok");
		btnTopicForAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_topicToAddTrivia) 
			{
				if(rdbtnTopic1.isSelected())
				{
					myTopic=firstTopic;
					myTopicSelectNumber=0;
					JOptionPane.showMessageDialog(null, "Selected: "+firstTopic);
				}
				else if(rdbtnTopic2.isSelected())
				{
					myTopic=secondTopic;
					myTopicSelectNumber=1;
					JOptionPane.showMessageDialog(null,  "Selected: "+secondTopic);
				}
				else if(rdbtnTopic3.isSelected())
				{
					myTopic=thirdTopic;
					myTopicSelectNumber=2;
					JOptionPane.showMessageDialog(null,  "Selected: "+thirdTopic);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please Select a topic!");
				}
			}
		});
		btnTopicForAdd.setForeground(Color.DARK_GRAY);
		btnTopicForAdd.setFont(new Font("Georgia", Font.BOLD, 20));
		btnTopicForAdd.setBackground(Color.WHITE);
		btnTopicForAdd.setBounds(416, 269, 107, 34);
		contentPane.add(btnTopicForAdd);
		
		JLabel lblAddTriviaContent = new JLabel("Enter Trivia Content");
		lblAddTriviaContent.setFont(new Font("Palatino Linotype", Font.BOLD, 18));
		lblAddTriviaContent.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddTriviaContent.setBounds(83, 381, 193, 62);
		contentPane.add(lblAddTriviaContent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(298, 330, 512, 207);
		contentPane.add(scrollPane);
		
		textAreaTriviaContent = new JTextArea();
		scrollPane.setViewportView(textAreaTriviaContent);
		textAreaTriviaContent.setBackground(Color.WHITE);
		textAreaTriviaContent.setWrapStyleWord(true);
		textAreaTriviaContent.setLineWrap(true);
		textAreaTriviaContent.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 20));
		textAreaTriviaContent.setMargin(new Insets(2,2,2,2));
		textAreaTriviaContent.setEditable(true);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_upload) 
			{
				if(myTopicSelectNumber!=-1)
				{
					//Uploading text from .txt file
					JFileChooser fc=new JFileChooser();
					int a=fc.showOpenDialog(null);
					
					if(a==JFileChooser.APPROVE_OPTION)
					{
						File myFile=fc.getSelectedFile();
						String myFilePath=myFile.getPath();
						
						try
						{
							BufferedReader br=new BufferedReader(new FileReader(myFilePath));
							
					        String s1="",s2="";  
					        
					        while((s1=br.readLine())!=null)
					        {    
					        	s2+=s1;    
					        }
					        
					        textAreaTriviaContent.setText(s2);
					        
					        br.close();   
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "Unable to read Selected File");
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please Select a Topic");
				}
			}
		});
		btnUpload.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnUpload.setBounds(502, 548, 89, 23);
		contentPane.add(btnUpload);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_addTrivia) 
			{
				String userKey=null;
				String emailId=null;
				int topic;
				String triviaContent=null;
				int likes;
				final String MSG_TYPE="ADD";
				
				if(myTopicSelectNumber!=-1)
				{
					emailId=ui.Constants.myEmail;
					triviaContent=textAreaTriviaContent.getText().trim();
					userKey=ui.Constants.myKey;
					topic=Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(myTopicSelectNumber)));
					likes=0;
					
					if(!triviaContent.isEmpty())
					{
						try
						{
							Gson gson=new Gson();
							
							JsonDataContract jdc=new JsonDataContract();
							
							jdc.setMessageType(MSG_TYPE);
							
							jdc.setEmail(emailId);
							jdc.setTriviaContent(triviaContent);
							jdc.setTopic(String.valueOf(topic));
							jdc.setLikes(String.valueOf(likes));
							
							jdc.setClientIp(client.Constants.clientIp);
							jdc.setClientPort(String.valueOf(client.Constants.clientPort));
							
							String clientData=gson.toJson(jdc,JsonDataContract.class);
							
							int result=sendData(clientData);
							
							if(result==1)//Correctly Added
							{
								JOptionPane.showMessageDialog(null, "Successfully added Trivia");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Error in Adding Trivia!");
							}
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
							menu_screen.setVisible(true);
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "Error in adding trivia");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "PLease enter trivia content");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select topic");
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
				
				if(jdc1.getErrorValue().isEmpty())
				{
					return 1;
				}
				return 0;
			}
		});
		btnAdd.setForeground(Color.DARK_GRAY);
		btnAdd.setFont(new Font("Georgia", Font.BOLD, 20));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(403, 638, 144, 46);
		contentPane.add(btnAdd);
		
		JButton btnGoToMenu = new JButton("MENU");
		btnGoToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_goBackToMenu) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
				menu_screen.setVisible(true);
			}
		});
		btnGoToMenu.setFont(new Font("Tahoma", Font.ITALIC, 18));
		btnGoToMenu.setBounds(10, 55, 100, 30);
		contentPane.add(btnGoToMenu);
	}
}
