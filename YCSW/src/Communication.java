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
							RegisterUser newUser=new RegisterUser(jdc);
							if(newUser.registerUser())
							{
								sendData(jdc.getEmail()+" Registered Successfully!");
							}
							else
							{
								sendData(jdc.getEmail()+" Not Registered!");
							}
							break;
						case "LOGIN":
							LogIn User=new LogIn(jdc);
							int loginStatus=User.loginUser();
							if(loginStatus==-1)
							{
								sendData(jdc.getEmail()+" is not registered!");
							}
							else if(loginStatus==-2)
							{
								System.out.println("Sending msg!");
								sendData(jdc.getEmail()+" Password is wrong!");
							}
							else if(loginStatus>0)
							{
								sendData(loginStatus+" User Succesfully Logged In");
							}
							else
							{
								sendData("Failure to Login");
							}
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
				Socket soc=Constants.clientQueue.get(clientKey);
				
				try (OutputStreamWriter out = new OutputStreamWriter(
				        soc.getOutputStream(), StandardCharsets.UTF_8)) {
				    out.write(data);
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
				
//				try
//				{	
//					Socket soc=Constants.clientQueue.get(clientKey);
//					DataOutputStream out= new DataOutputStream(soc.getOutputStream());
//					out.writeBytes(data);//issue
//					out.flush();
//					
//					System.out.println("Written to client");
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}
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
