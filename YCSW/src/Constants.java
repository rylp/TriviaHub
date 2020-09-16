import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	static Map<String,Socket> clientQueue=new HashMap<String,Socket>();
	
	public static String generateClientKey(String clientIpAddr,String clientPort)
	{
		return clientIpAddr+"$"+clientPort;
	}

}
