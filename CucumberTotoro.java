import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
public class CucumberTotoro {
    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile tile;
    private Point[] tileLocations;
    private SettlementBuilder settlements;
    private Player testPlayer;
    private Settlement chosensettlement;
    private ArrayList<Settlement> Settlementslist;


    @Given("^the settlement has a size 5$")
    public void setupsettlementfortotoro()throws Throwable{
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

    }
    @When("^a Totoro is placed$")
    public void validlyputtingatotoro(){
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
    }
    @Then("^the Totoro will be added to the hex$")
    public void thetotoroisadded(){
        Assert.assertEquals(Pieces.P1_TOTORO, board.getHexAtPointP(2, 0).getPiece());
    }

    //Given as above
    @When("^a tried Totoro is placed on a volcaneo type$")
    public void puttotoroonvolcaneo(){
        Point temppoint=new Point(0,1);
        Point templocationofsettlement = new Point(1,-1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Can not build totoro on volcano".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the Totoro is not added to the hex$")
    public void thetotoroisnotadded(){
        chosensettlement=new Settlement();
        Settlementslist = new ArrayList<>();
        Point tep= new Point(1,-1);
        for (Settlement s: Settlementslist) {
            if (s.contains(tep)) {
                chosensettlement = s;
                break;
            }
        }
        Assert.assertEquals(false, chosensettlement.containsTotoro());
    }
    @Given("^the settlement size is less than five$")
    public void setupsettlementfortotoroforsettlementless5()throws Throwable{
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
        board.addVillagerToBoard(true, new Point(1, 0));
        board.addVillagerToBoard(true, new Point(0, 0));
        board.addVillagerToBoard(true, new Point(0, -1));
        board.addVillagerToBoard(true, new Point(1, -1));
     //   board.addVillagerToBoard(true, new Point(2, 1));
        // board.addVillagerToBoard(true, new Point(-1, -1));
        settlements.calculateSettlements(board);
        rules.setSettlements(settlements.getPlayer1Settlements());

    }
    @When("^a Totoro tried is placed$")
    public void puttotoroonasettlementlessthan5(){
        Point temppoint=new Point(2,0);
        Point templocationofsettlement = new Point(1,-1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Size of settlement is not equal to or greater than 5".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the Totoro is not added to the settlement$")
    public void tosettlement(){
        chosensettlement=new Settlement();
        Settlementslist = new ArrayList<>();
        Point tep= new Point(1,-1);
        for (Settlement s: Settlementslist) {
            if (s.contains(tep)) {
                chosensettlement = s;
                break;
            }
        }
        Assert.assertEquals(false, chosensettlement.containsTotoro());
    }
    @Given("^the hex is occupied for a totoro$")
    public void setupsettlementforontop(){
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
    }
    @When("^a Totoro is placed on top of it$")
    public void puttotoroonaoccupiedhex(){
        Point temppoint=new Point(2,0);
        Point templocationofsettlement = new Point(2,-1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Cannot Build On An Occupied Hex".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^Totoro is not added to the hex instead$")
    public void settlementdoesnthavetotoro(){
        Point tep= new Point(1,-1);
        for (Settlement s: Settlementslist) {
            if (s.contains(tep)) {
                chosensettlement = s;
                break;
            }
        }
        Assert.assertEquals(false, chosensettlement.containsTotoro());
    }
    @Given("^the settlement contains a Totoro$")
    public void setupsettlementwithtototor(){
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

        Point temppoint = new Point (2,0);
        board.addTotoroToBoard(true,temppoint);
        chosensettlement.addPointToSettlement(new Point(2, 0));
        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);
    }
    @When("^a Totoro is placed in the same settlement$")
    public void puttotoronexttosettlementthatalreadyhasone(){
        Point temppoint=new Point(-1,-1);
        Point templocationofsettlement = new Point(2,-1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Can not build more than one totoros on one settlement".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the Totoro is not added to the hex or settlement$")
    public void thetotoroisnotaddedtosettlementthathastotoro(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(-1, -1).getPiece());
    }
    @Given("^the settlement has a Totoro$")
    public void setuptwosettlementwithtototoro(){
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

        Point temppoint = new Point (2,0);
        board.addTotoroToBoard(true,temppoint);
        chosensettlement.addPointToSettlement(new Point(2, 0));
        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);

        Tile tilejoiner=new Tile('G','G');
        p[0] = new Point(-1, 1);
        p[1] = new Point(-2, 2);
        p[2] = new Point(-1, 2);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tilejoiner, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tilejoiner, p);
        p[0] = new Point(-2, 3);
        p[1] = new Point(-1, 3);
        p[2] = new Point(-2, 4);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(0, 2);
        p[1] = new Point(1, 2);
        p[2] = new Point(0, 3);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        Settlement chosen2settlement=new Settlement();
        board.getHexAtPointP(new Point(-2, 2)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(-2, 2));

        board.getHexAtPointP(new Point(-2, 3)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(-2, 3));

        board.getHexAtPointP(new Point(-1, 3)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(-1, 3));

        board.getHexAtPointP(new Point(0, 2)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(0, 2));

        board.getHexAtPointP(new Point(0, 3)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(0, 3));

        temppoint = new Point (1,2);
        board.addTotoroToBoard(true,temppoint);
        chosen2settlement.addPointToSettlement(new Point(2, 0));
        Settlementslist.add(chosen2settlement);
        rules.setSettlements(Settlementslist);
    }
    @When("^it will connect to another settlement that has a Totoro$")
    public void connectthesettlementthathavetotoro(){
        Point temppoint=new Point(-2,2);
        ArrayList<Point> testlist = new ArrayList<Point>();
        try {
            testlist= rules.tryToExpand(testPlayer,'G',temppoint);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }
        board.expandSettlement(true,testlist);

    }
    @Then("^all adjacent hexes with given Terrain Type are now occupied between the settlements$")
    public void checktheexpansionworked(){
        Assert.assertEquals(Pieces.P1_VILLAGER, board.getHexAtPointP(-1, 1).getPiece());
    }
    @Given("^the settlement has a Totoro almost next to one that does not$")
    public void setuptwosettlementwithonethathastotoro(){
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

        Point temppoint = new Point (2,0);
        board.addTotoroToBoard(true,temppoint);
        chosensettlement.addPointToSettlement(new Point(2, 0));
        Settlementslist.add(chosensettlement);
        rules.setSettlements(Settlementslist);

        Tile tilejoiner=new Tile('G','G');
        p[0] = new Point(-1, 1);
        p[1] = new Point(-2, 2);
        p[2] = new Point(-1, 2);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tilejoiner, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tilejoiner, p);
        p[0] = new Point(-2, 3);
        p[1] = new Point(-1, 3);
        p[2] = new Point(-2, 4);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        p[0] = new Point(0, 2);
        p[1] = new Point(1, 2);
        p[2] = new Point(0, 3);
        tile = deck.getTile();
        try {
            rules.TryToAddTile(tile, p);
        } catch(GameRulesException e) {
            Assert.assertTrue(false);
        }
        board.addTile(tile, p);
        Settlement chosen2settlement=new Settlement();
        board.getHexAtPointP(new Point(-2, 2)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(-2, 2));

        board.getHexAtPointP(new Point(-2, 3)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(-2, 3));

        board.getHexAtPointP(new Point(-1, 3)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(-1, 3));

        board.getHexAtPointP(new Point(0, 2)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(0, 2));

        board.getHexAtPointP(new Point(0, 3)).setOccupied(Pieces.P1_VILLAGER, 1);
        chosen2settlement.addPointToSettlement(new Point(0, 3));

        chosen2settlement.addPointToSettlement(new Point(2, 0));
        Settlementslist.add(chosen2settlement);
        rules.setSettlements(Settlementslist);
    }
    @When("^the totoro will connect the two settlements$")
    public void connectthesettlementwithtotoro()throws Throwable{
        Point temppoint=new Point(-1,1);
        Point templocationofsettlement = new Point(-2,2);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }
        board.addTotoroToBoard(true,temppoint);
        settlements.calculateSettlements(board);
    }
    @Then("^the totoro is added$")
    public void checkthetotoroistherebetweenthetwo(){
        Assert.assertEquals(Pieces.P1_TOTORO, board.getHexAtPointP(-1, 1).getPiece());
    }
    @Given("^the settlement has all other attributes filled$")
    public void setupsettlementfortotoropiececheck()throws Throwable{
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
        testPlayer.totoroBeingPlaced();
        testPlayer.totoroBeingPlaced();
        testPlayer.totoroBeingPlaced();
        // board.addVillagerToBoard(true, new Point(-1, -1));

    }
    @When("^the player wants to put a totoro$")
    public void trytoplayannonexcisitingtotoro()throws Throwable{
        Point temppoint=new Point(-1,-1);
        Point templocationofsettlement = new Point(0,-1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Has played all Totoro pieces".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the totoro is not added$")
    public void spotstillemptybecausenoremainingtotoros(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(-1, -1).getPiece());
    }

    @Given("^the settlement has all other attributes filled but placement$")
    public void setupsettlementfortotoroplacecheck()throws Throwable{
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

    }
    @When("^the player wants to put a totoro away from the settlement$")
    public void trytoplayatotoroawayfromthesettlement()throws Throwable{
        Point temppoint=new Point(2,1);
        Point templocationofsettlement = new Point(0,-1);
        try {
            rules.tryToAddTotoro(testPlayer,temppoint,templocationofsettlement);
            Assert.assertTrue(true);
        } catch (GameRulesException e) {
            char [] expected = "Must build Totoro next to the Settlement".toCharArray();
            char [] actual = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expected, actual);
        }

    }
    @Then("^the totoro is not added to the hex$")
    public void totorofailstobeplacedawayfromsettlement(){
        Assert.assertEquals(Pieces.NONE, board.getHexAtPointP(2, 1).getPiece());
    }
}
