package serverModules;
import java.util.*;
import java.net.*;
import java.io.*;

public class Connection implements Runnable
{
	@Override
	public void run()
	{
		ServerSocket ss=null;
		
		try
		{
			int port=9997;
			ss=new ServerSocket(port);
			
			while(!Thread.currentThread().isInterrupted()) 
			{
				Socket s=ss.accept();
				
				String clientIpAddr=s.getInetAddress().getHostAddress();
				String clientPort= String.valueOf(s.getPort());
				
				ClientDetails newClient=new ClientDetails();
				newClient.setSoc(s);
				
				Constants.clientQueue.put(Constants.generateClientKey(clientIpAddr, clientPort), newClient);
				
				System.out.println("Connection Class:");
				
				Communication comm=new Communication(s);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
