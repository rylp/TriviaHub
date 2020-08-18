package users;

import java.util.*;

public class LogIn {
	
	public String userName;
	public String password;
	
	void login_user()
	{
		System.out.println("Enter userName");
		Scanner uname=new Scanner(System.in);
		this.userName=uname.nextLine();
		
		System.out.println("Enter password");
		Scanner pass=new Scanner(System.in);
		this.password=pass.nextLine();
	}
	
	void show_details()
	{
		System.out.println("UserName : "+this.userName);
		System.out.println("Password : "+this.password);
	}

}
