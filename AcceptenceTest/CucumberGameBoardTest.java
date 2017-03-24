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
        board.AddTile(tile, tileLocations);
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
        board.AddTile(tile, tileLocations);
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
        board.AddTile(tile, tileLocations);
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
    @When("^the tile is plaed in a nonempty spot")
    public void theTileIsPlacedinaNonemptyspot() throws GameRulesException{
        tile = deck.getTile();
        tileLocations[0] = new Point(0, 0);
        tileLocations[1] = new Point(1, 0);
        tileLocations[2] = new Point(0, 1);
        try {
            rules.TryToAddTile(tile, tileLocations);
        }catch (GameRulesException e) {
            Assert.assertEquals("\"The Tile Is Not Sitting On Three Hexes\"", e.getMessage());
        };
    }
    @Then("^the tile is not added to the map$")
    public void theTileIsNotAddedduetonotbeinganemptyspot() throws GameRulesException{
        try {
            rules.TryToAddTile(tile, tileLocations);
        }catch (GameRulesException e) {
            Assert.assertEquals("The Tile Is Not Sitting On Three Hexes", e.getMessage());
        };
    }
}
