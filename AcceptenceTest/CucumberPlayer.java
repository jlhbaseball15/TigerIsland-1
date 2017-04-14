import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;
import java.util.ArrayList;

public class CucumberPlayer {
    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private SettlementBuilder settlements;
    private Player testPlayer;
    private Settlement chosensettlement;
    private ArrayList<Settlement> Settlementslist;
    private ArrayList<Point> testlist;


    @Given("^That the meeples can be placed$")
    public void tilessetsomeeplescanbe()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];
        chosensettlement=new Settlement();
        Settlementslist = new ArrayList<>();

        p[0] = new Point(0, -1);
        p[1] = new Point(1, -1);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        board.addTile(tile, p);
        p[0] = new Point(1, 0);
        p[1] = new Point(0, 1);
        p[2] = new Point(1, 1);
        tile = deck.getTile();
        board.addTile(tile, p);

        p[0] = new Point(1, 0);
        p[1] = new Point(0, 1);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        board.addTile(tile, p);

        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();

    }

    @When("^the player places meeples on board to create settlement$")
    public void validlyputsettlmentwithvillager(){
        Point temppoint=new Point(0,-1);
        try {
            rules.tryToBuildNewSettlement(testPlayer,temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }
        board.addVillagerToBoard(true,temppoint);
        testPlayer.villagersBeingPlaced(1);

    }
    @Then("^the Value of remaining meeples is correctly updated$")
    public void checkvillagerleft(){
        Assert.assertEquals(19, testPlayer.getvillagersRemaining());
    }
    @Given("^That the tiger can be placed$")
    public void setupsettlementfortiger()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];
        chosensettlement=new Settlement();
        Settlementslist = new ArrayList<>();

        p[0] = new Point(0, -1);
        p[1] = new Point(1, -1);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        board.addTile(tile, p);
        p[0] = new Point(1, 0);
        p[1] = new Point(0, 1);
        p[2] = new Point(1, 1);
        tile = deck.getTile();
        board.addTile(tile, p);

        p[0] = new Point(0, 2);
        p[1] = new Point(1, 2);
        p[2] = new Point(0, 3);
        tile = deck.getTile();
        board.addTile(tile, p);
        p[0] = new Point(2, 0);
        p[1] = new Point(1, -1);
        p[2] = new Point(2, -1);
        tile = deck.getTile();

        board.addTile(tile, p);
        p[0] = new Point(1, 0);
        p[1] = new Point(1, -1);
        p[2] = new Point(0, -1);
        tile = deck.getTile();
        board.addTile(tile, p);
        p[0] = new Point(0, 2);
        p[1] = new Point(0, 1);
        p[2] = new Point(1, 1);
        tile = deck.getTile();
        board.addTile(tile, p);

        p[0] = new Point(0, 1);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        board.addTile(tile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();

        board.getHexAtPointP(new Point(2, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(2, 0));

        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);
        testPlayer.villagersBeingPlaced(1);

    }

    @When("^the player places tiger on the adjacent hex$")
    public void validlyputtingatigerforpiececheck(){
        Point temppoint=new Point(1,0);
        Point templocationofsettlement = new Point(2,0);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }
        board.addTigerToBoard(true,temppoint);
        testPlayer.tigerBeingPlaced();
    }
    @Then("^the Value of remaining tiger is correctly updated$")
    public void checktigerleft(){
        Assert.assertEquals(1, testPlayer.gettigersRemaining());
    }
    @Given("^That the totoro can be placed$")
    public void setupfortotoro()throws Throwable{
        board = new GameBoard();
        rules = new GameRules(board);
        deck = new Deck();
        Point p[] = new Point[3];
        chosensettlement=new Settlement();
        Settlementslist = new ArrayList<>();

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
        board.addTile(tile, p);
        p[0] = new Point(1, -1);
        p[1] = new Point(2, -1);
        p[2] = new Point(2, -2);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(0, -1);
        p[1] = new Point(-1, -1);
        p[2] = new Point(-1, 0);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();

        board.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(1, 0));

        board.getHexAtPointP(new Point(0, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(0, 0));

        board.getHexAtPointP(new Point(0, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(0, -1));

        board.getHexAtPointP(new Point(1, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(1, -1));

        board.getHexAtPointP(new Point(2, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(2, -1));

        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);
        testPlayer.villagersBeingPlaced(1);
        testPlayer.villagersBeingPlaced(1);
        testPlayer.villagersBeingPlaced(1);
        testPlayer.villagersBeingPlaced(1);
        testPlayer.villagersBeingPlaced(1);

    }
    @When("^the player places totoro on the adjacent hex$")
    public void validlyputtingatotoroforpiececheck(){
        Point temppoint=new Point(2,0);
        Point templocationofsettlement = new Point(2,1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "".toCharArray();
            char [] actual = e.getMessage().toCharArray();
        }
        board.addTotoroToBoard(true,temppoint);
        testPlayer.totoroBeingPlaced();
    }
    @Then("^the Value of remaining totoro is correctly updated$")
    public void checktotororemaining(){
        Assert.assertEquals(2, testPlayer.gettotorosRemaining());
    }

    @Given("^that the expansion can happen$")
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

        Tile temptile = new Tile('G','G');
        board.addTile(temptile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();
        board.addVillagerToBoard(true, new Point(1, 0));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
        testPlayer.villagersBeingPlaced(1);

    }
    @When("^the player places meeples on board to expand the settlement$")
    public void expandthesettlement()throws Throwable{
        Point temppoint=new Point(1,0);
        testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'G',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            System.out.println(e.getMessage());
        }
        board.expandSettlement(true,testlist);
        for(int i=0; i<testlist.size();i++) {
            testPlayer.villagersBeingPlaced(1);
        }
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());
    }
    @Then("^the Value of remaining meeples is correctly updated for exapansion$")
    public void thehexesareadded(){
        int expectedremaining = 20- testlist.size();
        expectedremaining--;
        Assert.assertEquals(expectedremaining, testPlayer.getvillagersRemaining());
    }

    //given same as above
    //when same as above

    @Then("^the player’s score is increased by number of meeple x level$")
    public void checkplayerscoreformeeple(){
        Assert.assertEquals(1, testPlayer.getScore());
    }
    //given same as above
    //when same as above

    @Then("^the player’s score is increased by 200")
    public void checkplayerscorefortotoro(){
        Assert.assertEquals(205, testPlayer.getScore());
    }
    //given same as above
    //when same as above
    @Then("^the player’s score is increased by 75")
    public void checkplayerscorefortiger(){
        Assert.assertEquals(76, testPlayer.getScore());
    }

    //given same as above
    //when same as above
    @Then("^the players score is increased by the number of meeple played based")
    public void checkplayerscoreforexpansion(){
        int expectedscore = 1+ testlist.size();
        Assert.assertEquals(expectedscore, testPlayer.getScore());
    }
}