package client;
import java.net.*;

public class ClientConnection
{
	public void connect() throws Exception {
		
		Socket socket=null;
		
		try
		{
			
			String ip=client.Constants.serverIp;
			int port=Integer.parseInt(client.Constants.serverPort);
			
			System.out.println("Connection Class");
			
			socket=new Socket(ip,port);
			
			Constants.socket=socket;
			
			Constants.clientIp=String.valueOf(socket.getLocalAddress()).replace("/","");
			Constants.clientPort=socket.getLocalPort();
			
			System.out.println("Client IP:"+Constants.clientIp);
			System.out.println("Client Port:"+Constants.clientPort);
			
			System.out.println("Connection Established");
			
			ClientCommunication clientComm=new ClientCommunication(socket);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

}
