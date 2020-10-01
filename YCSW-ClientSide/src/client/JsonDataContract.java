package client;

public class JsonDataContract {
	
	String MessageType="";
	String FirstName="";
	String LastName="";
	String Age="";
	String Password="";
	String Email="";
	String ErrorValue="";
	String ClientIp="";
	String TopicsKey="";
	String TriviaContent="";
	String Topic="";
	String Status="";
	String clientPort="";
	String likes="";
	String TriviaData="";
	String TriviaIds="";
	String TriviaIdtoDelete="";
	String TriviaIdtoLike="";
	
	public String getTriviaIdtoLike() {
		return TriviaIdtoLike;
	}
	public void setTriviaIdtoLike(String triviaIdtoLike) {
		TriviaIdtoLike = triviaIdtoLike;
	}
	
	public String getTriviaIdtoDelete() {
		return TriviaIdtoDelete;
	}
	public void setTriviaIdtoDelete(String triviaIdtoDelete) {
		TriviaIdtoDelete = triviaIdtoDelete;
	}
	
	public String getTriviaIds() {
		return TriviaIds;
	}
	public void setTriviaIds(String triviaIds) {
		TriviaIds = triviaIds;
	}
	
	public String getTriviaData() {
		return TriviaData;
	}
	public void setTriviaData(String triviaData) {
		TriviaData = triviaData;
	}
	
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	
	public String getClientPort() {
		return clientPort;
	}
	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getTopic() {
		return Topic;
	}
	public void setTopic(String topic) {
		Topic = topic;
	}
	public String getTriviaContent() {
		return TriviaContent;
	}
	public void setTriviaContent(String triviaContent) {
		TriviaContent = triviaContent;
	}
	
	public String getTopicsKey() {
		return TopicsKey;
	}
	public void setTopicsKey(String topicsKey) {
		TopicsKey = topicsKey;
	}
	public String getClientIp() {
		return ClientIp;
	}
	public void setClientIp(String clientIp) {
		ClientIp = clientIp;
	}
	public String getErrorValue() {
		return ErrorValue;
	}
	public void setErrorValue(String errorValue) {
		ErrorValue = errorValue;
	}
	public String getMessageType() {
		return MessageType;
	}
	public void setMessageType(String messageType) {
		MessageType = messageType;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
}
