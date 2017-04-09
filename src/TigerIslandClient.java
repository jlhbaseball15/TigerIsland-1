import java.io.*;
import java.net.*;

public class TigerIslandClient{
	private static String tournamentPassword, username, password;
	private static AI aiP1, aiP2;
	//private static Thread senderT, receiverT;

	public static void main(String[] args){

		String serverUrl = args[0];
		int port = Integer.parseInt(args[1]);
		tournamentPassword = args[2];
		username = args[3];
		password = args[4];

		if (args.length != 5) {
			System.err.println("need <serverUrl> <port> <tournamentpassword> <username> <password>");
			System.exit(1);
		}

		try(
				Socket socket = new Socket(serverUrl, port);
				PrintWriter    writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		){
			//while (true){
				authProtocol(writer, reader);
				challengeProtocol(writer, reader);

				//aiP1 = new AI(true);
				//aiP2 = new AI(false);
				//senderT = new Thread(new SenderThread(writer));
				//receiverT = new Thread(new ReceiverThread(reader));


			//}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void authProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		reader.readLine(); //Welcome to thunderdome
		writer.println("ENTER THUNDERDOME " + tournamentPassword);
		reader.readLine(); //Two enter one leave
		writer.println("I AM " + username + " " + password);
		reader.readLine(); //wait for tournament to begin...
	}

	private static void challengeProtocol(PrintWriter writer, BufferedReader reader) throws IOException {
		while (true){
			reader.readLine(); //NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCH(ES)

			roundProtocol(writer, reader);

			String message = reader.readLine();
			if (message.equals("END OF CHALLENGES"))
				break;
		}
	}

	private static void roundProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		while(true){
			reader.readLine(); //BEGIN ROUND <rid> OF <rounds>

			matchProtocol(writer, reader);

			String message = reader.readLine(); //END OF ROUND <rid> OF <rounds>
			if(message.split(" ").length == 6)
				break;
		}
	}

	private static void matchProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		reader.readLine(); //NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>

		aiP1 = new AI(true);
		aiP2 = new AI(false);

		moveProtocol(writer, reader);

		reader.readLine(); //GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
		reader.readLine(); //GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
	}

	private static void moveProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		//MAKE YOUR MOVE IN GAME <gid> WITHIN <time move > SECONDS: MOVE <#> PLACE <tile>
		//Create Message Object for it and Pass to correct AI

		//Poll for client messages and send whenever one is available

		//Two messages from server. Pass to correct AI
		//Move you just made (w/ legal confirmation) and move made by enemy in other game
	}
}
