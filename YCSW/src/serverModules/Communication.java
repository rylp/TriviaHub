package serverModules;
import java.util.*;

import com.google.gson.Gson;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Communication {
	
	private Socket s;

	public Communication(Socket s) {
		this.setS(s);
		
		Receiver rec=new Receiver(s);
		Constants.threadPool.submit(rec);
//		Thread t1=new Thread(rec,"Receiver Thread");
//		t1.start();
		
		System.out.println("Alive Threads "+Thread.activeCount());
	}
	
	public class Receiver implements Runnable
	{
		private Socket soc=null;
		private DataInputStream in=null;
		Transmitter tr=null;
		
		private void setSocketDetails()
		{
			try {
				in=new DataInputStream(new BufferedInputStream(soc.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public Receiver(Socket s) {
			this.setSoc(s);
			setSocketDetails();
		}
		
		@Override
		public void run() 
		{	
			System.out.println("Receiver Class: ");
			
			try {
				
				while(soc.isConnected())//Socket closed when it comes back here
				{
					int expectedBytes=in.available();
					
					if(expectedBytes>0)
					{
						byte[] buffer=new byte[expectedBytes];//in.read() demands byte buffer.
						
						in.read(buffer);
						
						String clientData=new String(buffer);//String is a object
						
						Gson gson=new Gson();
						
						JsonDataContract jdc=gson.fromJson(clientData, JsonDataContract.class);
						
						switch(jdc.getMessageType().toUpperCase())
						{
						case "REGISTER":
							CheckEmailExistence newUserEmail=new CheckEmailExistence(jdc);
							boolean checkEmail=newUserEmail.checkEmail();
							if(!checkEmail)
							{
								RegisterUser newUser=new RegisterUser(jdc);
								if(newUser.registerUser())//Correct Credentials
								{
									JsonDataContract responseJdc=new JsonDataContract();
									responseJdc.setStatus(Constants.SUCCESS);
									responseJdc.setEmail(jdc.getEmail());
									responseJdc.setMessageType(Constants.REGISTER);
									
									System.out.println("Closed: "+soc.isClosed());
									
									String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
									
									sendData(responseJSON);
								}
								else
								{
									JsonDataContract responseJdc=new JsonDataContract();
									responseJdc.setStatus(Constants.FAILURE);
									responseJdc.setEmail(jdc.getEmail());
									responseJdc.setErrorValue(Constants.ISSUE);
									responseJdc.setMessageType(Constants.REGISTER);
									
									String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
									
									sendData(responseJSON);
								}
							}
							else//EMAIL already Exists
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.EMAIL_EXISTS);
								responseJdc.setMessageType(Constants.REGISTER);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								System.out.println("Closed"+soc.isClosed());
								
								sendData(responseJSON);
							}
							break;
						case "LOGIN":
							LogIn User=new LogIn(jdc);
							int loginStatus=User.loginUser();
							if(loginStatus==-1)//WRONG EMAIL
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.WRONG_EMAIL);
								responseJdc.setMessageType(Constants.LOGIN);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else if(loginStatus==-2)//WRONG PASSWORD
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.WRONG_PASSWORD);
								responseJdc.setMessageType(Constants.LOGIN);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else if(loginStatus>0)//CORRECT CREDENTIALS
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setMessageType(Constants.LOGIN);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								//SET CLIENT DETAILS
								ClientDetails client=Constants.clientQueue.get(Constants.generateClientKey(jdc.getClientIp(), jdc.getClientPort()));
								
								if(client==null)
								{
									System.out.println("Client Not Found!");
									break;
								}
								
								client.setEmail(jdc.getEmail());
								client.setPassword(jdc.getPassword());
								
								
								System.out.println("Response Json sent to Client"+responseJSON);
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setErrorValue(Constants.ISSUE);
								responseJdc.setMessageType(Constants.REGISTER);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "SELECT":
							SelectTopics userTopics=new SelectTopics(jdc);
							boolean success=userTopics.updateTopicsinDB();
							if(success)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.SELECT);
								responseJdc.setTopicsKey(jdc.getTopicsKey());
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.SELECT);
								responseJdc.setErrorValue(Constants.ISSUE);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "ADD":
							AddTriviaServerSide addTrivia=new AddTriviaServerSide(jdc);
							boolean Result=addTrivia.insertTrivia();
							if(Result)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.ADD);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.ADD);
								responseJdc.setErrorValue(Constants.ISSUE);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "KEY":
							GetTopicKeyServerSide curUser=new GetTopicKeyServerSide(jdc);
							String TopicKey=curUser.extractKey();
							
							if(TopicKey.isEmpty())//No key uptil now
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.KEY);
								responseJdc.setErrorValue(Constants.EMPTY_TOPIC_KEY);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.KEY);
								responseJdc.setTopicsKey(TopicKey);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "VIEW":
							ViewTriviaServerSide viewTrivia=new ViewTriviaServerSide(jdc);
							String Content=viewTrivia.displayTrivia();

							if(Content.isEmpty())//Empty
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.VIEW);
								responseJdc.setErrorValue(Constants.EMPTY_TRIVIA);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else //Success
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.VIEW);
								responseJdc.setTriviaData(Content);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "VIEWMY":
							ViewMyTriviaServerSide viewMyTrivia=new ViewMyTriviaServerSide(jdc);
							boolean Res=viewMyTrivia.displayMyTrivia();
							//SUCCESS
							if(Res)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.VIEWMY);
								responseJdc.setTriviaData(viewMyTrivia.getMyTriviaData());
								responseJdc.setTriviaIds(viewMyTrivia.getMyTriviaIds());
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else//No TriviaADded yet
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.VIEWMY);
								responseJdc.setErrorValue(Constants.EMPTY_MY_TRIVIA);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "DELETE":
							DeleteMyTriviaServerSide delTrivia=new DeleteMyTriviaServerSide(jdc);
							boolean result=delTrivia.removeTrivia();
							
							if(result)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.DELETE);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.DELETE);
								responseJdc.setErrorValue(Constants.UNABLE_TO_DELETE);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "VIEWNOTMY":
							ViewNotMyTriviabyTopicServerSide viewNotMyTrivia=new ViewNotMyTriviabyTopicServerSide(jdc);
							boolean ress=viewNotMyTrivia.displayNotMyTrivia();
							if(ress)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.VIEWNOTMY);
								responseJdc.setTriviaData(viewNotMyTrivia.getMyTriviaData());
								responseJdc.setTriviaIds(viewNotMyTrivia.getMyTriviaIds());
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.VIEWNOTMY);
								responseJdc.setErrorValue(Constants.EMPTY_TOPIC_TRIVIA);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						case "LIKE":
							CheckIfAlreadyLiked alreadyLiked=new CheckIfAlreadyLiked(jdc);
							boolean liked=alreadyLiked.checkLike();
							
							if(liked)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.LIKE);
								responseJdc.setErrorValue(Constants.ALREADY_LIKED);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							
							else//Not Previously Liked.
							{
								LikeTriviaServerSide loggedInUser=new LikeTriviaServerSide(jdc);
								boolean answ=loggedInUser.LikeSelectedTrivia();
								if(answ)
								{
									UpdateLikeServerSide curLike=new UpdateLikeServerSide(jdc);
									boolean soln=curLike.updateLike();
									if(soln)
									{
										JsonDataContract responseJdc=new JsonDataContract();
										responseJdc.setEmail(jdc.getEmail());
										responseJdc.setStatus(Constants.SUCCESS);
										responseJdc.setMessageType(Constants.LIKE);
										
										String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
										
										sendData(responseJSON);
									}
									else
									{
										JsonDataContract responseJdc=new JsonDataContract();
										responseJdc.setEmail(jdc.getEmail());
										responseJdc.setStatus(Constants.FAILURE);
										responseJdc.setMessageType(Constants.LIKE);
										responseJdc.setErrorValue(Constants.ISSUE);
										
										String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
										
										sendData(responseJSON);
									}
								}
								else
								{
									JsonDataContract responseJdc=new JsonDataContract();
									responseJdc.setEmail(jdc.getEmail());
									responseJdc.setStatus(Constants.FAILURE);
									responseJdc.setMessageType(Constants.LIKE);
									responseJdc.setErrorValue(Constants.ISSUE);
									
									String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
									
									sendData(responseJSON);
								}
								
							}
							break;
						case "DISLIKE":
							CheckIfAlreadyDisliked alreadyDisliked=new CheckIfAlreadyDisliked(jdc);
							boolean disliked=alreadyDisliked.checkDislike();
							
							if(disliked)
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.DISLIKE);
								responseJdc.setErrorValue(Constants.ALREADY_DISLIKED);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else
							{
								DislikeTriviaServerSide currentUser=new DislikeTriviaServerSide(jdc);
								boolean answe=currentUser.disLikeSelectedTrivia();
								if(answe)
								{
									UpdateDislikeServerSide curDislike=new UpdateDislikeServerSide(jdc);
									boolean soln=curDislike.updateDislike();
									if(soln)
									{
										JsonDataContract responseJdc=new JsonDataContract();
										responseJdc.setEmail(jdc.getEmail());
										responseJdc.setStatus(Constants.SUCCESS);
										responseJdc.setMessageType(Constants.DISLIKE);
										
										String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
										
										sendData(responseJSON);
									}
									else
									{
										JsonDataContract responseJdc=new JsonDataContract();
										responseJdc.setEmail(jdc.getEmail());
										responseJdc.setStatus(Constants.FAILURE);
										responseJdc.setMessageType(Constants.DISLIKE);
										responseJdc.setErrorValue(Constants.ISSUE);
										
										String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
										
										sendData(responseJSON);
									}
								}
								else
								{
									JsonDataContract responseJdc=new JsonDataContract();
									responseJdc.setEmail(jdc.getEmail());
									responseJdc.setStatus(Constants.FAILURE);
									responseJdc.setMessageType(Constants.DISLIKE);
									responseJdc.setErrorValue(Constants.ISSUE);
									
									String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
									
									sendData(responseJSON);
								}	
							}
							break;
						case "FEEDBACK":
							AddFeedbackServerSide add_feedback=new AddFeedbackServerSide(jdc);
							
							boolean Resu=add_feedback.insertFeedback();
							
							if(Resu)//Success
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getFeedbackEmail());
								responseJdc.setStatus(Constants.SUCCESS);
								responseJdc.setMessageType(Constants.FEEDBACK);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							else//Failure
							{
								JsonDataContract responseJdc=new JsonDataContract();
								responseJdc.setEmail(jdc.getEmail());
								responseJdc.setStatus(Constants.FAILURE);
								responseJdc.setMessageType(Constants.FEEDBACK);
								responseJdc.setErrorValue(Constants.ISSUE);
								
								String responseJSON=gson.toJson(responseJdc,JsonDataContract.class);
								
								sendData(responseJSON);
							}
							break;
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sendData(String data)
		{
			String clientIpAddr=s.getInetAddress().getHostAddress();
			String clientPort= String.valueOf(s.getPort());
			
			System.out.println("Transmitting to Client");
			
			tr=new Transmitter();
			tr.sendDataToClient(data,Constants.generateClientKey(clientIpAddr, clientPort));
		}
		

		public Socket getSoc() {
			return soc;
		}
		
		public void setSoc(Socket soc) {
			this.soc = soc;
		}
	}
	
	public class Transmitter
	{
		public void sendDataToClient(String data,String clientKey)
		{
			if(Constants.clientQueue.containsKey(clientKey))
			{
				ClientDetails clientDetails=Constants.clientQueue.get(clientKey);
				
				Socket soc=clientDetails.getSoc();
				
				OutputStreamWriter os = null;
				try {
					os = new OutputStreamWriter(soc.getOutputStream());
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				PrintWriter out=new PrintWriter(os);
				out.println(data);
				out.flush();
				
				System.out.println("Sent data to client");
			}
		}
	}
	
	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
}
