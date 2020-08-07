package users;

import java.util.Scanner;

public class userDetails 
{
	public static void main(String[] args) 
	{
		System.out.println("Sign Up");
		
		String userID;
		String userName;
		String password;
		String verify_password;
		
		Scanner user_name=new Scanner(System.in);
		System.out.println("Enter your name");
		userName=user_name.nextLine();
		
		Scanner user_id=new Scanner(System.in);
		System.out.println("Enter your User Id");
		userID=user_id.nextLine();
		
		//Check if taken
		
		Scanner password1=new Scanner(System.in);
		System.out.println("Enter your password");
		password=password1.nextLine();
		
		Scanner password2=new Scanner(System.in);
		System.out.println("Verify password");
		verify_password=password2.nextLine();
		
		if(password.equals(verify_password))
		{
			System.out.println("User Registered Sucessfully");
		}
		else
		{
			System.out.println("Passwords don't match!");
		}
		
		//Assign PRN
	}

}
