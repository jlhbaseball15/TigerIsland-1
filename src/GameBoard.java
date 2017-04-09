import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {

    private HashMap<Point, Hex> boardGame;
    private static Point tileLocations[];
    private int currentTile;

    public GameBoard() {
        int mapCapacity = 144;
        currentTile = 0;
        boardGame = new HashMap<Point, Hex>(mapCapacity);
    }

    public HashMap getMap() { return boardGame; }

    public void addStartingTile() {
        Hex hex[] = new Hex [5];
        hex[0] = new Hex('V');
        hex[1] = new Hex('J');
        hex[2] = new Hex('L');
        hex[3] = new Hex('G');
        hex[4] = new Hex('R');

        hex[0].setTileNumber(currentTile);
        hex[1].setTileNumber(currentTile);
        hex[2].setTileNumber(currentTile);
        hex[3].setTileNumber(currentTile);
        hex[4].setTileNumber(currentTile);

        boardGame.put(new Point(0, 0), hex[0]); // Volcano Center
        boardGame.put(new Point(0, -1), hex[1]); // Jungle Top Left
        boardGame.put(new Point(1, -1), hex[2]); // Lake Top Right
        boardGame.put(new Point(0, 1), hex[3]); // Grasslands Bottom Right
        boardGame.put(new Point(-1, 1), hex[4]); // Rocky Bottom Left

        ++currentTile;
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
        ++currentTile;
    }

    public void addVillagerToBoard(boolean playerNumber, Point buildLocation) {
        Hex currentHex = getHexAtPointP(buildLocation);
        if (playerNumber) {
            currentHex.setOccupied(Pieces.P1_VILLAGER, currentHex.getLevel());
        }
        else {
            currentHex.setOccupied(Pieces.P2_VILLAGER, currentHex.getLevel());
        }
    }

    public void expandSettlement(boolean playerNumber, ArrayList<Point> expansionList) {
        for (Point p: expansionList) {
            addVillagerToBoard(playerNumber, p);
        }
    }

    public void addTigerToBoard(boolean playerNumber, Point buildLocation) {
        Hex currentHex = getHexAtPointP(buildLocation);
        if (playerNumber) {
            currentHex.setOccupied(Pieces.P1_TIGER, 1);
        }
        else {
            currentHex.setOccupied(Pieces.P2_TIGER, 1);
        }
    }

    public void addTotoroToBoard(boolean playerNumber, Point buildLocation) {
        Hex currentHex = getHexAtPointP(buildLocation);
        if (playerNumber) {
            currentHex.setOccupied(Pieces.P1_TOTORO, 1);
        }
        else {
            currentHex.setOccupied(Pieces.P2_TOTORO, 1);
        }
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

    public Point[] rotate(Point Location, int rotation){
        Point rotatedPlacement[] = new Point[3];
        int x = Location.x;
        int y = Location.y;
        rotatedPlacement[2] = new Point(x,y);
        if(rotation == 0){
            rotatedPlacement[0] = new Point(x,y-1);
            rotatedPlacement[1] = new Point(x+1,y-1);
        }
        else if (rotation == 1){
            rotatedPlacement[0] = new Point(x+1,y-1);
            rotatedPlacement[1] = new Point(x+1,y);
        }
        else if(rotation == 2){
            rotatedPlacement[0] = new Point(x+1,y);
            rotatedPlacement[1] = new Point(x,y+1);
        }
        else if(rotation == 3){
            rotatedPlacement[0] = new Point(x,y+1);
            rotatedPlacement[1] = new Point(x-1,y+1);

        }
        else if(rotation == 4){
            rotatedPlacement[0] = new Point(x-1,y+1);
            rotatedPlacement[1] = new Point(x-1,y);
        }
        else if(rotation == 5){
            rotatedPlacement[0] = new Point(x-1,y);
            rotatedPlacement[1] = new Point(x,y-1);
        }
        return rotatedPlacement;
    }

    public int findOrientation(Point[] locations){
        Point p1 = locations[0];
        Point p2 = locations[1];
        Point p3 = locations[2];

        if (p1.x == p3.x && p1.y == p3.y - 1 && p2.x == p3.x + 1 && p2.y == p3.y - 1) {
            return 0;
        }
        else if (p1.x == p3.x + 1 && p1.y == p3.y - 1 && p2.x == p3.x + 1 && p2.y == p3.y) {
            return 1;
        }
        else if (p1.x == p3.x + 1 && p1.y == p3.y && p2.x == p3.x && p2.y == p3.y + 1) {
            return 2;
        }
        else if (p1.x == p3.x && p1.y == p3.y + 1 && p2.x == p3.x - 1 && p2.y == p3.y + 1) {
            return 3;
        }
        else if (p1.x == p3.x - 1 && p1.y == p3.y + 1 && p2.x == p3.x - 1 && p2.y == p3.y) {
            return 4;
        }
        else {// p1.x == p3.x - 1 && p1.y == p3.y && p2.x == p3.x && p2.y == p3.y - 1
            return 5;
        }
    }

}


