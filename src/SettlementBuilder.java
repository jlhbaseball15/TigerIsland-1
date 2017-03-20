
public class SettlementBuilder {
    private Settlement[] player1Settlements; //TODO:ARRAYLIST
    private Settlement[] player2Settlements;

    //Queue visitNext
    //Hashmap visited

    public SettlementBuilder(){

    }

    public void calculateSettlements(GameBoard board){
        //Start at (0,0) and radiate out finding all pieces on the board
        if(board.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException(); //BoardEmptyException;
        }

        //add 0,0 to queue, then call method to check all surrounding tiles
        //add unchecked tiles to queue and then continue until queue is empty
        //upon checking add to hashmap and look to see if villager/totoro exits
        //if it does call another method that expands out from here and creates new settlement
        //every tile checked is added to same hashmap. new method has its own queue but only adds if
        //adjacent tile contains ... notqueueable???


        //maybe not if i can sort points starting at top left could be easier to understand
        //go though and get all points, sort into array from top left to bottom right
        //iterate through. first add point to new settlement. then next points look for adjacent
        //points (?left and above?) if exist in a settlement (hash) add to it. otherwise create new
        //continue until end of array...
    }

    public void getPlayer1Settlements(){

    }

    public void getPlayer2Settlements(){

    }

}
