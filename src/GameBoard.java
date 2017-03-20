import java.awt.*;
import java.util.HashMap;

public class GameBoard {

    private HashMap<Integer, HashMap<Integer, Hex>> BoardGame;
    private static Point TileLocations[];

    public GameBoard() {
        int mapCapacity = 144;
        BoardGame = new HashMap<Integer, HashMap<Integer, Hex>>(mapCapacity);
    }

    public HashMap getMap() { return BoardGame; }

    public void AddTile(Tile tile, Point hexPositions[]){
        Hex[] hexes = tile.getHexes();
        TileLocations = hexPositions;

        int tileSize = 3;
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

    public boolean hasTileInMap(int hexXPoint, int hexYPoint) {
        return BoardGame.containsKey(hexXPoint) &&
                BoardGame.get(hexXPoint).containsKey(hexYPoint);
    }

    public boolean hasTileInMap(Point point) {
        return BoardGame.containsKey(point.x) &&
                BoardGame.get(point.x).containsKey(point.y);
    }

    public int retrieveTileNumFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest].x).get(TileLocations[hexOfInterest].y).getTileNum();
    }

    public int retrieveTileNumFromHex(Point hexOfInterest) {
        return BoardGame.get(hexOfInterest.x).get(hexOfInterest.y).getTileNum();
    }

    public int retrieveLevelNumFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest].x).get(TileLocations[hexOfInterest].y).getLevel();
    }

    public int retrieveLevelNumFromHex(Point hexOfInterest) {
        return BoardGame.get(hexOfInterest.x).get(hexOfInterest.y).getLevel();
    }

    public int retrieveTerrainFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest].x).get(TileLocations[hexOfInterest].y).getTerrain();
    }

    public int retrieveTerrainFromHex(Point hexOfInterest) {
        return BoardGame.get(hexOfInterest.x).get(hexOfInterest.y).getTerrain();
    }

    public boolean isEmpty() {
        return BoardGame.isEmpty();
    }

}


