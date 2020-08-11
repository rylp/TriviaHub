package users;

import java.util.*;
import users.TopicSelection.*;

public class Trivia {

	public static void main(String[] args) 
	{
		TopicSelection ts=new TopicSelection();
		
		for(int i=0;i<2;i++)
		{
			ts.SelectTopic();
		}

	}
}
