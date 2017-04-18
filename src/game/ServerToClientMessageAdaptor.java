package game;

import java.awt.*;

public class ServerToClientMessageAdaptor {

    private String inputMessage;
    //private game.Message message;

    private static int currentPosition;
    private static double decimalPosition;

    public ServerToClientMessageAdaptor() {
        inputMessage = "";
        currentPosition = 0;
        decimalPosition = 0;
    }

    public Message translate(String inMessage) {
        inputMessage = inMessage;
        //message = new game.Message();
        currentPosition = 0;

        if (inputMessage.substring(0, 4).equals("MAKE")) {
            return translateActivePlayerMessage();
        }
        else {
            return translateBothPlayerMessage();
        }
    }

    private Message translateActivePlayerMessage() {
        Message message = new Message();
        message.isMove = true;
        // int length = 0;
        // char T1_T2[] = new char[2];

        // length = getNumLength();
        // message.setGID(inputMessage.substring(currentPosition - length, currentPosition));

        // length = getDecimalNumLength();
        // message.setTime(Double.parseDouble(inputMessage.substring(currentPosition - length, currentPosition)));

        // length = getNumLength();
        // message.setMove(Integer.parseInt(inputMessage.substring(currentPosition - length, currentPosition)));

        // getTileTerrain(T1_T2);
        // message.setTile(new game.Tile(T1_T2[0], T1_T2[1]));


        String[] strArr = inputMessage.split(" ");
        message.originalMessage = inputMessage;

        message.setGID(strArr[5]);
        message.setTime(Double.parseDouble(strArr[7]));
        message.setMove(Integer.parseInt(strArr[10]));
        char t1 = strArr[12].split("\\+")[0].charAt(0);
        char t2 = strArr[12].split("\\+")[1].charAt(0);
        message.setTile(new Tile(t1, t2));

        return message;
    }

    private Message translateBothPlayerMessage() {
        /*
        int length = 0;
        char T1_T2[] = new char[2];

        length = getNumLength();
        message.setGID(inputMessage.substring(currentPosition - length, currentPosition));

        length = getDecimalNumLength();
        message.setTime(Double.parseDouble(inputMessage.substring(currentPosition - length, currentPosition)));

        length = getNumLength();
        message.setMove(Integer.parseInt(inputMessage.substring(currentPosition - length, currentPosition)));

        getTileTerrain(T1_T2);
        message.setTile(new game.Tile(T1_T2[0], T1_T2[1]));
        */
        Message message = new Message();
        String[] strArr = inputMessage.split(" ");

        message.setGID(strArr[1]);
        message.setMove(Integer.parseInt(strArr[3]));
        message.setPID(strArr[5]);
        if(strArr[6].charAt(0) == 'P'){
            parseMove(message);
        }
        else{
            message.isGameOver = true;
        }

        return message;
    }

    private void parseMove(Message message){
        String[] strArr = inputMessage.split(" ");

        char t1 = strArr[7].split("\\+")[0].charAt(0);
        char t2 = strArr[7].split("\\+")[1].charAt(0);
        message.setTile(new Tile(t1, t2));

        int x = Integer.parseInt(strArr[9]);
        int z = Integer.parseInt(strArr[11]);
        message.setTilePoint(new Point(x, z));
        int o = Integer.parseInt(strArr[12]);
        message.getTile().setOrientation(o - 1);
        message.setOrientation(o - 1);

        if(strArr[13].equals("FOUNDED")){
            if (strArr[14].equals("SHANGRILA")) {
                message.setBuild(BuildOptions.NEW_SHANGRILA);
            } else {
                message.setBuild(BuildOptions.NEW_SETTLEMENT);
            }
            x = Integer.parseInt(strArr[16]);
            z = Integer.parseInt(strArr[18]);
            message.setBuildPoint(new Point(x, z));
        }
        else if(strArr[13].equals("EXPANDED")){
            if (strArr[14].equals("SHANGRILA")) {
                message.setBuild(BuildOptions.EXPAND_SHANGRILA);
            } else {
                message.setBuild(BuildOptions.EXPAND);
            }
            x = Integer.parseInt(strArr[16]);
            z = Integer.parseInt(strArr[18]);
            message.setBuildPoint(new Point(x, z));
            message.setTerrain(strArr[19].charAt(0));
        }
        else if(strArr[14].equals("TOTORO")){
            message.setBuild(BuildOptions.TOTORO_SANCTUARY);
            x = Integer.parseInt(strArr[17]);
            z = Integer.parseInt(strArr[19]);
            message.setBuildPoint(new Point(x, z));
        }
        else{//TIGER
            message.setBuild(BuildOptions.TIGER_PLAYGROUND);
            x = Integer.parseInt(strArr[17]);
            z = Integer.parseInt(strArr[19]);
            message.setBuildPoint(new Point(x, z));
        }
    }

    /*private void settupMoveMade() {
        int length = 0;
        int x, y, z, orientation;
        char T1_T2[] = new char[2];

        getTileTerrain(T1_T2);
        message.setTile(new game.Tile(T1_T2[0], T1_T2[1]));

        length = getNumLength();
        x = getNumValue(length);

        length = getNumLength();
        length = getNumLength();
        z = getNumValue(length);

        length = getNumLength();
        orientation = getNumValue(length) - 1;

        message.setTilePoint(new Point(x, z));
        message.getTile().setOrientation(orientation);

        if (inputMessage.charAt(++currentPosition) == 'F') { // found
            message.setBuild(game.BuildOptions.NEW_SETTLEMENT);
            message.setBuildPoint(parseBuildPoint());
        }
        else if (inputMessage.charAt(currentPosition) == 'E') { // expand
            message.setBuild(game.BuildOptions.EXPAND);
            message.setBuildPoint(parseBuildPoint());
            message.setTerrain(parseTerrainType());
        }
        else if (inputMessage.charAt(currentPosition += 7) == 'O') { // totoro sanctuary
            message.setBuild(game.BuildOptions.TOTORO_SANCTUARY);
            message.setBuildPoint(parseBuildPoint());
        }
        else { // tiger playground
            message.setBuild(game.BuildOptions.TIGER_PLAYGROUND);
            message.setBuildPoint(parseBuildPoint());
        }

    }

    private char parseTerrainType() {
        int length;

        return inputMessage.charAt(++currentPosition);
    }

    private Point parseBuildPoint() {
        int x, z, length;

        length = getNumLength();
        x = getNumValue(length);

        length = getNumLength();
        length = getNumLength();
        z = getNumValue(length);

        return new Point(x, z);
    }

    private void getTileTerrain(char [] t1_t2) {
        currentPosition += 7; // tile locatation in string
        int i = currentPosition;

        t1_t2[0] = inputMessage.charAt(i);
        while (inputMessage.charAt(i) != '+') {
            ++i;
        }
        t1_t2[1] = inputMessage.charAt(i + 1);
    }

    private int getDecimalNumLength() {
        int i = currentPosition;

        int j = 0;

        while (inputMessage.length() > i && (inputMessage.charAt(i) < 48 || inputMessage.charAt(i) > 57)) {
            ++i;
        }

        j = i;
        while (inputMessage.length() > j && (inputMessage.charAt(j) >= 48 &&
                inputMessage.charAt(j) <= 57 || inputMessage.charAt(j) == '.')) {
            ++j;
        }

        currentPosition = j;
        return j - i;
    }

    private int getNumLength() {
        int i = currentPosition;
        int j = 0;

        while (inputMessage.length() > i && (inputMessage.charAt(i) < 48 || inputMessage.charAt(i) > 57)) {
            ++i;
        }
        j = i;
        while (inputMessage.length() > j && (inputMessage.charAt(j) >= 48 && inputMessage.charAt(j) <= 57)) {
            ++j;
        }
        currentPosition = j;
        return j - i;
    }

    private int getNumValue(int length) {
        int start = currentPosition - length;
        int valueOfString = 0;

        for (int i = start; i < currentPosition; ++i) {
            valueOfString = (int) ((int) (inputMessage.charAt(i) - 48) * Math.pow(10, currentPosition - i - 1));
        }
        return valueOfString;
    }*/

}
