import java.awt.*;

public class GameRules {

    private final int tileSize = 3;
    private static int numberOfOverlappedTiles;
    private static Hex hexes[];
    private static Point TileLocations[];
    private GameBoard board;

    public GameRules(GameBoard board) {
        this.board = board;
    }

    public void TryToAddTile(Tile tile, Point TileHexPoints[]) throws GameRulesException{

        numberOfOverlappedTiles = 0;
        hexes = tile.getHexes();
        TileLocations = TileHexPoints;

        if( !board.isEmpty() ) {
            if (TheTileOverlapsAnother()) {
                if (numberOfOverlappedTiles < 3) {
                    throw new GameRulesException("The Tile Is Not Sitting On Three Hexes");
                }
                if (TileDirectlyOnTopOfAnother()) {
                    throw new GameRulesException("The Tile Is Directly Over Another Tile");
                }
                if (TilesOnDifferentLevels()) {
                    throw new GameRulesException("The Tile Is Located Over Different Leveled Tiles");
                }
                if (AddedTilesVolcanoIsNotOnAVolcano()) {
                    throw new GameRulesException("The Tile's Volcano Is Not Aligned With A Volcano Hex");
                }
            }
            else {
                if (NoAdjacentTiles()) {
                    throw new GameRulesException("The Tile Is Not Adjacent To An Existing Tile");
                }
            }
        }

        return ;
    }

    private boolean TheTileOverlapsAnother() {
        int x, y;
        boolean isOverlapped = false;

        for (int i = 0; i < tileSize; ++i) {
            Point p = TileLocations[i];

            if (board.hasTileInMap(p)) {
                isOverlapped = true;
                ++numberOfOverlappedTiles;
            }
        }

        return isOverlapped;
    }

    private boolean TileDirectlyOnTopOfAnother() {
        int Hex0_tileNum = board.retrieveTileNumFromHex(TileLocations[0]);
        int Hex1_tileNum = board.retrieveTileNumFromHex(TileLocations[1]);
        int Hex2_tileNum = board.retrieveTileNumFromHex(TileLocations[2]);

        return Hex0_tileNum == Hex1_tileNum && Hex1_tileNum == Hex2_tileNum;

    }

    private boolean TilesOnDifferentLevels() {
        int Hex0_LevelNum = board.retrieveLevelNumFromHex(TileLocations[0]);
        int Hex1_LevelNum = board.retrieveLevelNumFromHex(TileLocations[1]);
        int Hex2_LevelNum = board.retrieveLevelNumFromHex(TileLocations[2]);

        return Hex0_LevelNum != Hex1_LevelNum || Hex1_LevelNum != Hex2_LevelNum;

    }

    private boolean AddedTilesVolcanoIsNotOnAVolcano() {
        boolean NoMatch = true;

        for (int i = 0; i < tileSize; ++i) {
            if ((board.retrieveTerrainFromHex(TileLocations[i])) == 'V' && hexes[i].getTerrain() == 'V') {
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

            if (HasANeighbor(x, y)) return false;
        }

        return true;
    }

    private boolean HasANeighbor(int x, int y) {
        if (board.hasTileInMap(x, y - 1)) {
            return true;
        }
        if (board.hasTileInMap(x, y + 1)) {
            return true;
        }
        if (board.hasTileInMap(x - 1, y)) {
            return true;
        }
        if (board.hasTileInMap(x + 1, y - 1)) {
            return true;
        }
        if (board.hasTileInMap(x + 1, y)) {
            return true;
        }
        if (board.hasTileInMap(x + 1, y + 1)) {
            return true;
        }
        return false;
    }

}
