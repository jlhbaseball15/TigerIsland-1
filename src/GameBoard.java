import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class GameBoard {

    private HashMap<Point, Hex> BoardGame;
    private Vector<Tile> tilesOnBoard;
    private static Point TileLocations[];
    private Integer currentTile;

    public GameBoard() {
        int mapCapacity = 144;
        currentTile = 0;
        tilesOnBoard = new Vector<Tile>();
        BoardGame = new HashMap<Point, Hex>(mapCapacity);
    }

    public HashMap getMap() { return BoardGame; }

    public Vector getTilesOnBoard() {
        return tilesOnBoard;
    }

    public void AddTile(Tile tile, Point hexPositions[]){
        Hex[] hexes = tile.getHexes();
        TileLocations = hexPositions;

        int tileSize = 3;
        for (int i = 0; i < tileSize; ++i) {
            if (hasTileInMap(TileLocations[i])) {
                hexes[i].setLevel(retrieveLevelNumFromHex(i) + 1);
            }
            hexes[i].setTileNumber(currentTile);
            BoardGame.put(TileLocations[i], hexes[i]);
        }
        tilesOnBoard.add(currentTile, tile);
        ++currentTile;
    }

    public boolean hasTileInMap(int hexXPoint, int hexYPoint) {
        return BoardGame.containsKey(new Point(hexXPoint, hexYPoint));
    }

    public boolean hasTileInMap(Point point) {
        return BoardGame.containsKey(point);
    }

    public int retrieveTileNumFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest]).getTileNum();
    }

    public int retrieveLevelNumFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest]).getLevel();
    }

    public int retrieveTerrainFromHex(int hexOfInterest) {
        return BoardGame.get(TileLocations[hexOfInterest]).getTerrain();
    }

    public boolean isEmpty() {
        return BoardGame.isEmpty();
    }

    public Integer size() { return BoardGame.size(); }

}


