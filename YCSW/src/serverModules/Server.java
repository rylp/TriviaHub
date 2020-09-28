package serverModules;

public class Server {
	
	public static void main(String[] args) throws Exception
	{	
		System.out.println("Server Started");
		
		System.out.println("Waiting for connection");
		
		Connection con=new Connection();
		Constants.threadPool.submit(con);
		
//		Thread t=new Thread(con,"Connection Thread");
//		t.start();
	}
}
