package users;

import java.sql.*;
import java.util.*;

import users.LogIn;
import users.TopicSelection.*;
import users.AddTrivia.*;
import users.RegisterUser.*;
import users.ViewTrivia.*;

public class Trivia 
{	
	public static void main(String[] args) 
	{
		System.out.println("---WELCOME TO Trivia-Hub---");
		System.out.print("Choose your option");
		System.out.print("\n\t1.Log In");
		System.out.print("\n\t2.Register");
		Scanner sc=new Scanner(System.in);
		int ch=sc.nextInt();
		
		MySession sess=new MySession();
		
		if(ch==1) //Log In
		{
          LogIn curUser=new LogIn();
			
          curUser.loginUser();
          curUser.showDetails();
          
//          //set the userKey for now statically
//          String userKey="123";
//          
//          sess.setMykey(userKey);
//			
//          //display topics of choice
		}
		else //Register
		{	
			RegisterUser newUser = new RegisterUser(); 
			
			newUser.registerUser();
			newUser.showDetails();
			
			try
			{
				String url="jdbc:mysql://localhost:3306/trivia-db";
				String uname="root";
				String pass="temp123";
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(url,uname,pass);
				
				ResultSet rs;
				CallableStatement statement = con.prepareCall("{call getUserId()}");
				
				rs=statement.executeQuery();
				
				rs.next();
				int userId=rs.getInt(1);
				
				System.out.println("User Id: "+userId);
				
				sess.setMyid(userId);
				
				statement.close();
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//get topics of choice
			System.out.println("Select Topics of your choice");
			
			TopicSelection ts=new TopicSelection();	
			String userKey=ts.SelectTopic(sess.getMyid());
			sess.setMykey(userKey);	
		}
		
		int opt;
		
		do
		{
			System.out.println("Enter ur choice");
			System.out.print("\n\t1.Add Trivia");
			System.out.print("\n\t2.View Trivia");
			System.out.print("\n\t3.Delete Trivia");
			System.out.print("\n\t4.Add Topic of Choice");
			System.out.print("\n\t5.Like Trivia");
			System.out.print("\n\t6.Log Out");
			Scanner option=new Scanner(System.in);
			opt=option.nextInt();
			
			switch(opt)
			{
				case 1:
				{
					AddTrivia at=new AddTrivia();
					at.InsertTrivia(sess.getMykey(),sess.getMyid());
					break;
				}
				case 2:
				{
					ViewTrivia vt=new ViewTrivia();
					vt.DisplayTrivia(sess.getMykey(),sess.getMyid());
					
					break;
				}
				case 3:
				{
					break;
				}
				case 4:
				{
					break;
				
				}
				case 5:
				{
					break;
				
				}
				case 6:
				{
					System.out.println("Logged Out!");
					break;
				}
				default:System.out.println("Invalid Choice");
			}
			
		}while(opt!=6);

	}
}
