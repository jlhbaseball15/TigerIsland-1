import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

public class GameBoardTest {

    private GameBoard gameBoard;
    private Deck deck;
    private Tile tile;
    private Point HexPoints[];
    private Hex[] hex;

    @Before
    public void MapObject(){
        gameBoard = new GameBoard();
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
    public void PlaceFirstTile() {
        gameBoard.TryToAddTile(tile, HexPoints);
        Assert.assertFalse(gameBoard.isEmpty());
    }

    @Test
    public void GettingMap() {
        gameBoard.TryToAddTile(tile, HexPoints);
        HashMap<Integer, HashMap<Integer, Hex>> currentBoard = gameBoard.getMap();
        Assert.assertEquals('V', currentBoard.get(1).get(1).getTerrain());
    }

    @Test
    public void PlacingATileThatIsNotFlat() {
        gameBoard.TryToAddTile(tile, HexPoints);
        HexPoints[2] = new Point(-1, 1);
        Assert.assertEquals(-1, gameBoard.TryToAddTile(tile, HexPoints));
    }

//    @Test
//    public void PlacingATileOnTopOfAnother() {
//        gameBoard.TryToAddTile(tile, HexPoints);
//        hex[0].setTileNumber(1);
//        hex[1].setTileNumber(1);
//        hex[2].setTileNumber(1);
//        Assert.assertEquals(-1, gameBoard.TryToAddTile(tile, HexPoints));
//    }

    @Test
    public void PlacingASecondTileNextToFirstTile() {
        gameBoard.TryToAddTile(tile, HexPoints);

        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point(0, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(1, -1);
        Assert.assertEquals(0, gameBoard.TryToAddTile(tile, HexPoints));
    }

//    @Test
//    public void PlacingTileOnTheSameLevels() {
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(1);
//        hex[1].setLevel(1);
//        hex[2].setLevel(1);
//        hex[0].setTileNumber(1);
//        hex[1].setTileNumber(1);
//        hex[2].setTileNumber(1);
//        HexPoints[0] = new Point (0,-2);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[2] = new Point (1,-1);
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(2);
//        hex[1].setLevel(2);
//        hex[2].setLevel(2);
//        hex[0].setTileNumber(2);
//        hex[1].setTileNumber(2);
//        hex[2].setTileNumber(2);
//        HexPoints[0] = new Point (0,0);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[2] = new Point (1,-1);
//        Assert.assertEquals(0, gameBoard.TryToAddTile(tile, HexPoints));
//    }

//    @Test
//    public void PlacingTileOnUnevenLevels() {
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(1);
//        hex[1].setLevel(1);
//        hex[2].setLevel(1);
//        hex[0].setTileNumber(1);
//        hex[1].setTileNumber(1);
//        hex[2].setTileNumber(1);
//        HexPoints[0] = new Point (0,-2);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[2] = new Point (1,-1);
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(2);
//        hex[1].setLevel(2);
//        hex[2].setLevel(2);
//        hex[0].setTileNumber(2);
//        hex[1].setTileNumber(2);
//        hex[2].setTileNumber(2);
//        HexPoints[0] = new Point (0,0);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[2] = new Point (1,-1);
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(3);
//        hex[1].setLevel(3);
//        hex[2].setLevel(3);
//        hex[0].setTileNumber(3);
//        hex[1].setTileNumber(3);
//        hex[2].setTileNumber(3);
//        HexPoints[0] = new Point (0,-2);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[2] = new Point (1,-1);
//        Assert.assertEquals(-2, gameBoard.TryToAddTile(tile, HexPoints));
//    }

    @Test
    public void BoardNotEmptyAndPlacingTileByItself() {
        gameBoard.TryToAddTile(tile, HexPoints);

        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTileNumber(1);
        hex[1].setTileNumber(1);
        hex[2].setTileNumber(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (-1,-2);
        HexPoints[2] = new Point (0,-3);

       Assert.assertEquals(-4, gameBoard.TryToAddTile(tile, HexPoints));
    }

//    @Test
//    public void PlacingTileOnEvenLevelsAndVolcanosDontOverLap() {
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(1);
//        hex[1].setLevel(1);
//        hex[2].setLevel(1);
//        hex[0].setTileNumber(1);
//        hex[1].setTileNumber(1);
//        hex[2].setTileNumber(1);
//        HexPoints[0] = new Point (0,-2);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[2] = new Point (1,-1);
//        gameBoard.TryToAddTile(tile, HexPoints);
//
//        hex[0].setLevel(2);
//        hex[1].setLevel(2);
//        hex[2].setLevel(2);
//        hex[0].setTileNumber(2);
//        hex[1].setTileNumber(2);
//        hex[2].setTileNumber(2);
//        HexPoints[2] = new Point (0,0);
//        HexPoints[1] = new Point (0,-1);
//        HexPoints[0] = new Point (1,-1);
//        Assert.assertEquals(-1, gameBoard.TryToAddTile(tile, HexPoints));
//    }
}
