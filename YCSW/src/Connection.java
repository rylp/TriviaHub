import java.util.*;
import java.net.*;
import java.io.*;

public class Connection implements Runnable
{
	@Override
	public void run()
	{
		try
		{
			while(!Thread.currentThread().isInterrupted()) 
			{
				int port=9997;
				ServerSocket ss=new ServerSocket(port);
				
				Socket s=ss.accept();
				
				
				System.out.println("Connection Class:");
				System.out.println(s.getInetAddress().getHostAddress());
				System.out.println(s.getPort());
				System.out.println(s.getInputStream());
				System.out.println(s.getOutputStream());
				
				Communication comm=new Communication(s);
				
				ss.close();
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
