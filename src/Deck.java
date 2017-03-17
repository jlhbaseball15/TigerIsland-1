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
    public Deck(String unparsedDeck ){
        String[] parsedByTile =  unparsedDeck.split("\\s+");
        for(int i = 0; i < 48; i++){
            switch(parsedByTile[i]){
                case("JJ"):
                    deck.push(new Tile('J','J'));
                    break;
                case("JR"):
                    deck.push(new Tile('J','R'));
                    break;
                case("JL"):
                    deck.push(new Tile('J','L'));
                    break;
                case("JG"):
                    deck.push(new Tile('J','G'));
                    break;
                case("RJ"):
                    deck.push(new Tile('R','J'));
                    break;
                case("RR"):
                    deck.push(new Tile('R','R'));
                    break;
                case("RL"):
                    deck.push(new Tile('R','L'));
                    break;
                case("RG"):
                    deck.push(new Tile('R','G'));
                    break;
                case("LJ"):
                    deck.push(new Tile('L','J'));
                    break;
                case("LR"):
                    deck.push(new Tile('L','R'));
                    break;
                case("LL"):
                    deck.push(new Tile('L','L'));
                    break;
                case("LG"):
                    deck.push(new Tile('L','G'));
                    break;
                case("GJ"):
                    deck.push(new Tile('G','J'));
                    break;
                case("GR"):
                    deck.push(new Tile('G','R'));
                    break;
                case("GL"):
                    deck.push(new Tile('G','L'));
                    break;
                case("GG"):
                    deck.push(new Tile('G','G'));
                    break;
        }
        
        
            }
        }

    public Tile getTile() {
        return deck.pop();
    }

}