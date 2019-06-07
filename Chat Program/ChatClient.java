import java.io.*;
import java.net.*;

public class ChatClient{

	private Socket server;
	public String userInput;
	private static String cca;
	private static int ccp;
	//value that allows users to decide whether to use the GUI
	private static Boolean cgui;
	private String username;
	private ClientGUI gui;

	public ChatClient(String ip, int port){
		
		try{
			server = new Socket(ip, port);
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

	public void start(){

			if (cgui == true){
				gui = new ClientGUI(this);
			}

			//thread that sends messages to server
			Thread sendToServer = new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
						PrintWriter out = new PrintWriter(server.getOutputStream(), true);
						while(true){
								try{
									if(cgui == true){
										gui.sendMessage();
									}
									else{
										userInput = userIn.readLine();
									}
									//check if user wants to logout
									if (userInput.equals("EXIT")){
										System.exit(0);
									}
									else{
										out.println(userInput);
									}
								}
								catch(IOException e){
									e.printStackTrace();
								}
						}
					}
					catch(IOException e){
						e.printStackTrace();
					}

				}
			});

			//thread that reads messages from the server
			Thread readFromServer = new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						BufferedReader serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
						while(true){
							try{
								String serverRes = serverIn.readLine();
								if (cgui == true){
									gui.displayMessage(serverRes);
								}
								else{
									System.out.println(serverRes);
								}
							}
							catch(IOException e){
								e.printStackTrace();
							}
						}
					}
					catch(IOException e){
						e.printStackTrace();
					}

				}
			});

			//start both threads
			sendToServer.start();
			readFromServer.start();
	}

	public static void main(String[] args) {
		ccp = 14001;
		cca = "localhost";
		cgui = false;
		
		try{
			for(int i = 0; i < args.length; i++){
				switch(args[i]){
					case "-cca": cca = args[i+1];
					break;
					case "-ccp": ccp = Integer.parseInt(args[i+1]);
					break;
					case "-cgui": cgui = Boolean.valueOf(args[i+1]);
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println("Error with parameters, using defaults");
		}
		new ChatClient(cca, ccp).start();
	}
}