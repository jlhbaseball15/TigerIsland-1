package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TigerIslandClient{
	private static String tournamentPassword, username, password;
	private static ServerToClientMessageAdaptor s2c;
	private static ClientToServerMessageAdaptor c2s;
	private static ConcurrentLinkedQueue<Message> p1InMessages, p1OutMessages, p2InMessages, p2OutMessages;
	private static Thread aiP1, aiP2;
	private static String PID = "";

	public static void main(String[] args){
		s2c = new ServerToClientMessageAdaptor();
		c2s = new ClientToServerMessageAdaptor();

		p1InMessages = new ConcurrentLinkedQueue();
		p1OutMessages = new ConcurrentLinkedQueue();
		p2InMessages = new ConcurrentLinkedQueue();
		p2OutMessages = new ConcurrentLinkedQueue();

        if (args.length != 5) {
			System.err.println("game.TigerIslandClient <serverUrl> <port> <tournamentpassword> <username> <password>");
			System.exit(1);
		}

		String serverUrl = args[0];
		int port = Integer.parseInt(args[1]);
		tournamentPassword = args[2];
		username = args[3];
		password = args[4];

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
		System.out.println(reader.readLine()); //WELCOME TO ANOTHER EDITION OF THUNDERDOME!
		writer.println("ENTER THUNDERDOME " + tournamentPassword);
		System.out.println(reader.readLine()); //TWO SHALL ENTER, ONE SHALL LEAVE
		writer.println("I AM " + username + " " + password);
		String pid = reader.readLine();
		System.out.println(pid); //WAIT FOR THE TOURNAMENT TO BEGIN <pid>
		PID = pid.split(" ")[6];
	}

	private static void challengeProtocol(PrintWriter writer, BufferedReader reader) throws IOException {
		while (true){
			System.out.println(reader.readLine()); //NEW CHALLENGE <cid> YOU WILL PLAY <rounds> MATCH(ES)

			roundProtocol(writer, reader);

			String message = reader.readLine();
			System.out.println(message);
			if (message.equals("END OF CHALLENGES"))
				break;
		}
	}

	private static void roundProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		while(true){
			String o = reader.readLine();
			System.out.println(o); //BEGIN ROUND <rid> OF <rounds>


			String m = reader.readLine();
			if(m.split(" ")[0].equals("NEW")){
				matchProtocol(writer, reader);
				m = reader.readLine();
			}

			System.out.println(m);
			if(m.split(" ").length == 6)
				break;
		}
	}

	private static void matchProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		//System.out.println(reader.readLine()); //NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER <pid>

		p1InMessages.clear(); p2InMessages.clear(); p1OutMessages.clear(); p2OutMessages.clear();

		aiP1 = new Thread(new AI(true, p1InMessages, p1OutMessages, PID));
		aiP2 = new Thread(new AI(false, p2InMessages, p2OutMessages, PID));

		aiP1.start();
		aiP2.start();

		moveProtocol(writer, reader);

		aiP1.interrupt();
		aiP2.interrupt();

		//System.out.println(reader.readLine()); //GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
		//System.out.println(reader.readLine()); //GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
	}

	private static void moveProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		//MAKE YOUR MOVE IN GAME <gid> WITHIN <time move > SECONDS: MOVE <#> PLACE <tile>
		String message = reader.readLine();
		System.out.println(message + " START!!!");
		p1InMessages.add(s2c.translate(message));
		message = message.split(" ")[5];  //game.Player 1 Game

		String player1Game = message;
		boolean game1Over = false;
		boolean game2Over = false;

		int overCount = 0;
        while(overCount < 2){
            //read in message
        	//only wait for message if buffer has string!!
        	if(reader.ready()){
	        	String m = reader.readLine();
	        	System.out.println(m);

	        	//BREAK ON GAME <GID> OVER ...

	            //send to whoever cares
	            if(m.split(" ")[2].equals("OVER")){
//	            	if(m.split(" ")[2].equals(player1Game))
//	            		game1Over = true;
//	            	else
//	            		game2Over = true;
	            	overCount++;
	            }
	        	else if(m.split(" ")[0].equals("MAKE")){
	        		String game = m.split(" ")[5];
	        		if(game.equals(player1Game))
	        			p1InMessages.add(s2c.translate(m));
	        		else
	        			p2InMessages.add(s2c.translate(m));
	        	}
	        	else{
	        		String game = m.split(" ")[1];
	        		if(game.equals(player1Game))
	        			p1InMessages.add(s2c.translate(m));
	        		else
	        			p2InMessages.add(s2c.translate(m));
	        	}
	        }
            //check for game.AI messages
	        if(!p1OutMessages.isEmpty()){
	        	String out = c2s.translate(p1OutMessages.remove());
				System.out.println(">> " + out);
				writer.println(out);
			}
        	if(!p2OutMessages.isEmpty()){
				String out = c2s.translate(p2OutMessages.remove());
				System.out.println(">> " + out);
				writer.println(out);
			}
        }
	}
}
