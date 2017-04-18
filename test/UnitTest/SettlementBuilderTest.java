package UnitTest;

import game.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dontf on 4/5/2017.
 */
public class SettlementBuilderTest {

    private static GameBoard board;
    private static GameRules rules;
    private static SettlementBuilder settlements;
    private static Deck deck;
    private static Tile tile;
    private static Point points[];

    @BeforeClass
    public static void settupBosrd() throws GameRulesException {
        board = new GameBoard();
        rules = new GameRules(board);
        settlements = new SettlementBuilder();
        points = new Point[3];
        deck = new Deck();

        tile = deck.getTile();
        points[0] = new Point(1, 0);
        points[1] = new Point(0, 1);
        points[2] = new Point(0, 0);
        board.addTile(tile, points);

        tile = deck.getTile();
        points[0] = new Point(1, 1);
        points[1] = new Point(2, 1);
        points[2] = new Point(2, 0);
        board.addTile(tile, points);

        tile = deck.getTile();
        points[0] = new Point(3, 0);
        points[1] = new Point(4, 0);
        points[2] = new Point(3, 1);
        board.addTile(tile, points);

        tile = deck.getTile();
        points[0] = new Point(-1, 2);
        points[1] = new Point(0, 2);
        points[2] = new Point(-1, 3);
        board.addTile(tile, points);

        tile = deck.getTile();
        points[0] = new Point(1, 2);
        points[1] = new Point(0, 3);
        points[2] = new Point(1, 3);
        board.addTile(tile, points);

        tile = deck.getTile();
        points[0] = new Point(2, 2);
        points[1] = new Point(3, 2);
        points[2] = new Point(2, 3);
        board.addTile(tile, points);

        tile = deck.getTile();
        points[0] = new Point(-1, 4);
        points[1] = new Point(-2, 5);
        points[2] = new Point(-1, 5);
        board.addTile(tile, points);

        board.addVillagerToBoard(true, new Point(1, 0));
        board.addVillagerToBoard(true, new Point(0, 1));
        board.addVillagerToBoard(true, new Point(1, 1));
        board.addVillagerToBoard(true, new Point(-1, 2));

        board.addVillagerToBoard(true, new Point(3, 0));
        board.addVillagerToBoard(true, new Point(4, 0));

        board.addVillagerToBoard(true, new Point(0, 3));

        board.addVillagerToBoard(false, new Point(1, 2));
        board.addVillagerToBoard(false, new Point(2, 2));
        board.addVillagerToBoard(false, new Point(3, 2));

        board.addVillagerToBoard(false, new Point(-2, 5));
        board.addVillagerToBoard(false, new Point(-1, 5));

        settlements.calculateSettlements(board);
    }

    @Test
    public void playerOneHas3Settlements() {
        int size = settlements.getPlayer1Settlements().size();

        Assert.assertEquals(3, size);
    }

    @Test
    public void playerTwoHas2Settlements() {
        int size = settlements.getPlayer2Settlements().size();

        Assert.assertEquals(2, size);
    }

    @Test
    public void playerOnesSettlementsAreCorrectSize() {
        ArrayList<Settlement> settle = settlements.getPlayer1Settlements();
        int size[] = new int[3];
        int i = 0;

        for(Settlement s: settle) {
            size[i] = s.getSettlement().size();
            ++i;
        }

        int zero = size[0], one = size[1], two = size[2];
        if (zero > one) {
            if (one > two) {

            }
            else {
                size[1] = two;
                size[2] = one;
            }
        }
        else {
            if (two > one) {
                size[0] = two;
                size[2] = zero;
            }
            else if (two > zero){
                size[0] = one;
                size[1] = two;
                size[2] = zero;
            }
            else {
                size[0] = one;
                size[1] = zero;
            }
        }

        Assert.assertEquals(4, size[0]);
        Assert.assertEquals(2, size[1]);
        Assert.assertEquals(1, size[2]);
    }

    @Test
    public void playerTwosSettlementsAreCorrectSize() {
        ArrayList<Settlement> settle = settlements.getPlayer2Settlements();
        int size[] = new int[2];
        int i = 0;

        for(Settlement s: settle) {
            size[i] = s.getSettlement().size();
            ++i;
        }

        int zero = size[0], one = size[1];

        if (zero > one) {

        }
        else {
            size[0] = one;
            size[1] = zero;
        }

        Assert.assertEquals(3, size[0]);
        Assert.assertEquals(2, size[1]);
    }

}
