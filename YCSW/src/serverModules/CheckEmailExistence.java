package serverModules;
import java.sql.*;

public class CheckEmailExistence {
	
	private String clientEmail=null;

	public CheckEmailExistence(JsonDataContract jdc) {
		this.clientEmail=jdc.getEmail();
	}
	
	public boolean checkEmail()
	{
		boolean Exists=false;
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call checkIfEmailExists(?)}");
			statement.setString(1, this.clientEmail);
			
			rs=statement.executeQuery();
			
			if(rs.next())//Returns a userId -> Email Exists.
			{
				System.out.println("Email Already Exists");
				Exists=true;
			}
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return Exists;		
	}
}
