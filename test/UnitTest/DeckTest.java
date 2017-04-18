package UnitTest;

import game.Deck;
import game.Hex;
import game.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.EmptyStackException;

public class DeckTest {
    private Deck deck;
    private Deck shuffledDeck;
    private Deck DeckBecomesEmpty;
    private Deck drawAfterEmptyDeck;



    @Before
    public void CreateDecks() {
        deck = new Deck();
        shuffledDeck = new Deck("JL GL");
        DeckBecomesEmpty = new Deck("JG");
        drawAfterEmptyDeck = new Deck("RR");

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

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void drawFromEmptyDeck() {
        Tile tile = drawAfterEmptyDeck.getTile();
        exception.expect(EmptyStackException.class);
        Tile SecondTile = drawAfterEmptyDeck.getTile();
    }
    
}
