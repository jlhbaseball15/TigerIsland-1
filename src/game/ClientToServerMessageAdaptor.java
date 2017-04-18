package game;

import java.awt.*;

/**
 * Created by dontf on 4/3/2017.
 */
public class ClientToServerMessageAdaptor {
    private String outputMessage;
    private Message message;

    public ClientToServerMessageAdaptor() {
        outputMessage = "";
        message = new Message();
    }

    public String translate(Message message) {
        this.message = message;

        outputMessage = "GAME " + message.getGID() + " MOVE " + message.getMove() + " PLACE ";

        outputMessage += addTileToMessage();

        outputMessage += addTilePointAndOrientationToMessage();

        outputMessage += addBuildMessage();

        return outputMessage;
    }

    private String addBuildMessage() {
        BuildOptions build = message.getBuild();
        Point p = message.getBuildPoint();
        int x = p.x;
        int z = p.y;
        int y = -x - z;
        String buildMessage;

        if (build == BuildOptions.NEW_SETTLEMENT) {
            buildMessage = "FOUND SETTLEMENT AT " + x + " " + y + " " + z;
        }
        else if (build == BuildOptions.EXPAND) {
            buildMessage = "EXPAND SETTLEMENT AT " + x + " " + y + " " + z + " " + getTerrainFromMessage();
        }
        else if (build == BuildOptions.TOTORO_SANCTUARY) {
            buildMessage = "BUILD TOTORO SANCTUARY AT " + x + " " + y + " " + z;
        }
        else if (build == BuildOptions.TIGER_PLAYGROUND) {
            buildMessage = "BUILD TIGER PLAYGROUND AT " + x + " " + y + " " + z;
        }
        else { // build == NOOP
            buildMessage = "UNABLE TO BUILD";
        }

        return buildMessage;
    }

    private String getTerrainFromMessage() {
        char T = message.getTerrain();

        if (T == 'J') {
            return "JUNGLE";
        }
        else if (T == 'L') {
            return "LAKE";
        }
        else if (T == 'G') {
            return "GRASS";
        }
        else { // T == 'R'
            return "ROCK";
        }

    }

    private String addTilePointAndOrientationToMessage() {
        Point p = message.getTilePoint();
        int x = p.x;
        int z = p.y;
        int y = -x - z;
        String tilePoint = "";

        tilePoint = x + " " + y + " " + z + " " + (message.getOrientation() + 1) + " ";

        return tilePoint;
    }

    private String addTileToMessage() {
        String terrains = "";
        char T1 = message.getTile().getHexes()[0].getTerrain();
        char T2 = message.getTile().getHexes()[1].getTerrain();

        if (T1 == 'J') {
            terrains = "JUNGLE+";
        }
        else if (T1 == 'L') {
            terrains = "LAKE+";
        }
        else if (T1 == 'G') {
            terrains = "GRASS+";
        }
        else if (T1 == 'R') {
            terrains = "ROCK+";
        }

        if (T2 == 'J') {
            terrains += "JUNGLE AT ";
        }
        else if (T2 == 'L') {
            terrains += "LAKE AT ";
        }
        else if (T2 == 'G') {
            terrains += "GRASS AT ";
        }
        else if (T2 == 'R') {
            terrains += "ROCK AT ";
        }

        return terrains;
    }
}
