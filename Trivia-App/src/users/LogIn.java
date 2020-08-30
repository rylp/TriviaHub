package users;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

public class LogIn {
	
	public String email;
	public String password;
	public String topicKey;
	public int userId;
	
	int loginUser()
	{
		System.out.println("Enter your email ID");
		Scanner sc=new Scanner(System.in);
		this.email=sc.nextLine();
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call loginUser(?)}");
			statement.setString(1, this.email);
			
			rs=statement.executeQuery();
			
			String verify_pass="";
			String topicKey="";
			int userId=0;
			
			if(!rs.next())
			{
				System.out.println("ERROR!");
				System.out.println("Email ID DNE!");
				return -1;
			}
			
			do
			{
				userId=rs.getInt("user_id");
				topicKey=rs.getString("topic_key");
				verify_pass=rs.getString("pass");
				
			}while(rs.next());
			
			System.out.println("Enter password");
			Scanner pass_sc=new Scanner(System.in);
			this.password=pass_sc.nextLine();
			
			if(this.password.equals(verify_pass))
			{
				System.out.println("User Logged In Sucessfully");
				System.out.println("UserID: "+userId);
				System.out.println("Topic Key: "+topicKey);
				this.userId=userId;
				this.topicKey=topicKey;
			}
			else
			{
				System.out.println("ERROR!");
				System.out.println("Passwords don't match!");
				return -1;
			}

			statement.close();
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return this.userId;
	}
	
	String showDetails()
	{
		System.out.println("UserName : "+this.email);
		
		return this.topicKey;
	}
}
