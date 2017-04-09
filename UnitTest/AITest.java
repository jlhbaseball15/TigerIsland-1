/**
 * Created by Saulo on 4/8/2017.
 */

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.*;
import java.awt.*;
public class AITest {
    private Tile tile0;
    private Tile tile;
    private Tile tile2;
    private Tile tile3;
    private AI ourAI;

    @Test
    public void addingAStartTileToBoard() {
        ourAI = new AI(true);
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(0, 0)).getTerrain());
        Assert.assertEquals('J',  ourAI.returnGameBoard().getHexAtPointP(new Point(0, -1)).getTerrain());
        Assert.assertEquals('L', ourAI.returnGameBoard().getHexAtPointP(new Point(1, -1)).getTerrain());
        Assert.assertEquals('G', ourAI.returnGameBoard().getHexAtPointP(new Point(0, 1)).getTerrain());
        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-1, 1)).getTerrain());

    }
    @Test
    public void firstTileAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);
        for(Point point:ourAI.getlastTilePlacedLocations()) {
            System.out.println(point.getX() +" "+ point.getY());
        }
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, -1)).getTerrain());
        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-1, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, 0)).getTerrain());
    }
    @Test
    public void secondTileAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);

        tile2 = new Tile('G','J');
        ourAI.decideTilePlacement(tile2);

        for(Point point:ourAI.getlastTilePlacedLocations()) {
            System.out.println(point.getX() +" "+ point.getY());
        }
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, -1)).getTerrain());
        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-1, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, 0)).getTerrain());

        Assert.assertEquals('G', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, -1)).getTerrain());
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-3, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, 0)).getTerrain());
    }
    @Test
    public void ThirdTileAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);

        tile2 = new Tile('G','J');
        ourAI.decideTilePlacement(tile2);

        tile3 = new Tile('R','G');
        ourAI.decideTilePlacement(tile3);

        for(Point point:ourAI.getlastTilePlacedLocations()) {
            System.out.println(point.getX() +" "+ point.getY());
        }
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, -1)).getTerrain());
        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-1, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, 0)).getTerrain());

        Assert.assertEquals('G', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, -1)).getTerrain());
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-3, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, 0)).getTerrain());

        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-6, -1)).getTerrain());
        Assert.assertEquals('G', ourAI.returnGameBoard().getHexAtPointP(new Point(-5, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-6, 0)).getTerrain());
    }
    @Test
    public void enemyPlacedTiles() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);

        Point point = new Point(-3,0);
        tile2 = new Tile('G','J');
        ourAI.oppoentsTilePlacement(tile2,point,4);

        for(Point point2:ourAI.getlastTilePlacedLocations()) {
            System.out.println(point2.getX() +" "+ point2.getY());
        }

        Assert.assertEquals('G', ourAI.returnGameBoard().getHexAtPointP(new Point(-3, 1)).getTerrain());
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, 1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-3, 0)).getTerrain());
    }
    @Test
    public void EnemyPlacedTiles2() {
        ourAI = new AI(true);
        tile0 = new Tile('J','R');
        ourAI.decideTilePlacement(tile0);

        Point point = new Point(-4,0);
        tile2 = new Tile('G','J');
        ourAI.oppoentsTilePlacement(tile2,point,1);

        tile = new Tile('R','J');
        ourAI.decideTilePlacement(tile);

        for(Point point3:ourAI.getlastTilePlacedLocations()) {
            System.out.println(point3.getX() +" "+ point3.getY());
        }

        Assert.assertEquals('G', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, -1)).getTerrain());
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-3, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-4, 0)).getTerrain());

        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-6, -1)).getTerrain());
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-5, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-6, 0)).getTerrain());
    }

    @Test
    public void largeBuild() {
        ourAI = new AI(true);
        tile = new Tile('J','R');

        for (int x = 0; x < 143; x++) {
            ourAI.decideTilePlacement(tile);

            Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-2 + (x * -2), -1)).getTerrain());
            Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-1 + (x * -2), -1)).getTerrain());
            Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-2 + (x * -2), 0)).getTerrain());
        }
    }
}
