import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
public class CucumberTiger {
    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private SettlementBuilder settlements;
    private Player testPlayer;
    private Settlement chosensettlement;
    private ArrayList<Settlement> Settlementslist;

    @Given("^the hex level is greater than two$")
    public void setupfortiger()throws Throwable{
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

    }

    @When("^a Tiger is placed$")
    public void validlyputtingatiger(){
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
    }
    @Then("^the Tiger will be added to the hex$")
    public void thetigerisadded(){
        Assert.assertEquals(Pieces.P1_TIGER, board.getHexAtPointP(1, 0).getPiece());
    }

    @Given("^the hex is level three or higher$")
    public void setupfortigeronvolcaneohex()throws Throwable{
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

        board.addTile(tile, p);
        p[0] = new Point(1, 0);
        p[1] = new Point(1, -1);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        board.addTile(tile, p);

        p[0] = new Point(0, 1);
        p[1] = new Point(1, 0);
        p[2] = new Point(0, 0);
        tile = deck.getTile();
        board.addTile(tile, p);
        testPlayer= new Player("TestPlayer");
        settlements = new SettlementBuilder();

        board.getHexAtPointP(new Point(0, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(0, -1));

        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);

    }
    @When("^a tried Tiger is placed on a volcaneo type$")
    public void puttingatigeronvolcaneo(){
        Point temppoint=new Point(0,0);
        Point templocationofsettlement = new Point(0, -1);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Can not build tiger on volcano".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the Tiger is not added to the hex$")
    public void thetigerisnotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(0, 0).getPiece());
    }
    @Given("^the hex level is less than three$")
    public void setupfortigeronleveltwohex()throws Throwable{
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

        board.getHexAtPointP(new Point(0, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(0, -1));

        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);

    }
    @When("^a Tiger tried is placed$")
    public void puttingatigeronleveltwo(){
        Point temppoint=new Point(1,-1);
        Point templocationofsettlement = new Point(0, -1);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Level of hex must be three or greater".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the Tiger is not added to the HEX$")
    public void thetigerisnotaddedtothehex(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(1, -1).getPiece());
    }
    @Given("^the hex is occupied for a tiger$")
    public void setupfortigeronoccupiedhex()throws Throwable{
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

        board.getHexAtPointP(new Point(1, 0)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(1, 0));

        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);

    }

    @When("^a Tiger is placed on top of it$")
    public void trytoplacetigeronetop(){
        Point temppoint=new Point(1,0);
        Point templocationofsettlement = new Point(2,0);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build On An Occupied Hex".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^Tiger is not added to the hex instead$")
    public void thetigerisnotaddedinstead(){
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(1, 0).getPiece());
    }

    //given same as above
    @When("^the player wants to put a tiger away from the settlement$")
    public void trytoplacetigerawayfromsettlement(){
        Point temppoint=new Point(0,1);
        Point templocationofsettlement = new Point(2,0);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Must build Tiger next to the Settlement".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the tiger is not added to the hex$")
    public void thetigerisnotaddedawayfromhex(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(0, 1).getPiece());
    }
    @Given("^the settlement has all other attributes for tiger filled$")
    public void setupfortigerbutplayerhasnoneleft()throws Throwable{
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
        testPlayer.tigerBeingPlaced();
        testPlayer.tigerBeingPlaced();

    }

    @When("^the player wants to put a tiger$")
    public void invalidlyputtingatiger(){
        Point temppoint=new Point(1,0);
        Point templocationofsettlement = new Point(2,0);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Has played all Tiger pieces".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }
    }
    @Then("^the tiger is not added$")
    public void thetigerthatdoesntexistisnotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(1, 0).getPiece());
    }
    @Given("^the settlement has a Tiger$")
    public void setupfortigersettlementtatalreadyhasone()throws Throwable{
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
        board.getHexAtPointP(new Point(2, -1)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosensettlement.addPointToSettlement(new Point(2, -1));

        Point placementpoint = new Point (1,0);
        board.addTigerToBoard(true,placementpoint);
        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);
    }

    @When("^the player wants to add another tiger$")
    public void placingasecondtiger(){
        Point temppoint=new Point(1,-1);
        Point templocationofsettlement = new Point(2,0);
        try {
            rules.tryToAddTiger(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Can not build more than one tiger on one settlement".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            System.out.print(e.getMessage());
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the tiger is not added to the hex given$")
    public void thesecondtigerisnotadded(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(1, -1).getPiece());
    }

}
