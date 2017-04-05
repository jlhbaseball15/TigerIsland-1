import java.io.*;
import java.net.*;
import java.util.*;

public class TigerIslandClient{

	public static void main(String[] args){
		String serverUrl = args[0];
		int port = Integer.parseInt(args[1]);
		String tournamentPassword = args[2];
		String userName = args[3];
		String passWord = args[4];

		if (args.length != 5) {
			System.err.println(
					"need <serverUrl> <port> <tournamentpassword> <username> <password>");
			System.exit(1);
		}

		/*
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
		}*/
	}
}
