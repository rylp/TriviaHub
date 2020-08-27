package users;

import java.util.*;
import java.sql.*;

public class ChooseTriviaforDeletion {
	
	int GetTriviaId(String userKey,int userId)
	{
		HashMap<Integer, String> myTrivia=new HashMap<Integer,String>();

		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call showTriviaByUser(?)}");
			statement.setInt(1, userId);
			
			rs=statement.executeQuery();
			
			while(rs.next())
			{
				myTrivia.put(rs.getInt("trivia_id"),rs.getString("trivia_content"));
			}
			
			if(myTrivia.size()==0)
			{
				System.out.println("No Trivia Added Yet!!!");
				return -1;
			}
			
			else
			{
				System.out.println("----Displaying Your Trivia-----");	
		        for (HashMap.Entry<Integer,String> entry : myTrivia.entrySet())
		        {
		            System.out.println(entry.getKey() + " : " + entry.getValue());
		        }
			}
			
			statement.close();
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		System.out.println("Choose the triviaId of the trivia to delete");
		Scanner ch=new Scanner(System.in);
		int choice=ch.nextInt();
		
		return choice;
	}

}
