package client;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) 
	{
		communicationEndpoint();
	}

	public static void communicationEndpoint() {
		System.out.println("Client started!");
		
		System.out.println("Connecting to Server");
		
		ClientConnection clientConn=new ClientConnection();
		clientConn.connect();
	}

}
