import java.util.*;
import java.net.*;
import java.io.*;

public class Communication {
	
	private Socket s;

	public Communication(Socket s) {
		this.setS(s);
		
		Runnable rec=new Receiver(s);
		Thread t1=new Thread(rec,"Receiver Thread");
		t1.start();
		
		System.out.println("Alive Threads "+Thread.activeCount());
	}
	
	public class Receiver implements Runnable
	{
		private Socket soc;
		
		public Receiver(Socket s) {
			this.setSoc(s);
		}
		
		@Override
		public void run() {
			System.out.println("Receiver Class: ");
			System.out.println(soc.getInetAddress().getHostAddress());
			System.out.println(soc.getPort());
		}

		public Socket getSoc() {
			return soc;
		}
		
		public void setSoc(Socket soc) {
			this.soc = soc;
		}
	}
	
	public class Transmitter
	{
		
	}
	
	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
}
