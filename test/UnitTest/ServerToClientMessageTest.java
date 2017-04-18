package UnitTest;

import game.BuildOptions;
import game.Message;
import game.ServerToClientMessageAdaptor;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

/**
 * Created by dontf on 4/4/2017.
 */
public class ServerToClientMessageTest {

    private static Message mess;
    private static ServerToClientMessageAdaptor StoC;


    @BeforeClass
    public static void settup() {
        StoC = new ServerToClientMessageAdaptor();
    }

    @Test
    public void CorrectTranslationForActive() {
        String input = "MAKE YOUR MOVE IN GAME 123 WITHIN 1.56 SECONDS: MOVE 4 PLACE ROCK+LAKE";
        mess = StoC.translate(input);

        Assert.assertTrue(mess.getGID().equals("123"));
        Assert.assertEquals(1.56, mess.getTime(), 0.01);
        Assert.assertEquals(4, mess.getMove());
        Assert.assertEquals('R', mess.getTile().getHexes()[0].getTerrain());
        Assert.assertEquals('L', mess.getTile().getHexes()[1].getTerrain());
    }

    @Test
    public void CorrectTranslationForExpanstion() {
        String input = "GAME 1248 MOVE 6 PLAYER A54t PLACED LAKE+GRASS AT 1 5 2 1 EXPANDED SETTLEMENT AT 2 3 4 J";
        mess = StoC.translate(input);

        Assert.assertTrue(mess.getGID().equals("1248"));
        Assert.assertTrue(mess.getPID().equals("A54t"));
        Assert.assertEquals(6, mess.getMove());
        Assert.assertEquals('L', mess.getTile().getHexes()[0].getTerrain());
        Assert.assertEquals('G', mess.getTile().getHexes()[1].getTerrain());
        Assert.assertEquals(new Point(1, 2), mess.getTilePoint());
        Assert.assertEquals(0, mess.getOrientation());
        Assert.assertEquals(BuildOptions.EXPAND, mess.getBuild());
        Assert.assertEquals(new Point(2, 4), mess.getBuildPoint());
        Assert.assertEquals('J', mess.getTerrain());
    }

    @Test
    public void CorrectTranslationForForfeit() {
        String input = "GAME 124 MOVE 10 PLAYER 532 FORFEITED: TIMEOUT";
        mess = StoC.translate(input);

        Assert.assertEquals(true, mess.getIsGameOver());
    }

    @Test
    public void CorrectTranslationForFounding() {
        String input = "GAME 1248 MOVE 6 PLAYER WTF6574MYcar PLACED PADDY+GRASS AT 1 5 2 1 FOUNDED SETTLEMENT AT 2 3 4";
        mess = StoC.translate(input);

        Assert.assertTrue(mess.getGID().equals("1248"));
        Assert.assertTrue(mess.getPID().equals("WTF6574MYcar"));
        Assert.assertEquals(6, mess.getMove());
        Assert.assertEquals('P', mess.getTile().getHexes()[0].getTerrain());
        Assert.assertEquals('G', mess.getTile().getHexes()[1].getTerrain());
        Assert.assertEquals(new Point(1, 2), mess.getTilePoint());
        Assert.assertEquals(0, mess.getOrientation());
        Assert.assertEquals(BuildOptions.NEW_SETTLEMENT, mess.getBuild());
        Assert.assertEquals(new Point(2, 4), mess.getBuildPoint());
    }

    @Test
    public void CorrectTranslationForTotoro() {
        String input = "GAME 1248 MOVE 6 PLAYER 864 PLACED LAKE+GRASS AT 1 5 2 1 BUILT TOTORO SANCTUARY AT 2 3 4";
        mess = StoC.translate(input);

        Assert.assertTrue(mess.getGID().equals("1248"));
        Assert.assertTrue(mess.getPID().equals("864"));
        Assert.assertEquals(6, mess.getMove());
        Assert.assertEquals('L', mess.getTile().getHexes()[0].getTerrain());
        Assert.assertEquals('G', mess.getTile().getHexes()[1].getTerrain());
        Assert.assertEquals(new Point(1, 2), mess.getTilePoint());
        Assert.assertEquals(0, mess.getOrientation());
        Assert.assertEquals(BuildOptions.TOTORO_SANCTUARY, mess.getBuild());
        Assert.assertEquals(new Point(2, 4), mess.getBuildPoint());
    }

    @Test
    public void CorrectTranslationForTiger() {
        String input = "GAME 1248 MOVE 6 PLAYER 864 PLACED LAKE+GRASS AT 1 5 2 1 BUILT TIGER PLAYGROUND AT 2 3 4";
        mess = StoC.translate(input);

        Assert.assertTrue(mess.getGID().equals("1248"));
        Assert.assertTrue(mess.getPID().equals("864"));
        Assert.assertEquals(6, mess.getMove());
        Assert.assertEquals('L', mess.getTile().getHexes()[0].getTerrain());
        Assert.assertEquals('G', mess.getTile().getHexes()[1].getTerrain());
        Assert.assertEquals(new Point(1, 2), mess.getTilePoint());
        Assert.assertEquals(0, mess.getOrientation());
        Assert.assertEquals(BuildOptions.TIGER_PLAYGROUND, mess.getBuild());
        Assert.assertEquals(new Point(2, 4), mess.getBuildPoint());
    }
}
