package users;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

public class DislikeTrivia {
	
	private final String MSG_TYPE="DISLIKE";
	private String emailid=null;
	private int triviaId;
	
	public DislikeTrivia(int triviaId) 
	{
		this.emailid=users.Constants.myEmail;
		this.triviaId=triviaId;
	}

	public boolean DislikeSelectedTrivia() 
	{
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(this.MSG_TYPE);
		
		jdc.setEmail(this.emailid);
		jdc.setTriviaIdtoDislike(String.valueOf(this.triviaId));
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		boolean Result=sendData(clientData);
		
		return Result;
	}

	public boolean sendData(String clientData) {
		
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
}
