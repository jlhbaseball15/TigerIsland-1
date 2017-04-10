import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameBoardTest {

    private GameBoard gameBoard;
    private Deck deck;
    private Tile tile;
    private Point hexPoints[];

    @Before
    public void MapObject(){
        gameBoard = new GameBoard();
        deck = new Deck();
        hexPoints = new Point[3];
        hexPoints[0] = new Point(0,0);
        hexPoints[1] = new Point(1,0);
        hexPoints[2] = new Point(1,1);
    }

    @Test
    public void MapIsEmpty() {
        Assert.assertTrue(gameBoard.isEmpty());
    }

    @Test
    public void MapIsNotEmpty() {
        tile = deck.getTile();
        gameBoard.addTile(tile, hexPoints);
        Assert.assertFalse(gameBoard.isEmpty());
    }

    @Test
    public void GettingMap() {
        tile = deck.getTile();
        gameBoard.addTile(tile, hexPoints);
       HashMap<Point, Hex> currentBoard = gameBoard.getMap();
        Assert.assertEquals('V', currentBoard.get(new Point(1,1)).getTerrain());
    }

    @Test
    public void addingAStartTileToBoard() {
        gameBoard.addStartingTile();

        Assert.assertEquals('V', gameBoard.getHexAtPointP(new Point(0, 0)).getTerrain());
        Assert.assertEquals('J', gameBoard.getHexAtPointP(new Point(0, -1)).getTerrain());
        Assert.assertEquals('L', gameBoard.getHexAtPointP(new Point(1, -1)).getTerrain());
        Assert.assertEquals('G', gameBoard.getHexAtPointP(new Point(0, 1)).getTerrain());
        Assert.assertEquals('R', gameBoard.getHexAtPointP(new Point(-1, 1)).getTerrain());
    }

    @Test
    public void NoHashConflicts() {
        Point hexLoc[] = new Point[3];
        Point temp[] = new Point[3];

        int k = 0;
        for (int i = -3; i < 6; ++i) {
            for (int j = -8; j < 8; ++j) {
                if (k != 0 && (k % 3) == 0) {
                    gameBoard.addTile(deck.getTile(), hexLoc);
                    temp = hexLoc;
                }
                hexLoc[k % 3] = new Point(i, j);
                ++k;
            }
        }
        gameBoard.addTile(deck.getTile(), hexLoc);

        Integer numHexes = 144;
        boolean isCorrect = false;
        if (numHexes.equals(gameBoard.size())) {
            isCorrect = true;
        }
        Assert.assertTrue(isCorrect);
    }

    @Test
    public void ResetOccupationOnNuke() {
        tile = deck.getTile();
        tile.getHexes()[1].setOccupied(Pieces.P1_VILLAGER, 1);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(0,0);
        hexPoints[1] = new Point(1,0);
        hexPoints[2] = new Point(1,1);
        gameBoard.addTile(tile, hexPoints);

        Assert.assertEquals(Pieces.NONE, gameBoard.getHexAtPointP(hexPoints[1]).getPiece());
    }

    @Test
    public void buildingNewSettlement() {
        Player p1 = new Player("Bob");
        tile = deck.getTile();

        hexPoints[0] = new Point(0, 0);
        hexPoints[1] = new Point(1, 0);
        hexPoints[2] = new Point(0, 1);
        gameBoard.addTile(tile, hexPoints);

        gameBoard.addVillagerToBoard(true, new Point(1, 0));
        Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(1, 0)).getPiece());
        Assert.assertEquals(1, gameBoard.getHexAtPointP(new Point(1, 0)).getNumOfPeices());
    }

    @Test
    public void expandingASettlement() {
        GameRules rules = new GameRules(gameBoard);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        Hex hex[] = new Hex[3];

        Player p1 = new Player("Bob");
        tile = deck.getTile();

        hexPoints[0] = new Point(0, 0);
        hexPoints[1] = new Point(1, 0);
        hexPoints[2] = new Point(0, 1);
        gameBoard.addTile(tile, hexPoints);

        gameBoard.getHexAtPointP(hexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(hexPoints[1]);
        settlements.add(settle);
        rules.setSettlements(settlements);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(1, -2);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(2, -1);
        hexPoints[1] = new Point(2, -2);
        hexPoints[2] = new Point(3, -2);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(2, -3);
        hexPoints[1] = new Point(3, -3);
        hexPoints[2] = new Point(3, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(1, -3);
        hexPoints[1] = new Point(1, -4);
        hexPoints[2] = new Point(2, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(0, -3);
        hexPoints[1] = new Point(-1, -3);
        hexPoints[2] = new Point(0, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(-2, -3);
        hexPoints[1] = new Point(-2, -4);
        hexPoints[2] = new Point(-1, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(0, -3);
        hexPoints[1] = new Point(1, -4);
        hexPoints[2] = new Point(0, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hexPoints[0] = new Point(2, -3);
        hexPoints[1] = new Point(3, -4);
        hexPoints[2] = new Point(2, -4);
        gameBoard.addTile(tile, hexPoints);

        ArrayList<Point> expansionMap;

        try {
            expansionMap = rules.tryToExpand(p1, 'G', new Point(1, 0));
            gameBoard.expandSettlement(true, expansionMap);
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(0,0)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(0,-1)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(1,-1)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(2,-1)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(2,-2)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(2,-3)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(1,-3)).getPiece());
            Assert.assertEquals(Pieces.P1_VILLAGER, gameBoard.getHexAtPointP(new Point(0,-3)).getPiece());

            Assert.assertEquals(2, gameBoard.getHexAtPointP(new Point(2,-3)).getNumOfPeices());
            Assert.assertEquals(1, gameBoard.getHexAtPointP(new Point(0,0)).getNumOfPeices());

        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }

    @Test
    public void BuildingATotoroSanctuary() {
        Player p2 = new Player("Bob");
        Tile tile = deck.getTile();
        gameBoard.addTile(tile, hexPoints);

        gameBoard.addTotoroToBoard(false, new Point(0, 0));

        Assert.assertEquals(Pieces.P2_TOTORO, gameBoard.getHexAtPointP(0, 0).getPiece());
    }

    @Test
    public void BuildingATigerSanctuary() {
        Player p1 = new Player("Bob");
        Tile tile = deck.getTile();
        gameBoard.addTile(tile, hexPoints);

        gameBoard.addTigerToBoard(true, new Point(0, 0));

        Assert.assertEquals(Pieces.P1_TIGER, gameBoard.getHexAtPointP(0, 0).getPiece());
    }
}
