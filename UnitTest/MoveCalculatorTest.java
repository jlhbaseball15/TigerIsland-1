import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by scott on 4/11/17.
 */
public class MoveCalculatorTest {
    private GameBoard gameBoard;
    private GameRules gameRules;
    private Deck deck;
    private Tile tile;
    private Point hexPoints[];
    private MoveCalculator moveCalculator;
    private Player testPlayer1;
    private Player testPlayer2;
    private SettlementBuilder settlements;

    @Before
    public void MapObject(){
        gameBoard = new GameBoard();
        gameRules = new GameRules(gameBoard);
        testPlayer1 = new Player("Player1");
        testPlayer2 = new Player("Player2");
        moveCalculator = new MoveCalculator(gameBoard, gameRules);
        deck = new Deck();
        settlements = new SettlementBuilder();
        hexPoints = new Point[3];
    }

    @Test
    public void tilePlacementListWithOneTileOnBoard() {
        tile = deck.getTile();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(0,0);
        gameBoard.addTile(tile, hexPoints);
        tile = deck.getTile();

        ArrayList<Point[]>[] placementList = moveCalculator.getTilePlacementArrayList(tile);
        checkIfEveryPlacementIsValid(placementList);
    }

    //tilePlacementNum should include locations where tile can be placed ontop of tiles
    @Test
    public void tilePlacementListWithTwoTilesOnBoard() {

        //add first tile
        tile = deck.getTile();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(0, 0);
        gameBoard.addTile(tile, hexPoints);
        //add second tile
        tile = deck.getTile();
        hexPoints[0] = new Point(1,0);
        hexPoints[1] = new Point(2, -1);
        hexPoints[2] = new Point(2, 0);
        gameBoard.addTile(tile, hexPoints);
        //get next tile
        tile = deck.getTile();
        hexPoints[0] = new Point(1, -1);
        hexPoints[1] = new Point(1, 0);
        hexPoints[2] = new Point(0, 0);

        ArrayList<Point[]>[] placementList = moveCalculator.getTilePlacementArrayList(tile);
        checkIfEveryPlacementIsValid(placementList);
    }

    @Test
    public void tilePlacementListAfterPlacingStartingTile() {
        gameBoard.addStartingTile();
        tile = deck.getTile();

        ArrayList<Point[]>[] placementList = moveCalculator.getTilePlacementArrayList(tile);
        checkIfEveryPlacementIsValid(placementList);
    }

    @Test
    public void newSettlementListWithOneTileOnBoard() {
        gameBoard.addStartingTile();

        int placementListSize = moveCalculator.possibleNewSettlementPlacementNum(testPlayer1);
        Assert.assertEquals(4, placementListSize);
    }

    @Test
    public void newSettlementListWithThreeTilesOnBoard() {
        //add first tile
        tile = deck.getTile();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(0, 0);
        gameBoard.addTile(tile, hexPoints);
        //add second tile
        tile = deck.getTile();
        hexPoints[0] = new Point(1,0);
        hexPoints[1] = new Point(2, -1);
        hexPoints[2] = new Point(2, 0);
        gameBoard.addTile(tile, hexPoints);
        //get third tile
        tile = deck.getTile();
        hexPoints[0] = new Point(3, 0);
        hexPoints[1] = new Point(3, -1);
        hexPoints[2] = new Point(4, -1);
        gameBoard.addTile(tile, hexPoints);

        int placementListSize = moveCalculator.possibleNewSettlementPlacementNum(testPlayer1);
        Assert.assertEquals(6, placementListSize);
    }

    @Test
    public void newSettlementListWithOneSettlementAlready() {
        //add first tile
        tile = deck.getTile();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(0, 0);
        gameBoard.addTile(tile, hexPoints);
        //add second tile
        tile = deck.getTile();
        hexPoints[0] = new Point(1,0);
        hexPoints[1] = new Point(2, -1);
        hexPoints[2] = new Point(2, 0);
        gameBoard.addTile(tile, hexPoints);
        //add settlement
        gameBoard.addVillagerToBoard(true, new Point(0, -1));
        try {
            settlements.calculateSettlements(gameBoard);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        }

        int placementListSize = moveCalculator.possibleNewSettlementPlacementNum(testPlayer1);
        Assert.assertEquals(3, placementListSize);
    }

    @Test
    public void tigerListWithTwoTilesOnBoard() {
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        //place first tile
        tile = deck.getTile();
        hexPoints = new Point [3];
        hexPoints[0] = new Point (0,0);
        hexPoints[1] = new Point (1,0);
        hexPoints[2] = new Point (0,1);
        gameBoard.addTile(tile, hexPoints);
        //add second tile
        tile = deck.getTile();
        hexPoints[0] = new Point(1, -1);
        hexPoints[1] = new Point(0, -1);
        hexPoints[2] = new Point(-1, 0);
        gameBoard.addTile(tile, hexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        gameBoard.getHexAtPointP(new Point(0, 0)).setLevel(10);

        int tigerListNum = moveCalculator.possibleTigerPlacementNum(true, testPlayer1);
        Assert.assertEquals(1, tigerListNum);

        int num2 = moveCalculator.possibleTigerPlacementNum(false, testPlayer2);
        Assert.assertEquals(0, num2);
    }

    @Test
    public void newSettlementListWhenPlayerHasNoVillagers() {
        //add first tile
        tile = deck.getTile();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(0, 0);
        gameBoard.addTile(tile, hexPoints);

        testPlayer1.villagersBeingPlaced(20);

        int placementListSize = moveCalculator.possibleNewSettlementPlacementNum(testPlayer1);
        Assert.assertEquals(0, placementListSize);
    }

    @Test
    public void totoroListWithValidSpot() {
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        tile = deck.getTile();
        hexPoints = new Point [3];
        hexPoints[0] = new Point (0,0);
        hexPoints[1] = new Point (1,0);
        hexPoints[2] = new Point (0,1);
        gameBoard.addTile(tile, hexPoints);


        tile = deck.getTile();
        hexPoints[0] = new Point(0, -1);
        hexPoints[1] = new Point(1, -1);
        hexPoints[2] = new Point(1, -2);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(2, -1);
        hexPoints[1] = new Point(2, -2);
        hexPoints[2] = new Point(3, -2);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(2, -3);
        hexPoints[1] = new Point(3, -3);
        hexPoints[2] = new Point(3, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(1, -3);
        hexPoints[1] = new Point(1, -4);
        hexPoints[2] = new Point(2, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(0, -3);
        hexPoints[1] = new Point(-1, -3);
        hexPoints[2] = new Point(0, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(-2, -3);
        hexPoints[1] = new Point(-2, -4);
        hexPoints[2] = new Point(-1, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(0, -3);
        hexPoints[1] = new Point(1, -4);
        hexPoints[2] = new Point(0, -4);
        gameBoard.addTile(tile, hexPoints);

        tile = deck.getTile();
        hexPoints[0] = new Point(2, -3);
        hexPoints[1] = new Point(3, -4);
        hexPoints[2] = new Point(2, -4);
        gameBoard.addTile(tile, hexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        gameBoard.getHexAtPointP(new Point(0, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(0, 0));

        gameBoard.getHexAtPointP(new Point(0, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(0, -1));

        gameBoard.getHexAtPointP(new Point(1, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, -1));

        gameBoard.getHexAtPointP(new Point(2, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(2, -1));

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        int num = moveCalculator.possibleTotoroPlacementNum(true, testPlayer1);
        Assert.assertEquals(2, num);

        int num2 = moveCalculator.possibleTotoroPlacementNum(false, testPlayer2);
        Assert.assertEquals(0, num2);
    }

    @Test
    public void expansionListWithThreeTilesPlacedAndTwoDiffSettlements() {
        Settlement settle1 = new Settlement();
        Settlement settle2 = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        tile = new Tile('J', 'J');
        hexPoints = new Point [3];
        hexPoints[0] = new Point (0,-1);
        hexPoints[1] = new Point (1,-1);
        hexPoints[2] = new Point (0,0);
        gameBoard.addTile(tile, hexPoints);


        tile = new Tile('G', 'G');
        hexPoints[0] = new Point(1, 0);
        hexPoints[1] = new Point(2, -1);
        hexPoints[2] = new Point(2, 0);
        gameBoard.addTile(tile, hexPoints);

        tile = new Tile('G', 'G');
        hexPoints[0] = new Point(3,0);
        hexPoints[1] = new Point(3, -1);
        hexPoints[2] = new Point(4, -1);

        gameBoard.getHexAtPointP(new Point(0, -1)).setOccupied(Pieces.P2_VILLAGER, 1);
        settle1.addPointToSettlement(new Point(0, -1));
        settlements.add(settle1);
        gameBoard.getHexAtPointP(new Point(2, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle2.addPointToSettlement(new Point(2, -1));
        settlements.add(settle2);

        gameRules.setSettlements(settlements);

        int num = moveCalculator.possibleSettlementExpandNum(true, testPlayer1);
        Assert.assertEquals(2, num);

        int num2 = moveCalculator.possibleSettlementExpandNum(false, testPlayer2);
        Assert.assertEquals(1, num2);
    }

    public void checkIfEveryPlacementIsValid(ArrayList<Point[]>[] placementList) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < placementList[i].size(); j++) {
                try {
                    gameRules.TryToAddTile(tile, placementList[i].get(j));
                } catch (GameRulesException e) {
                    Assert.assertTrue(false);
                }
                Assert.assertTrue(true);
            }
        }
    }


}
