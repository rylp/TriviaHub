package users;

import java.sql.*;
import java.util.*;

public class ViewTrivia {
	
	void DisplayTrivia(String userKey,int userID)
	{
	    ArrayList<String> TopicTrivia = new ArrayList<String>();
		Constants constant=new Constants();
		
		System.out.println("Choose Topic to Display Trivia");
		
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
		
		int topic_key=Integer.parseInt(String.valueOf(userKey.charAt(choice)));
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call displayTrivia(?)}");
			statement.setInt(1, topic_key);
			
			rs=statement.executeQuery();
			
			while(rs.next())
			{
				TopicTrivia.add(rs.getString("trivia_content"));
			}
			
			if(TopicTrivia.size()==0)
			{
				System.out.println("No Trivia Added Yet!!!");
			}
			
			else
			{
				System.out.println("----Displaying Trivia-----");
				for(int i=0;i<TopicTrivia.size();i++)
				{
					System.out.println(i+" : "+TopicTrivia.get(i));
				}	
			}
			
			statement.close();
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
