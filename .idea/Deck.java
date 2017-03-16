/**
 * Created by dontf on 3/16/2017.
 */
public class Deck {
    public Deckdesigner newDeck;
    public int currentTile;

    public Deck() {
        currentTile = 0;
        newDeck = new Deckdesigner();
    }

    public Hex[][] getTile() {
        return newDeck.getTile();
    }

}
