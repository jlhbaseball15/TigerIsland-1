import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;

public class CucumberGameBoardTest {
    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private SettlementBuilder settlements;

    @Given("^an empty board$")
    public void anemptyboard() {
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(0,1);
    }

    @When("^a tile is placed$")
    public void atileisplaced() throws GameRulesException {
        try {
            rules.TryToAddTile(tile, tileLocations);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        }
    }

    @Then("^the tile is added on the board$")
    public void thetileisaddedontheboard() {
        board.addTile(tile, tileLocations);
        Assert.assertFalse(board.isEmpty());
    }



    @Given("^The board is not empty$")
    public void theBoardIsNotEmpty() throws GameRulesException {
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0, 0);
        tileLocations[1] = new Point(1, 0);
        tileLocations[2] = new Point(0, 1);
        try {
            rules.TryToAddTile(tile, tileLocations);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, tileLocations);
    }

    @When("^The tile is placed$")
    public void theTileIsPlaced() throws GameRulesException {
        tile = deck.getTile();
        tileLocations[0] = new Point(0, -2);
        tileLocations[1] = new Point(1, -2);
        tileLocations[2] = new Point(0, -1);
        try {
            rules.TryToAddTile(tile, tileLocations);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        }
    }

    @Then("^the tile is added to the map$")
    public void theTileIsAddedToTheMap() {
        board.addTile(tile, tileLocations);
    }


    //given from above

    @When("^The tile is placed away from other tiles$")
    public void theTileIsPlacedAwayFromOtherTiles() throws GameRulesException {
        tile = deck.getTile();
        tileLocations[0] = new Point(3, 0);
        tileLocations[1] = new Point(4, 0);
        tileLocations[2] = new Point(3, 1);
        try {
            rules.TryToAddTile(tile, tileLocations);
        }catch (GameRulesException e) {
            Assert.assertEquals("The Tile Is Not Adjacent To An Existing Tile", e.getMessage());
        };
    }

    @Then("^the tile is not added to the map$")
    public void theTileIsNotAddedToTheMap() throws GameRulesException{
        try {
            rules.TryToAddTile(tile, tileLocations);
        }catch (GameRulesException e) {
            Assert.assertEquals("The Tile Is Not Adjacent To An Existing Tile", e.getMessage());
        };
    }

    //given same as above
    @When("^the tile is plaed in a nonempty spot$")
    public void theTileIsPlacedinaNonemptyspot() throws GameRulesException{
        tile = deck.getTile();
        tileLocations[0] = new Point(0, 0);
        tileLocations[1] = new Point(-1, 1);
        tileLocations[2] = new Point(0, 1);
        try {
            rules.TryToAddTile(tile, tileLocations);
        }catch (GameRulesException e) {
            Assert.assertEquals("The Tile Is Not Sitting On Three Hexes", e.getMessage());
        };
    }
    @Then("^the tile is not added to the map due to the Tile overhanging$")
    public void theTileIsNotAddedduetonotbeinganemptyspot() throws GameRulesException{
        try {
            rules.TryToAddTile(tile, tileLocations);
        }catch (GameRulesException e) {
            Assert.assertEquals("The Tile Is Not Sitting On Three Hexes", e.getMessage());
        };
    }

    @Given("^The tile is placed on two or three tiles at level two or higher$")
    public void theTileIsPlacedOnTwoOrThreeTilesAtLevelTwoOrHigher() throws Throwable {
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];

        p[0] = new Point(0, 1);
        p[1] = new Point(-1, 1);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);

        p[0] = new Point(1, 0);
        p[1] = new Point(2, 0);
        p[2] = new Point(1, 1);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
    }

    @And("^The two non-volcanic hexes are occupies by the same size two settlement$")
    public void theTwoNonVolcanicHexesAreOccupiesByTheSameSizeTwoSettlement() throws Throwable {
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 1));
        board.addVillagerToBoard(true, new Point(1, 0));

        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(0, 1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(1, 0).getPiece());

        settlements.calculateSettlements(board);

        Assert.assertEquals(1, settlements.getPlayer1Settlements().size());
        Assert.assertEquals(2, settlements.getPlayer1Settlements().get(0).getSettlement().size());
    }

    @When("^The tile is placed in that location$")
    public void theTileIsPlacedInThatLocation() throws Throwable {
        tile = deck.getTile();
        Point p[] = new Point [3];
        p[0] = new Point(0, 1);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 0);

        rules.setSettlements(settlements.getPlayer1Settlements());
        rules.setChosenSettlement(settlements.getPlayer1Settlements().get(0));

        try {
            rules.TryToAddTile(tile, p);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            System.out.println(e.getMessage());
            char [] expected = "A settlement cannot be destroyed".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);
        }
    }

    @Then("^the placement is rejected$")
    public void thePlacementIsRejected() throws Throwable {
        Assert.assertEquals(1, board.getHexAtPointP(0, 0).getLevel());
    }

    @And("^At least one of the covered hexes is occupied, but will not be destroyed$")
    public void atLeastOneOfTheCoveredHexesIsOccupiedButWillNotBeDestroyed() throws Throwable {
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 1));
        board.addVillagerToBoard(true, new Point(-1, 1));

        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(0, 1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(-1, 1).getPiece());

        settlements.calculateSettlements(board);

        Assert.assertEquals(1, settlements.getPlayer1Settlements().size());
        Assert.assertEquals(2, settlements.getPlayer1Settlements().get(0).getSettlement().size());
    }

    @When("^The tile is placed on that spot$")
    public void theTileIsPlacedOnThatSpot() throws Throwable {
        tile = deck.getTile();
        Point p[] = new Point [3];
        p[0] = new Point(0, 1);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 0);

        rules.setSettlements(settlements.getPlayer1Settlements());
        rules.setChosenSettlement(settlements.getPlayer1Settlements().get(0));

        try {
            rules.TryToAddTile(tile, p);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        }

    }

    @Then("^The tile is added to the board$")
    public void theTileIsAddedToTheBoard() throws Throwable {
        tile = deck.getTile();

        Point p[] = new Point[3];
        p[0] = new Point(0, 1);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 0);

        try {
            rules.TryToAddTile(tile, p);
            board.addTile(tile, p);
            Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(0, 1).getPiece());
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        }
    }

}
