import java.io.*;
import java.net.*;

public class TigerIslandClient{
	private static final String serverUrl = "localhost";
	private static final int port = 8080;

	public static void main(String[] args){
		try(
			Socket         socket = new Socket(server_URL, port_number);
            PrintWriter    writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		){
			String clientMessage = "";
			String serverMessage = "";
			while (!serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")){
				serverMessage = reader.readLine();
				//SEND MESSAGE TO HANDLER
				//clientMessage = ?
				//RECEIVE RESPONSE MESSAGE AND SENT TO SERVER
				writer.println(clientMessage);
			}		
		}
	}
}
