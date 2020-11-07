package serverModules;

import java.net.Inet4Address;

public class Server {
	
	public static void main(String[] args) throws Exception
	{	
		System.out.println("Server Started");
		
		System.out.println("Waiting for connection");
		
		System.out.println("Server IP:"+Inet4Address.getLocalHost());
		
		String ip=String.valueOf(Inet4Address.getLocalHost()).split("/")[1];
		
		System.out.println(ip);
		
		Connection con=new Connection();
		Constants.threadPool.submit(con);
		
//		Thread t=new Thread(con,"Connection Thread");
//		t.start();
	}
}
