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
        String[] parsedByTile =  unparsedDeck.split("\\s+");
        for(String terrains : parsedByTile){
            deck.push(new Tile(terrains.charAt(0), terrains.charAt(1)));
        }    
    }

    public Tile getTile() {
        return deck.pop();
    }

}
