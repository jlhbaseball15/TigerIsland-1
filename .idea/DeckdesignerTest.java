import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dontf on 3/16/2017.
 */
public class DeckdesignerTest {

    @Test
    public void CheckDecksFirstTile() {
        Deckdesigner createDeck = new Deckdesigner();
        Assert.assertEquals('A', createDeck.getTile()[0][0].getTerrain());
    }

    @Test
    public void RecieveThreeHexes() {
        Deckdesigner createDeck = new Deckdesigner();
        Hex[][] hex = createDeck.getTile();
        Assert.assertEquals('A', hex[0][0].getTerrain());
        Assert.assertEquals('A', hex[0][1].getTerrain());
        Assert.assertEquals('V', hex[0][2].getTerrain());
    }
}
