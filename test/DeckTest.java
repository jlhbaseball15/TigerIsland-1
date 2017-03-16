import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dontf on 3/16/2017.
 */
public class DeckTest {
    private Deck MyDeck;

    @Before
    public void CreatDeck() {
        MyDeck = new Deck();
    }

    @Test
    public void RecieveThreeHexes() {
        Hex[][] hex = MyDeck.getTile();
        Assert.assertEquals('A', hex[0][0].getTerrain());
        Assert.assertEquals('A', hex[0][1].getTerrain());
        Assert.assertEquals('V', hex[0][2].getTerrain());
    }
}
