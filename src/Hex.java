
public class Hex {
    private int TileNumber;
    private int LevelNumber;
    private char TerrainType; // J = jungle, R = rocky, L = lake, G = grasslands, V = volcano
    private Pieces Occupied;

    public Hex(char Terrain) {
        TileNumber = 0;
        LevelNumber = 0;
        TerrainType = Terrain;
        Occupied = Pieces.NONE;
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

    public void setOccupied(Pieces occupied) { Occupied = occupied; }

    public void setLevel(int level) { LevelNumber = level; }

    public void setTileNumber(int tile) { TileNumber = tile; }
}
