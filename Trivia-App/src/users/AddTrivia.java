package users;

import java.util.*;
import users.TopicSelection.*;

public class AddTrivia 
{		
	void InsertTrivia(String user_key)
	{
		Constants constant=new Constants();
		
		System.out.println("For which topic do u want to add trivia??");
		
		System.out.println("Topics Choosen:");
		
		for(int i=0;i<3;i++)
		{
			try
			{
				System.out.println(i+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(user_key.charAt(i)))));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("Choose a topic");
		Scanner ch=new Scanner(System.in);
		int choice=ch.nextInt();
		
		System.out.println("Enter trivia");
		Scanner tr=new Scanner(System.in);
		String trivia=tr.nextLine();
		
		//add into database
	}

}
