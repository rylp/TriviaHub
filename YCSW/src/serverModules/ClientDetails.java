package serverModules;
import java.net.Socket;

public class ClientDetails {
	
	private Socket soc=null;
	private String email=null;
	private String password=null;
	public Socket getSoc() {
		return soc;
	}
	public void setSoc(Socket soc) {
		this.soc = soc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
