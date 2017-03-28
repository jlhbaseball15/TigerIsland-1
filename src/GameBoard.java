import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class GameBoard {

    private HashMap<Point, Hex> boardGame;
    private Vector<Tile> tilesOnBoard;
    private static Point tileLocations[];
    private int currentTile;

    public GameBoard() {
        int mapCapacity = 144;
        currentTile = 0;
        tilesOnBoard = new Vector<Tile>();
        boardGame = new HashMap<Point, Hex>(mapCapacity);
    }

    public HashMap getMap() { return boardGame; }

    public Vector getTilesOnBoard() {
        return tilesOnBoard;
    }

    public void addTile(Tile tile, Point hexPositions[]){
        Hex[] hexes = tile.getHexes();
        tileLocations = hexPositions;

        int tileSize = 3;
        for (int i = 0; i < tileSize; ++i) {
            if (hasTileInMap(tileLocations[i])) {
                hexes[i].setLevel(retrieveLevelNumFromHex(tileLocations[i]) + 1);
                hexes[i].setOccupied(Pieces.NONE, 0);
            }
            hexes[i].setTileNumber(currentTile);
            boardGame.put(tileLocations[i], hexes[i]);
        }
        tilesOnBoard.add(currentTile, tile);
        ++currentTile;
    }

    public void building(BuildOptions build) {

    }

    public boolean hasTileInMap(int hexXPoint, int hexYPoint) {
        return boardGame.containsKey(new Point(hexXPoint, hexYPoint));
    }

    public boolean hasTileInMap(Point point) {
        return boardGame.containsKey(point);
    }

    public Hex getHexAtPointP(Point P) {
        return boardGame.get(P);
    }

    public Hex getHexAtPointP(int x, int y) {
        return boardGame.get(new Point(x, y));
    }

    public int retrieveTileNumFromHex(Point hexOfInterest) {
        return boardGame.get(hexOfInterest).getTileNum();
    }

    public int retrieveLevelNumFromHex(Point hexOfInterest) {
        return boardGame.get(hexOfInterest).getLevel();
    }

    public char retrieveTerrainFromHex(Point hexOfInterest) {
        return boardGame.get(hexOfInterest).getTerrain();
    }

    public boolean isEmpty() {
        return boardGame.isEmpty();
    }

    public int size() { return boardGame.size(); }

}


