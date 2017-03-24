/**
 * Created by Colette on 3/23/2017.
 */
import org.junit.Assert;
import org.junit.Test;

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
        testplayer.MeepleBeingPlaced(3);
        Assert.assertEquals(17,testplayer.getvillagersRemaining());
        Assert.assertEquals(9,testplayer.getScore());
    }
    @Test
    public void increasingscore(){
        Player testplayer = new Player("testplayer");
        testplayer.MeepleBeingPlaced(3);
        Assert.assertEquals(9,testplayer.getScore());
    }
    @Test
    public void multipledecreasingvillagers(){
        Player testplayer = new Player("testplayer");
        testplayer.MeepleBeingPlaced(3);
        testplayer.MeepleBeingPlaced(4);
        testplayer.MeepleBeingPlaced(1);
        testplayer.MeepleBeingPlaced(3);
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
        testplayer.MeepleBeingPlaced(1);
        testplayer.MeepleBeingPlaced(3);
        testplayer.totoroBeingPlaced();
        testplayer.MeepleBeingPlaced(1);
        Assert.assertEquals(286,testplayer.getScore());
    }
}
