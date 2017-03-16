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

    public Tile getTile() {
        return deck.pop();
    }

}