import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class projectServerCode {
	static class ClientRequest implements Runnable {
		private String name;
		private Scanner in;
		private PrintWriter out;
		private Socket socket;
		
		ClientRequest(Socket c){
			socket = c;
			
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			
			while (true) {
				
				
				
				
				
			}
			
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			
			
			
			
	}
	
	
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
			}
		}
			catch (Exception ex) {
			// TODO Auto-generated catch block
			System.out.println("Error!\r\n");
		}

	
		


	}

	

 }
}
