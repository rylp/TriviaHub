import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class SelectTopics {
	
	private String topicKey=null;
	private String email=null;

	public SelectTopics(JsonDataContract jdc) {
		this.topicKey=jdc.getTopicsKey();
		this.email=jdc.getEmail();
	}
	
	public boolean updateTopicsinDB()
	{
		boolean Success=false;
		
		//Directly adding topicKey as Email will be a verfied existing email.
		//Procure Client Email from backend
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call addTopicKey(?,?)}");
			statement.setString(1, this.topicKey);
			statement.setString(2, this.email);

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
