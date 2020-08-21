package users;

import java.util.*;

public class LogIn {
	
	public String email;
	public String password;
	
	void loginUser()
	{
		System.out.println("Enter your email ID");
		Scanner sc=new Scanner(System.in);
		this.email=sc.nextLine();
		
		System.out.println("Enter password");
		Scanner pass=new Scanner(System.in);
		this.password=pass.nextLine();
		
		//later
	}
	
	void showDetails()
	{
		System.out.println("UserName : "+this.email);
		System.out.println("Password : "+this.password);
	}

}
