import java.io.*;

public class ReceiverThread implements Runnable{
    private BufferedReader reader;
//    private AI pA;
//    private AI pB;

    public ReceiverThread(BufferedReader reader /*, AI ai */){
        this.reader = reader;
        //set AI
    }

    @Override
    public void run(){
        String serverMessage;
        while(true){
            try {
                serverMessage = reader.readLine();
            } catch (IOException e) {e.printStackTrace();}

            //Create new Message() and pass to correct AI
            //Don't pass our AI move when server sends it back

        }
    }
}
