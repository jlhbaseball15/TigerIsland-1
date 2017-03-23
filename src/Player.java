/**
 * Created by Saulo on 3/20/2017.
 */
public class Player {
    private String playerName;
    private int score;
    private int villagersRemaining;
    private int totorosRemaining;
    private int tigersRemaining;

    public Player(String playername){
        score = 0;
        villagersRemaining = 20;
        totorosRemaining = 3;
        tigersRemaining = 2;
        playerName = playername;
    }

    public void MeepleBeingPlaced(int numPlaced){
        villagersRemaining = villagersRemaining-numPlaced;
        score = score + numPlaced*numPlaced;
    }
    public void totoroBeingPlaced(){
        totorosRemaining = totorosRemaining -1;
        score = score+200;
    }

    public void tigerBeingPlaced() {
        tigersRemaining = tigersRemaining - 1;
        score = score + 75;
    }

    public int getScore(){
        return score;
    }

    /*
    public TilePlacement placetile(Tile tile){
        //calls a funtion to decide what movement to do if our player
        //return input from tcp sever if not our player
        //returns a TilePlacement

    }*/

}
