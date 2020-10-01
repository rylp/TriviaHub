package users;

import java.util.*;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class ChooseTriviaToLike {
	
	private final String MSG_TYPE="VIEWNOTMY";
	private String emailid=null;
	private String userKey=null;
	private int topicSelected;
	
	Constants constant=new Constants();
	
	ArrayList<String> myTopics=null;
	
	public ChooseTriviaToLike() 
	{
		myTopics=constant.getFirstList();
		this.emailid=users.Constants.myEmail;
		this.userKey=users.Constants.myKey;
	}
	
	public int viewTrivia()
	{
		this.topicSelected=selectTopic();
		
		if(this.topicSelected==-3)
		{
			//INcorrect topic selected
			System.out.println("Invalid Topic Selected");
			return -3;
		}
		
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(this.MSG_TYPE);
		
		jdc.setEmail(this.emailid);
		jdc.setTopic(String.valueOf(this.topicSelected));
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		int Result=sendData(clientData);
		
		return Result;
	}

	public int sendData(String clientData) 
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
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		System.out.println("Trivia Recieved: "+jdc1.getTriviaData());
		
		System.out.println("Trivia Ids Recieved: "+jdc1.getTriviaIds());
		
		if(jdc1.getTriviaData().isEmpty())
		{
			System.out.println("No trivia currently added for that topic");
			return -2;
		}
		else
		{
			int triviaId=chooseTrivia(jdc1.getTriviaData(),jdc1.getTriviaIds());
			return triviaId;
		}
	}

	private int chooseTrivia(String triviaData, String triviaIds) 
	{	
		String[] receivedTrivia=triviaData.split("#");
		String[] receivedTrviaIds=triviaIds.split("#");
		
		int length=receivedTrivia.length;
		
		System.out.println("Choose Which Trivia you want to like");
		
		for(int i=0;i<length;i++)
		{
			System.out.println(receivedTrviaIds[i]+" : "+receivedTrivia[i]);
		}
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Choose -1 to not like any");
		int triviaId=Integer.parseInt(sc.nextLine());
		
		return triviaId;
	}

	private int selectTopic() 
	{
		int topicSelected=-1;
		
		System.out.println("Your Topics of Interest");
		
		for(int i=0;i<3;i++)
		{
			try
			{
				System.out.println(i+1+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(this.userKey.charAt(i)))));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Choose a topic");
		topicSelected=Integer.parseInt(String.valueOf(this.userKey.charAt(Integer.parseInt(sc.nextLine())-1)));
		
		if(topicSelected>=0 && topicSelected<=9)
		{
			return topicSelected;
		}
		
		return -3;
	}
	
//	int GetTriviaId(String userKey,int userId)
//	{
//		ArrayList<String>TopicTrivia=new ArrayList<String>();
//		ArrayList<Integer>TriviaIds=new ArrayList<Integer>();
//		
//		Constants constant=new Constants();
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
//			CallableStatement statement = con.prepareCall("{call displayTriviaByTopic(?,?)}");
//			statement.setInt(1, topic_key);
//			statement.setInt(2, userId);
//			
//			rs=statement.executeQuery();
//			
//			while(rs.next())
//			{
//				TopicTrivia.add(rs.getString("trivia_content"));
//				TriviaIds.add(rs.getInt("trivia_id"));
//			}
//			
//			if(TopicTrivia.size()==0)
//			{
//				System.out.println("No Trivia Added Yet!!!");
//				return -1;
//			}
//			
//			else
//			{
//				System.out.println("----Displaying Trivia-----");
//				for(int i=0;i<TopicTrivia.size();i++)
//				{
//					System.out.println(TriviaIds.get(i)+" : "+TopicTrivia.get(i));
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
//		
//		System.out.println("Choose Trivia Id for Trivia to like");
//		Scanner sc=new Scanner(System.in);
//		int triviaKey=sc.nextInt();
//		
//		if(!TriviaIds.contains(triviaKey))
//		{
//			System.out.println("Invalid Trivia Key");
//			return -1;
//		}
//		
//		return triviaKey;	
//	}

}
