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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class TriviaClientTopicSelectionScreen extends JFrame {

	private JPanel contentPane;
	private JLabel lblTopic1=null;
	private JLabel lblTopic2=null;
	private JLabel lblTopic3=null;
	private JComboBox comboBoxTopic1=null;
	private JComboBox comboBoxTopic2=null;
	private JComboBox comboBoxTopic3=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaClientTopicSelectionScreen frame = new TriviaClientTopicSelectionScreen();
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
	public TriviaClientTopicSelectionScreen() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectTopicsOf = new JLabel("Select Topics of Interest");
		lblSelectTopicsOf.setFont(new Font("Palatino Linotype", Font.BOLD, 25));
		lblSelectTopicsOf.setBounds(327, 46, 287, 64);
		contentPane.add(lblSelectTopicsOf);
		
		Constants cons=new Constants();
		
		String[] topics = cons.getFirstList().toArray(new String[cons.getFirstList().size()]);
		
		comboBoxTopic1 = new JComboBox(topics);	
		comboBoxTopic1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		comboBoxTopic1.setBounds(521, 165, 160, 40);
		comboBoxTopic1.setSelectedIndex(0);
		contentPane.add(comboBoxTopic1);
		
		comboBoxTopic2 = new JComboBox(topics);
		comboBoxTopic2.setSelectedIndex(0);
		comboBoxTopic2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		comboBoxTopic2.setBounds(521, 274, 160, 40);
		contentPane.add(comboBoxTopic2);
		
		lblTopic2 = new JLabel("");
		lblTopic2.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblTopic2.setBounds(415, 235, 166, 40);
		contentPane.add(lblTopic2);
		
		comboBoxTopic3 = new JComboBox(topics);
		comboBoxTopic3.setSelectedIndex(0);
		comboBoxTopic3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		comboBoxTopic3.setBounds(521, 399, 160, 40);
		contentPane.add(comboBoxTopic3);
		
		JLabel lblSelectFirstTopic = new JLabel("Select 1st Topic");
		lblSelectFirstTopic.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectFirstTopic.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblSelectFirstTopic.setBounds(297, 165, 175, 40);
		contentPane.add(lblSelectFirstTopic);
		
		JLabel lblSelectSecondTopic = new JLabel("Select 2nd Topic");
		lblSelectSecondTopic.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectSecondTopic.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblSelectSecondTopic.setBounds(297, 274, 175, 41);
		contentPane.add(lblSelectSecondTopic);
		
		JLabel lblSelectrdTopic = new JLabel("Select 3rd Topic");
		lblSelectrdTopic.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectrdTopic.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblSelectrdTopic.setBounds(297, 399, 175, 41);
		contentPane.add(lblSelectrdTopic);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eSelect) 
			{
				
				String userKey="";
				String MSG_TYPE="SELECT";
				
				try
				{
					int topic1=comboBoxTopic1.getSelectedIndex();
					int topic2=comboBoxTopic2.getSelectedIndex();
					int topic3=comboBoxTopic3.getSelectedIndex();
					
					if(topic1==topic2 || topic1==topic3 || topic2==topic3)
					{
						JOptionPane.showMessageDialog(contentPane, "Select Unique Topics");
					}
					else
					{
						userKey+=Integer.toString(topic1);
						userKey+=Integer.toString(topic2);
						userKey+=Integer.toString(topic3);
						
						Constants con=new Constants();
						
						//declaring 3 topics
						String firstTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(userKey.charAt(0))));
						String secondTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(userKey.charAt(1))));
						String thirdTopic=con.getFirstList().get(Integer.parseInt(String.valueOf(userKey.charAt(2))));
						
						JOptionPane.showMessageDialog(contentPane, "Your Topics of Choice: \n"+"1."+firstTopic+"\n"+"2."+secondTopic+"\n"+"3."+thirdTopic);
						
						int res = JOptionPane.showConfirmDialog(contentPane,"Sure? Confirm Topics?", "Topics of Choice",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						
						if(res==JOptionPane.YES_OPTION)
						{
							Gson gson=new Gson();
							
							JsonDataContract jdc=new JsonDataContract();
							
							jdc.setMessageType(MSG_TYPE);
							
							jdc.setEmail(ui.Constants.myEmail);
							jdc.setTopicsKey(userKey);
							
							jdc.setClientIp(client.Constants.clientIp);
							jdc.setClientPort(String.valueOf(client.Constants.clientPort));
							
							jdc.setClientPort(String.valueOf(client.Constants.clientPort));
							
							String clientData=gson.toJson(jdc,JsonDataContract.class);
							
							int Result=sendData(clientData);
							
							if(Result==1)
							{
								JOptionPane.showMessageDialog(contentPane, "Successfully Updated Topics");
								
								contentPane.setVisible(false);
								dispose();
								
								TriviaClientMenuScreen reg_user=new TriviaClientMenuScreen();
								reg_user.setVisible(true);
							}
						}
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(contentPane, "Enter valid details");
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
				
				System.out.println("Got back JSON");
				
				System.out.println("Status: "+jdc1.getStatus());
				
				System.out.println("Message Type: "+jdc1.getMessageType());
				
				System.out.println("Error Value: "+jdc1.getErrorValue());
				
				//may set userId latter thru changes in JsonDataContract and Server-Side Modules
				ui.Constants.myKey=jdc1.getTopicsKey();
				
				return 1;
			}
		});
		btnNewButton.setFont(new Font("Georgia", Font.BOLD, 20));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(449, 522, 117, 51);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(215, 235, 508, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(215, 348, 508, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(215, 493, 515, 2);
		contentPane.add(separator_2);
		
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
				JOptionPane.showMessageDialog(contentPane, "Email us at: rohanlimaye20@gmail.com");
			}
		});
		myMenu.add(myMenuItem_ContactUs);
	}
}
