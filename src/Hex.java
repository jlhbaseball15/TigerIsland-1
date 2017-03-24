/**
 * Created by dontf on 3/14/2017.
 */
public class Hex {
    private int TileNumber;
    private int LevelNumber;
    private char TerrainType; // J = jungle, R = rocky, L = lake, G = grasslands, V = volcano
    private Pieces Occupied;
    private int numOfPeices;
    private boolean isItNukable;

    public Hex(char Terrain) {
        TileNumber = 0;
        LevelNumber = 1;
        TerrainType = Terrain;
        Occupied = Pieces.NONE;
        isItNukable = true;
        numOfPeices = 0;
        isItNukable = true;

    }

    public int getLevel() {
        return LevelNumber;
    }

    public int getTileNum() {
        return TileNumber;
    }

    public char getTerrain() {
        return TerrainType;
    }

    public Pieces getPiece() { return Occupied; }

    public void setOccupied(Pieces occupied, int numofpeices) {
        Occupied = occupied;
        numOfPeices = numofpeices;
        if(Occupied == Pieces.P1_TOTORO || Occupied == Pieces.P2_TOTORO || Occupied ==Pieces.P1_TIGER || Occupied ==Pieces.P2_TIGER){
            isItNukable = false;
        }
    }
    public boolean canHexBeNuked(){ return isItNukable; }

    public void setLevel(int level) { LevelNumber = level; }

    public void setTileNumber(int tile) { TileNumber = tile; }
}
