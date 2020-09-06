public class Server {
	
	public static void main(String[] args) throws Exception{
		
		System.out.println("Server Started");
		
		System.out.println("Waiting for connection");
		
		Runnable con=new Connection();
		Thread t=new Thread(con,"Connection Thread");
		t.start();
	}

}
