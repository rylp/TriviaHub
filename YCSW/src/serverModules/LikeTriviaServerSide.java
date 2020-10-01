package serverModules;

import java.sql.*;

public class LikeTriviaServerSide {
	
	private String emailid=null;
	private int triviaId;
	
	public LikeTriviaServerSide(JsonDataContract jdc) 
	{
		this.emailid=jdc.getEmail();
		this.triviaId=Integer.parseInt(jdc.getTriviaIdtoLike());
	}
	
	public boolean LikeSelectedTrivia()
	{
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);

			CallableStatement statement = con.prepareCall("{call addLikeByTriviaId(?)}");
			statement.setInt(1, this.triviaId);
			
			statement.executeUpdate();
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}

}
