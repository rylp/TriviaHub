import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

public class LogIn {
	
	private String email;
	private String password;

	public LogIn(JsonDataContract jdc) {
		this.email = jdc.getEmail();
		this.password = jdc.getPassword();
	}

	int loginUser()
	{	
		int userId=0;
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call loginUser(?)}");
			statement.setString(1, this.email);
			
			rs=statement.executeQuery();
			
			String verify_pass="";
			
			if(!rs.next())
			{
				System.out.println("ERROR!");
				System.out.println("Email ID DNE!");
				return -1;
			}
			
			verify_pass=rs.getString("pass");
			userId=rs.getInt("user_id");
			
			if(this.password.equals(verify_pass))
			{
				System.out.println("User Logged In Sucessfully");
			}
			else
			{
				System.out.println("ERROR!");
				System.out.println("Passwords don't match!");
				return -2;
			}

			statement.close();
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return userId;
	}
	
}
