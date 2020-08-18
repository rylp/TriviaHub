package users;

import java.util.*;
import java.sql.*;

public class RegisterUser 
{	
	public String fname;
	public String lname;
	public int age;
	public String password;
	
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
		
		System.out.println("Enter Your Password");
		this.password=sc.nextLine();
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call registerNewUser(?,?,?,?)}");
			statement.setString(1, this.fname);
			statement.setString(2, this.lname);
			statement.setInt(3, this.age);
			statement.setString(4, this.password);
			
			statement.execute();
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void show_details()
	{
		System.out.println("Welcome "+this.fname+" "+this.lname);
	}

}
