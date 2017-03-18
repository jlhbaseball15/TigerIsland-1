import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileTest {

    @Test
    public void correctTerrainPlacementOnTile(){
        Tile tile = new Tile('J', 'R');
        char t0 = tile.getHexes()[0].getTerrain();
        char t1 = tile.getHexes()[1].getTerrain();
        char t2 = tile.getHexes()[2].getTerrain();
        Assert.assertEquals('J', t0);
        Assert.assertEquals('R', t1);
        Assert.assertEquals('V', t2);
    }

    @Test
    public void tileHasCorrectNumberOfHexes(){
        Tile tile = new Tile('J','J');
        Hex[] hexes = tile.getHexes();
        Assert.assertEquals(3, hexes.length);
    }

    @Test
    public void testRotateClockwise(){
        Tile tile = new Tile('J','J');
        for(int i = 0; i < 12; i++){
            Assert.assertEquals((i % 6), tile.getOrientation());
            tile.rotateClockwise();
        }
    }

    @Test
    public void testRotateCounterClockwise(){
        Tile tile = new Tile('J','J');
        int[] arr = {0,5,4,3,2,1,0,5,4,3,2,1};
        for(int i : arr){
            Assert.assertEquals(i, tile.getOrientation());
            tile.rotateCounterClockwise();
        }
    }
}
