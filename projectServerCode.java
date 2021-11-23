import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


////////////////////////////////////////////////////////////////////////////////////////////////////
//								TCP-Tac-Toe ---- Server Code									  //
//								Sam Bogonis & Shemar Mahase										  //
//							Network Programming - Final Project									  //
//									projectServerCode											  //
////////////////////////////////////////////////////////////////////////////////////////////////////

public class projectServerCode {
	
////////////////////////////////////////////////////////////////////////////////////////////////////
// People class: defines individual players and ties their information to their thread connection
////////////////////////////////////////////////////////////////////////////////////////////////////
	static class Players {
		String name;
		Socket connection;
		
		Players(Socket c){
			connection = c;
		}
		
		public void setName(String n) {
			name = n;
		}
		
		public String getName() {
			return name;
		}
		
		public Socket getConnection() {
			return connection;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////	
// Lists: keeps track of players, previous messages, etc.
////////////////////////////////////////////////////////////////////////////////////////////////////	
	static List<Players> players = new ArrayList<>();
	static List<String> prevMsg = new ArrayList<>();
	
////////////////////////////////////////////////////////////////////////////////////////////////////
// ClientRequest: server-side socket handling
////////////////////////////////////////////////////////////////////////////////////////////////////
	static class ClientRequest implements Runnable {
			Socket connectionSocket;
			
			ClientRequest(Socket c){
				connectionSocket = c;
				
		}
			
//////////////////////////////////////////////////////////////////////////////////////////////////// 
// run(): handles users inputs, where the magic happens
////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {
			// this String name will need to be replaced...
			String name; 
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
			
			//this will need to be modified. 
			String clientMessage ="";
			name = in.readLine();
			for(int i = 0; i < players.size();i++) {
				if(players.get(i).getConnection()==connectionSocket) {
					players.get(i).setName(name);
				}
			}
			for(int i = 0; i < prevMsg.size();i++) {
				out.writeBytes(prevMsg.get(i));
			}
			sendToAll(name + " has joined the chat!\n");
			
			while (true) {
				
				//put something here
				
				
				
			}
			
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			
			
			
			
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
// main(String[] args): sets up multithreading and adds new users to players 
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(1234);
			System.out.println("This server is ready to receive");

			while (true) {
				Socket connectionSocket = serverSocket.accept();
				ClientRequest clientRequest = new ClientRequest(connectionSocket);
				Thread thread = new Thread(clientRequest);
				thread.start();
				players.add(new Players(connectionSocket));
				
			}
		}
			catch (Exception ex) {
			// TODO Auto-generated catch block
			System.out.println("Error!\r\n");
		}

	
		


	}
////////////////////////////////////////////////////////////////////////////////////////////////////
// sendToAll(String msg, String name): sends a name and string message to users connected to the server 
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void sendToAll(String msg, String name) throws IOException {
		if(prevMsg.size() < 5) {
			prevMsg.add(name+": "+msg);
		}
		else {
			prevMsg.remove(0);
			prevMsg.add(name+": "+msg);
		}
		for(int i = 0; i < players.size();i++) {
			if(name.equals(players.get(i).getName())) {
				continue;
			}
			DataOutputStream outToClient = new DataOutputStream(players.get(i).getConnection().getOutputStream());
			outToClient.writeBytes(name+": "+msg);
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
// sentToAll(String msg): sends a string message to users connected to the server
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void sendToAll(String msg) throws IOException {
		if(prevMsg.size() < 5) {
			prevMsg.add(msg);
		}
		else {
			prevMsg.remove(0);
			prevMsg.add(msg);
		}
		for(int i = 0; i < players.size();i++) {
			DataOutputStream outToClient = new DataOutputStream(players.get(i).getConnection().getOutputStream());
			outToClient.writeBytes(msg);
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
// closeConnection: closes TCP connection of specific user, removes from players
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void closeConnection(String name) {
		for(int i = 0; i < players.size();i++) {
			if(name.equals(players.get(i).getName())) {
				players.remove(i);
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
 }
}
