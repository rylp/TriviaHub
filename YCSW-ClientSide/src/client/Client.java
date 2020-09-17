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
		
		Runnable clientConn=new ClientConnection();
		Thread tc=new Thread(clientConn,"ClientConnectionThread");
		tc.start();
	}

}
