import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;

public class CucumberGameBoardTest {
    private GameBoard board;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private int isPlaced;

    @Given("^an empty board$")
    public void anemptyboard() {
        board = new GameBoard();
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(1,1);
    }

    @When("^a tile is placed$")
    public void atileisplaced(){
        board.TryToAddTile(tile, tileLocations);
    }

    @Then("^the tile is added on the board$")
    public void thetileisaddedontheboard() {
        Assert.assertFalse(board.isEmpty());
    }



    @Given("^The board is not empty$")
    public void theBoardIsNotEmpty() {
        board = new GameBoard();
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0, 0);
        tileLocations[1] = new Point(1, 0);
        tileLocations[2] = new Point(1, 1);
        board.TryToAddTile(tile, tileLocations);
    }

    @When("^The tile is placed$")
    public void theTileIsPlaced() {
        tile = deck.getTile();
        tileLocations[0] = new Point(0, -2);
        tileLocations[1] = new Point(1, -1);
        tileLocations[2] = new Point(1, -1);
        isPlaced = board.TryToAddTile(tile, tileLocations);
    }

    @Then("^the tile is added to the map$")
    public void theTileIsAddedToTheMap() {
        Assert.assertEquals(0, isPlaced);
    }


    //given from above

    @When("^The tile is placed away from other tiles$")
    public void theTileIsPlacedAwayFromOtherTiles() {
        tile = deck.getTile();
        tileLocations[0] = new Point(3, 0);
        tileLocations[1] = new Point(4, 0);
        tileLocations[2] = new Point(4, 1);
        isPlaced = board.TryToAddTile(tile, tileLocations);
    }

    @Then("^the tile is not added to the map$")
    public void theTileIsNotAddedToTheMap() {
        Assert.assertEquals(-4, isPlaced);
    }
}
