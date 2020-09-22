import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	static Map<String,ClientDetails> clientQueue=new HashMap<String,ClientDetails>();
	
	public static final String SUCCESS="SUCCESS"; 
	public static final String FAILURE="FAILURE"; 
	public static final String LOGIN="LOGIN"; 
	public static final String REGISTER="REGISTER"; 
	public static final String WRONG_EMAIL="INCORRECT-EMAIL";
	public static final String WRONG_PASSWORD="INCORRECT-PASSWORD";
	public static final String EMAIL_EXISTS="EMAIL-ALREADY-EXISTS";
	public static final String ISSUE="NETWORK-ISSUE-PLEASE-RETRY";
	
	public static String generateClientKey(String clientIpAddr,String clientPort)
	{
		return clientIpAddr+"$"+clientPort;
	}

}
