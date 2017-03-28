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
        gameBoard.AddTile(tile, HexPoints);
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
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(1, -1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

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


    /* ---  build option test --- */

    @Test
    public void NewSettlementOnNonExistantHex () {
        Player player = new Player("Bob");
        try {
            gameRules.tryToBuild(player, BuildOptions.NEW_SETTLEMENT, new Point(0, 0), 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On An Empty Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementOnVolcano() throws GameRulesException {
        Player player = new Player("Bob");
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        try {
            gameRules.tryToBuild(player, BuildOptions.NEW_SETTLEMENT, HexPoints[2], 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On A Volcano".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementOnOccupiedHex() throws GameRulesException {
        Player player = new Player("Bob");
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[0]).setOccupied(Pieces.P1_VILLAGER, 1);

        try {
            gameRules.tryToBuild(player, BuildOptions.NEW_SETTLEMENT, HexPoints[0], 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build On An Occupied Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementWithNoVillagers() throws GameRulesException {
        Player player = new Player("Bob");
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        player.villagersBeingPlaced(20);

        try {
            gameRules.tryToBuild(player, BuildOptions.NEW_SETTLEMENT, HexPoints[0], 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Player Does Not Have Enough Villagers".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementAboveLevelOne() throws GameRulesException {
        Player player = new Player("Bob");
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[0]).setLevel(3);

        try {
            gameRules.tryToBuild(player, BuildOptions.NEW_SETTLEMENT, HexPoints[0], 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Build New Settlement Above Level One".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void NewSettlementInValidLocation() throws GameRulesException {
        Player player = new Player("Bob");
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        try {
            gameRules.tryToBuild(player, BuildOptions.NEW_SETTLEMENT, HexPoints[0], 'L');
            Assert.assertTrue(true);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }

    @Test
    public void ExpandOntoAnEmptyHex() {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.tryToBuild(player, BuildOptions.EXPAND, new Point(5, 5), 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Expand From An Empty Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void ExpandIntoNonSettlement() {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.AddTile(tile, HexPoints);

        try {
            gameRules.tryToBuild(player, BuildOptions.EXPAND, HexPoints[1], 'G');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Expand From Non-Settlement".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void ExpandOntoVolcanoes() {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        try {
            gameRules.tryToBuild(player, BuildOptions.EXPAND, HexPoints[1], 'V');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Cannot Expand Onto Volcanoes".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void ExpandWithoutEnoughVillagers() {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameBoard.AddTile(tile, HexPoints);

        player.villagersBeingPlaced(18);

        try {
            gameRules.tryToBuild(player, BuildOptions.EXPAND, new Point(1, 0), 'G');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "Player Does Not Have Enough Villagers".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
            Assert.assertEquals(3, gameRules.getVillagersCount());
        };
    }

    @Test
    public void ExpansionReturnsEmptyList() {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point (1,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameBoard.AddTile(tile, HexPoints);

        try {
            gameRules.tryToBuild(player, BuildOptions.EXPAND, new Point(1, 0), 'L');
            Assert.assertTrue(false);
        } catch(GameRulesException e) {
            char expectedMessage[] = "No Hexes With Given Terrain Type To Expand To".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void ExpansionIsValidForOneTile() {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();
        gameBoard.AddTile(tile, HexPoints);

        gameBoard.getHexAtPointP(HexPoints[1]).setOccupied(Pieces.P1_VILLAGER, 1);
        settle.addPointToSettlement(HexPoints[1]);
        settlements.add(settle);
        gameRules.setSettlements(settlements);

        ArrayList<Point> expansionMap;

        try {
            expansionMap = gameRules.tryToBuild(player, BuildOptions.EXPAND, HexPoints[1], 'G');
            Assert.assertTrue(expansionMap.contains(new Point(0,0)));
            Assert.assertEquals(1, gameRules.getVillagersCount());
            Assert.assertTrue(true);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        };
    }

    @Test
    public void ExpansionIsValidForManyTiles() throws GameRulesException {
        Player player = new Player("Bob");
        Settlement settle = new Settlement();
        ArrayList<Settlement> settlements = new ArrayList<>();

        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

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
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -1);
        HexPoints[1] = new Point(2, -2);
        HexPoints[2] = new Point(3, -2);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -3);
        HexPoints[2] = new Point(3, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(1, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(-1, -3);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(-2, -3);
        HexPoints[1] = new Point(-2, -4);
        HexPoints[2] = new Point(-1, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(0, -3);
        HexPoints[1] = new Point(1, -4);
        HexPoints[2] = new Point(0, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints[0] = new Point(2, -3);
        HexPoints[1] = new Point(3, -4);
        HexPoints[2] = new Point(2, -4);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        // done adding tiles //
        ArrayList<Point> expansionMap;

        try {
            expansionMap = gameRules.tryToBuild(player, BuildOptions.EXPAND, new Point(1, 0), 'G');

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
}
