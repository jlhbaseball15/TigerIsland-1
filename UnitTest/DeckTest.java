import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeckTest {
    private Deck deck;
    private Deck shuffledDeck;

    @Before
    public void CreatDeck() {
        deck = new Deck();
        shuffledDeck = new Deck("JL GL");
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
        Tile tile = shuffledDeck.getTile();
        Hex[] hex = tile.getHexes();
        Assert.assertEquals('G', hex[0].getTerrain());
        Assert.assertEquals('L', hex[1].getTerrain());
        Assert.assertEquals('V', hex[2].getTerrain());
    }
}
