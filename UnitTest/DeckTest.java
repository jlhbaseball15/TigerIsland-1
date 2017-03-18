import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeckTest {
    private Deck deck;
    private Deck shuffledDeck;
    private Deck DeckBecomesEmpty;


    @Before
    public void CreateDecks() {
        deck = new Deck();
        shuffledDeck = new Deck("JL GL");
        DeckBecomesEmpty = new Deck("JG");
    }

    @Test
    public void ReceiveThreeHexes() {
        Tile tile = deck.getTile();
        Hex[] hex = tile.getHexes();
        Assert.assertEquals('G', hex[0].getTerrain());
        Assert.assertEquals('G', hex[1].getTerrain());
        Assert.assertEquals('V', hex[2].getTerrain());
    }
     @Test
    public void ShuffledReceiveThreeHexes() {
        Tile tile = shuffledDeck.getTile();
        Hex[] hex = tile.getHexes();
        Assert.assertEquals('J', hex[0].getTerrain());
        Assert.assertEquals('L', hex[1].getTerrain());
        Assert.assertEquals('V', hex[2].getTerrain());
    }
    @Test
    public void LastTileGetsDrawnFromDeck() {
        Tile tile = DeckBecomesEmpty.getTile();
        Hex[] hex = tile.getHexes();
        Assert.assertEquals('J', hex[0].getTerrain());
        Assert.assertEquals('G', hex[1].getTerrain());
        Assert.assertEquals('V', hex[2].getTerrain());
    }
    
}
