import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.IdentityHashMap;


/**
 * Created by dontf on 3/14/2017.
 */
public class Maptest {

    private map gameBoard;
    private Hex hex[];
    private char type[] = {'V', 'L', 'G'};
    private Point HexPoints[];

    @Before
    public void MapObject(){
        gameBoard = new map();
        hex = new Hex[3];
        HexPoints = new Point [3];
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (1,0);
        HexPoints[2] = new Point (1,1);
        for (int i = 0; i < 3; ++i) {
            hex[i] = new Hex(type[i]);
            hex[i].setLevel(1);
        }
    }

    @Test
    public void PlaceFirstTile() {
        gameBoard.TryToAddTile(hex, HexPoints);
        Assert.assertFalse(gameBoard.isEmpty());
    }

    @Test
    public void GettingMap() {
        gameBoard.TryToAddTile(hex, HexPoints);
        HashMap<Integer, HashMap<Integer, Hex>> currentBoard = gameBoard.getMap();
        Assert.assertEquals('V', currentBoard.get(0).get(0).getTerrain());
    }

    @Test
    public void PlacingATileThatIsNotFlat() {
        gameBoard.TryToAddTile(hex, HexPoints);
        HexPoints[2] = new Point(-1, 1);
        Assert.assertEquals(-1, gameBoard.TryToAddTile(hex, HexPoints));
    }

    @Test
    public void PlacingATileOnTopOfAnother() {
        gameBoard.TryToAddTile(hex, HexPoints);
        Assert.assertEquals(-1, gameBoard.TryToAddTile(hex, HexPoints));
    }

    @Test
    public void PlacingASecondTileNextToFirstTile() {
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0] = new Hex(type[0]);
        hex[1] = new Hex(type[1]);
        hex[2] = new Hex(type[2]);
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTile(1);
        hex[1].setTile(1);
        hex[2].setTile(1);
        HexPoints[0] = new Point(0, -2);
        HexPoints[1] = new Point(0, -1);
        HexPoints[2] = new Point(1, -1);
        Assert.assertEquals(0, gameBoard.TryToAddTile(hex, HexPoints));
    }

    @Test
    public void PlacingTileOnEvenLevels() {
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0] = new Hex(type[0]);
        hex[1] = new Hex(type[1]);
        hex[2] = new Hex(type[2]);
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTile(1);
        hex[1].setTile(1);
        hex[2].setTile(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0] = new Hex(type[0]);
        hex[1] = new Hex(type[1]);
        hex[2] = new Hex(type[2]);
        hex[0].setLevel(2);
        hex[1].setLevel(2);
        hex[2].setLevel(2);
        hex[0].setTile(2);
        hex[1].setTile(2);
        hex[2].setTile(2);
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        Assert.assertEquals(0, gameBoard.TryToAddTile(hex, HexPoints));
    }

    @Test
    public void PlacingTileOnUnevenLevels() {
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0] = new Hex(type[0]);
        hex[1] = new Hex(type[1]);
        hex[2] = new Hex(type[2]);
        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTile(1);
        hex[1].setTile(1);
        hex[2].setTile(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0] = new Hex(type[0]);
        hex[1] = new Hex(type[1]);
        hex[2] = new Hex(type[2]);
        hex[0].setLevel(2);
        hex[1].setLevel(2);
        hex[2].setLevel(2);
        hex[0].setTile(2);
        hex[1].setTile(2);
        hex[2].setTile(2);
        HexPoints[0] = new Point (0,0);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0] = new Hex(type[0]);
        hex[1] = new Hex(type[1]);
        hex[2] = new Hex(type[2]);
        hex[0].setLevel(3);
        hex[1].setLevel(3);
        hex[2].setLevel(3);
        hex[0].setTile(3);
        hex[1].setTile(3);
        hex[2].setTile(3);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (0,-1);
        HexPoints[2] = new Point (1,-1);
        Assert.assertEquals(-1, gameBoard.TryToAddTile(hex, HexPoints));
    }

    @Test
    public void BoardNotEmptyAndPlacingTileByItself() {
        gameBoard.TryToAddTile(hex, HexPoints);

        hex[0].setLevel(1);
        hex[1].setLevel(1);
        hex[2].setLevel(1);
        hex[0].setTile(1);
        hex[1].setTile(1);
        hex[2].setTile(1);
        HexPoints[0] = new Point (0,-2);
        HexPoints[1] = new Point (-1,-2);
        HexPoints[2] = new Point (0,-3);

       Assert.assertEquals(-1, gameBoard.TryToAddTile(hex, HexPoints));
    }
}
