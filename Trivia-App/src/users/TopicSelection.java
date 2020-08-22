package users;

import java.sql.*;
import java.util.*;

public class TopicSelection 
{	
	Constants constant=new Constants();
	
	ArrayList<String> myTopics=null;
	HashMap<Integer, String> userTopics=null;
	
	public TopicSelection() 
	{
		myTopics = constant.getFirstList();
		userTopics=new HashMap<Integer,String>(); 
	}

	String SelectTopic(int userID)
	{	
		System.out.println("Choose 3 topics of your interests");
		
		String key="";
		
		for(int i=0;i<3;i++)
		{
			System.out.println("List of Topics => ");
			for(int j=0;j<myTopics.size();j++)
			{
				System.out.println(j+" "+ myTopics.get(j));
			}
			
				Scanner t=new Scanner(System.in);
				System.out.println("Enter Topic index");
				int topic=t.nextInt();
				key+=Integer.toString(topic);
				System.out.println("Topic choosen => "+myTopics.get(topic));
		}
		
		for(int i=0;i<myTopics.size();i++)
		{
			System.out.println(i+" "+ myTopics.get(i));
		}
		
		System.out.println("Key: "+key);
		
		userTopics.put(userID,key);
		
		System.out.println("Topics Choosen:");
		for(int i=0;i<3;i++)
		{
			char value=userTopics.get(userID).charAt(i);
			int index=Character.getNumericValue(value);
			System.out.println(i+" "+myTopics.get(index));
		}
		
		//add key into database userdetails
		
		try
		{
			String url="jdbc:mysql://localhost:3306/trivia-db";
			String uname="root";
			String pass="temp123";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pass);

			CallableStatement statement = con.prepareCall("{call addTopicKey(?,?)}");
			statement.setString(1, key);
			statement.setInt(2, userID);
			
			statement.executeUpdate();
			
			statement.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return key;
	}
	
}
