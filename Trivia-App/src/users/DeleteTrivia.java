package users;

import java.util.*;
import java.sql.*;

public class DeleteTrivia {
	
	void RemoveTrivia(int triviaId)
	{	
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);

			CallableStatement statement = con.prepareCall("{call deleteTriviaById(?)}");
			statement.setInt(1, triviaId);
			
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
