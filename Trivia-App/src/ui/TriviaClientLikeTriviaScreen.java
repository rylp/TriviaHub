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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class TriviaClientLikeTriviaScreen extends JFrame {

	private JPanel contentPane;
	
	private JList likeList;
	
	private String myTopic=null;
	private int myTopicSelectNumber=-1;
	
	private String triviaIds=null;
	private String triviaData=null;
	
	private int selectedIndex=-1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientLikeTriviaScreen frame = new TriviaClientLikeTriviaScreen();
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
	public TriviaClientLikeTriviaScreen() {
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
		
		JLabel lblSelectTopicFor = new JLabel("Select Topic for which you want to like trivia");
		lblSelectTopicFor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTopicFor.setForeground(Color.BLACK);
		lblSelectTopicFor.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
		lblSelectTopicFor.setBounds(189, 31, 480, 54);
		contentPane.add(lblSelectTopicFor);
		
		Constants con=new Constants();
		
		//declaring 3 topics
		String firstTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(0))));
		String secondTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(1))));
		String thirdTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(2))));
		
		JRadioButton rdbtnTopic1 = new JRadioButton();
		rdbtnTopic1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic1.setBounds(337, 92, 216, 34);
		rdbtnTopic1.setText(firstTopic);
		contentPane.add(rdbtnTopic1);
		
		JRadioButton rdbtnTopic2 = new JRadioButton();
		rdbtnTopic2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic2.setBounds(337, 143, 216, 34);
		rdbtnTopic2.setText(secondTopic);
		contentPane.add(rdbtnTopic2);
		
		JRadioButton rdbtnTopic3 = new JRadioButton();
		rdbtnTopic3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic3.setBounds(337, 190, 216, 34);
		rdbtnTopic3.setText(thirdTopic);
		contentPane.add(rdbtnTopic3);
		
		ButtonGroup btnGroupTopic=new ButtonGroup();
		btnGroupTopic.add(rdbtnTopic1);
		btnGroupTopic.add(rdbtnTopic2);
		btnGroupTopic.add(rdbtnTopic3);
		
		JButton btnTopicForView = new JButton("Ok");
		btnTopicForView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_ok) 
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
		btnTopicForView.setForeground(Color.DARK_GRAY);
		btnTopicForView.setFont(new Font("Georgia", Font.BOLD, 20));
		btnTopicForView.setBackground(Color.WHITE);
		btnTopicForView.setBounds(389, 231, 107, 34);
		contentPane.add(btnTopicForView);
		
		JButton btnShowTrivia = new JButton("Show Trivia");
		btnShowTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_showTrivia) 
			{
				String email=ui.Constants.myEmail;
				String topicKey=ui.Constants.myKey;
				int topic=Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(myTopicSelectNumber)));
				
				final String MSG_TYPE="VIEWNOTMY";
				
				if(myTopicSelectNumber!=-1)
				{
					try
					{
						Gson gson=new Gson();
						
						JsonDataContract jdc=new JsonDataContract();
						
						jdc.setMessageType(MSG_TYPE);
						
						jdc.setEmail(email);
						jdc.setTopicsKey(topicKey);
						jdc.setTopic(String.valueOf(topic));
						
						jdc.setClientIp(client.Constants.clientIp);
						jdc.setClientPort(String.valueOf(client.Constants.clientPort));
						
						String clientData=gson.toJson(jdc,JsonDataContract.class);
						
						int result=sendData(clientData);
						
						if(result==-1)
						{
							JOptionPane.showMessageDialog(null, "There is no trivia currently added for this topic!");
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
							menu_screen.setVisible(true);
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error in liking trivia");
					}	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please Select a Topic");
				}
			}

			private int sendData(String clientData) 
			{
				client.SocketDetails socDetails=new client.SocketDetails();
				
				try {
					socDetails.setIn(new DataInputStream(client.Constants.socket.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					socDetails.setOut(new OutputStreamWriter(client.Constants.socket.getOutputStream(),StandardCharsets.UTF_8));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				client.ClientCommunication clComm=new client.ClientCommunication(); 
				
				client.ClientCommunication.Transmitter tr=clComm.new Transmitter();//class within class
				
				client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socDetails);
				
				String triviaReceived=jdc1.getTriviaData();
				
				triviaData=jdc1.getTriviaData();
				triviaIds=jdc1.getTriviaIds();
				
				if(!jdc1.getTriviaData().isEmpty())
				{
					//If there exists a trivia
					DefaultListModel DLM=new DefaultListModel();
					
					String[] recievedTrivia=triviaReceived.split("#");
					
					System.out.println("Displaying Trivia");
					
					for(String trivia:recievedTrivia)
					{
						DLM.addElement(trivia);
					}
					
					likeList.setModel(DLM);
					
					return 0;
				}
				
				return -1;
			}
		});
		btnShowTrivia.setForeground(Color.DARK_GRAY);
		btnShowTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnShowTrivia.setBackground(Color.WHITE);
		btnShowTrivia.setBounds(337, 294, 216, 46);
		contentPane.add(btnShowTrivia);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(154, 351, 636, 325);
		contentPane.add(scrollPane);
		
		likeList = new JList();
		likeList.setBorder(new LineBorder(new Color(0, 0, 0)));
		likeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		likeList.setBackground(Color.CYAN);
		likeList.setFont(new Font("Georgia", Font.BOLD, 20));
		scrollPane.setViewportView(likeList);
		
		JButton btnLikeTrivia = new JButton("Like Trivia");
		btnLikeTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_likeTrivia) 
			{
				final String MSG_TYPE="LIKE";
				String emailid=ui.Constants.myEmail;
				String topicKey=ui.Constants.myKey;
				
				selectedIndex=likeList.getSelectedIndex();
				
				if(selectedIndex!=-1)
				{
					String[] receivedIds=triviaIds.split("#");
					
					int triviaId=Integer.parseInt(receivedIds[selectedIndex]);
					
					try
					{
						Gson gson=new Gson();
						
						JsonDataContract jdc=new JsonDataContract();
						
						jdc.setMessageType(MSG_TYPE);
						
						jdc.setEmail(emailid);
						jdc.setTriviaIdtoLike(String.valueOf(triviaId));
						
						jdc.setClientIp(client.Constants.clientIp);
						jdc.setClientPort(String.valueOf(client.Constants.clientPort));
						
						String clientData=gson.toJson(jdc,JsonDataContract.class);
						
						boolean Result=sendData(clientData);
						
						if(Result)
						{
							JOptionPane.showMessageDialog(null, "Successfully Liked Selected Trivia");
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
							menu_screen.setVisible(true);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Already Liked this Trivia");
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error in Liking Trivia");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No Trivia Selected Currently!");
				}
			}

			private boolean sendData(String clientData) 
			{
				client.SocketDetails socDetails=new client.SocketDetails();
				
				try {
					socDetails.setIn(new DataInputStream(client.Constants.socket.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					socDetails.setOut(new OutputStreamWriter(client.Constants.socket.getOutputStream(),StandardCharsets.UTF_8));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				client.ClientCommunication clComm=new client.ClientCommunication(); 
				
				client.ClientCommunication.Transmitter tr=clComm.new Transmitter();//class within class
				
				client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socDetails);
				
				if(jdc1.getStatus().equals(ui.Constants.SUCCESS))
				{
					return true;
				}
				
				else//FAILURE
				{
					//check if already liked.
					if(jdc1.getErrorValue().equals(ui.Constants.ALREADY_LIKED))
					{
						return false;
					}
				}
				
				return true;
			}
				
		});
		btnLikeTrivia.setForeground(Color.DARK_GRAY);
		btnLikeTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnLikeTrivia.setBackground(Color.WHITE);
		btnLikeTrivia.setBounds(280, 704, 216, 46);
		contentPane.add(btnLikeTrivia);
		

		
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
		btnGoToMenu.setBounds(10, 33, 100, 30);
		contentPane.add(btnGoToMenu);
		
		JButton btnDislikeTrivia = new JButton("Dislike Trivia");
		btnDislikeTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_dislike) 
			{
				final String MSG_TYPE="DISLIKE";
				String emailid=ui.Constants.myEmail;
				String topicKey=ui.Constants.myKey;
				
				selectedIndex=likeList.getSelectedIndex();
				
				if(selectedIndex!=-1)
				{
					String[] receivedIds=triviaIds.split("#");
					
					int triviaId=Integer.parseInt(receivedIds[selectedIndex]);
					
					try
					{
						Gson gson=new Gson();
						
						JsonDataContract jdc=new JsonDataContract();
						
						jdc.setMessageType(MSG_TYPE);
						
						jdc.setEmail(emailid);
						jdc.setTriviaIdtoDislike(String.valueOf(triviaId));
						
						jdc.setClientIp(client.Constants.clientIp);
						jdc.setClientPort(String.valueOf(client.Constants.clientPort));
						
						String clientData=gson.toJson(jdc,JsonDataContract.class);
						
						boolean Result=sendData(clientData);
						
						if(Result)
						{
							JOptionPane.showMessageDialog(null, "Successfully Disliked Selected Trivia");
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
							menu_screen.setVisible(true);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Trivia Already Disliked");
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error in Disliking Trivia");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No Trivia Added Currently!");
				}	
			}

			private boolean sendData(String clientData) 
			{
				client.SocketDetails socDetails=new client.SocketDetails();
				
				try {
					socDetails.setIn(new DataInputStream(client.Constants.socket.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					socDetails.setOut(new OutputStreamWriter(client.Constants.socket.getOutputStream(),StandardCharsets.UTF_8));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				client.ClientCommunication clComm=new client.ClientCommunication(); 
				
				client.ClientCommunication.Transmitter tr=clComm.new Transmitter();//class within class
				
				client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socDetails);
				
				if(jdc1.getStatus().equals(ui.Constants.SUCCESS))
				{
					return true;
				}
				
				else//FAILURE
				{
					//check if already liked.
					if(jdc1.getErrorValue().equals(ui.Constants.ALREADY_DISLIKED))
					{
						return false;
					}
				}
				
				return true;
			}
		});
		btnDislikeTrivia.setForeground(Color.DARK_GRAY);
		btnDislikeTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnDislikeTrivia.setBackground(Color.WHITE);
		btnDislikeTrivia.setBounds(521, 704, 216, 46);
		contentPane.add(btnDislikeTrivia);
	}
}
