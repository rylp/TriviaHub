package users;

import java.sql.*;
import java.util.*;

public class UpdateLike {
	
	void updateLikedTrivia(int userId,int triviaId)
	{
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call addLikeInDB(?,?,?)}");
			statement.setInt(1, triviaId);
			statement.setInt(2, userId);
			statement.setString(3, "Yes");
			
			statement.executeUpdate();
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
