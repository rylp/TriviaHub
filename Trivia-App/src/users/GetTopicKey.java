package users;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import client.SocketDetails;
import client.ClientCommunication.Transmitter;
import json.JsonDataContract;

public class GetTopicKey {
	
	private final String MSG_TYPE="KEY";
	private String emailId=null;
	
	public GetTopicKey() 
	{
		this.emailId=Constants.myEmail;
	}
	
	public boolean extractKey()
	{
		Gson gson=new Gson();
		
		JsonDataContract jdc=new JsonDataContract();
		
		//we'll work with TopicsKey itself
		
		jdc.setMessageType(this.MSG_TYPE);
		jdc.setEmail(this.emailId);
		
		jdc.setClientIp(client.Constants.clientIp);
		jdc.setClientPort(String.valueOf(client.Constants.clientPort));
		
		String clientData=gson.toJson(jdc,JsonDataContract.class);
		
		boolean Result=sendData(clientData);
		
		return Result;
	}

	public boolean sendData(String clientData) 
	{
		boolean Success=true;
		
		client.SocketDetails socDetails=new client.SocketDetails();
		
		//set up inputstream
		try {
			socDetails.setIn(new DataInputStream(client.Constants.socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set up outputstream
		
		try {
			socDetails.setOut(new OutputStreamWriter(client.Constants.socket.getOutputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ClientData: "+clientData);
		
		client.ClientCommunication clientComm=new client.ClientCommunication();
		
		client.ClientCommunication.Transmitter tr=clientComm.new Transmitter();
		
		client.JsonDataContract jdc1=tr.sendDataToServer(clientData,socDetails);
		
		System.out.println("Got back JSON");
		
		System.out.println("Status: "+jdc1.getStatus());
		
		System.out.println("Message Type: "+jdc1.getMessageType());
		
		System.out.println("Error Value: "+jdc1.getErrorValue());
		
		System.out.println("Topic Key Recieved: "+jdc1.getTopicsKey());
		
		//set up the key retrived from server-side
		if(jdc1.getErrorValue().isEmpty())
		{
			users.Constants.myKey=jdc1.getTopicsKey();
		}
		else if(jdc1.getErrorValue().equals(users.Constants.EMPTY_TOPIC_KEY))
		{
			System.out.println("No Key Available");
			
			//TODO:Give option of adding topic key
			
			return false;
		}

		return Success;		
	}
}
