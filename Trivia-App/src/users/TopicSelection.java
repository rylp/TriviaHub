package users;

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

	String SelectTopic(String userName,int userId)
	{
		System.out.println("UserID : "+userId);
		System.out.println("UserName : "+userName);
		
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
		
		userTopics.put(userId,key);
		
		System.out.println("Topics Choosen:");
		for(int i=0;i<3;i++)
		{
			char value=userTopics.get(userId).charAt(i);
			int index=Character.getNumericValue(value);
			System.out.println(i+" "+myTopics.get(index));
		}
		return key;
	}
	
}
