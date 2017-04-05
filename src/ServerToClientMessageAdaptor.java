import java.awt.*;

/**
 * Created by dontf on 4/3/2017.
 */
public class ServerToClientMessageAdaptor {

    private String inputMessage;
    private Message message;

    private static int currentPosition;
    private static double decimalPosition;

    public ServerToClientMessageAdaptor() {
        inputMessage = "";
        currentPosition = 0;
        decimalPosition = 0;
    }

    public Message translate(String inMessage) {
        inputMessage = inMessage;
        message = new Message();
        currentPosition = 0;

        if (inputMessage.substring(0, 4).equals("MAKE")) {
            return translateActivePlayerMessage();
        }
        else {
            return translateBothPlayerMessage();
        }
    }

    private Message translateActivePlayerMessage() {
        int length = 0;
        char T1_T2[] = new char[2];

        length = getNumLength();
        message.setGID(inputMessage.substring(currentPosition - length, currentPosition));

        length = getDecimalNumLength();
        message.setTime(Double.parseDouble(inputMessage.substring(currentPosition - length, currentPosition)));

        length = getNumLength();
        message.setMove(Integer.parseInt(inputMessage.substring(currentPosition - length, currentPosition)));

        getTileTerrain(T1_T2);
        message.setTile(new Tile(T1_T2[0], T1_T2[1]));

        return message;
    }

    private Message translateBothPlayerMessage() {
        int length = 0;

        length = getNumLength();
        message.setGID(inputMessage.substring(currentPosition - length, currentPosition));

        length = getNumLength();
        message.setMove(Integer.parseInt(inputMessage.substring(currentPosition - length, currentPosition)));

        length = getNumLength();
        message.setPID(inputMessage.substring(currentPosition - length, currentPosition));

        if (inputMessage.charAt(++currentPosition) == 'P') {
            settupMoveMade();
        }
        else {
            message.isGameOver = true;
        }

        return message;
    }

    private void settupMoveMade() {
        int length = 0;
        int x, y, z, orientation;
        char T1_T2[] = new char[2];

        getTileTerrain(T1_T2);
        message.setTile(new Tile(T1_T2[0], T1_T2[1]));

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
            message.setBuild(BuildOptions.NEW_SETTLEMENT);
            message.setBuildPoint(parseBuildPoint());
        }
        else if (inputMessage.charAt(currentPosition) == 'E') { // expand
            message.setBuild(BuildOptions.EXPAND);
            message.setBuildPoint(parseBuildPoint());
            message.setTerrain(parseTerrainType());
        }
        else if (inputMessage.charAt(currentPosition += 7) == 'O') { // totoro sanctuary
            message.setBuild(BuildOptions.TOTORO_SANCTUARY);
            message.setBuildPoint(parseBuildPoint());
        }
        else { // tiger playground
            message.setBuild(BuildOptions.TIGER_PLAYGROUND);
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
    }

}
