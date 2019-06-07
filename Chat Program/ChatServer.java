import java.net.*;
import java.net.ServerSocket;
import java.io.*;
import java.util.*;

public class ChatServer {

	private ServerSocket in;
	//vector is synchronised
	public Vector<RunThread> clientList;
	private Socket currentSocket;
	private static int csp;
	private int i = 1;

	public ChatServer(int csp){
		try{
			in = new ServerSocket(csp);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		clientList = new Vector<RunThread>();
	}

	public void go(){
		try{
			System.out.println("server Listening");
			while(true){
				//look for and accept connections from clients
				Socket s = in.accept();
				RunThread newClient = new RunThread(s, this, "client" + i);
				Thread t = new Thread(newClient);
				//add client to client list
				clientList.add(newClient);
				t.start();
				System.out.println("client added");
				//increment i for naming
				i++;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//close connection
		finally{
			try{
				in.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		csp = 14017;
		
		try{
			for(int i = 0; i < args.length; i++){
				switch(args[i]){
					case "-csp": csp = Integer.parseInt(args[i+1]);
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println("Error with parameter, using default");
		}
		new ChatServer(csp).go();
	}
}