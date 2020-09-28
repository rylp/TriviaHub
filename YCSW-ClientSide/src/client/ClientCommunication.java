package client;
import java.util.*;

import com.google.gson.Gson;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class ClientCommunication {
	
	private Socket s;

	public ClientCommunication(Socket s) {
		this.setS(s);
		
		System.out.println("Alive Threads "+Thread.activeCount());
	}
	
	public ClientCommunication() {
	}

	public class Receiver implements Runnable
	{
		private Socket soc=null;
		private DataInputStream in=null;
		Transmitter tr=null;
		
		private void setSocketDetails()
		{
			try {
				in=new DataInputStream(new BufferedInputStream(soc.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public Receiver(Socket s) {
			this.setSoc(s);
			setSocketDetails();
		}
		
		@Override
		public void run() 
		{	
			System.out.println("Receiver Class: ");
			
			try {
				
				while(soc.isConnected())
				{
					int expectedBytes=in.available();
					
					if(expectedBytes>0)
					{
						byte[] buffer=new byte[expectedBytes];//in.read() demands byte buffer.
						
						in.read(buffer);
						
						String serverData=new String(buffer);//String is a object
						
						Gson gson=new Gson();
						
						JsonDataContract jdc=gson.fromJson(serverData, JsonDataContract.class);
						
						switch(jdc.getMessageType().toUpperCase())
						{
						case "REGISTER":
							break;
						case "LOGIN":
							System.out.println("Client Side Reciever");
							break;
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sendData(String data,SocketDetails socDetails)
		{	
			tr=new Transmitter();
			tr.sendDataToServer(data,socDetails);
		}

		public Socket getSoc() {
			return soc;
		}
		
		public void setSoc(Socket soc) {
			this.soc = soc;
		}
	}
	
	public class Transmitter
	{
		private DataInputStream in=null;
		
		public JsonDataContract sendDataToServer(String data,SocketDetails socDetails)
		{
			JsonDataContract jdc=new JsonDataContract();
			
			System.out.println(data);
			
			//set up the input stream 'in' of Transmitter
//			try {
//				in=new DataInputStream(client.Constants.socket.getInputStream());
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
			in=socDetails.getIn();
			
			OutputStreamWriter out=socDetails.getOut();

//			try (OutputStreamWriter out = new OutputStreamWriter(
//					client.Constants.socket.getOutputStream(), StandardCharsets.UTF_8)) 
			
			try
			{	
				out.write(data);
				out.flush();
				
				//TODO:ADD receiving logic.
				while(client.Constants.socket.isConnected())
				{
					int expectedBytes=in.available();
					
					if(expectedBytes>0)
					{
						System.out.println("Recieved Confirmation from server side at client side!");
						
						byte[] buffer=new byte[expectedBytes];//in.read() demands byte buffer.
						
						in.read(buffer);
						
						String serverData=new String(buffer);//String is a object
						
						System.out.println(serverData);
						
						Gson gson=new Gson();
						
						jdc=gson.fromJson(serverData, JsonDataContract.class);
						
						switch(jdc.getMessageType().toUpperCase())
						{
						case "REGISTER":
							System.out.println("Client side REGISTER Reciever");
							break;
						case "LOGIN":
							System.out.println("Client Side LOGIN Reciever");
							break;
						case "SELECT":
							System.out.println("Client Side SELECT Reciever");
							break;
						case "KEY":
							System.out.println("Client Side KEY Reciever");
							break;
						case "ADD":
							System.out.println("Client Side ADD Receiver");
							break;
						case "VIEW":
							System.out.println("Client Side VIEW Receiver");
							break;
						}
						
						System.out.println("Closed: "+client.Constants.socket.isClosed());
						return jdc;
					}
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			System.out.println("Closed: "+client.Constants.socket.isClosed());
			return jdc;
		}
	}
	
	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
}
