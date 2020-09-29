package serverModules;

import java.sql.CallableStatement;
import java.sql.DriverManager;

public class DeleteMyTriviaServerSide {
	
	private String emailId=null;
	private int triviaId;
	
	public DeleteMyTriviaServerSide(JsonDataContract jdc) 
	{
		this.emailId=jdc.getEmail();
		this.triviaId=Integer.parseInt(jdc.getTriviaIdtoDelete());
	}
	
	public boolean removeTrivia()
	{
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);

			CallableStatement statement = con.prepareCall("{call deleteTriviaById(?)}");
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
