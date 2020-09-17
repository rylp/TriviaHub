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
		
		public void sendData(String data)
		{	
			tr=new Transmitter();
			tr.sendDataToServer(data,soc);
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
		
		public JsonDataContract sendDataToServer(String data,Socket soc)
		{
			JsonDataContract jdc=new JsonDataContract();
			
			System.out.println(data);

				try (OutputStreamWriter out = new OutputStreamWriter(
				        soc.getOutputStream(), StandardCharsets.UTF_8)) 
				{
				    out.write(data);
				    out.flush();
				    
					Runnable rec=new Receiver(s);
					Thread t1=new Thread(rec,"Receiver Thread");
					t1.start();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			
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
