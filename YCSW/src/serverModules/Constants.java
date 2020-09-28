package serverModules;
import java.net.Socket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {
	
	static Map<String,ClientDetails> clientQueue=new HashMap<String,ClientDetails>();
	
	public static ExecutorService threadPool=Executors.newFixedThreadPool(20);
	
	public static final String SUCCESS="SUCCESS"; 
	public static final String FAILURE="FAILURE"; 
	
	public static final String LOGIN="LOGIN"; 
	public static final String REGISTER="REGISTER";
	public static final String SELECT="SELECT";
	public static final String ADD="ADD";
	public static final String VIEW="VIEW";
	public static final String KEY="KEY";
	
	
	public static final String WRONG_EMAIL="INCORRECT-EMAIL";
	public static final String WRONG_PASSWORD="INCORRECT-PASSWORD";
	public static final String EMAIL_EXISTS="EMAIL-ALREADY-EXISTS";
	public static final String ISSUE="NETWORK-ISSUE-PLEASE-RETRY";
	public static final String EMPTY_TRIVIA="NO-TRIVIA-AVAILABLE-CURRENTLY";
	public static final String EMPTY_TOPIC_KEY="NO-TOPIC-KEY-AVAILABLE-CURRENTLY";
	
	
	public static String generateClientKey(String clientIpAddr,String clientPort)
	{
		return clientIpAddr+"$"+clientPort;
	}

}
