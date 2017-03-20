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

    @Before
    public void MapObject(){
        gameBoard = new GameBoard();
        deck = new Deck();
        HexPoints = new Point[3];
        HexPoints[0] = new Point(0,0);
        HexPoints[1] = new Point(1,0);
        HexPoints[2] = new Point(1,1);
    }

    @Test
    public void MapIsEmpty() {
        Assert.assertTrue(gameBoard.isEmpty());
    }

    @Test
    public void MapIsNotEmpty() {
        tile = deck.getTile();
        gameBoard.AddTile(tile, HexPoints);
        Assert.assertFalse(gameBoard.isEmpty());
    }

    @Test
    public void GettingMap() {
        tile = deck.getTile();
        gameBoard.AddTile(tile, HexPoints);
        HashMap<Integer, HashMap<Integer, Hex>> currentBoard = gameBoard.getMap();
        Assert.assertEquals('V', currentBoard.get(1).get(1).getTerrain());
    }
}
