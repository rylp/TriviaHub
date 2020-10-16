package ui;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
	
	public static String myEmail=null;
	public static String myKey=null;
	public static int myUserId;
	
	public static final String WRONG_EMAIL="INCORRECT-EMAIL";
	public static final String WRONG_PASSWORD="INCORRECT-PASSWORD";
	public static final String EMAIL_EXISTS="EMAIL-ALREADY-EXISTS";
	public static final String EMPTY_TOPIC_KEY="NO-TOPIC-KEY-AVAILABLE-CURRENTLY";
	
	public static final int SCREEN_WIDTH=1000;
	public static final int SCREEN_HEIGHT=800;
	

	private ArrayList<String> firstList = new ArrayList<String>(Arrays.asList("History", "Economics", "Animals",
			"Geography", "Maths", "Chemistry", "Physics", "Biology", "Music", "Sports"));

	public ArrayList<String> getFirstList() {
		return firstList;
	}

}
