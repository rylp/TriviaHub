package users;

import java.util.*;


import users.LogIn;
import users.TopicSelection.*;
import users.AddTrivia.*;
import users.RegisterUser.*;

public class Trivia 
{	
	public static void main(String[] args) 
	{
		System.out.println("---WELCOME TO Trivia-Hub---");
		System.out.print("Choose your option");
		System.out.print("\n\t1.Log In");
		System.out.print("\n\t2.Register");
		Scanner sc=new Scanner(System.in);
		int ch=sc.nextInt();
		
		MySession sess=new MySession();
		
		if(ch==1) //Log In
		{
          LogIn cur_user=new LogIn();
			
          cur_user.login_user();
          cur_user.show_details();
          
          //set the userKey for now statically
          String userKey="123";
          
          sess.setMykey(userKey);
			
          //display topics of choice
		}
		else //Register
		{	
			RegisterUser newUser = new RegisterUser(); 
			
			newUser.register_user();
			newUser.show_details();
			
			//get topics of choice
			System.out.println("Select Topics of your choice");
			
			TopicSelection ts=new TopicSelection();	
			String userKey=ts.SelectTopic(newUser.userName,newUser.userId);
			sess.setMykey(userKey);
			
		}
		
		int opt;
		
		do
		{
			System.out.println("Enter ur choice");
			System.out.print("\n\t1.Add Trivia");
			System.out.print("\n\t2.View Trivia");
			System.out.print("\n\t3.Delete Trivia");
			System.out.print("\n\t4.Add Topic of Choice");
			System.out.print("\n\t5.Like Trivia");
			System.out.print("\n\t6.Log Out");
			Scanner option=new Scanner(System.in);
			opt=option.nextInt();
			
			switch(opt)
			{
				case 1:
				{
					String userKey=sess.getMykey();
					AddTrivia at=new AddTrivia();
					at.InsertTrivia(userKey);
					break;
				}
				case 2:
				{
					
					
					break;
				}
				case 3:
				{
					break;
				}
				case 4:
				{
					break;
				
				}
				case 5:
				{
					break;
				
				}
				case 6:
				{
					System.out.println("Logged Out!");
					break;
				}
				default:System.out.println("Invalid Choice");
			}
			
		}while(opt!=6);

	}
}
