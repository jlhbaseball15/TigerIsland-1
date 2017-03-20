/**
 * Created by Saulo on 3/20/2017.
 */
public class Player {
    private String playerName;
    private int score;
    private int villagersRemaining;
    private int totorosRemaining;

    public Player(){
        score = 0;
        villagersRemaining = 20;
        totorosRemaining = 3;
    }

    public void MeepleBeingPlaced(int numPlaced){
        villagersRemaining = villagersRemaining-numPlaced;
    }
    public void totoroBeingPlaced(){
        totorosRemaining = totorosRemaining -1;
    }

}
