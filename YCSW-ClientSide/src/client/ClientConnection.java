package client;
import java.net.*;

public class ClientConnection
{
	public void connect() throws Exception {
		
		Socket socket=null;
		
		try
		{
			//Should come from GUI.
//			String ip="localhost";
			
			String ip="192.168.0.28";

//			String ip=String.valueOf(Inet4Address.getLocalHost()).split("/")[1];
			int port=9997;
			
			System.out.println("Connection Class");
			
			socket=new Socket(ip,port);
			
			Constants.socket=socket;
			
			Constants.clientIp=socket.getInetAddress().getHostAddress();
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
