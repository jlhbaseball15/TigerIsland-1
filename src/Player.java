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

    public void villagersBeingPlaced(int numPlaced){
        if(numPlaced <= villagersRemaining) {
            villagersRemaining = villagersRemaining - numPlaced;
            score = score + numPlaced * numPlaced;
        }else{
            throw new IndexOutOfBoundsException("tried to place more villagers than available.");
        }
    }
    public void totoroBeingPlaced(){
        if(1 <= totorosRemaining) {
            totorosRemaining = totorosRemaining - 1;
            score = score + 200;
        }else{
            throw new IndexOutOfBoundsException("tried to place more totoros than available.");
        }
    }

    public void tigerBeingPlaced() {
        if(1 <= tigersRemaining) {
            tigersRemaining = tigersRemaining - 1;
            score = score + 75;
        }else{
            throw new IndexOutOfBoundsException("tried to place more tigers than available.");
        }
    }

    public int getScore(){
        return score;
    }

    public int getvillagersRemaining(){
        return villagersRemaining;
    }

    public int gettotorosRemaining(){
        return totorosRemaining;
    }

    public int gettigersRemaining(){
        return tigersRemaining;
    }

    public int getTotalPiecesRemaining() { return villagersRemaining + totorosRemaining + tigersRemaining; }

    /*
    public TilePlacement placetile(Tile tile){
        //calls a funtion to decide what movement to do if our player
        //return input from tcp sever if not our player
        //returns a TilePlacement

    }*/

}
