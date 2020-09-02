package users;

import java.util.*;
import java.sql.*;

public class CheckIfAlreadyLiked {
	
	int verifyLike(int userId,int triviaId)
	{
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call checkIfValidLike(?,?)}");
			statement.setInt(1, triviaId);
			statement.setInt(2, userId);
			
			rs=statement.executeQuery();
			
			if(!rs.next())
			{
				//No match for like
				System.out.println("Liking Trivia...");
				return 1;
			}
			
			statement.close();
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Already Liked!!!");
		return -1;
	}
}
