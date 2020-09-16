package client;
import java.net.*;

public class ClientConnection
{
	public boolean connect() 
	{
		Socket socket=null;
		
		boolean isConnected=false;
		
		try
		{
			String ip="localhost";
			int port=9997;
			
			System.out.println("Connection Class");
			
			socket=new Socket(ip,port);
			
			Constants.socket=socket;
			
			Constants.clientIp=socket.getInetAddress().getHostAddress();
			Constants.clientPort=socket.getLocalPort();
			
			System.out.println("Connection Established");
			
			ClientCommunication clientComm=new ClientCommunication(socket);
			
			isConnected=true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return isConnected;
	}
}
