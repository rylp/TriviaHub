package users;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

public class ViewTrivia {
	
	private int topic;
	private String email=null;
	private String topicKey=null;
	private final String MSG_TYPE="VIEW";
	
	Constants constant=new Constants();
	
	ArrayList<String> myTopics=null;
	
	public ViewTrivia() 
	{
		myTopics = constant.getFirstList();
		this.email=Constants.myEmail;
		this.topicKey=Constants.myKey;	
	}
	
	public boolean DisplayTrivia()
	{
		System.out.println("Choose Topic to View Trivia");
		
		for(int i=0;i<3;i++)
		{
			try
			{
				System.out.println(i+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(this.topicKey.charAt(i)))));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Choose a topic");
		this.topic=Integer.parseInt(String.valueOf(this.topicKey.charAt(Integer.parseInt(sc.nextLine()))));
		
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(MSG_TYPE);
		
		jdc.setEmail(this.email);
		jdc.setTopicsKey(this.topicKey);
		jdc.setTopic(String.valueOf(this.topic));
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		boolean Result=sendData(clientData);
		
		return Result;
	}

	public boolean sendData(String clientData) 
	{
		boolean Success=true;
		
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
		
		System.out.println("ClientData: "+clientData);
		
		client.ClientCommunication clientComm=new client.ClientCommunication();
		
		client.ClientCommunication.Transmitter tr=clientComm.new Transmitter();
		
		client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socketDetails);
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		System.out.println("Retrieved Data: "+jdc1.getTriviaData());
		
		String triviaReceived=jdc1.getTriviaData();
		
		//If there exists a trivia
		if(!jdc1.getTriviaData().isEmpty())
		{
			String[] recievedTrivia=triviaReceived.split("#");
			
			System.out.println("Displaying Trivia");
			
			int inc=1;
			
			for(String trivia:recievedTrivia)
			{
				System.out.println(inc+": "+trivia);
				inc++;
			}
		}
		
		return Success;
	}
	
	
//	void DisplayTrivia(String userKey,int userID)
//	{
//	    ArrayList<String> TopicTrivia = new ArrayList<String>();
//		Constants constant=new Constants();
//		
//		System.out.println("Choose Topic to Display Trivia");
//		
//		System.out.println("Topics Choosen:");
//		for(int i=0;i<3;i++)
//		{
//			try
//			{
//				System.out.println(i+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(userKey.charAt(i)))));
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		
//		System.out.println("Choose a topic");
//		Scanner ch=new Scanner(System.in);
//		int choice=ch.nextInt();
//		
//		int topic_key=Integer.parseInt(String.valueOf(userKey.charAt(choice)));
//		
//		try
//		{
//			String url="jdbc:mysql://localhost:3306/trivia-db";
//			String uname="root";
//			String pass="temp123";
//			
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con=DriverManager.getConnection(url,uname,pass);
//			
//			ResultSet rs;
//			CallableStatement statement = con.prepareCall("{call displayTrivia(?)}");
//			statement.setInt(1, topic_key);
//			
//			rs=statement.executeQuery();
//			
//			while(rs.next())
//			{
//				TopicTrivia.add(rs.getString("trivia_content"));
//			}
//			
//			if(TopicTrivia.size()==0)
//			{
//				System.out.println("No Trivia Added Yet!!!");
//			}
//			
//			else
//			{
//				System.out.println("----Displaying Trivia-----");
//				for(int i=0;i<TopicTrivia.size();i++)
//				{
//					System.out.println(i+" : "+TopicTrivia.get(i));
//				}	
//			}
//			
//			statement.close();
//			con.close();
//			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}
