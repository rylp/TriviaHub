package users;

import java.sql.*;
import java.util.*;

import client.Client;
import users.CheckIfAlreadyLiked.*;
import users.UpdateLike.*;
import users.LogIn.*;
import users.TopicSelection.*;
import users.AddTrivia.*;
import users.RegisterUser.*;
import users.ViewTrivia.*;
import users.ChooseTriviaforDeletion.*;
import users.DeleteTrivia.*;
import users.ChooseTriviaToLike.*;
import users.LikeTrivia.*;

public class Trivia 
{	
	public static void main(String[] args) 
	{
		Client.communicationEndpoint();
		triviaStarter();
	}

	public static void triviaStarter() {
		
		System.out.println("---------------------------");
		System.out.println("");
		System.out.println("---WELCOME TO Trivia-Hub---");
		System.out.println("");
		System.out.println("---------------------------");
		
		System.out.print("\n\tChoose your option");
		System.out.print("\n\t1.Log In");
		System.out.print("\n\t2.Register");
		System.out.println();
		Scanner sc=new Scanner(System.in);
		int ch=sc.nextInt();
		
		MySession sess=new MySession();
		
		if(ch==1) //Log In
		{
          LogIn curUser=new LogIn();
			
          boolean Status=curUser.loginUser();
          
          if(!Status)
          {
        	  System.out.println("");
        	  System.out.println("----Exiting!!!---");
        	  System.out.println("");
        	  return;
          }
          else //set TopicKey in Constants if login is Successful
          {
        	  GetTopicKey loggedinUser=new GetTopicKey();
        	  boolean Result=loggedinUser.extractKey();
        	  
        	  if(Result)
        	  {
        		  //Create cons object
        		  Constants cons=new Constants();
        		  
        		  System.out.println("Successfull Retrived Topic Key");
        		  System.out.println("Your Topics: ");
        		  
        		  for(int i=1;i<=3;i++)
        		  {
        			  try
        			  {
        				  System.out.println(i+": "+cons.getFirstList().get(Integer.parseInt(String.valueOf(Constants.myKey.charAt(i-1)))));
        			  }
        			  catch(Exception e)
        			  {
        				  e.printStackTrace();
        			  }
        		  }
        	  }
        	  else//Cud not retrieve topics
        	  {
        		  System.out.println("Could not retrieve ur topics");
        	  }
          }
		}
		
		else if(ch==2) //Register
		{
			//TODO:hERE DELETE USER currently created if topic selection fails 10 times.
			
			RegisterUser newUser = new RegisterUser();
			
			boolean Status=newUser.registerUser();
			
			if(!Status)
			{
				System.out.println("");
		      	System.out.println("----Exiting!!!---");
		      	System.out.println("");
		      	return;
			}
			
			else //Go into Topic Selection if Register is Successful
			{
				boolean result=false;
				int limit=0;
				
				do
				{
					TopicSelection chooseTopics=new TopicSelection();
					
					result=chooseTopics.SelectTopic();
					
					limit++;
					
				}while(!result && limit!=10);
				
				//TODO:hERE DELETE USER currently created if topic selection fails 10 times.
				if(limit==10)
				{
					System.out.println("Registration Failed");

					System.out.println("");
			      	System.out.println("----Exiting!!!---");
			      	System.out.println("");
			      	return;
				}
			}
		}
		
		else
		{
			System.out.println("Invalid Choice!!!");
			System.out.println("Exiting");
			return;
		}
		
		int opt;
		
		do
		{
			System.out.println("Email: "+users.Constants.myEmail);
			System.out.println("Topic Key: "+users.Constants.myKey);
			
			System.out.println("-----------------------");
			System.out.println("");
			System.out.println("\n\tEnter ur choice");
			System.out.print("\n\t1.Add Trivia");
			System.out.print("\n\t2.View Trivia");
			System.out.print("\n\t3.Delete Trivia");
			System.out.print("\n\t4.Like Trivia");
//			System.out.println("\n\t5.Unlike Trivia");
			System.out.print("\n\t5.Add Topic of Choice");
			System.out.print("\n\t6.Log Out");
			System.out.println("");
			System.out.println("-------------------------");
			Scanner option=new Scanner(System.in);
			opt=option.nextInt();
			
			switch(opt)
			{
				case 1:
				{
					//TODO: Have to verify whether topic choosen is in TopicsKey.
					//TODO: Fixing boolean Success for multiple hits.
					
					AddTrivia newTrivia=new AddTrivia();
					boolean Success=newTrivia.InsertTrivia();
					if(!Success)
					{
						System.out.println("Unsuccessful Insertion of Trivia");
					}
					else
					{
						System.out.println("Successfully Inserted Trivia");
					}
					break;
				}
				case 2:
				{
					//TODO: vIEW trivia based on likes
					//TODO: Provide like option while calling trivia
					
					ViewTrivia viewTrivia=new ViewTrivia();
					boolean Success=viewTrivia.DisplayTrivia();
					if(!Success)
					{
						System.out.println("Unsuccessful Viewing of Trivia");
					}
					else
					{
						System.out.println("Successfully Viewed Trivia");
					}
					break;
				}
				case 3:
				{
					ChooseTriviaforDeletion curUser=new ChooseTriviaforDeletion();
					int triviaId=curUser.showTrivia();
					
					if(triviaId==-2)//Empty
					{
						System.out.println("No Trivia Added Yet!");
					}
					else if(triviaId==-1)
					{
						System.out.println("No Trivia Deleted!");
					}
					else
					{
						//TODO: User enters trivia id which is not his. Need to be verified.
						//TODO: check some corner cases.
						DeleteTrivia delTrivia=new DeleteTrivia(triviaId);
						boolean ans=delTrivia.RemoveTrivia();
						if(ans)
						{
							System.out.println("Successfully deleted");
						}
						else
						{
							System.out.println("Unsuccessful Deletion");
						}
					}
					break;
				}
				case 4:
				{
					//TODO: Incorrect Number choosen to Like
					//TODO: Multiple time choosen same trivia to like.
					//TODO: vIEW trivia based on likes
					//TODO: Dont display already liked trivia or msg from database that already liked.
					
					ChooseTriviaToLike currentUser=new ChooseTriviaToLike();
					
					int triviaId=currentUser.viewTrivia();
					
					if(triviaId==-1)
					{
						System.out.println("No Like Updated");
					}
					else if(triviaId==-2)
					{
						System.out.println("No Trivia currently for ur topic");
					}
					else if(triviaId==-3)
					{
						System.out.println("Invalid topic selected");
					}
					else
					{
						LikeTrivia likeTrivia=new LikeTrivia(triviaId);
						
						boolean answer=likeTrivia.LikeSelectedTrivia();
						
						if(answer)
						{
							System.out.println("Successfully liked");
						}
						else
						{
							System.out.println("Could not like");
						}	
					}
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
