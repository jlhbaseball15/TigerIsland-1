import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;

public class OrientationTest {

    private Deck deck;
    private GameBoard gameboard;

    @Before
    public void Setup(){
        gameboard= new GameBoard();
        deck = new Deck();
        Player Playerone=new Player("Playerone");
        Player Playertwo=new Player("Playertwo");
        }

    @Test
    public void testorientation1(){
        int orientation=0;
        Point spot = new Point(0,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(0,result[0].x);
       Assert.assertEquals(-1,result[0].y);
        Assert.assertEquals(1,result[1].x);
       Assert.assertEquals(-1,result[1].y);
        Assert.assertEquals(0,result[2].x);
        Assert.assertEquals(0,result[2].y);
    }

    @Test
    public void testorientation2(){
        int orientation=1;
        Point spot = new Point(0,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(1,result[0].x);
        Assert.assertEquals(-1,result[0].y);
        Assert.assertEquals(1,result[1].x);
        Assert.assertEquals(0,result[1].y);
        Assert.assertEquals(0,result[2].x);
       Assert.assertEquals(0,result[2].y);
    }
    @Test
    public void testorientation3(){
        int orientation=2;
        Point spot = new Point(0,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(1,result[0].x);
        Assert.assertEquals(0,result[0].y);
        Assert.assertEquals(0,result[1].x);
        Assert.assertEquals(1,result[1].y);
        Assert.assertEquals(0,result[2].x);
        Assert.assertEquals(0,result[2].y);
    }
    @Test
    public void testorientation4(){
        int orientation=3;
        Point spot = new Point(0,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(0,result[0].x);
        Assert.assertEquals(1,result[0].y);
        Assert.assertEquals(-1,result[1].x);
        Assert.assertEquals(1,result[1].y);
        Assert.assertEquals(0,result[2].x);
        Assert.assertEquals(0,result[2].y);
    }
    @Test
    public void testorientation5(){
        int orientation=4;
        Point spot = new Point(0,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(-1,result[0].x);
        Assert.assertEquals(1,result[0].y);
        Assert.assertEquals(-1,result[1].x);
        Assert.assertEquals(0,result[1].y);
        Assert.assertEquals(0,result[2].x);
        Assert.assertEquals(0,result[2].y);
    }
    @Test
    public void testorientation6(){
        int orientation=5;
        Point spot = new Point(0,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(-1,result[0].x);
        Assert.assertEquals(0,result[0].y);
        Assert.assertEquals(0,result[1].x);
        Assert.assertEquals(-1,result[1].y);
        Assert.assertEquals(0,result[2].x);
        Assert.assertEquals(0,result[2].y);
    }
    @Test
    public void randomtestfor1(){
        int orientation=0;
        Point spot = new Point(1,0);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(1,result[0].x);
        Assert.assertEquals(-1,result[0].y);
        Assert.assertEquals(2,result[1].x);
        Assert.assertEquals(-1,result[1].y);
        Assert.assertEquals(1,result[2].x);
        Assert.assertEquals(0,result[2].y);
    }

    @Test
    public void randomtestfor3(){
        int orientation=2;
        Point spot = new Point(2,-2);
        Point[] result = gameboard.rotate(spot,orientation);
        Assert.assertEquals(3,result[0].x);
        Assert.assertEquals(-2,result[0].y);
        Assert.assertEquals(2,result[1].x);
        Assert.assertEquals(-1,result[1].y);
        Assert.assertEquals(2,result[2].x);
        Assert.assertEquals(-2,result[2].y);
    }
}
