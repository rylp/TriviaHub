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
			//TODO: fIX ISSUE of choosing same topic like key-> 333 or 223
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
				
				do
				{
					TopicSelection chooseTopics=new TopicSelection();
					
					result=chooseTopics.SelectTopic();
					
//					if(!status)
//					{
//						System.out.println("");
//				      	System.out.println("----Exiting!!!---");
//				      	System.out.println("");
//				      	return;
//					}
					
				}while(!result);
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
					ChooseTriviaToLike cttl=new ChooseTriviaToLike();
					int triviaId=cttl.GetTriviaId(sess.getMykey(),sess.getMyid());
					
					if(triviaId!=-1)
					{
						CheckIfAlreadyLiked cial=new CheckIfAlreadyLiked();
						int check=cial.verifyLike(sess.getMyid(),triviaId);
						
						if(check==1)//can add like
						{
							LikeTrivia lt=new LikeTrivia();
							lt.LikeSelectedTrivia(triviaId);
							
							UpdateLike ul=new UpdateLike();
							ul.updateLikedTrivia(sess.getMyid(),triviaId);
						}
						else
						{
							System.out.println("Trivia Already Liked!");
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
