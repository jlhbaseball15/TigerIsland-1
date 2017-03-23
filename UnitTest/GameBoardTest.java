import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

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
       HashMap<Point, Hex> currentBoard = gameBoard.getMap();
        Assert.assertEquals('V', currentBoard.get(new Point(1,1)).getTerrain());
    }

    @Test
    public void GettingTilesOnBoard() {
        tile = deck.getTile();
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        HexPoints[0] = new Point(2,0);
        HexPoints[1] = new Point(2,1);
        HexPoints[2] = new Point(3,1);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        HexPoints[0] = new Point(3,0);
        HexPoints[1] = new Point(4,0);
        HexPoints[2] = new Point(4,1);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        HexPoints[0] = new Point(1,-2);
        HexPoints[1] = new Point(2,-1);
        HexPoints[2] = new Point(2,-2);
        gameBoard.AddTile(tile, HexPoints);

        tile = deck.getTile();
        HexPoints[0] = new Point(3,-2);
        HexPoints[1] = new Point(3,-1);
        HexPoints[2] = new Point(4,-1);
        gameBoard.AddTile(tile, HexPoints);

        Vector<Tile> tileVector = gameBoard.getTilesOnBoard();
        Assert.assertTrue(tileVector.size() == 5);
    }

    @Test
    public void NoHashConflicts() {
        Point hexLoc[] = new Point[3];
        Point temp[] = new Point[3];

        int k = 0;
        for (int i = -3; i < 6; ++i) {
            for (int j = -8; j < 8; ++j) {
                if (k != 0 && (k % 3) == 0) {
                    gameBoard.AddTile(deck.getTile(), hexLoc);
                    temp = hexLoc;
                }
                hexLoc[k % 3] = new Point(i, j);
                ++k;
            }
        }
        gameBoard.AddTile(deck.getTile(), hexLoc);

        Integer numHexes = 144;
        boolean isCorrect = false;
        if (numHexes.equals(gameBoard.size())) {
            isCorrect = true;
        }
        Assert.assertTrue(isCorrect);
    }
}
