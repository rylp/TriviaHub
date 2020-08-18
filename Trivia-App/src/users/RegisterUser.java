package users;

import java.util.*;

public class RegisterUser 
{	
	public String fname;
	public String lname;
	public int age;
	public String userName;
	public String password;
	public int userId;
//    private static int userIdCounter = 0;
//
//    public RegisterUser()
//    {
//        this.userId= userIdCounter++;
//    }
	
	void register_user()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Your First Name");
		this.fname=sc.nextLine();
		
		System.out.println("Enter Your Last Name");
		this.lname=sc.nextLine();
		
		System.out.println("Enter Your Age");
		this.age=sc.nextInt();
		
		sc.nextLine();
		
		System.out.println("Enter Your UserName");
		this.userName=sc.nextLine();
		
		System.out.println("Enter Your Password");
		this.password=sc.nextLine();
	}
	
	void show_details()
	{
		System.out.println("UserID : "+this.userId);
		System.out.println("UserName : "+this.userName);
		System.out.println("First Name : "+this.fname);
		System.out.println("Last Name : "+this.lname);
		System.out.println("Password : "+this.password);
		System.out.println("Age : "+this.age);
	}

}
