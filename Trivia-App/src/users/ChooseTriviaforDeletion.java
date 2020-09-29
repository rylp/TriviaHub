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

public class ChooseTriviaforDeletion {
	
	private String emailId=null;
	private String topicKey=null;
	private final String MSG_TYPE="VIEWMY";
	private int triviaId;
	
	Constants constant=new Constants();
	
	ArrayList<String> myTopics=null;
	
	public ChooseTriviaforDeletion() 
	{
		myTopics=constant.getFirstList();
		this.emailId=users.Constants.myEmail;
		this.topicKey=users.Constants.myKey;
	}
	
	public int showTrivia()
	{
		System.out.println("Your Trivia");
		
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(this.MSG_TYPE);
		
		jdc.setEmail(this.emailId);
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		int Result=sendData(clientData);
		
		return Result;
	}

	private int sendData(String clientData) {
		
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
		
		if(jdc1.getTriviaData().isEmpty())//Empty
		{
			System.out.println("No Trivia yet Added");
			return -2;
		}
		else
		{
			int triviaId=chooseTrivia(jdc1.getTriviaData(),jdc1.getTriviaIds());
			return triviaId;
		}
	}

	private int chooseTrivia(String triviaData,String triviaIds) 
	{
		
		String[] receivedTrivia=triviaData.split("#");
		String[] receivedTrviaIds=triviaIds.split("#");
		
		int length=receivedTrivia.length;
		
		System.out.println("Choose Which Trivia you want to delete");
		
		for(int i=0;i<length;i++)
		{
			System.out.println(receivedTrviaIds[i]+" : "+receivedTrivia[i]);
		}
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Choose -1 to not delete any");
		int triviaId=Integer.parseInt(sc.nextLine());
		
		return triviaId;
	}
	
//	int GetTriviaId(String userKey,int userId)
//	{
//		HashMap<Integer, String> myTrivia=new HashMap<Integer,String>();
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
//			CallableStatement statement = con.prepareCall("{call showTriviaByUser(?)}");
//			statement.setInt(1, userId);
//			
//			rs=statement.executeQuery();
//			
//			while(rs.next())
//			{
//				myTrivia.put(rs.getInt("trivia_id"),rs.getString("trivia_content"));
//			}
//			
//			if(myTrivia.size()==0)
//			{
//				System.out.println("No Trivia Added Yet!!!");
//				return -1;
//			}
//			
//			else
//			{
//				System.out.println("----Displaying Your Trivia-----");	
//		        for (HashMap.Entry<Integer,String> entry : myTrivia.entrySet())
//		        {
//		            System.out.println(entry.getKey() + " : " + entry.getValue());
//		        }
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
//		
//		System.out.println("Choose the triviaId of the trivia to delete");
//		System.out.println("Enter -1 is you don't want to delete any");
//		Scanner ch=new Scanner(System.in);
//		int choice=ch.nextInt();
//		
//		return choice;
//	}

}
