import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class TigerIslandClient{
	private static String tournamentPassword, username, password;
	private static ServerToClientMessageAdaptor s2c;
	private static ClientToServerMessageAdaptor c2s;
	private static Queue<Message> p1InMessages, p1OutMessages, p2InMessages, p2OutMessages;
	private static Thread aiP1, aiP2;

	public static void main(String[] args){
		s2c = new ServerToClientMessageAdaptor();
		c2s = new ClientToServerMessageAdaptor();

		p1InMessages = new LinkedList<Message>();
		p1OutMessages = new LinkedList<Message>();
		p2InMessages = new LinkedList<Message>();
		p2OutMessages = new LinkedList<Message>();

		String serverUrl = args[0];
		int port = Integer.parseInt(args[1]);
		tournamentPassword = args[2];
		username = args[3];
		password = args[4];

		if (args.length != 5) {
			System.err.println("TigerIslandClient <serverUrl> <port> <tournamentpassword> <username> <password>");
			System.exit(1);
		}

		try(
				Socket socket = new Socket(serverUrl, port);
				PrintWriter    writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		){

			authProtocol(writer, reader);
			challengeProtocol(writer, reader);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void authProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		reader.readLine(); //WELCOME TO ANOTHER EDITION OF THUNDERDOME!
		writer.println("ENTER THUNDERDOME " + tournamentPassword);
		reader.readLine(); //TWO SHALL ENTER, ONE SHALL LEAVE
		writer.println("I AM " + username + " " + password);
		reader.readLine(); //WAIT FOR THE TOURNAMENT TO BEGIN <pid>
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

		aiP1 = new Thread(new AI(true, p1InMessages, p1OutMessages));
		aiP2 = new Thread(new AI(false, p2InMessages, p2OutMessages));

		aiP1.start();
		aiP2.start();

		moveProtocol(writer, reader);

		reader.readLine(); //GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
		reader.readLine(); //GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
	}

	private static void moveProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		//MAKE YOUR MOVE IN GAME <gid> WITHIN <time move > SECONDS: MOVE <#> PLACE <tile>
		String message = reader.readLine();
		p1InMessages.add(s2c.translate(message));
		message = message.split(" ")[5];  //Game I just made a move in

		//Poll for client messages and send whenever one is available
		while(p1OutMessages.isEmpty());
		writer.println(c2s.translate(p1OutMessages.remove()));

		//Two messages from server. Pass to correct AI
		//Move you just made (w/ legal confirmation) and move made by enemy in other game
		String m1 = reader.readLine();
		String m2 = reader.readLine();
		if(m1.split(" ")[1].equals(message)){
			p1InMessages.add(s2c.translate(m1));
			p2InMessages.add(s2c.translate(m2));
		}
		else{
			p2InMessages.add(s2c.translate(m1));
			p1InMessages.add(s2c.translate(m2));
		}

		//make move other game
		message = reader.readLine();
		p2InMessages.add(s2c.translate(message));
		message = message.split(" ")[5];

		//poll 4 message other game
		while(p2OutMessages.isEmpty());
		writer.println(c2s.translate(p2OutMessages.remove()));

		//two messages other game
		m1 = reader.readLine();
		m2 = reader.readLine();
		if(m1.split(" ")[1].equals(message)){
			p1InMessages.add(s2c.translate(m1));
			p2InMessages.add(s2c.translate(m2));
		}
		else{
			p2InMessages.add(s2c.translate(m1));
			p1InMessages.add(s2c.translate(m2));
		}
	}
}
