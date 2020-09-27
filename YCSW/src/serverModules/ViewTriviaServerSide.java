package serverModules;

import java.sql.*;

public class ViewTriviaServerSide {
	
	private String emailId=null;
	private int topic;

	public ViewTriviaServerSide(JsonDataContract jdc) 
	{
		this.emailId=jdc.getEmail();
		this.topic=Integer.parseInt(jdc.getTopic());
	}
	
	public String displayTrivia()
	{
		String Content="";
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call displayTrivia(?)}");
			statement.setInt(1, this.topic);
			
			rs=statement.executeQuery();
			
			//If No current Trivia in particular topic
			if(!rs.next())
			{
				//Returning empty String.
				return Content;
			}
			
			//If Trivia exists
			do
			{
				Content=Content.concat(rs.getString("trivia_content"));
				Content=Content.concat("#");
				
			}while(rs.next());
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Content;
	}
}
