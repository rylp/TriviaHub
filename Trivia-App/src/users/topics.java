package users;
import java.util.*;

public class topics {

	public static void main(String[] args) 
	{	
		ArrayList<String> myTopics = new ArrayList<String>(10);
        	HashMap<Integer, String> user_topics = new HashMap<>(); 
        
		String[] firstList = new String[] {"History", "Economics", "Animals", "Geography","Maths"};
		myTopics.addAll(Arrays.asList(firstList));
		
		Scanner userID=new Scanner(System.in);
		System.out.println("Enter your userID");
		int userid=userID.nextInt();
		Scanner userName=new Scanner(System.in);
		System.out.println("Enter your userName");
		String username=userName.next();
		
		System.out.println("UserID : "+userid);
		System.out.println("UserName : "+username);
		
		System.out.println("Choose 3 topics of your interests");
		System.out.println("List of Topics => ");
		
		String key="";
		
		for(int i=0;i<myTopics.size();i++)
		{
			System.out.println(i+" "+ myTopics.get(i));
		}
		
		for(int i=0;i<3;i++)
		{
			Scanner ch=new Scanner(System.in);
			System.out.println("1.Select topic or 2.Add new topic");
			int choice=ch.nextInt();
			
			if(choice==1)
			{
				Scanner t=new Scanner(System.in);
				System.out.println("Enter Topic index");
				int topic=t.nextInt();
				key+=Integer.toString(topic);
				System.out.println("Topic choosen => "+myTopics.get(topic));
			}
			else
			{
				Scanner new_topic=new Scanner(System.in);
				System.out.println("Enter new topic");
				String nt=new_topic.nextLine();
				myTopics.add(nt);
				key+=Integer.toString(myTopics.size()-1);
				System.out.println("Topic choosen => "+myTopics.get(myTopics.size()-1));
			}
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
