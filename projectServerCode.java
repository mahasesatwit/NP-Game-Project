import java.net.ServerSocket;
import java.net.Socket;

public class projectServerCode {
	static class ClientRequest implements Runnable {
		Socket connectionSocket;
		
		ClientRequest(Socket c){
			connectionSocket = c;
			
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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
