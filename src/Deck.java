import java.util.Stack;

public class Deck {
    private static final int NUM_TERRAIN = 4;
    private char[] terrain = {'J', 'R', 'L', 'G'};
    private Stack<Tile> deck = new Stack<>();

    public Deck() {
        for(int i = 0; i < NUM_TERRAIN; i++){
            for(int j = 0; j < NUM_TERRAIN; j++){
                deck.push(new Tile(terrain[i], terrain[j]));
            }
        }
    }

    //for use later when tcp server pases us the shuffled deck order as a string
    public Deck(String unparsedDeck ){
        createShuffledDeck(unparsedDeck);
    }

    public void createShuffledDeck( String unparsedDeck){
        Stack<Tile> deck2 = new Stack<>();
        String[] parsedByTile =  unparsedDeck.split("\\s+");
        String[] parsedByTileReversed = new String[parsedByTile.length];
        int reversedArrayIndex = 0;
        for(int i = parsedByTile.length-1; i >= 0; i--){
            parsedByTileReversed[reversedArrayIndex] = parsedByTile[i];
            reversedArrayIndex++;
        }
        for(String terrains : parsedByTileReversed){
            deck2.push(new Tile(terrains.charAt(0), terrains.charAt(1)));
        }
        deck = deck2;
    }



    public Tile getTile() {
        return deck.pop();
    }

}
