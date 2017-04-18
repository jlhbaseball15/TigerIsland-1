package UnitTest;

import game.BuildOptions;
import game.ClientToServerMessageAdaptor;
import game.Message;
import game.Tile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

/**
 * Created by dontf on 4/4/2017.
 */
public class ClientToServerMessageTest {

    private static ClientToServerMessageAdaptor CtoS;

    @BeforeClass
    public static void settup() {
        CtoS = new ClientToServerMessageAdaptor();
    }

    @Test
    public void buildNewSettlementMessage() {
        String actualOutput, expectedOutput;
        Message m = new Message();

        m.setGID("124");
        m.setMove(6);

        m.setTile(new Tile('L', 'G'));
        m.setTilePoint(new Point(0, 0));
        m.setOrientation(3);

        m.setBuild(BuildOptions.NEW_SETTLEMENT);
        m.setBuildPoint(new Point(4, 5));

        expectedOutput = "GAME 124 MOVE 6 PLACE LAKE+GRASS AT 0 0 0 4 FOUND SETTLEMENT AT 4 -9 5";
        actualOutput = CtoS.translate(m);

        Assert.assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    public void buildExpansionMessage() {
        String actualOutput, expectedOutput;
        Message m = new Message();

        m.setGID("124");
        m.setMove(6);

        m.setTile(new Tile('G', 'G'));
        m.setTilePoint(new Point(1, 0));
        m.setOrientation(3);

        m.setBuild(BuildOptions.EXPAND);
        m.setBuildPoint(new Point(3, 2));
        m.setTerrain('L');

        expectedOutput = "GAME 124 MOVE 6 PLACE GRASS+GRASS AT 1 -1 0 4 EXPAND SETTLEMENT AT 3 -5 2 LAKE";
        actualOutput = CtoS.translate(m);

        Assert.assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    public void buildTotoroMessage() {
        String actualOutput, expectedOutput;
        Message m = new Message();

        m.setGID("124");
        m.setMove(6);

        m.setTile(new Tile('L', 'G'));
        m.setTilePoint(new Point(0, 0));
        m.setOrientation(3);

        m.setBuild(BuildOptions.TOTORO_SANCTUARY);
        m.setBuildPoint(new Point(4, 5));

        expectedOutput = "GAME 124 MOVE 6 PLACE LAKE+GRASS AT 0 0 0 4 BUILD TOTORO SANCTUARY AT 4 -9 5";
        actualOutput = CtoS.translate(m);

        Assert.assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    public void buildTigerMessage() {
        String actualOutput, expectedOutput;
        Message m = new Message();

        m.setGID("126");
        m.setMove(3);

        m.setTile(new Tile('L', 'G'));
        m.setTilePoint(new Point(0, 0));
        m.setOrientation(3);

        m.setBuild(BuildOptions.TIGER_PLAYGROUND);
        m.setBuildPoint(new Point(4, 5));

        expectedOutput = "GAME 126 MOVE 3 PLACE LAKE+GRASS AT 0 0 0 4 BUILD TIGER PLAYGROUND AT 4 -9 5";
        actualOutput = CtoS.translate(m);

        Assert.assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    public void buildNOOPMessage() {
        String actualOutput, expectedOutput;
        Message m = new Message();

        m.setGID("124");
        m.setMove(6);

        m.setTile(new Tile('L', 'G'));
        m.setTilePoint(new Point(0, 0));
        m.setOrientation(3);

        m.setBuild(BuildOptions.NOOP);
        m.setBuildPoint(new Point(4, 5));

        expectedOutput = "GAME 124 MOVE 6 PLACE LAKE+GRASS AT 0 0 0 4 UNABLE TO BUILD";
        actualOutput = CtoS.translate(m);

        Assert.assertTrue(actualOutput.equals(expectedOutput));
    }

}
