package serverModules;

import java.sql.CallableStatement;
import java.sql.DriverManager;

public class DislikeTriviaServerSide {
	
	private String emailid=null;
	private int triviaId;
	
	public DislikeTriviaServerSide(JsonDataContract jdc) 
	{
		this.emailid=jdc.getEmail();
		this.triviaId=Integer.parseInt(jdc.getTriviaIdtoDislike());
	}

	public boolean disLikeSelectedTrivia() 
	{
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);

			CallableStatement statement = con.prepareCall("{call addDislikeByTriviaId(?)}");
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
