package serverModules;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class CheckIfAlreadyDisliked 
{
	private String emailid=null;
	private int triviaId;
	
	public CheckIfAlreadyDisliked(JsonDataContract jdc) 
	{
		this.emailid=jdc.getEmail();
		this.triviaId=Integer.parseInt(jdc.getTriviaIdtoDislike());
	}
	
	public boolean checkDislike()
	{
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call checkIfValidDislike(?,?)}");
			statement.setInt(1, this.triviaId);
			statement.setString(2, this.emailid);
			
			rs=statement.executeQuery();
			
			if(!rs.next())
			{
				//empty
				return false;
			}
				
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
