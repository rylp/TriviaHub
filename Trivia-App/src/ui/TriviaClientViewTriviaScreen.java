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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class TriviaClientViewTriviaScreen extends JFrame {

	private JPanel contentPane;
	private JList viewList;
	private String myTopic=null;
	private int myTopicSelectNumber=-1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientViewTriviaScreen frame = new TriviaClientViewTriviaScreen();
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
	public TriviaClientViewTriviaScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 140, 0));
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
		
		JLabel lblSelectTopicForView = new JLabel("Select Topic for which you want to view trivia");
		lblSelectTopicForView.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTopicForView.setForeground(Color.YELLOW);
		lblSelectTopicForView.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 22));
		lblSelectTopicForView.setBounds(238, 33, 480, 54);
		contentPane.add(lblSelectTopicForView);
		
		Constants con=new Constants();
		
		//declaring 3 topics
		String firstTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(0))));
		String secondTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(1))));
		String thirdTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(2))));
		
		JRadioButton rdbtnTopic1 = new JRadioButton("");
		rdbtnTopic1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic1.setBounds(370, 94, 216, 34);
		rdbtnTopic1.setText(firstTopic);
		contentPane.add(rdbtnTopic1);
		
		JRadioButton rdbtnTopic2 = new JRadioButton("");
		rdbtnTopic2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic2.setBounds(370, 134, 216, 34);
		rdbtnTopic2.setText(secondTopic);
		contentPane.add(rdbtnTopic2);
		
		JRadioButton rdbtnTopic3 = new JRadioButton("");
		rdbtnTopic3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		rdbtnTopic3.setBounds(370, 180, 216, 34);
		rdbtnTopic3.setText(thirdTopic);
		contentPane.add(rdbtnTopic3);
		
		ButtonGroup btnGroupTopic=new ButtonGroup();
		btnGroupTopic.add(rdbtnTopic1);
		btnGroupTopic.add(rdbtnTopic2);
		btnGroupTopic.add(rdbtnTopic3);
		
		JButton btnTopicForView = new JButton("Ok");
		btnTopicForView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_topicToViewTrivia) 
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
		btnTopicForView.setBounds(416, 221, 107, 34);
		contentPane.add(btnTopicForView);
		
		
		JButton btnShowTrivia = new JButton("Show Trivia");
		btnShowTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_showtrivia) 
			{
				int topic;
				String email=null;
				String topicKey=null;
				final String MSG_TYPE="VIEW";
				
				if(myTopicSelectNumber!=-1)
				{
					email=ui.Constants.myEmail;
					topicKey=ui.Constants.myKey;
					topic=Integer.parseInt(String.valueOf(ui.Constants.myKey.charAt(myTopicSelectNumber)));
					
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
						
						int Result=sendData(clientData);
						
						if(Result==0)
						{
							JOptionPane.showMessageDialog(null, "No Trivia on this topic currently");
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
							menu_screen.setVisible(true);
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error in adding trivia");
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
				
				String triviaReceived=jdc1.getTriviaData();
				
				//If there exists a trivia
				if(!jdc1.getTriviaData().isEmpty())
				{
					DefaultListModel DLM=new DefaultListModel();
					
					String[] recievedTrivia=triviaReceived.split("#");
					
					System.out.println("Displaying Trivia");
					
					for(String trivia:recievedTrivia)
					{
						DLM.addElement(trivia);
					}
					
					viewList.setModel(DLM);
					
					return 1;
				}

				return 0;
			}
		});
		btnShowTrivia.setForeground(Color.DARK_GRAY);
		btnShowTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnShowTrivia.setBackground(Color.WHITE);
		btnShowTrivia.setBounds(370, 284, 216, 46);
		contentPane.add(btnShowTrivia);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(185, 380, 698, 358);
		contentPane.add(scrollPane);
		
		viewList = new JList();
		viewList.setBorder(new LineBorder(new Color(0, 0, 0)));
		viewList.setBackground(Color.GREEN);
		viewList.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
		scrollPane.setViewportView(viewList);
		
		JButton btnGoToMenu = new JButton("MENU");
		btnGoToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_backToMenu) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
				menu_screen.setVisible(true);
			}
		});
		btnGoToMenu.setFont(new Font("Tahoma", Font.ITALIC, 18));
		btnGoToMenu.setBounds(10, 50, 100, 30);
		contentPane.add(btnGoToMenu);
//		
//		String[] myListElements= {"Monday","Tuesday","Wednesday", 
//                "Thursday","Friday","Saturday","Sunday"};
	}
}
