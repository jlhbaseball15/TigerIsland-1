import java.awt.*;
import java.util.ArrayList;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class CucumberPieces {
    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private SettlementBuilder settlements;
    private Player testPlayer;

    @Given ("^the Terrain Type is not a volcano and the hex is unoccupied$")
    public void emptynonvolcanoehex(){
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
    @When ("^a meeple is placed$")
    public void trytoaddvillagertononvolcanoeemptyhex(){
        settlements = new SettlementBuilder();
        testPlayer= new Player("TestPlayer");
        try {
            rules.tryToBuildNewSettlement(testPlayer, tileLocations[1]);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {

        }
    }

    @Then("^the meeple will be added to the hex$")
    public void addedthemeeple(){
        settlements = new SettlementBuilder();
        testPlayer= new Player("TestPlayer");
        try {
            rules.tryToBuildNewSettlement(testPlayer, tileLocations[1]);
            board.addVillagerToBoard(true, new Point(1, 0));
            Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(1, 0).getPiece());
        } catch (GameRulesException e) {
            Assert.assertTrue(true);
        }

    }

    @Given("^the Terrain Type is a volcano$")
    public void thehexisavolcaneo(){
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

    @When("^A meeple is placed$")
    public void trytoplacemeeple(){
        settlements = new SettlementBuilder();
        testPlayer= new Player("TestPlayer");
        try {
            rules.tryToBuildNewSettlement(testPlayer, tileLocations[2]);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build On A Volcano".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);

        }
    }
    @Then ("^the meeple is not added to the hex$")
    public void themeepleisnotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(0, 1).getPiece());
    }

    @Given ("^the hex is occupied$")
    public void anoccupiedhex(){
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        tile = deck.getTile();
        tileLocations = new Point[3];
        tileLocations[0] = new Point(0,0);
        tileLocations[1] = new Point(1,0);
        tileLocations[2] = new Point(0,1);
        board.addTile(tile, tileLocations);
        settlements = new SettlementBuilder();
        testPlayer= new Player("TestPlayer");
        board.addVillagerToBoard(true, new Point(1, 0));

    }
    @When("^a Meeple is placed$")
    public void placingameepleinanoccupiedhex(){
        Point temppoint=new Point(1,0);
        try {
            rules.tryToBuildNewSettlement(testPlayer, temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build On An Occupied Hex".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);

        }
    }
    @Then("^the Meeple is not added to the hex$")
    public void theMeepleisnotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(0, 1).getPiece());
    }
    @Given("^the hex is not level one and the Terrain Type is not a volcano$")
    public void setupupperlevelhex() throws Throwable{
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
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(1, -1);
        p[1] = new Point(1, -2);
        p[2] = new Point(2, -2);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(1, 0);
        p[1] = new Point(0, 1);
        p[2] = new Point(1, 1);
        tile = deck.getTile();

       Tile temptile = new Tile('G','G');
        board.addTile(temptile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }
    @When("^the meeples isare placed$")
    public void addingupperlevelmeeple(){
        Point temppoint=new Point(0,0);
        ArrayList<Point> testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'G',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
        }
        board.expandSettlement(true,testlist);

    }
    @Then("^the point is added to the settlement$")
    public void themeepleshavebeenadded(){
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(0, 1).getPiece());
    }

    @Given("^there is an empty spot$")
    public void emptyspotgenerator(){
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
    }
    @When("^A Meeple is placed$")
    public void meepleonemptyspottest(){
        Point temppoint=new Point(1,1);
        try {
            rules.tryToBuildNewSettlement(testPlayer, temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build On An Empty Hex".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);

        }
    }

    @Then("^The Meeple Is Not Added$")
    public void meepleonemptyspotcheckfail(){
        Assert.assertEquals(false,board.hasTileInMap(1,1));
    }

    @Given ("^There is an occupided hex on the map$")
    public void occupiedhexgenetor()throws Throwable{
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
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(0, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }

    @When ("^A meeple is placed there$")
    public void tryingtoplaceameepleinanoccupiedspot(){
      Player testPlayer2= new Player("TestPlayer");
        Point temppoint=new Point(0,0);
        try {
            rules.tryToBuildNewSettlement(testPlayer2, temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build On An Occupied Hex".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);

        }
    }
    @Then("^The Meeple is not added there$")
    public void newsettlementonoccpiedspot(){
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(0, 0).getPiece());
    }

    @Given("^There is an unoccupied nonlevel one hex on the map$")
    public void nonlevelonehexgenerator(){
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
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(1, -1);
        p[1] = new Point(1, -2);
        p[2] = new Point(2, -2);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(1, 0);
        p[1] = new Point(0, 1);
        p[2] = new Point(1, 1);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
    }
    @When ("^A settlement is built there$")
    public void tryingtobuildanewsettlementabovelevelonetest(){
        Point temppoint=new Point(1,0);
        try {
            rules.tryToBuildNewSettlement(testPlayer, temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build New Settlement Above Level One".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);

        }
    }
    @Then("^the settlement in not created$")
    public void failtobuildnewsettlementabovelevelone(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(1, 0).getPiece());
    }

    @Given("^The player does have not any remaining meeple$")
    public void noremainingmeeples()throws Throwable{
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
        testPlayer= new Player("TestPlayer");
        testPlayer.villagersBeingPlaced(20);
    }
    @When ("^The player tries to place a meeple$")
    public void cantplacemeepleifallplayed(){
        Point temppoint=new Point(1,0);
        try {
            rules.tryToBuildNewSettlement(testPlayer, temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Player Does Not Have Enough Villagers".toCharArray();
            char [] actual = e.getMessage().toCharArray();

            Assert.assertArrayEquals(expected, actual);

        }
    }
    @Then("^The player cannot add a meeple$")
    public void failedtoaddanothermeeple(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(1, 0).getPiece());
    }
}
