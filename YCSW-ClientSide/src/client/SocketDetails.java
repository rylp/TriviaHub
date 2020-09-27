package client;

import java.io.DataInputStream;
import java.io.OutputStreamWriter;

public class SocketDetails {
	
	private DataInputStream in=null;
	private OutputStreamWriter out=null;
	
	public DataInputStream getIn() {
		return in;
	}
	public void setIn(DataInputStream in) {
		this.in = in;
	}
	public OutputStreamWriter getOut() {
		return out;
	}
	public void setOut(OutputStreamWriter out) {
		this.out = out;
	}
}
