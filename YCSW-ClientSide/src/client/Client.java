package client;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) 
	{
		communicationEndpoint();
	}

	public static boolean communicationEndpoint() {
		System.out.println("Client started!");
		
		System.out.println("Connecting to Server");
		
		try {
			ClientConnection clientConn=new ClientConnection();
			clientConn.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
