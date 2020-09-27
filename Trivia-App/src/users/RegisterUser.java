package users;

import java.util.*;
import com.google.gson.Gson;

import json.JsonDataContract;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class RegisterUser 
{	
	private String fname=null;
	private String lname=null;
	private int age=0;
	private String password=null;
	private String email=null;
	private final String MSG_TYPE="REGISTER";
	
	public boolean registerUser()
	{	
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter your Email ID");
		this.email=sc.nextLine();
		
		System.out.println("Enter your First Name");
		this.fname=sc.nextLine();
		
		System.out.println("Enter your Last Name");
		this.lname=sc.nextLine();
		
		System.out.println("Enter your Age");
		this.age=Integer.parseInt(sc.nextLine());
		
		System.out.println("Enter your Password");
		this.password=sc.nextLine();
		
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		jdc.setMessageType(MSG_TYPE);
		
		jdc.setFirstName(this.fname);
		jdc.setLastName(this.lname);
		jdc.setEmail(this.email);
		jdc.setAge(String.valueOf(this.age));
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
		
		client.ClientCommunication clientComm=new client.ClientCommunication();
		
		client.ClientCommunication.Transmitter tr=clientComm.new Transmitter();
		
		client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socketDetails);
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		//Set up email. May set up UserId latter in JsonDataContract & Server-Side
		users.Constants.myEmail=jdc1.getEmail();
		
		return Success;
	}
	
	void showDetails()
	{
		System.out.println("Welcome "+this.fname+" "+this.lname);
	}

}
