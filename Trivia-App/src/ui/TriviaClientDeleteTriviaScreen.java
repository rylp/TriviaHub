package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
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
import javax.swing.JList;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class TriviaClientDeleteTriviaScreen extends JFrame {

	private JPanel contentPane;
	
	private JList deleteList;
	
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
					TriviaClientDeleteTriviaScreen frame = new TriviaClientDeleteTriviaScreen();
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
	public TriviaClientDeleteTriviaScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
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
		
		JLabel lblSelectTriviaToDelete = new JLabel("Select Trivia you want to delete");
		lblSelectTriviaToDelete.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTriviaToDelete.setForeground(Color.BLACK);
		lblSelectTriviaToDelete.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
		lblSelectTriviaToDelete.setBounds(215, 33, 480, 54);
		contentPane.add(lblSelectTriviaToDelete);
		
		JButton btnShowTrivia = new JButton("Show My Trivia");
		btnShowTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_showToDelete) 
			{
				String email=ui.Constants.myEmail;
				String topicKey=ui.Constants.myKey;
				final String MSG_TYPE="VIEWMY";
				
				try
				{
					Gson gson=new Gson();
					
					JsonDataContract jdc=new JsonDataContract();
					
					jdc.setMessageType(MSG_TYPE);
					
					jdc.setEmail(email);
					jdc.setTopicsKey(topicKey);
					
					jdc.setClientIp(client.Constants.clientIp);
					jdc.setClientPort(String.valueOf(client.Constants.clientPort));
					
					String clientData=gson.toJson(jdc,JsonDataContract.class);
					
					int result=sendData(clientData);
					
					if(result==-1)
					{
						JOptionPane.showMessageDialog(null, "You haven't added any trivia");
						
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
				
				triviaData=jdc1.getTriviaData();
				triviaIds=jdc1.getTriviaIds();
				
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
					
					deleteList.setModel(DLM);
					
					return 1;
				}		
				return -1;
			}
		});
		btnShowTrivia.setForeground(Color.DARK_GRAY);
		btnShowTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnShowTrivia.setBackground(Color.WHITE);
		btnShowTrivia.setBounds(359, 103, 216, 46);
		contentPane.add(btnShowTrivia);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(146, 187, 696, 356);
		contentPane.add(scrollPane);
		
		deleteList = new JList();
		deleteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(deleteList);
		deleteList.setForeground(new Color(0, 0, 0));
		deleteList.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
		deleteList.setBorder(new LineBorder(new Color(0, 0, 0)));
		deleteList.setBackground(Color.CYAN);
		
		JButton btnDelTrivia = new JButton("Delete Selected Trivia");
		btnDelTrivia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e_delSelectedTrivia) 
			{
				final String MSG_TYPE="DELETE";
				
				selectedIndex=deleteList.getSelectedIndex();
				
				if(selectedIndex==-1)
				{
					JOptionPane.showMessageDialog(null, "Please Select a Trivia from the list");
				}
				
				else
				{
					String[] receivedIds=triviaIds.split("#");
					
					int triviaId=Integer.parseInt(receivedIds[selectedIndex]);
					
					try
					{
						Gson gson=new Gson();
						
						JsonDataContract jdc=new JsonDataContract();
						
						jdc.setMessageType(MSG_TYPE);
						
						jdc.setEmail(ui.Constants.myEmail);
						jdc.setTriviaIdtoDelete(String.valueOf(triviaId));
						
						jdc.setClientIp(client.Constants.clientIp);
						jdc.setClientPort(String.valueOf(client.Constants.clientPort));
						
						String clientData=gson.toJson(jdc,JsonDataContract.class);
						
						boolean Result=sendData(clientData);
						
						if(Result)
						{
							JOptionPane.showMessageDialog(null, "Successfully Deleted Selected Trivia");
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
							menu_screen.setVisible(true);
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error in Deleting Trivia");
					}	
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
				
				return true;
			}
		});
		btnDelTrivia.setForeground(Color.DARK_GRAY);
		btnDelTrivia.setFont(new Font("Georgia", Font.BOLD, 20));
		btnDelTrivia.setBackground(Color.WHITE);
		btnDelTrivia.setBounds(359, 593, 275, 46);
		contentPane.add(btnDelTrivia);
		
		JButton btnGoToMenu = new JButton("MENU");
		btnGoToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				contentPane.setVisible(false);
				dispose();
				
				TriviaClientMenuScreen menu_screen=new TriviaClientMenuScreen();
				menu_screen.setVisible(true);
			}
		});
		btnGoToMenu.setFont(new Font("Tahoma", Font.ITALIC, 18));
		btnGoToMenu.setBounds(10, 47, 100, 30);
		contentPane.add(btnGoToMenu);
	}
}
