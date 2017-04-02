/**
 * Created by Colette on 3/23/2017.
 */
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Playertest {

    @Test
    public void newplayer(){
        Player testplayer = new Player("testplayer");
        Assert.assertEquals(0,testplayer.getScore());
        Assert.assertEquals(20,testplayer.getvillagersRemaining());
        Assert.assertEquals(3,testplayer.gettotorosRemaining());
        Assert.assertEquals(2,testplayer.gettigersRemaining());
    }

    @Test
    public void decreasingvillagers(){
        Player testplayer = new Player("testplayer");
        testplayer.villagersBeingPlaced(3);
        Assert.assertEquals(17,testplayer.getvillagersRemaining());
        Assert.assertEquals(9,testplayer.getScore());
    }
    @Test
    public void increasingscore(){
        Player testplayer = new Player("testplayer");
        testplayer.villagersBeingPlaced(3);
        Assert.assertEquals(9,testplayer.getScore());
    }
    @Test
    public void multipledecreasingvillagers(){
        Player testplayer = new Player("testplayer");
        testplayer.villagersBeingPlaced(3);
        testplayer.villagersBeingPlaced(4);
        testplayer.villagersBeingPlaced(1);
        testplayer.villagersBeingPlaced(3);
        Assert.assertEquals(9,testplayer.getvillagersRemaining());
    }
    @Test
    public void decreasingtigers(){
        Player testplayer = new Player("testplayer");
        testplayer.tigerBeingPlaced();
        Assert.assertEquals(1,testplayer.gettigersRemaining());
    }

    @Test
    public void decreasingtotoros(){
        Player testplayer = new Player("testplayer");
        testplayer.totoroBeingPlaced();
        Assert.assertEquals(2,testplayer.gettotorosRemaining());
    }
    @Test
    public void multipleincreasingscore(){
        Player testplayer = new Player("testplayer");
        testplayer.tigerBeingPlaced();
        testplayer.villagersBeingPlaced(1);
        testplayer.villagersBeingPlaced(3);
        testplayer.totoroBeingPlaced();
        testplayer.villagersBeingPlaced(1);
        Assert.assertEquals(286,testplayer.getScore());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void placeMoreVillagersThanAvailable() {
        Player testplayer = new Player("testplayer");
        exception.expect(IndexOutOfBoundsException.class);
        testplayer.villagersBeingPlaced(21);
    }

    @Test
    public void placeMoreTotorosThanAvailable() {
        Player testplayer = new Player("testplayer");
        testplayer.totoroBeingPlaced();
        testplayer.totoroBeingPlaced();
        testplayer.totoroBeingPlaced();
        exception.expect(IndexOutOfBoundsException.class);
        testplayer.totoroBeingPlaced();
    }

    @Test
    public void placeMoreTigersThanAvailable() {
        Player testplayer = new Player("testplayer");
        testplayer.tigerBeingPlaced();
        testplayer.tigerBeingPlaced();
        exception.expect(IndexOutOfBoundsException.class);
        testplayer.tigerBeingPlaced();
    }
}



