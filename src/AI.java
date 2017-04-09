
import java.awt.*;
import java.util.*;


/**
 * Created by Saulo on 4/4/2017.
 */
public class AI implements Runnable{
    private static GameBoard gameboard;
    private static GameRules gamerules;
    private Queue<Message> inMessages = new LinkedList<Message>();
    private Queue<Message> outMessage = new LinkedList<Message>();
    private Message mIN = new Message();
    private Message mOUT = new Message();
    private Point tilePlacement[];
    private Point lastTilePlacedLocations[];
    private SettlementBuilder settlementBuilder;
    private int biggestXcord;
    private int biggestYcord;
    private int smallestXcord;
    private int smallestYcord;
    private Player ourPlayer;
    int whenToPlacetotoro;
    private boolean isFirst;



    public AI(boolean areWeFirst){
        gameboard =  new GameBoard();
        gameboard.addStartingTile();
        isFirst = areWeFirst;
        lastTilePlacedLocations = new Point[3];
        biggestXcord = 1;
        biggestYcord = 1;
        smallestXcord = -1;
        smallestYcord = -1;
        ourPlayer = new Player("ourPlayer");
        settlementBuilder = new SettlementBuilder();
        whenToPlacetotoro = 0;
        gamerules = new GameRules(gameboard);
    }

    public Message removeOutMessage() {
        return outMessage.remove();
    }

    public void addInMessage(Message m) {
        inMessages.add(m);
    }

    public void run() {
        if(isFirst) {
            while(true) {
                while (inMessages.isEmpty()) {  // wait for my turn
                }

                mIN = inMessages.remove();

                mOUT.setMove(mIN.getMove());
                mOUT.setGID(mIN.getGID());

                decideTilePlacement(mIN.getTile());
                decideBuildType(); // need to setup message!!!

                while (inMessages.isEmpty()) {} // did server think my move was valid?

                mIN = inMessages.remove();

                if(mIN.getIsGameOver()) {
                    break;
                }

                while (inMessages.isEmpty()) {  // my opponents move, hope he/she lost!
                }

                mIN = inMessages.remove();

                if(mIN.getIsGameOver()) {
                    break;
                }

                oppoentsTilePlacement(mIN.getTile(), mIN.getTilePoint(), mIN.getOrientation());
                oponentBuild(mIN.getBuild(), mIN.getBuildPoint(), mIN.getTerrain());
            }
        }
        else {

        }
    }

    public void decideTilePlacement(Tile tile){
        boolean tilePlaced = false;
        tilePlacement = new Point[3];
        for (int vertical = -1; vertical < biggestYcord; vertical++) {
            for (int horizontal = smallestXcord; horizontal < biggestXcord; horizontal++) {
                tilePlacement[0] = new Point(horizontal,vertical-1);
                tilePlacement[1] = new Point(horizontal+1,vertical-1);
                tilePlacement[2] = new Point(horizontal,vertical);
                try {
                    gamerules.TryToAddTile(tile, tilePlacement);
                    gameboard.addTile(tile, tilePlacement);
                    mOUT.setTile(tile);
                    mOUT.setTilePoint(tilePlacement[2]);
        // add tile orientation            mOUT.setOrientation(gameboard.);
                    tilePlaced = true;
                } catch(GameRulesException e) {
                    tilePlaced = false;
                }
                if(tilePlaced == true) {
                    break;
                }
            }
            if(tilePlaced == true) {
                lastTilePlacedLocations[0] = tilePlacement[0];
                lastTilePlacedLocations[1] = tilePlacement[1];
                lastTilePlacedLocations[2] = tilePlacement[2];
                maxAndMinCord();
                break;
            }
        }
    }

    public Point[] rotate(int x, int y, int rotation){
        Point rotatedPlacement[] = new Point[3];
        if(rotation == 0){
            rotatedPlacement[0] = new Point(x,y-1);
            rotatedPlacement[1] = new Point(x+1,y-1);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 1){
            rotatedPlacement[0] = new Point(x+1,y-1);
            rotatedPlacement[1] = new Point(x+1,y);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 2){
            rotatedPlacement[0] = new Point(x+1,y);
            rotatedPlacement[1] = new Point(x,y+1);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 3){
            rotatedPlacement[0] = new Point(x,y+1);
            rotatedPlacement[1] = new Point(x-1,y+1);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 4){
            rotatedPlacement[0] = new Point(x-1,y+1);
            rotatedPlacement[1] = new Point(x-1,y);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 5){
            rotatedPlacement[0] = new Point(x-1,y);
            rotatedPlacement[1] = new Point(x,y-1);
            rotatedPlacement[2] = new Point(x,y);
        }
        return rotatedPlacement;
    }

    public void maxAndMinCord(){
        for(Point point:lastTilePlacedLocations){
            if((int)point.getX() > biggestXcord){
                biggestXcord = (int)point.getX() + 3;
            }
            if((int)point.getX() < smallestXcord){
                smallestXcord = (int)point.getX() - 3;
            }
            if((int)point.getY() > biggestYcord){
                biggestYcord = (int)point.getY() + 3;
            }
            if((int)point.getY() < smallestYcord){
                smallestYcord = (int)point.getY() - 3;
            }
        }
    }

    public void oppoentsTilePlacement(Tile tile, Point location, int rotation){
        int x = (int)location.getX();
        int y = (int)location.getY();
        Point tileLocation[] = rotate(x,y,rotation);
        gameboard.addTile(tile, tileLocation);

        lastTilePlacedLocations[0] = tileLocation[0];
        lastTilePlacedLocations[1] = tileLocation[1];
        lastTilePlacedLocations[2] = tileLocation[2];

        maxAndMinCord();
    }

    public void decideBuildType(){
        Pieces piece = Pieces.NONE;
        BuildOptions decidedBuildOptions = BuildOptions.NOOP;
        if((whenToPlacetotoro >= 5) && (ourPlayer.gettotorosRemaining() > 0)){
            decidedBuildOptions = BuildOptions.TOTORO_SANCTUARY;
            piece = Pieces.P1_TOTORO;
            whenToPlacetotoro = 0;
        }else {
            if ((whenToPlacetotoro < 5) && (ourPlayer.getvillagersRemaining() > 0)) {
                decidedBuildOptions = BuildOptions.NEW_SETTLEMENT;
                piece = Pieces.P1_VILLAGER;
            }
            whenToPlacetotoro++;
           while( !performBuild(decidedBuildOptions, lastTilePlacedLocations[0], piece) ) {}
        }
    }

    public boolean performBuild(BuildOptions buildtype, Point location, Pieces pieceType){

        if(buildtype == BuildOptions.NEW_SETTLEMENT) {

            try {
                gamerules.tryToBuildNewSettlement(ourPlayer, location);
                gameboard.addVillagerToBoard(true, location);
                settlementBuilder.calculateSettlements(gameboard);
                return true;
            }catch(GameRulesException e){
                return false;
            }
        }
        if(buildtype == BuildOptions.TOTORO_SANCTUARY) {
            try {
                gamerules.tryToAddTotoro(ourPlayer, location, new Point(location.x+1, location.y));
                gameboard.getHexAtPointP(location).setOccupied(pieceType, 1);
                settlementBuilder.calculateSettlements(gameboard);
                return true;
            }catch(GameRulesException e){
                return false;
            }
        }
        if(buildtype == BuildOptions.TIGER_PLAYGROUND) {
            try {
                gamerules.tryToAddTiger(ourPlayer, location, new Point(location.x + 1, location.y));
                gameboard.getHexAtPointP(location).setOccupied(pieceType, 1);
                settlementBuilder.calculateSettlements(gameboard);
                return true;
            }catch(GameRulesException e){
                return false;
            }
        }
        if(buildtype == BuildOptions.EXPAND) {
            return false;  // we are not expanding as of now
        }
        if(buildtype == BuildOptions.NOOP) {
            mOUT.setBuild(BuildOptions.NOOP);
            return true;
        }
        return false;
    }

    public void oponentBuild(BuildOptions buildtype, Point location, char terrain){

        if(buildtype == BuildOptions.NEW_SETTLEMENT) {
            gameboard.addVillagerToBoard(false, location);
            try {
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TOTORO_SANCTUARY) {
            gameboard.addTotoroToBoard(false, location);
            try {
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TIGER_PLAYGROUND) {
            gameboard.addTigerToBoard(false, location);
            try {
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.EXPAND) {
            ArrayList<Point> expansion;
            try {
                expansion = gamerules.tryToExpand(new Player("Bob"), terrain, location);
                gameboard.expandSettlement(false, expansion);
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.NOOP) {
            // The Game should have ended... Why am I here
        }
    }

}

