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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectTopicsOf = new JLabel("Select Topics of Interest");
		lblSelectTopicsOf.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblSelectTopicsOf.setBounds(257, 42, 240, 60);
		contentPane.add(lblSelectTopicsOf);
		
		Constants cons=new Constants();
		
		String[] topics = cons.getFirstList().toArray(new String[cons.getFirstList().size()]);
		
		comboBoxTopic1 = new JComboBox(topics);	
		comboBoxTopic1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent eTopicSelected1) 
			{				
				if(eTopicSelected1.getStateChange()==ItemEvent.SELECTED)
				{
					int selection=comboBoxTopic1.getSelectedIndex();
					String topic=comboBoxTopic1.getSelectedItem().toString();
					
					JOptionPane.showMessageDialog(null, "Selection: "+topic);
					
					lblTopic1.setText("Selection: "+ selection+" "+topic);
				}
			}
		});
		comboBoxTopic1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		comboBoxTopic1.setBounds(220, 130, 160, 40);
		comboBoxTopic1.setSelectedIndex(0);
		contentPane.add(comboBoxTopic1);
		
		comboBoxTopic2 = new JComboBox(topics);
		comboBoxTopic2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent eTopicSelected2) 
			{
				if(eTopicSelected2.getStateChange()==ItemEvent.SELECTED)
				{
					int selection=comboBoxTopic2.getSelectedIndex();
					String topic=comboBoxTopic2.getSelectedItem().toString();
					
					JOptionPane.showMessageDialog(null, "Selection: "+topic);
					
					lblTopic2.setText("Selection: "+ selection+" "+topic);
				}
			}
		});
		comboBoxTopic2.setSelectedIndex(0);
		comboBoxTopic2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		comboBoxTopic2.setBounds(220, 235, 160, 40);
		contentPane.add(comboBoxTopic2);
		
		lblTopic2 = new JLabel("");
		lblTopic2.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblTopic2.setBounds(415, 235, 166, 40);
		contentPane.add(lblTopic2);
		
		comboBoxTopic3 = new JComboBox(topics);
		comboBoxTopic3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent eTopicSelected3) 
			{
				if(eTopicSelected3.getStateChange()==ItemEvent.SELECTED)
				{
					int selection=comboBoxTopic3.getSelectedIndex();
					String topic=comboBoxTopic3.getSelectedItem().toString();
					
					JOptionPane.showMessageDialog(null, "Selection: "+topic);
					
					lblTopic3.setText("Selection: "+ selection+" "+topic);
				}
			}
		});
		comboBoxTopic3.setSelectedIndex(0);
		comboBoxTopic3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		comboBoxTopic3.setBounds(220, 340, 160, 40);
		contentPane.add(comboBoxTopic3);
		
		lblTopic3 = new JLabel("");
		lblTopic3.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblTopic3.setBounds(428, 340, 166, 40);
		contentPane.add(lblTopic3);
		
		JLabel lblNewLabel = new JLabel("Select 1st Topic");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setBounds(69, 130, 128, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblSelectSecondTopic = new JLabel("Select 2nd Topic");
		lblSelectSecondTopic.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSelectSecondTopic.setBounds(69, 236, 128, 40);
		contentPane.add(lblSelectSecondTopic);
		
		JLabel lblSelectrdTopic = new JLabel("Select 3rd Topic");
		lblSelectrdTopic.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSelectrdTopic.setBounds(70, 339, 128, 40);
		contentPane.add(lblSelectrdTopic);
		
		JButton btnNewButton = new JButton("Submit");
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
					
					if(topic1==topic2 || topic1==topic2 || topic2==topic3)
					{
						JOptionPane.showMessageDialog(null, "Select Unique Topics");
					}
					else
					{
						userKey+=Integer.toString(topic1);
						userKey+=Integer.toString(topic2);
						userKey+=Integer.toString(topic3);

						JOptionPane.showMessageDialog(null, "Your selected topics: "+userKey);
						
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
							JOptionPane.showMessageDialog(null, "Successfully Updated Topics");
							
							contentPane.setVisible(false);
							dispose();
							
							TriviaClientMenuScreen reg_user=new TriviaClientMenuScreen();
							reg_user.setVisible(true);
						}
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
				
				System.out.println("Got back JSON");
				
				System.out.println("Status: "+jdc1.getStatus());
				
				System.out.println("Message Type: "+jdc1.getMessageType());
				
				System.out.println("Error Value: "+jdc1.getErrorValue());
				
				//may set userId latter thru changes in JsonDataContract and Server-Side Modules
				ui.Constants.myKey=jdc1.getTopicsKey();
				
				return 1;
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(241, 432, 111, 40);
		contentPane.add(btnNewButton);
		
		JLabel lblDefaulthistory = new JLabel("Default:History");
		lblDefaulthistory.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblDefaulthistory.setBounds(415, 130, 166, 40);
		contentPane.add(lblDefaulthistory);
	}
}
