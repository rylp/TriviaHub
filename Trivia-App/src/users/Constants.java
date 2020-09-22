package users;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
	
	public static String myEmail=null;
	public static String myKey=null;

	private ArrayList<String> firstList = new ArrayList<String>(Arrays.asList("History", "Economics", "Animals",
			"Geography", "Maths", "Chemistry", "Physics", "Biology", "Music", "Sports"));

	public ArrayList<String> getFirstList() {
		return firstList;
	}
	
}
