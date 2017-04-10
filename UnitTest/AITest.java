/**
 * Created by Saulo on 4/8/2017.
 */

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
public class AITest {
    private Tile tile0;
    private Tile tile;
    private Tile tile2;
    private Tile tile3;
    private Tile tile4;
    private Tile tile5;
    private Tile tile6;
    private Tile tile7;
    private Tile tile8;
    private Tile tile9;
    private Tile tile10;
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
        ourAI.oppoentsTilePlacement(tile2,point,3);

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
        ourAI.oppoentsTilePlacement(tile2,point,0);

        tile = new Tile('R','J');
        ourAI.decideTilePlacement(tile);


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
    @Test
    public void firstVillagerAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);
        ourAI.decideBuildType();
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(0,-1).getPiece());
        Assert.assertEquals('J', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, -1)).getTerrain());
        Assert.assertEquals('R', ourAI.returnGameBoard().getHexAtPointP(new Point(-1, -1)).getTerrain());
        Assert.assertEquals('V', ourAI.returnGameBoard().getHexAtPointP(new Point(-2, 0)).getTerrain());
    }
    @Test
    public void secondVillagerAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);
        ourAI.decideBuildType();


        tile2 = new Tile('G','L');
        ourAI.decideTilePlacement(tile2);
        ourAI.decideBuildType();

        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(0,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-1,-1).getPiece());

    }
    @Test
    public void thirdVillagerAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);
        ourAI.decideBuildType();


        tile2 = new Tile('G','L');
        ourAI.decideTilePlacement(tile2);
        ourAI.decideBuildType();

        tile3 = new Tile('R','G');
        ourAI.decideTilePlacement(tile3);
        ourAI.decideBuildType();


        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(0,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-1,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-2,-1).getPiece());


    }
    @Test
    public void fithVillagerAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);
        ourAI.decideBuildType();


        tile2 = new Tile('G','L');
        ourAI.decideTilePlacement(tile2);
        ourAI.decideBuildType();

        tile3 = new Tile('R','G');
        ourAI.decideTilePlacement(tile3);
        ourAI.decideBuildType();

        tile4 = new Tile('G','L');
        ourAI.decideTilePlacement(tile4);
        ourAI.decideBuildType();

        tile5 = new Tile('R','G');
        ourAI.decideTilePlacement(tile5);
        ourAI.decideBuildType();

        tile6 = new Tile('J','R');
        ourAI.decideTilePlacement(tile6);
        ourAI.decideBuildType();


        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(0,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-1,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-2,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-3,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-4,-1).getPiece());
        Assert.assertEquals(Pieces.P1_TOTORO, ourAI.returnGameBoard().getHexAtPointP(-5,-1).getPiece());
    }
    @Test
    public void newSettlementVillagerAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J','R');
        ourAI.decideTilePlacement(tile);
        ourAI.decideBuildType();


        tile2 = new Tile('G','L');
        ourAI.decideTilePlacement(tile2);
        ourAI.decideBuildType();

        tile3 = new Tile('R','G');
        ourAI.decideTilePlacement(tile3);
        ourAI.decideBuildType();

        tile4 = new Tile('G','L');
        ourAI.decideTilePlacement(tile4);
        ourAI.decideBuildType();

        tile5 = new Tile('R','G');
        ourAI.decideTilePlacement(tile5);
        ourAI.decideBuildType();

        tile6 = new Tile('J','R');
        ourAI.decideTilePlacement(tile6);
        ourAI.decideBuildType();

        tile6 = new Tile('G','J');
        ourAI.decideTilePlacement(tile6);
        ourAI.decideBuildType();

        Assert.assertEquals(Pieces.P1_TOTORO, ourAI.returnGameBoard().getHexAtPointP(-5,-1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-7,-1).getPiece());
    }

    @Test
    public void newPatternVillagerAIPlaced() {
        ourAI = new AI(true);
        tile = new Tile('J', 'R');
        ourAI.decideTilePlacement(tile);
        ourAI.decideBuildType();


        tile2 = new Tile('G', 'L');
        ourAI.decideTilePlacement(tile2);
        ourAI.decideBuildType();

        tile3 = new Tile('R', 'G');
        ourAI.decideTilePlacement(tile3);
        ourAI.decideBuildType();

        tile4 = new Tile('G', 'L');
        ourAI.decideTilePlacement(tile4);
        ourAI.decideBuildType();

        tile5 = new Tile('R', 'G');
        ourAI.decideTilePlacement(tile5);
        ourAI.decideBuildType();

        tile6 = new Tile('J', 'R');
        ourAI.decideTilePlacement(tile6);
        ourAI.decideBuildType();

        tile7 = new Tile('G', 'J');
        ourAI.decideTilePlacement(tile7);
        ourAI.decideBuildType();

        tile8 = new Tile('G', 'J');
        ourAI.decideTilePlacement(tile8);
        ourAI.decideBuildType();

        Assert.assertEquals(Pieces.P1_TOTORO, ourAI.returnGameBoard().getHexAtPointP(-5, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-7, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-8, -1).getPiece());
    }


    @Test
    public void completedGameAIPlaced() {
        ourAI = new AI(true);
        for(int i = 0; i<4;i++) {
            tile = new Tile('J','R');
            ourAI.decideTilePlacement(tile);
            ourAI.decideBuildType();

            tile2 = new Tile('G', 'L');
            ourAI.decideTilePlacement(tile2);
            ourAI.decideBuildType();

            tile3 = new Tile('R', 'G');
            ourAI.decideTilePlacement(tile3);
            ourAI.decideBuildType();

            tile4 = new Tile('G', 'L');
            ourAI.decideTilePlacement(tile4);
            ourAI.decideBuildType();

            tile5 = new Tile('R', 'G');
            ourAI.decideTilePlacement(tile5);
            ourAI.decideBuildType();

            tile6 = new Tile('J', 'R');
            ourAI.decideTilePlacement(tile6);
            ourAI.decideBuildType();
        }
        tile7 = new Tile('J', 'R');
        ourAI.decideTilePlacement(tile7);
        ourAI.decideBuildType();


        Assert.assertEquals(Pieces.P1_TOTORO, ourAI.returnGameBoard().getHexAtPointP(-5, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-7, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-8, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-9, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-10, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-11, -1).getPiece());
        Assert.assertEquals(Pieces.P1_TOTORO, ourAI.returnGameBoard().getHexAtPointP(-12, -1).getPiece());
        Assert.assertEquals(Pieces.P1_VILLAGER, ourAI.returnGameBoard().getHexAtPointP(-14, -1).getPiece());


    }
}
