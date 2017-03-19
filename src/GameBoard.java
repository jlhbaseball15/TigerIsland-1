import java.awt.*;
import java.util.HashMap;

public class GameBoard {
    private final int mapCapacity = 144;
    private final int tileSize = 3;

    private HashMap<Integer, HashMap<Integer, Hex>> BoardGame;
    private static int numberOfOverlappedTiles;
    private static Hex hexes[];
    private static Point TileLocations[];

    public GameBoard() {
        BoardGame = new HashMap<Integer, HashMap<Integer, Hex>>(mapCapacity);
    }

    public HashMap getMap() { return BoardGame; }

    public int TryToAddTile(Tile tile, Point TileHexPoints[]) {

        numberOfOverlappedTiles = 0;
        hexes = tile.getHexes();
        TileLocations = TileHexPoints;

        if( !isEmpty() ) {
            if (TheTileOverlapsAnother()) {
                if (numberOfOverlappedTiles < 3) {
                    return -1;
                }
                if (TileDirectlyOnTopOfAnother()) {
                    return -1;
                }
                if (TilesOnDifferentLevels()) {
                    return -2;
                }
                if (AddedTilesVolcanoIsNotOnAVolcano()) {
                    return -3;
                }
            }
            else {
                if (NoAdjacentTiles()) {
                    return -4;
                }
            }
        }

        AddTile();

        return 0;
    }

    private boolean TheTileOverlapsAnother() {
        int x, y;
        boolean isOverlapped = false;

        for (int i = 0; i < tileSize; ++i) {
            x = TileLocations[i].x;
            y = TileLocations[i].y;

            if (hasTileBelow(x, y)) {
                isOverlapped = true;
                ++numberOfOverlappedTiles;
            }
        }

        return isOverlapped;
    }

    private boolean TileDirectlyOnTopOfAnother() {
        int Hex0_tileNum = retrieveTileNumFromHex(0);
        int Hex1_tileNum = retrieveTileNumFromHex(1);
        int Hex2_tileNum = retrieveTileNumFromHex(2);

        if (Hex0_tileNum == Hex1_tileNum && Hex1_tileNum == Hex2_tileNum) {
            return true;
        }

        return false;
    }

    private boolean TilesOnDifferentLevels() {
        int Hex0_LevelNum = retrieveLevelNumFromHex(0);
        int Hex1_LevelNum = retrieveLevelNumFromHex(1);
        int Hex2_LevelNum = retrieveLevelNumFromHex(2);

        if (Hex0_LevelNum != Hex1_LevelNum || Hex1_LevelNum != Hex2_LevelNum) {
            return true;
        }

        return false;
    }

    private boolean AddedTilesVolcanoIsNotOnAVolcano() {
        boolean NoMatch = true;

        for (int i = 0; i < tileSize; ++i) {
            if ((retrieveTerrainFromHex(i)) == 'V' && hexes[i].getTerrain() == 'V') {
                NoMatch = false;
                break;
            }
        }

        return NoMatch;
    }

    private boolean NoAdjacentTiles() {
        int x, y;
        for (int i = 0; i < tileSize; ++i) {
            x = TileLocations[i].x;
            y = TileLocations[i].y;

            if (hasTileBelow(x, y - 1)) { return false; }
            if (hasTileBelow(x, y + 1)) { return false; }
            if (hasTileBelow(x - 1, y)) { return false; }
            if (hasTileBelow(x + 1, y - 1)) { return false; }
            if (hasTileBelow(x + 1, y)) { return false; }
            if (hasTileBelow(x + 1, y + 1)) { return false; }
        }

        return true;
    }

     /*
       Below Functions get values needed from the map container,
       This is specific to the hashmap, and limits need changes
       to the class if the storage container is changed
     */

    private void AddTile(){
        for (int i = 0; i < tileSize; ++i) {
            // if X coordinate is in map, add Y to internal hashmap
            if (BoardGame.containsKey(TileLocations[i].x)) {
                BoardGame.get(TileLocations[i].x).put(TileLocations[i].y, hexes[i]);
            }
            // if X coordinate is not in map, add X with a new hashmap as value
            // add y to new has map
            else {
                BoardGame.put(TileLocations[i].x, new HashMap<Integer, Hex>());
                BoardGame.get(TileLocations[i].x).put(TileLocations[i].y, hexes[i]);
            }
        }
    }

    private boolean hasTileBelow(int hexXPoint, int hexYPoint) {
        return BoardGame.containsKey(hexXPoint) &&
                BoardGame.get(hexXPoint).containsKey(hexYPoint);
    }

    private int retrieveTileNumFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest].x).get(TileLocations[hexOfInterest].y).getTileNum();
    }

    private int retrieveLevelNumFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest].x).get(TileLocations[hexOfInterest].y).getLevel();
    }

    private int retrieveTerrainFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest].x).get(TileLocations[hexOfInterest].y).getTerrain();
    }

    public boolean isEmpty() {
        return BoardGame.isEmpty();
    }

}


