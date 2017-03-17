
public class Tile {
    private static final int NUM_HEXES = 3;
    private Hex[] hexes;
    private int orientation; // Value: 0-5


    public Tile(char t1, char t2){
        hexes = new Hex[NUM_HEXES];
        hexes[0] = new Hex(t1);
        hexes[1] = new Hex(t2);
        hexes[2] = new Hex('V');
        orientation = 0;
    }

    public Hex[] getHexes(){
        return hexes;
    }

    public int getOrientation(){
        return orientation;
    }

    public void rotateClockwise(){
        orientation = (orientation + 1) % 6;
    }

    public void rotateCounterClockwise(){
        orientation = (orientation - 1) % 6;
    }
}
