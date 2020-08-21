package users;

import java.sql.*;
import java.util.*;
import users.TopicSelection.*;

public class AddTrivia 
{		
	void InsertTrivia(String userKey)
	{
		Constants constant=new Constants();
		
		System.out.println("For which topic do u want to add trivia??");
		
		System.out.println("Topics Choosen:");
		
		for(int i=0;i<3;i++)
		{
			try
			{
				System.out.println(i+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(userKey.charAt(i)))));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("Choose a topic");
		Scanner ch=new Scanner(System.in);
		int choice=ch.nextInt();
		
		System.out.println("Enter trivia");
		Scanner tr=new Scanner(System.in);
		String trivia=tr.nextLine();
		
		int likes=0;
		int topic_key=Integer.parseInt(String.valueOf(userKey.charAt(choice)));
		
		System.out.println("Topic ID: "+topic_key);
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call addTrivia(?,?,?)}");
			statement.setString(1, trivia);
			statement.setInt(2, topic_key);
			statement.setInt(3, likes);
			
			statement.execute();
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
