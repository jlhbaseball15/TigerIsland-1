import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dontf on 3/16/2017.
 */
public class DeckTest {
    private Deck deck;

    @Before
    public void CreatDeck() {
        deck = new Deck();
    }

    @Test
    public void RecieveThreeHexes() {
        Tile tile = deck.getTile();
        Hex[] hex = tile.getHexes();
        Assert.assertEquals('G', hex[0].getTerrain());
        Assert.assertEquals('G', hex[1].getTerrain());
        Assert.assertEquals('V', hex[2].getTerrain());
    }
     @Test
    public void ShuffledRecieveThreeHexes() {
        Tile tile = ShuffledDeck.getTile();
        Hex[] hex = tile.getHexes();
        Assert.assertEquals('G', hex[0].getTerrain());
        Assert.assertEquals('L', hex[1].getTerrain());
        Assert.assertEquals('V', hex[2].getTerrain());
    }
}
