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

public class LikeTrivia {
	
	private final String MSG_TYPE="LIKE";
	private String emailid=null;
	private int triviaId;
	
	public LikeTrivia(int triviaId) 
	{
		this.emailid=users.Constants.myEmail;
		this.triviaId=triviaId;
	}
	
	public boolean LikeSelectedTrivia()
	{
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(this.MSG_TYPE);
		
		jdc.setEmail(this.emailid);
		jdc.setTriviaIdtoLike(String.valueOf(this.triviaId));
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		boolean Result=sendData(clientData);
		
		return Result;
	}

	public boolean sendData(String clientData) 
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
		
		return true;
	}
	
//	void LikeSelectedTrivia(int triviaId)
//	{
//		try
//		{
//			String url="jdbc:mysql://localhost:3306/trivia-db";
//			String uname="root";
//			String pass="temp123";
//			
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con=DriverManager.getConnection(url,uname,pass);
//
//			CallableStatement statement = con.prepareCall("{call addLikeByTopicId(?)}");
//			statement.setInt(1, triviaId);
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
//		
//	}

}
