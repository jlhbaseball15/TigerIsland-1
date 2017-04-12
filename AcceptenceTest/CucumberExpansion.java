/**
 * Created by Colette on 4/9/2017.
 */
import java.awt.*;
import java.util.ArrayList;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
public class CucumberExpansion {
    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private SettlementBuilder settlements;
    private Player testPlayer;

    @Given("^when a player finishes their turn$")
    public void playerbuildsasettlement(){
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(0,1);
        board.addTile(tile, tileLocations);


    }
    @When("^a new settlement is formed or its size recalculated$")
    public void createsettlement()throws Throwable{
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }

    @Then("^all Meeples in the settlement will belong to the same player$")
    public void checksettlement(){
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(0, 0).getPiece());
    }

    @Given("^the player successfully build or expand in the turn$")
    public void addedtwosettlement()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(0,1);
        board.addTile(tile, tileLocations);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }
    @When("^the turn is over$")
    public void addsettlementnexttothatsettlement()throws Throwable{
        board.addVillagerToBoard(true, new Point(1, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }
    @Then("^all adjacent settlements will be count as one settlement with size same as the number of the hex it contains$")
    public void onesettlement(){
        Assert.assertEquals(2,settlements.getPlayer1Settlements().size());
    }

    @Given("^the player has the meeples for an expansion$")
    public void setupforexpansion()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];

        p[0] = new Point(0, 0);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 1);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(2, 1);
        p[1] = new Point(2, 0);
        p[2] = new Point(1, 1);
        tile = deck.getTile();

        Tile temptile = new Tile('G','G');
        board.addTile(temptile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(1, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());

    }
    @When("^the player expands$")
    public void expandthesettlement(){
        Point temppoint=new Point(1,0);
        ArrayList<Point> testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'G',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            System.out.println(e.getMessage());
        }
        board.expandSettlement(true,testlist);
    }
    @Then("^all adjacent hexes with given Terrain Type are now occupied$")
    public void thehexesareadded(){
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(2, 1).getPiece());
    }

    @Given("^the player does not have the meeples for an expansion$")
    public void setupforexpansionaandnomeeplesremaining()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];

        p[0] = new Point(0, 0);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 1);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(2, 1);
        p[1] = new Point(2, 0);
        p[2] = new Point(1, 1);
        tile = deck.getTile();

        Tile temptile = new Tile('G','G');
        board.addTile(temptile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(1, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
        testPlayer.villagersBeingPlaced(19);

    }
    @When("^the player tries expands$")
    public void expandthesettlementwithnomeeples(){
        Point temppoint=new Point(1,0);
        ArrayList<Point> testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'G',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Player Does Not Have Enough Villagers".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^all adjacent hexes with given Terrain Type are not occupied$")
    public void thehexesarenotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(2, 1).getPiece());
    }

    @Given("^the player wants to expand on a terrain not adjacent to their settlement$")
    public void setupforexpansionaandterrainnotadjacent()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];

        p[0] = new Point(0, 0);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 1);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(2, 1);
        p[1] = new Point(2, 0);
        p[2] = new Point(1, 1);
        tile = deck.getTile();

        Tile temptile = new Tile('G','J');
        board.addTile(temptile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());


    }
    @When("^the player wants expands$")
    public void expandthesettlementwithterrainnotadjacet(){
        Point temppoint=new Point(0,0);
        ArrayList<Point> testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'J',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "No Hexes With Given Terrain Type To Expand To".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^adjacent hexes with given Terrain Type are not occupied$")
    public void thehexarenotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(2, 0).getPiece());
    }
    @Given("^the player wants to expand on a volcanoe terrain$")
    public void setupforexpansionaintovolcaneo()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(0,1);
        board.addTile(tile, tileLocations);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }
    @When("^the player wants expands into volcaneos$")
    public void expandthesettlementintoavolcaneo(){
        Point temppoint=new Point(0,0);
        ArrayList<Point> testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'V',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Expand Onto Volcanoes".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^adjacent hexes with given Terrain Type volcaneo are not occupied$")
    public void thevolcaneohexarenotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(0, 1).getPiece());
    }
}
