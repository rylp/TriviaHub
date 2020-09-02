package users;

import java.util.*;
import java.sql.*;

public class ChooseTriviaToLike {
	
	int GetTriviaId(String userKey,int userId)
	{
		ArrayList<String>TopicTrivia=new ArrayList<String>();
		ArrayList<Integer>TriviaIds=new ArrayList<Integer>();
		
		Constants constant=new Constants();
		
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
			CallableStatement statement = con.prepareCall("{call displayTriviaByTopic(?,?)}");
			statement.setInt(1, topic_key);
			statement.setInt(2, userId);
			
			rs=statement.executeQuery();
			
			while(rs.next())
			{
				TopicTrivia.add(rs.getString("trivia_content"));
				TriviaIds.add(rs.getInt("trivia_id"));
			}
			
			if(TopicTrivia.size()==0)
			{
				System.out.println("No Trivia Added Yet!!!");
				return -1;
			}
			
			else
			{
				System.out.println("----Displaying Trivia-----");
				for(int i=0;i<TopicTrivia.size();i++)
				{
					System.out.println(TriviaIds.get(i)+" : "+TopicTrivia.get(i));
				}	
			}
			
			statement.close();
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Choose Trivia Id for Trivia to like");
		Scanner sc=new Scanner(System.in);
		int triviaKey=sc.nextInt();
		
		if(!TriviaIds.contains(triviaKey))
		{
			System.out.println("Invalid Trivia Key");
			return -1;
		}
		
		return triviaKey;	
	}

}
