package serverModules;

import java.sql.*;

public class AddTriviaServerSide {
	
	private String emailId=null;
	private String triviaContent=null;
	private int topic;
	private int likes;
	
	public AddTriviaServerSide(JsonDataContract jdc) {
		this.emailId=jdc.getEmail();
		this.triviaContent=jdc.getTriviaContent();
		this.topic=Integer.parseInt(jdc.getTopic());
		this.likes=Integer.parseInt(jdc.getLikes());
	}
	
	public boolean insertTrivia()
	{
		boolean Success=false;
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call addTrivia(?,?,?,?)}");
			statement.setString(1, this.emailId);
			statement.setString(2, this.triviaContent);
			statement.setInt(3, this.topic);
			statement.setInt(4, this.likes);
			
			statement.executeUpdate();
			
			statement.close();
			con.close();
			
			Success=true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Success;
	}

}
