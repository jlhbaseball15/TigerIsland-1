import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertEquals(0, hex.getTileNum());
        Assert.assertEquals('V', hex.getTerrain());
        Assert.assertEquals(Pieces.NONE, hex.getPiece());
    }

    @Test
    public void setOccupiedValidTest() {
        Hex hex = new Hex(type);
        hex.setOccupied(Pieces.P1_VILLAGER);
        Assert.assertEquals(Pieces.P1_VILLAGER, hex.getPiece());
    }

    @Test
    public void setOccupiedInvalidTest() {
        Hex hex = new Hex(type);
        hex.setOccupied(Pieces.NONE);
        Assert.assertEquals(Pieces.NONE, hex.getPiece());
    }
}
