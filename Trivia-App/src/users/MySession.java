package users;

public class MySession {
	
	private String mykey;
	private int myid;
	
	public static String myEmail=null;
	
	public static String getMyEmail() {
		return myEmail;
	}

	public static void setMyEmail(String myEmail) {
		MySession.myEmail = myEmail;
	}

	public int getMyid() {
		return myid;
	}

	public void setMyid(int myid) {
		this.myid = myid;
	}

	public String getMykey() {
		return mykey;
	}

	public void setMykey(String mykey) {
		this.mykey = mykey;
	}
}
