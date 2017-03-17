
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;

public class CucumberGameBoardTest {
    private GameBoard board;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations = new Point[3];

    @Given("^an empty board$")
    public void creatAnEmptyBoard() throws Throwable {
        board = new GameBoard();
        deck = new Deck();
        tile = deck.getTile();
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(1,1);
    }

    @When("^a tile is placed$")
    public void placeTileOnBoard() throws Throwable {
        board.TryToAddTile(tile.getHexes(), tileLocations);
    }

    @Then("^the tile is added to the board$")
    public void tileIsAddedToTheBoard() throws Throwable {
        Assert.assertFalse(board.isEmpty());
    }

}
