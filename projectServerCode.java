import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
		String lobbyChoice;
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
		
//		public void setLobbyChoice(String i) {
//			lobbyChoice = i;
//		}
//		
//		public String getLobbyChoice() {
//			return lobbyChoice;
//		}
		
		public Socket getConnection() {
			return connection;
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
//newLobby class: second iteration based on code found on stack overflow
//https://stackoverflow.com/questions/58937039/java-create-a-team-then-add-players
////////////////////////////////////////////////////////////////////////////////////////////////////
	static class Lobby {
		String lobbyName;
		
		public Lobby(String lobbyName) {
			this.lobbyName = lobbyName;
		}
		
		public void addPlayer (Players player) {
			players.add(player);
		}
		
		public String getLobbyName() {
			return lobbyName;
		}
		
		public List<Players> getPlayers() {
			return players;
		}

		public static boolean lobbyExists(String userLobbyChoice) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
//Game class: based on code found on stack overflow
//https://stackoverflow.com/questions/58937039/java-create-a-team-then-add-players
////////////////////////////////////////////////////////////////////////////////////////////////////
	static class Game {
		HashMap<String, Lobby> lobbies = new LinkedHashMap<String, Lobby>();
		String[] allLobbyNames;
		int numLobbies = 0;
		
		public void addLobby(String lobbyName) {
			Lobby lobby = new Lobby(lobbyName);
			lobbies.put(lobbyName, lobby);
			allLobbyNames[numLobbies] = lobbyName;
			numLobbies++;
		}
		
		public void addPlayer (String teamName, String name, Socket c) {
			Players player = new Players(c);
			player.setName(name);
			Lobby lobby = lobbies.get(teamName);
			lobby.addPlayer(player);
		}

		public void lobbyExists (String possibleName) {
			for (int i = 0; i <= numLobbies; i++) {
				if (allLobbyNames[i] == possibleName) {
					boolean exists = true;
				}
				else {
					boolean exists = false;
				}
			}
		}
		
		
		public void print() {
			for(Map.Entry<String , Lobby> entry : lobbies.entrySet() ) {
				Lobby lobby = entry.getValue();
				System.out.println("Lobby Name: " + lobby.getLobbyName());
				System.out.println("Players: " + lobby.getPlayers());	
			}
			
		}

}

////////////////////////////////////////////////////////////////////////////////////////////////////
//Lobby class: creates new lobbys, adds ability to add players to lobbys
////////////////////////////////////////////////////////////////////////////////////////////////////	
//	static class Lobby {
//		String[] activePlayers;
//		String[] activeLobbies;
//		String[] lobbyNames;
//		
//		//creates a lobby 
//		Lobby(String name) {
//			for (int x = 0; x < 10; x++) {
//			activeLobbies[x] = null;
//			lobbyNames[x] = name;
//			activePlayers[x] = null;
//			}
//		}
//		
//		//to add a player to a lobby: addPlayer(player.getName(i), i, activeLobbies[x]);
//		public void addPlayer (String name, int i, int lobbyNum) {
//			//add players to the active players in the active lobby number lobbyNum
//			activeLobbies[i] = activePlayers[i];
//		}
//		
//		public void removePlayer(String name, int i, int lobbyNum) {
//			
//		}
//	}
////////////////////////////////////////////////////////////////////////////////////////////////////	
// Lists: keeps track of players, previous messages, etc.
////////////////////////////////////////////////////////////////////////////////////////////////////	
	static List<Players> players = new ArrayList<>();
	static List<String> prevMsg = new ArrayList<>();
	
	static String[] board;
	static String turn;
	
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
			String lobbyChoice;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
			
			Game game = new Game();
			
			//this will need to be modified. 
			String clientMessage = "";
			name = in.readLine();
			lobbyChoice = in.readLine();
			for(int i = 0; i < players.size(); i++) {
				if(players.get(i).getConnection() == connectionSocket) {
					players.get(i).setName(name);
					
					DataOutputStream outToClient = new DataOutputStream(players.get(i).getConnection().getOutputStream());
					
					
					if (lobbyChoice.contentEquals("1") == true) {
						//user chooses to create new lobby
						//players.get(i).setLobbyChoice("1");
						
						//HAVE A PRINT STATEMENT HERE CLIENT SIDE ASKING FOR LOBBY NAME
						
						String lobbyName = in.readLine();
						game.addLobby(lobbyName);
						game.addPlayer(lobbyName, players.get(i).getName(), players.get(i).getConnection());
				
						
						
						System.out.println("Code 01");
						outToClient.writeBytes("User Chose to Create New Lobby " + lobbyName + "! (01)\r\n");
						System.out.println(players.get(i).getName() + " has created lobby '" + lobbyName);
					}
					
					if (lobbyChoice.contentEquals("2") == true) {
						//user chooses to join existing lobby
						//players.get(i).setLobbyChoice("2");
						String userLobbyChoice = in.readLine();
						if (Lobby.lobbyExists(userLobbyChoice) == true) {
							game.addPlayer(userLobbyChoice, players.get(i).getName(), players.get(i).getConnection());
							
						}
							//game.addPlayer(userLobbyChoice, players.get(i).getName(), players.get(i).getConnection());
						else {
							System.out.println("Invalid Lobby Selection!");
							outToClient.writeBytes("Invalid Lobby Selection! Please choose an existing lobby name or create a new one (1): ");
							String choice = in.readLine();
							if (choice.contentEquals("1")) {
								String lobbyName = in.readLine();
								game.addLobby(lobbyName);
								game.addPlayer(lobbyName, players.get(i).getName(), players.get(i).getConnection());
						
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								
								System.out.println("Code 01");
								outToClient.writeBytes("User Chose to Create New Lobby " + lobbyName + "! (31)\r\n");
								System.out.println(players.get(i).getName() + " has created lobby '" + lobbyName);
							}
						}
							System.out.println("Code 02");
						
							outToClient.writeBytes("User Chose to Join Existing Lobby! (02)\r\n");
						
					}
					
					
					
//					else if (lobbyChoice.contentEquals("1") == false && lobbyChoice.contentEquals("2") == false) {
//						
//						outToClient.writeBytes("Server: You must choose to either create a new lobby (1), or join an existing lobby (2): ");
//						
//						lobbyChoice = in.readLine();
//						
//						if (lobbyChoice.contentEquals("1") == true) {
//							//user chooses to create new lobby
//							players.get(i).setLobbyChoice("1");
//							System.out.println("Code 11");
//							outToClient.writeBytes("User Chose to Create New Lobby! (11)\r\n");
//						}
//						if (lobbyChoice.contentEquals("2") == true) {
//							//user chooses to join existing lobby
//							players.get(i).setLobbyChoice("2");
//							System.out.println("Code 12");
//							outToClient.writeBytes("User Chose to Join Existing Lobby! (12)\r\n");
//
//						}
//						
//						else {
//							outToClient.writeBytes("Server: user entered invalid input, terminating connection...");
//							closeConnection(players.get(i).getName());
//						}
//						
//					}
					
					
					
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
		int port = 1234;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server listening to port " + port);

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
//displayGameBoard(): displays current game board
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void displayGameBoard() {
		//this was sourced from an existing GitHub game
		//https://gist.github.com/xaviablaza-zz/3844825
		//this needs to be altered to be sent to the players
		
		
		System.out.println("/---|---|---\\");
		System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
		System.out.println("|-----------|");
		System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
		System.out.println("|-----------|");
		System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
		System.out.println("/---|---|---\\");
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////
// sendToAll(String msg, String name): sends a name and string message to users connected to the server 
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void sendToAll(String msg, String name) throws IOException {
		if(prevMsg.size() < 5) {
			prevMsg.add(name + ": " + msg);
		}
		else {
			prevMsg.remove(0);
			prevMsg.add(name + ": " + msg);
		}
		for(int i = 0; i < players.size(); i++) {
			if(name.equals(players.get(i).getName())) {
				continue;
			}
			DataOutputStream outToClient = new DataOutputStream(players.get(i).getConnection().getOutputStream());
			outToClient.writeBytes(name + ": " + msg);
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
		for(int i = 0; i < players.size(); i++) {
			DataOutputStream outToClient = new DataOutputStream(players.get(i).getConnection().getOutputStream());
			outToClient.writeBytes(msg);
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////
// closeConnection: closes TCP connection of specific user, removes from players
////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void closeConnection(String name) {
		for(int i = 0; i < players.size(); i++) {
			if(name.equals(players.get(i).getName())) {
				players.remove(i);
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
 }
}
