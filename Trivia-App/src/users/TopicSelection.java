package users;

import java.util.*;

public class TopicSelection {
	
	String[] firstList = new String[] {"History", "Economics", "Animals", "Geography","Maths","Chemistry","Physics","Biology","Music","Sports"};
	
	ArrayList<String> myTopics = new ArrayList<String>(Arrays.asList(firstList));
    HashMap<Integer, String> user_topics = new HashMap<>(); 
    
	void SelectTopic()
	{
		
		Scanner userID=new Scanner(System.in);
		System.out.println("Enter your userID");
		int userid=userID.nextInt();
		Scanner userName=new Scanner(System.in);
		System.out.println("Enter your userName");
		String username=userName.next();
		
		System.out.println("UserID : "+userid);
		System.out.println("UserName : "+username);
		
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
		
		user_topics.put(userid,key);
		
		System.out.println("Topics Choosen:");
		for(int i=0;i<3;i++)
		{
			char value=user_topics.get(userid).charAt(i);
			int index=Character.getNumericValue(value);
			System.out.println(i+" "+myTopics.get(index));
		}

	}

}
