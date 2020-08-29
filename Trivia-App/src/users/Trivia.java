package users;
import java.sql.*;


import java.util.*;

import users.LogIn;
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
			
          int userId=curUser.loginUser();
          if(userId==-1)
          {
        	  System.out.println("");
        	  System.out.println("----Exiting!!!---");
        	  System.out.println("");
        	  return;
          }
          String topicKey=curUser.showDetails();
          
          sess.setMyid(userId);
          sess.setMykey(topicKey);
          
          Constants constant=new Constants();
          
          System.out.println("Topics Choosen:");
          
          for(int i=0;i<3;i++)
          {
        	  try
        	  {
        		  System.out.println(i+" "+constant.getFirstList().get(Integer.parseInt(String.valueOf(sess.getMykey().charAt(i)))));
        	  }
        	  catch(Exception e)
        	  {
        		  e.printStackTrace();
        	  }
          }
		}
		else //Register
		{	
			RegisterUser newUser = new RegisterUser(); 
			
			newUser.registerUser();
			newUser.showDetails();
			
			try
			{
				String url="jdbc:mysql://localhost:3306/trivia-db";
				String uname="root";
				String pass="temp123";
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(url,uname,pass);
				
				ResultSet rs;
				CallableStatement statement = con.prepareCall("{call getUserId()}");
				
				rs=statement.executeQuery();
				
				rs.next();
				int userId=rs.getInt(1);
				
				System.out.println("User Id: "+userId);
				
				sess.setMyid(userId);
				
				statement.close();
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//get topics of choice
			System.out.println("Select Topics of your choice");
			
			TopicSelection ts=new TopicSelection();	
			String userKey=ts.SelectTopic(sess.getMyid());
			sess.setMykey(userKey);	
		}
		
		int opt;
		
		do
		{
			System.out.println("-----------------------");
			System.out.println("");
			System.out.println("\n\tEnter ur choice");
			System.out.print("\n\t1.Add Trivia");
			System.out.print("\n\t2.View Trivia");
			System.out.print("\n\t3.Delete Trivia");
			System.out.print("\n\t4.Like Trivia");
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
					AddTrivia at=new AddTrivia();
					at.InsertTrivia(sess.getMykey(),sess.getMyid());
					break;
				}
				case 2:
				{
					ViewTrivia vt=new ViewTrivia();
					vt.DisplayTrivia(sess.getMykey(),sess.getMyid());
					
					break;
				}
				case 3:
				{
					ChooseTriviaforDeletion ctfd=new ChooseTriviaforDeletion();
					int triviaId=ctfd.GetTriviaId(sess.getMykey(),sess.getMyid());
					
					if(triviaId!=-1)
					{	
						DeleteTrivia dt=new DeleteTrivia();
						dt.RemoveTrivia(triviaId);
					}

					break;
				}
				case 4:
				{
					ChooseTriviaToLike cttl=new ChooseTriviaToLike();
					int triviaId=cttl.GetTriviaId(sess.getMykey(),sess.getMyid());
					
					if(triviaId!=-1)
					{
						LikeTrivia lt=new LikeTrivia();
						lt.LikeSelectedTrivia(triviaId);
						
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
