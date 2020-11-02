package serverModules;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFeedbackServerSide 
{
	private String email=null;
	private String feedbackContent=null;
	private String username=null;
	private String feedbackDate=null;
	
	public AddFeedbackServerSide(JsonDataContract jdc) 
	{
		this.email=jdc.getFeedbackEmail();
		this.feedbackContent=jdc.getFeedbackContent();
		this.username=jdc.getFeedbackName();
		
        //Setting Date
        Date date=new Date();
        SimpleDateFormat curDate=new SimpleDateFormat("dd/MM/yyyy");
        this.feedbackDate=curDate.format(date);
	}
	
	public boolean insertFeedback()
	{
		boolean Success=false;
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			CallableStatement statement = con.prepareCall("{call addFeedback(?,?,?,?)}");
			statement.setString(1, this.email);
			statement.setString(2, this.username);
			statement.setString(3, this.feedbackContent);
			statement.setString(4, this.feedbackDate);
			
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
