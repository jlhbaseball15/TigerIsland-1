import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class GameRulesTest {

    private GameRules gameRules;
    private GameBoard gameBoard;
    private Deck deck;
    private Tile tile;
    private Point HexPoints[];
    private Hex[] hex;

    @Before
    public void MapObject(){
        gameBoard = new GameBoard();
        gameRules = new GameRules(gameBoard);
        deck = new Deck();
        tile = deck.getTile();
        hex = tile.getHexes();
        HexPoints = new Point [3];
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(0);
        hex[1].setTileNumber(0);
        hex[2].setTileNumber(0);
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (1,0);
        HexPoints[2] = new Point (1,1);
    }

    @Test
    public void PlaceFirstTile() throws GameRulesException {
        try {
            gameRules.TryToAddTile(tile, HexPoints);
        }catch (GameRulesException e) {
            Assert.assertTrue(false);
        };
        Assert.assertTrue(true);
    }

    @Test
    public void PlacingATileThatIsNotFlat() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);
        HexPoints[2] = new Point(-1, 1);
       try {
           gameRules.TryToAddTile(tile, HexPoints);
           Assert.assertTrue(false);
       } catch (GameRulesException e) {
           char expectedMessage[] = "The Tile Is Not Sitting On Three Hexes".toCharArray();
           char actualMessage[] = e.getMessage().toCharArray();
           Assert.assertArrayEquals(expectedMessage, actualMessage);
       };

    }

    @Test
    public void PlacingATileOnTopOfAnother() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile Is Directly Over Another Tile".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void PlacingASecondTileNextToFirstTile() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point(0, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(1, -1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        };
        Assert.assertTrue(true);
    }

    @Test
    public void PlacingTileOnTheSameLevels() throws GameRulesException { // also checks that volcanoes are aligned
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(2);
        hex[1].setLevel(2);
        hex[2].setLevel(2);
        hex[0].setTileNumber(2);
        hex[1].setTileNumber(2);
        hex[2].setTileNumber(2);
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
        } catch (GameRulesException e) {
            Assert.assertTrue(false);
        };
        Assert.assertTrue(true);
    }

    @Test
    public void PlacingTileOnUnevenLevels() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(2);
        hex[1].setLevel(2);
        hex[2].setLevel(2);
        hex[0].setTileNumber(2);
        hex[1].setTileNumber(2);
        hex[2].setTileNumber(2);
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(3);
        hex[1].setLevel(3);
        hex[2].setLevel(3);
        hex[0].setTileNumber(3);
        hex[1].setTileNumber(3);
        hex[2].setTileNumber(3);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile Is Located Over Different Leveled Tiles".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };

    }

    @Test
    public void BoardNotEmptyAndPlacingTileByItself() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (-1,-2);
        HexPoints[2] = new Point (0,-3);

        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile Is Not Adjacent To An Existing Tile".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }

    @Test
    public void PlacingTileOnEvenLevelsAndVolcanosDontOverLap() throws GameRulesException {
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameRules.TryToAddTile(tile, HexPoints);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        hex = tile.getHexes();
        hex[0].setLevel(2);
        hex[1].setLevel(2);
        hex[2].setLevel(2);
        hex[0].setTileNumber(2);
        hex[1].setTileNumber(2);
        hex[2].setTileNumber(2);
        HexPoints[2] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[0] = new Point (1,-1);
        try {
            gameRules.TryToAddTile(tile, HexPoints);
            Assert.assertTrue(false);
        } catch (GameRulesException e) {
            char expectedMessage[] = "The Tile's Volcano Is Not Aligned With A Volcano Hex".toCharArray();
            char actualMessage[] = e.getMessage().toCharArray();
            Assert.assertArrayEquals(expectedMessage, actualMessage);
        };
    }
}
