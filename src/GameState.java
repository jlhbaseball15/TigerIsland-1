import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Saulo on 3/20/2017.
 */
public class GameState {
    private Player firstPlayer;
    private Player secondPlayer;
    private Deck mainDeck = new Deck();
    private GameBoard gameboard;

    public GameState(String nameOfPlayerWhoGoesFirst, String nameOfPlayerWhoGoessecond, Deck deck){
        mainDeck = deck;
        firstPlayer = new Player(nameOfPlayerWhoGoesFirst);
        secondPlayer = new Player(nameOfPlayerWhoGoessecond);
    }

    public void startGame(){

    }



}
