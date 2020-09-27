package users;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

import com.google.gson.Gson;

import client.Constants;
import client.ClientCommunication;
import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

public class LogIn {
	
	private String email;
	private String password;
	private final String MSG_TYPE="LOGIN";
	
	public boolean loginUser()
	{
		System.out.println("Enter your email ID");
		Scanner sc=new Scanner(System.in);
		this.email=sc.nextLine();
		
		System.out.println("Enter password");
		Scanner pass_sc=new Scanner(System.in);
		this.password=pass_sc.nextLine();
		
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(MSG_TYPE);
		
		jdc.setEmail(this.email);
		jdc.setPassword(this.password);
		
		System.out.println(client.Constants.clientIp);
		
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
		
		client.ClientCommunication clComm=new client.ClientCommunication(); 
		
		client.ClientCommunication.Transmitter tr=clComm.new Transmitter();//class within class
		
		client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socketDetails);
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		//may set userId latter thru changes in JsonDataContract and Server-Side Modules
		//TODO: Fix users.Constants.myEmail if wrong email/password.
		users.Constants.myEmail=jdc1.getEmail();
		
		return Success;
	}
	
}
