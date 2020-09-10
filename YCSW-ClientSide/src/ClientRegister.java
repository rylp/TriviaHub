import java.util.*;

public class ClientRegister {
	
	private String fname;
	private String lname;
	private String age;
	private String password;
	private String email;
	
	public void registerUser(JsonDataContract jdc)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter ur email");
		this.email=sc.nextLine();
		jdc.setEmail(this.email);
		
		System.out.println("Enter ur first name");
		this.fname=sc.nextLine();
		jdc.setFirstName(this.fname);
		
		System.out.println("Enter ur last name");
		this.lname=sc.nextLine();
		jdc.setLastName(this.lname);
		
		//Taken as a string. Parsing as Integer at server-side.
		System.out.println("Enter ur age");
		this.age=sc.nextLine();
		jdc.setAge(this.age);
		
		System.out.println("Enter ur password");
		this.password=sc.nextLine();
		jdc.setPassword(this.password);
	}

}
