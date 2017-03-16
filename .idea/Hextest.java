import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dontf on 3/14/2017.
 */
public class Hextest {

    private int level;
    private int tile;
    private char type;

    @Before
    public void setup() {
        level = 1;
        tile = 5;
        type = 'V';
    }

    @Test
    public void HexObjectTest() {
        Hex hex = new Hex(type);
    }

    @Test
    public void getTileTest() {
        Hex hex = new Hex(type);
        Assert.assertEquals(0, hex.getLevel());
        Assert.assertEquals(0, hex.getTile());
        Assert.assertEquals('V', hex.getTerrain());
        Assert.assertEquals(Pieces.NO_ONE, hex.getPiece());
    }

    @Test
    public void setOccupiedValidTest() {
        Hex hex = new Hex(type);
        hex.setOccupied(Pieces.PLAYER1MEEPLE);
        Assert.assertEquals(Pieces.PLAYER1MEEPLE, hex.getPiece());
    }

    @Test
    public void setOccupiedInvalidTest() {
        Hex hex = new Hex(type);
        hex.setOccupied(Pieces.NO_ONE);
        Assert.assertEquals(Pieces.NO_ONE, hex.getPiece());
    }
}
