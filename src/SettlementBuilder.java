import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SettlementBuilder {
    private ArrayList<Settlement> player1Settlements;
    private ArrayList<Settlement> player2Settlements;

    private Queue<Point> visitNext;    //Queue visitNext
    //Hashmap visited
    //Hashmap alreadyInSettlement

    public SettlementBuilder(){
        player1Settlements = new ArrayList<>();
        player2Settlements = new ArrayList<>();
        visitNext = new LinkedList<Point>();
    }

    public void calculateSettlements(GameBoard board){
        //Start at (0,0) and radiate out finding all pieces on the board
        if(board.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException(); //BoardEmptyException;
        }

        visitNext.add(new Point(0, 0));

        while(!visitNext.isEmpty()){
            //add all neighbors of current point to queue as long as not visited

        }

        //add 0,0 to queue, then call method to check all surrounding tiles
        //add unchecked tiles to queue and then continue until queue is empty
        //upon checking add to hashmap and look to see if villager/totoro exits
        //if it does call another method that expands out from here and creates new settlement
        //every tile checked is added to same hashmap. new method has its own queue but only adds if
        //adjacent tile contains ... notqueueable???

    }

    public ArrayList<Settlement> getPlayer1Settlements(){
        return player1Settlements;
    }

    public ArrayList<Settlement> getPlayer2Settlements(){
        return player2Settlements;
    }

}
