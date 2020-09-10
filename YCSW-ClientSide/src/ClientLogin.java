import java.util.*;

public class ClientLogin {
	
	private String email=null;
	private String password=null;
	
//	private JsonDataContract jdc=null;
	
//	public ClientLogin(JsonDataContract jdc) {
//		this.jdc=jdc;
//	}
	
	public void loginUser(JsonDataContract jdc)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter ur email");
		this.email=sc.nextLine();
		jdc.setEmail(this.email);
		
		System.out.println("Enter ur password");
		this.password=sc.nextLine();
		jdc.setPassword(this.password);
	}

}
