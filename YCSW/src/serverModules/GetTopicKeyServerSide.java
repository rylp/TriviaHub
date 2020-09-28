package serverModules;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class GetTopicKeyServerSide {
	
	private String emailId=null;
	
	public GetTopicKeyServerSide(JsonDataContract jdc) 
	{
		this.emailId=jdc.getEmail();
	}
	
	public String extractKey()
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
			CallableStatement statement = con.prepareCall("{call getTopicKey(?)}");
			statement.setString(1, this.emailId);
			
			rs=statement.executeQuery();
			
			if(!rs.next())
			{
				//empty
				return Content;
			}
			
			do
			{
				String topicExists=rs.getString("topic_key");
				if(topicExists==null)
				{
					return Content;
				}
				Content=Content.concat(topicExists);
				
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
