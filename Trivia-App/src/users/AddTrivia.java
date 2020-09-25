package users;

import java.sql.*;
import java.util.*;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;
import users.TopicSelection.*;

public class AddTrivia 
{
	private String userKey=null;
	private String emailId=null;
	private int topic;
	private String triviaContent=null;
	private int likes;
	private final String MSG_TYPE="ADD";
	
	Constants constant=new Constants();
	
	ArrayList<String> myTopics=null;
	
	public AddTrivia() 
	{	
		myTopics = constant.getFirstList();
		this.userKey=Constants.myKey;
		this.emailId=Constants.myEmail;
		this.likes=0;
	}

	public boolean InsertTrivia()
	{	
		System.out.println("Your Topics of Interest");
		
		for(int i=0;i<3;i++)
		{
			try
			{
				System.out.println(i+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(this.userKey.charAt(i)))));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Choose a topic");
		this.topic=Integer.parseInt(String.valueOf(this.userKey.charAt(Integer.parseInt(sc.nextLine()))));
		
		System.out.println("Enter trivia");
		this.triviaContent=sc.nextLine();
		
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(this.MSG_TYPE);
		
		jdc.setEmail(this.emailId);
		jdc.setTriviaContent(this.triviaContent);
		jdc.setTopic(String.valueOf(this.topic));
		jdc.setLikes(String.valueOf(this.likes));
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		boolean Result=sendData(clientData);
		
		return Result;
	}

	public boolean sendData(String clientData) 
	{
		boolean Success=true;
		
		System.out.println("ClientData: "+clientData);
		
		client.ClientCommunication clientComm=new client.ClientCommunication();
		
		client.ClientCommunication.Transmitter tr=clientComm.new Transmitter();
		
		client.JsonDataContract jdc1=tr.sendDataToServer(clientData, client.Constants.socket);
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		return Success;
	}
	
//	void InsertTrivia(String userKey,int userID)
//	{
//		Constants constant=new Constants();
//		
//		System.out.println("For which topic do u want to add trivia??");
//		
//		System.out.println("Topics Choosen:");
//		
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
//		System.out.println("Enter trivia");
//		Scanner tr=new Scanner(System.in);
//		String trivia=tr.nextLine();
//		
//		int likes=0;
//		int topic_key=Integer.parseInt(String.valueOf(userKey.charAt(choice)));
//		
//		System.out.println("Topic ID: "+topic_key);
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
//			CallableStatement statement = con.prepareCall("{call addTrivia(?,?,?,?)}");
//			statement.setString(1, trivia);
//			statement.setInt(2, topic_key);
//			statement.setInt(3, likes);
//			statement.setInt(4, userID);
//			
//			statement.executeUpdate();
//			
//			statement.close();
//			con.close();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}

}
