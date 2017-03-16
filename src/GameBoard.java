import java.awt.*;
import java.util.HashMap;

/**
 * Created by dontf on 3/14/2017.
 */
public class GameBoard {
    private final int mapCapacity = 144;
    private final int tileSize = 3;

    private HashMap<Integer, HashMap<Integer, Hex>> BoardGame;
    private static Hex Tiles[];
    private static Point TileLocations[];

    public GameBoard() {
        BoardGame = new HashMap<Integer, HashMap<Integer, Hex>>(mapCapacity);
    }

    public HashMap getMap() { return BoardGame; }

    public int TryToAddTile(Hex TileHexes[], Point TileHexPoints[]) {

        Tiles = TileHexes;
        TileLocations = TileHexPoints;

        if(isEmpty()) {
            AddTile();
        }
        else {
            if(TilesOverlapIncorrectly()) {
                return -1;
            }
            if (NoAdjacentTiles()) {
                return -1;
            }
            AddTile();
        }
        return 0;
    }

    private boolean TilesOverlapIncorrectly() {
        boolean isOverlapped = false;
        int numberOfOverlap = 0;

        for (int i = 0; i < tileSize; ++i) {
            if (BoardGame.containsKey(TileLocations[i].x) && BoardGame.get(TileLocations[i].x).containsKey(TileLocations[i].y)) {
                isOverlapped = true;
                ++numberOfOverlap;
            }
        }

        if(!isOverlapped) {
            return false;
        }
        // The tile is not flat
        if (numberOfOverlap < 3) {
            return true;
        }

        if (TileOnTopOfAnother()) {
            return true;
        }

        return false;
    }

    private boolean NoAdjacentTiles() {
        int x, y;
        for (int i = 0; i < tileSize; ++i) {
            x = TileLocations[i].x;
            y = TileLocations[i].y;
            if (BoardGame.containsKey(x) && BoardGame.get(x).containsKey(y - 1)) { return false; }
            if (BoardGame.containsKey(x) && BoardGame.get(x).containsKey(y + 1)) { return false; }
            if (BoardGame.containsKey(x - 1) && BoardGame.get(x - 1).containsKey(y)) { return false; }
            if (BoardGame.containsKey(x + 1) && BoardGame.get(x + 1).containsKey(y - 1)) { return false; }
            if (BoardGame.containsKey(x + 1) && BoardGame.get(x + 1).containsKey(y)) { return false; }
            if (BoardGame.containsKey(x + 1) && BoardGame.get(x + 1).containsKey(y + 1)) { return false; }
        }

        return true;
    }

    private boolean TileOnTopOfAnother() {
        // directly over another
        if (BoardGame.get(TileLocations[0].x).get(TileLocations[0].y).getTileNum() ==
                BoardGame.get(TileLocations[1].x).get(TileLocations[1].y).getTileNum() &&
                BoardGame.get(TileLocations[1].x).get(TileLocations[1].y).getTileNum() ==
                        BoardGame.get(TileLocations[2].x).get(TileLocations[2].y).getTileNum()) {
            return true;
        }
        // over different leveled tiles
        if (BoardGame.get(TileLocations[0].x).get(TileLocations[0].y).getLevel() !=
                BoardGame.get(TileLocations[1].x).get(TileLocations[1].y).getLevel() ||
                BoardGame.get(TileLocations[1].x).get(TileLocations[1].y).getLevel() !=
                        BoardGame.get(TileLocations[2].x).get(TileLocations[2].y).getLevel()) {
            return true;
        }
        // Volcanoes must line up
        boolean NoMatch = true;
        for (int i = 0; i < tileSize; ++i) {
            if ((BoardGame.get(TileLocations[i].x).get(TileLocations[i].y).getTerrain() == 'V' &&
                    Tiles[i].getTerrain() == 'V')) {
                NoMatch = false;
                break;
            }
        }

        return NoMatch;
    }

    private void AddTile(){
        for (int i = 0; i < tileSize; ++i) {
            if (BoardGame.containsKey(TileLocations[i].x)) {
                BoardGame.get(TileLocations[i].x).put(TileLocations[i].y, Tiles[i]);
            }
            else {
                BoardGame.put(TileLocations[i].x, new HashMap<Integer, Hex>());
                BoardGame.get(TileLocations[i].x).put(TileLocations[i].y, Tiles[i]);
            }
        }
    }

    public boolean isEmpty() {
        return BoardGame.isEmpty();
    }

}


