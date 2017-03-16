import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;

/**
 * Created by dontf on 3/16/2017.
 */
public class CucumberGameBoardTest {
    private GameBoard board;
    private Deck deck;
    private Hex[] tile;
    private Point[] tileLocations;

    @Given("^an empty board$")
    public void creatAnEmptyBoard() {
        board = new GameBoard();
        deck = new Deck();
        tile = deck.getTile();
        tileLocations[0] = new Point(0,0);
        tileLocations[0] = new Point(1,0);
        tileLocations[0] = new Point(1,1);
    }

    @When("^a tile is placed$")
    public void placeTileOnBoard(){
        board.TryToAddTile(tile, tileLocations);
    }

    @Then("^the tile is added to the board$")
    public void tileIsAddedToTheBoard() {
        Assert.assertFalse(board.isEmpty());
    }

}
