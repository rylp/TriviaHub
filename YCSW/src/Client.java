import java.net.*;

public class Client {

	public static void main(String[] args) throws Exception{
		
		String ip="localhost";
		int port=9997;
		
		Socket s=new Socket(ip,port);
		
		System.out.println("Connection Established...");
	}
}
