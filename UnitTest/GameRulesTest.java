import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class GameRulesTest {

    private GameRules gameRules;
    private GameBoard gameBoard;
    private Deck deck;
    private Tile tile;
    private Point HexPoints[];
    private Hex[] hex;

    @Before
    public void MapObject(){
        gameBoard = new GameBoard();
        gameRules = new GameRules(gameBoard);
        deck = new Deck();
        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints = new Point [3];
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (1,0);
        HexPoints[2] = new Point (0,1);
    }

    /* ---  tile placement test --- */

    @Test
    public void PlacingHexesNonAdjacentToEachOther() {
        HexPoints[2] = new Point (0, 0);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        }catch (GameRulesException e) {
            char expectedMessage[] = "The Hexes of a tile must be adjacent".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(5, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(-4, -1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        }catch (GameRulesException e) {
            char expectedMessage[] = "The Hexes of a tile must be adjacent".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void PlaceFirstTile() throws GameRulesException {
        try {
            gameRules.TryToAddTile(tile, HexPoints);
        }catch (GameRulesException e) {
            Assert.assertTrue(false);
        };
        Assert.assertTrue(true);
    }

    @Test
    public void PlacingATileThatIsNotFlat() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);
        HexPoints[2] = new Point(1, -1);
       try {
           gameRules.TryToAddTile(tile, HexPoints);
           Assert.assertTrue(false);
       } catch (GameRulesException e) {
           char expectedMessage[] = "The Tile Is Not Sitting On Three Hexes".toCharArray();
           char actualMessage[] = e.getMessage().toCharArray();
           Assert.assertArrayEquals(expectedMessage, actualMessage);
       };

    }

    @Test
    public void PlacingATileOnTopOfAnother() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile Is Directly Over Another Tile".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void PlacingASecondTileNextToFirstTile() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(1, -1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        };
        Assert.assertTrue(true);
    }

    @Test
    public void PlacingTileOnTheSameLevels() throws GameRulesException { // also checks that volcanoes are aligned
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        };
        Assert.assertTrue(true);
    }

    @Test
    public void PlacingTileOnUnevenLevels() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile Is Located Over Different Leveled Tiles".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };

    }

    @Test
    public void BoardNotEmptyAndPlacingTileByItself() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (-1,-2);
        HexPoints[2] = new Point (0,-3);

        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile Is Not Adjacent To An Existing Tile".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void PlacingTileOnEvenLevelsAndVolcanosDontOverLap() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[2] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[0] = new Point (1,-1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile's Volcano Is Not Aligned With A Volcano Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void CannotPlaceOnTotoroOrTiger() throws GameRulesException{
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(1, -1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile.getHexes()[1].setOccupied(Pieces.P1_TIGER, 1);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);

        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Tile's Cannot Be Placed On A Totoro or A Tiger".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void cannotDestroySettlement() throws  GameRulesException{
        ArrayList<Settlement> settlementList = new ArrayList<>();
        Settlement villages= new Settlement();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile.getHexes()[1].setOccupied(Pieces.P1_VILLAGER, 1);
        villages.addPointToSettlement(HexPoints[1]);
        settlementList.add(villages);
        gameRules.setSettlements(settlementList);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);

        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "A settlement cannot be destroyed".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }


    /* ---  build option test  --- */
    /* ---  new_settlement  --- */

    @Test
    public void NewSettlementOnNonExistantHex () {
        Player player = new Player("Bob", true);
        try {
            gameRules.tryToBuildNewSettlement(player, new Point(0, 0));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On An Empty Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementOnVolcano() throws GameRulesException {
        Player player = new Player("Bob", true);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        try {
            gameRules.tryToBuildNewSettlement(player, HexPoints[2]);
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On A Volcano".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementOnOccupiedHex() throws GameRulesException {
        Player player = new Player("Bob", true);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[0]).setOccupied(Pieces.P1_VILLAGER, 1);

        try {
            gameRules.tryToBuildNewSettlement(player, HexPoints[0]);
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On An Occupied Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementWithNoVillagers() throws GameRulesException {
        Player player = new Player("Bob", true);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        player.villagersBeingPlaced(20);

        try {
            gameRules.tryToBuildNewSettlement(player, HexPoints[0]);
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Player Does Not Have Enough Villagers".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementAboveLevelOne() throws GameRulesException {
        Player player = new Player("Bob", true);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[0]).setLevel(3);

        try {
            gameRules.tryToBuildNewSettlement(player, HexPoints[0]);
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build New Settlement Above Level One".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementInValidLocation() throws GameRulesException {
        Player player = new Player("Bob", true);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        try {
            gameRules.tryToBuildNewSettlement(player, HexPoints[0]);
            Assert.assertTrue(true);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }

    /* ---  Expanding  --- */

    @Test
    public void ExpandOntoVolcanoes() {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToExpand(player, 'V');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Expand Onto Volcanoes".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void ExpandWithoutEnoughVillagers() {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameBoard.addTile(tile, HexPoints);

        player.villagersBeingPlaced(18);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToExpand(player, 'G');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Player Does Not Have Enough Villagers".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
            Assert.assertEquals(3, gameRules.getVillagersCount());
        };
    }

    @Test
    public void ExpansionReturnsEmptyList() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToExpand(player, 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "No Hexes With Given Terrain Type To Expand To".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void ExpansionIsValidForOneTile() {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        ArrayList<Point> expansionMap;

        try {
            gameRules.setChosenSettlement(settle);
            expansionMap = gameRules.tryToExpand(player, 'G');
            Assert.assertTrue(expansionMap.contains(new Point(0,0)));
            Assert.assertEquals(1, gameRules.getVillagersCount());
            Assert.assertTrue(true);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }

    @Test
    public void ExpansionIsValidForManyTiles() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        // done adding tiles //
        ArrayList<Point> expansionMap;

        try {
            gameRules.setChosenSettlement(settle);
            expansionMap = gameRules.tryToExpand(player, 'G');

            Assert.assertTrue(expansionMap.contains(new Point(0,0)));
            Assert.assertTrue(expansionMap.contains(new Point(0,-1)));
            Assert.assertTrue(expansionMap.contains(new Point(1,-1)));
            Assert.assertTrue(expansionMap.contains(new Point(2,-1)));
            Assert.assertTrue(expansionMap.contains(new Point(2,-2)));
            Assert.assertTrue(expansionMap.contains(new Point(2,-3)));
            Assert.assertTrue(expansionMap.contains(new Point(1,-3)));
            Assert.assertTrue(expansionMap.contains(new Point(0,-3)));

            Assert.assertFalse(expansionMap.contains(new Point(-2,-3)));

            Assert.assertEquals(10, gameRules.getVillagersCount());
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }

    /* ---  Placeing a Totoro  --- */

    @Test
    public void placingATotoroInSettlementWithSizeLessThanFive() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(0, 0));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Size of settlement is not equal to or greater than 5".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placingTotoroOnAVolcano() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

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

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(0, 1));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Can not build totoro on volcano".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placingTotoroOnASettlementContainingATotoro() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);


        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

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

        gameBoard.getHexAtPointP(new Point(2, -2)).setOccupied(Pieces.P1_TOTORO, 1);
        settle.addPointToSettlement(new Point(2, -2));
        settle.setTotoro();

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(2, -3));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Can not build more than one totoros on one settlement".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placingTotoroWithNoneRemainingForPlayerP1() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);


        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

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

        player.totoroBeingPlaced();
        player.totoroBeingPlaced();
        player.totoroBeingPlaced();

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(2, -2));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Has played all Totoro pieces".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placingTotoroAwayFromSettlement() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);


        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

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

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(2, -3));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Must build Totoro next to the Settlement".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placingTotoroOnOccupiedHex() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);


        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

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

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(2, -1));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On An Occupied Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placingTotoroInAValidPlace() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);


        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

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

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTotoro(player, new Point(2, -2));
            Assert.assertTrue(true);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }



    /* ---  Placing a Tiger  --- */

    @Test
    public void placeTigerBelowLevelThree() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(0, 0));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Level of hex must be three or greater".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placeTigerOnAVolcano() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        gameBoard.getHexAtPointP(new Point(0, 1)).setLevel(3);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(0, 1));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Can not build tiger on volcano".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placeATigerOnASettlementWithATiger() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        gameBoard.getHexAtPointP(new Point(0, 0)).setLevel(3);
        gameBoard.getHexAtPointP(new Point(0, 0)).setOccupied(Pieces.P1_TIGER, 1);
        settle.addPointToSettlement(new Point(0, 0));
        settle.setTiger();

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        gameBoard.getHexAtPointP(new Point(1, -1)).setLevel(3);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(1, -1));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Can not build more than one tiger on one settlement".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placeTigerWithNoTigersRemaining() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        gameBoard.getHexAtPointP(new Point(0, 0)).setLevel(5);

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        player.tigerBeingPlaced();
        player.tigerBeingPlaced();

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(0, 0));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Has played all Tiger pieces".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placeTigerAwayFromTheSettlement() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        gameBoard.getHexAtPointP(new Point(0, -1)).setLevel(5);

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(0, -1));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Must build Tiger next to the Settlement".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void placeTigerOnAnOccupiedHex() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -1);
        HexPoints[1] = new Point(1, -1);
        HexPoints[2] = new Point(1, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        gameBoard.getHexAtPointP(new Point(0, 0)).setLevel(5);
        gameBoard.getHexAtPointP(0, 0).setOccupied(Pieces.P1_VILLAGER, 5);

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(0, 0));
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On An Occupied Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void PlacingATigerInAValidSpot() throws GameRulesException {
        Player player = new Player("Bob", true);
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.addTile(tile, HexPoints);

        gameBoard.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(new Point(1, 0));

        settlements.add(settle);
        gameRules.setSettlements(settlements);

        gameBoard.getHexAtPointP(new Point(0, 0)).setLevel(10);

        try {
            gameRules.setChosenSettlement(settle);
            gameRules.tryToAddTiger(player, new Point(0, 0));
            Assert.assertTrue(true);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }
}
