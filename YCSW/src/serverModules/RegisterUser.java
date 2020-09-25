package serverModules;
import java.sql.*;

public class RegisterUser 
{	
	private String fname;
	private String lname;
	private int age;
	private String password;
	private String email;
	
	public RegisterUser(JsonDataContract jdc) {
		this.fname = jdc.getFirstName();
		this.lname = jdc.getLastName();
		this.age = Integer.parseInt(jdc.getAge());
		this.password = jdc.getPassword();
		this.email = jdc.getEmail();
	}

	boolean registerUser()
	{
		boolean Success=false;
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call registerNewUser(?,?,?,?,?)}");
			statement.setString(1, this.fname);
			statement.setString(2, this.lname);
			statement.setString(3, this.email);
			statement.setInt(4, this.age);
			statement.setString(5, this.password);
			
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
	
	void showDetails()
	{
		System.out.println("Welcome "+this.fname+" "+this.lname);
	}

}
