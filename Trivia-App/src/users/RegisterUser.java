package users;

import java.util.*;
import com.google.gson.Gson;

import json.JsonDataContract;

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
		
		client.ClientCommunication clientComm=new client.ClientCommunication();
		
		client.ClientCommunication.Transmitter tr=clientComm.new Transmitter();
		
		client.JsonDataContract jdc1=tr.sendDataToServer(clientData, client.Constants.socket);
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		return Success;
	}
	
	
//	public boolean registerUser()
//	{	
//		Scanner sc=new Scanner(System.in);
//		
//		System.out.println("Enter Your Email ID");
//		this.email=sc.nextLine();
//		
//		System.out.println("Enter Your First Name");
//		this.fname=sc.nextLine();
//		
//		System.out.println("Enter Your Last Name");
//		this.lname=sc.nextLine();
//		
//		System.out.println("Enter Your Age");
//		this.age=sc.nextInt();
//		
//		sc.nextLine();
//		
//		System.out.println("Enter Your Password");
//		this.password=sc.nextLine();
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
//			CallableStatement statement = con.prepareCall("{call registerNewUser(?,?,?,?,?)}");
//			statement.setString(1, this.fname);
//			statement.setString(2, this.lname);
//			statement.setString(3, this.email);
//			statement.setInt(4, this.age);
//			statement.setString(5, this.password);
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
	
	void showDetails()
	{
		System.out.println("Welcome "+this.fname+" "+this.lname);
	}

}
