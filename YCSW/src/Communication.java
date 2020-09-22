import java.util.*;

import com.google.gson.Gson;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Communication {
	
	private Socket s;

	public Communication(Socket s) {
		this.setS(s);
		
		Runnable rec=new Receiver(s);
		Thread t1=new Thread(rec,"Receiver Thread");
		t1.start();
		
		System.out.println("Alive Threads "+Thread.activeCount());
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
				
				while(soc.isConnected())//Socket closed when it comes back here
				{
					int expectedBytes=in.available();
					
					if(expectedBytes>0)
					{
						byte[] buffer=new byte[expectedBytes];//in.read() demands byte buffer.
						
						in.read(buffer);
						
						String clientData=new String(buffer);//String is a object
						
						Gson gson=new Gson();
						
						JsonDataContract jdc=gson.fromJson(clientData, JsonDataContract.class);
						
						switch(jdc.getMessageType().toUpperCase())
						{
						case "REGISTER":
							CheckEmailExistence newUserEmail=new CheckEmailExistence(jdc);
							boolean checkEmail=newUserEmail.checkEmail();
							if(!checkEmail)
							{
								RegisterUser newUser=new RegisterUser(jdc);
								if(newUser.registerUser())
								{
									JsonDataContract responseJdc=new JsonDataContract();
									responseJdc.setStatus(Constants.SUCCESS);
									responseJdc.setEmail(jdc.getEmail());
									responseJdc.setMessageType(Constants.REGISTER);
									
									String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
									
									sendData(responseJSON);
								}
								else
								{
									JsonDataContract responseJdc=new JsonDataContract();
									responseJdc.setStatus(Constants.FAILURE);
									responseJdc.setEmail(jdc.getEmail());
									responseJdc.setErrorValue(Constants.ISSUE);
									responseJdc.setMessageType(Constants.REGISTER);
									
									String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
									
									sendData(responseJSON);
								}
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.EMAIL_EXISTS);
								responseJdc.setMessageType(Constants.REGISTER);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "LOGIN":
							LogIn User=new LogIn(jdc);
							int loginStatus=User.loginUser();
							if(loginStatus==-1)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.WRONG_EMAIL);
								responseJdc.setMessageType(Constants.LOGIN);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else if(loginStatus==-2)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.WRONG_PASSWORD);
								responseJdc.setMessageType(Constants.LOGIN);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else if(loginStatus>0)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setMessageType(Constants.LOGIN);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								//SET CLIENT DETAILS
								ClientDetails client=Constants.clientQueue.get(Constants.generateClientKey(jdc.getClientIp(), jdc.getClientPort()));
								
								if(client==null)
								{
									System.out.println("Client Not Found!");
									break;
								}
								
								client.setEmail(jdc.getEmail());
								client.setPassword(jdc.getPassword());
								
								
								System.out.println("Response Json sent to Client"+responseJSON);
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.ISSUE);
								responseJdc.setMessageType(Constants.REGISTER);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "SELECT":
							SelectTopics userTopics=new SelectTopics(jdc);
							boolean success=userTopics.updateTopicsinDB();
							if(success)
							{
								sendData("Successfully updated Topics of Interest");
							}
							else
							{
								sendData("Error in Topic Selection!");
							}
							break;
						case "ADD":
//							AddTrivia addTrivia=new AddTrivia(jdc);
//							addTrivia.insertTrivia();
							break;	
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sendData(String data)
		{
			String clientIpAddr=s.getInetAddress().getHostAddress();
			String clientPort= String.valueOf(s.getPort());
			
			System.out.println("Transmitting to Client");
			
			tr=new Transmitter();
			tr.sendDataToClient(data,Constants.generateClientKey(clientIpAddr, clientPort));
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
		public void sendDataToClient(String data,String clientKey)
		{
			
			if(Constants.clientQueue.containsKey(clientKey))
			{
				ClientDetails clientDetails=Constants.clientQueue.get(clientKey);
				
				Socket soc=clientDetails.getSoc();
				
				OutputStreamWriter os = null;
				try {
					
					os = new OutputStreamWriter(soc.getOutputStream());
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				PrintWriter out=new PrintWriter(os);
				out.println(data);
				out.flush();
				
				System.out.println("Sent data to client");
			}
		}
		
	}
	
	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
}
