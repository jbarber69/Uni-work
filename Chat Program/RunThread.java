import java.net.*;
import java.net.ServerSocket;
import java.io.*;
import java.util.*;

public class RunThread extends Thread{
	
	private Socket clientSocket;
	private InputStreamReader isr;
	private BufferedReader clientIn;
	private PrintWriter out;
	private Vector<RunThread> clientList = new Vector<RunThread>();
	private Socket receiverSocket;
	private RunThread receiver;
	private ChatServer cServ;
	private String username;
	private boolean loggedIn;
	
	public RunThread(Socket sock, ChatServer server, String user){
		clientSocket = sock;
		cServ = server;
		username = user;
		loggedIn = true;
	}
	
	@Override
	public void run(){
		while(true){
			clientList = cServ.clientList;
			try{
				//initialise isr and br	
				InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
				BufferedReader clientIn = new BufferedReader(isr);
				//get user message
				String userMessage = clientIn.readLine();

				//send message to all other clients
				for(int j = 0; j < clientList.size(); j++){
					receiver = clientList.get(j);
					receiverSocket = receiver.clientSocket;
					//dont send to sender
					if(clientSocket == receiverSocket){
						//do nothing
					}
					else{
						this.sendMessage(userMessage, receiverSocket, username);
					}
				}	
			}
			catch(IOException e){
				System.out.println("ERROR");
				e.printStackTrace();
			}
		}				
	}

	public void sendMessage(String message, Socket s, String username){
		try{
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			//print username and message to server
			out.println(username + " : " + message);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}











