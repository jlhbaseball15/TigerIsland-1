import java.io.*;
import java.net.*;

public class TigerIslandClient{
	private static String tournamentPassword, username, password;

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
			while (true){
				authProtocol(writer, reader);
				//new AI1
				//new AI2
				Thread senderT = new Thread(new SenderThread(writer));
				Thread receiverT = new Thread(new ReceiverThread(reader));

				reader.readLine(); //new challenge...
				reader.readLine(); //begin round ID...

			}		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void authProtocol(PrintWriter writer, BufferedReader reader) throws IOException{
		reader.readLine(); //Welcome to thunderdome
		writer.println("ENTER THUNDERDOME " + tournamentPassword);
		reader.readLine(); //Two enter one leave
		writer.println("I AM " + username + " " + password);
		reader.readLine(); //wait for tournament to begin...
	}
}
