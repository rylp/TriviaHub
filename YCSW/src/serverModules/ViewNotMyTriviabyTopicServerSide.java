package serverModules;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ViewNotMyTriviabyTopicServerSide {
	
	private String emailid=null;
	private int topicSelected;
	private String myTriviaData;
	private String myTriviaIds;
	
	public ViewNotMyTriviabyTopicServerSide(JsonDataContract jdc) 
	{
		this.emailid=jdc.getEmail();
		this.topicSelected=Integer.parseInt(jdc.getTopic());
	}
	
	public boolean displayNotMyTrivia()
	{
		String myTempTriviaData="";
		String myTempTriviaIds="";
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			java.sql.Connection con=DriverManager.getConnection(url,uname,pass);
			
			ResultSet rs;
			CallableStatement statement = con.prepareCall("{call displayTriviaByTopic(?,?)}");
			statement.setString(1, this.emailid);
			statement.setInt(2, this.topicSelected);
			
			rs=statement.executeQuery();
			
			if(!rs.next())
			{
				System.out.println("No Trivia Added Yet by User");
				return false;
			}
			
			do
			{
				myTempTriviaData=myTempTriviaData.concat(rs.getString("trivia_content"));
				myTempTriviaIds=myTempTriviaIds.concat(String.valueOf(rs.getInt("trivia_id")));
				
				myTempTriviaData=myTempTriviaData.concat("#");
				myTempTriviaIds=myTempTriviaIds.concat("#");
				
			}while(rs.next());
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.myTriviaData=myTempTriviaData;
		this.myTriviaIds=myTempTriviaIds;
		
		System.out.println("Trivia Content=> "+this.myTriviaData);
		System.out.println("Trivia iDS=> "+this.myTriviaIds);

		return true;
	}

	public String getMyTriviaData() {
		return myTriviaData;
	}

	public void setMyTriviaData(String myTriviaData) {
		this.myTriviaData = myTriviaData;
	}

	public String getMyTriviaIds() {
		return myTriviaIds;
	}

	public void setMyTriviaIds(String myTriviaIds) {
		this.myTriviaIds = myTriviaIds;
	}

}
