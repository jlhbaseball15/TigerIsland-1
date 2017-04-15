import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created by Saulo on 4/4/2017.
 */
public class AI implements Runnable{
    private GameBoard gameboard;
    private GameRules gamerules;
    private volatile ConcurrentLinkedQueue<Message> inMessages;
    private volatile ConcurrentLinkedQueue<Message> outMessage;
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
    private int whenToPlacetotoro;
    private boolean isFirst;
    private String PID;
    private boolean notThefirstPiece;
    private Point lastPiecePlaced;
    private int verticalBranchLocation;
    private int timesTotoroBuildInterrupted;





    public AI(boolean areWeFirst, ConcurrentLinkedQueue<Message> in, ConcurrentLinkedQueue<Message> out, String pid){
        gameboard =  new GameBoard();
        gameboard.addStartingTile();
        isFirst = areWeFirst;
        lastTilePlacedLocations = new Point[3];
        biggestXcord = 3;
        biggestYcord = 3;
        smallestXcord = -3;
        smallestYcord = -3;
        ourPlayer = new Player("ourPlayer");
        settlementBuilder = new SettlementBuilder();
        whenToPlacetotoro = 0;
        gamerules = new GameRules(gameboard);
        inMessages = in;
        outMessage = out;
        PID = pid;
        notThefirstPiece = false;
        verticalBranchLocation = 0;
        timesTotoroBuildInterrupted = 0;
    }

    public AI(boolean areWeFirst){
        gameboard =  new GameBoard();
        gameboard.addStartingTile();
        isFirst = areWeFirst;
        lastTilePlacedLocations = new Point[3];
        biggestXcord = 3;
        biggestYcord = 3;
        smallestXcord = -3;
        smallestYcord = -3;
        ourPlayer = new Player("ourPlayer");
        settlementBuilder = new SettlementBuilder();
        whenToPlacetotoro = 0;
        gamerules = new GameRules(gameboard);
        notThefirstPiece = false;
        verticalBranchLocation = 0;
        timesTotoroBuildInterrupted = 0;

    }

    public Message removeOutMessage() {
        return outMessage.remove();
    }

    public void addInMessage(Message m) {
        inMessages.add(m);
    }

    public void run() {
        boolean running = true;
        if(isFirst) {
            while(true) {
                while (inMessages.isEmpty()) {  // wait for my turn
                    if(Thread.interrupted()) {
                        running = false;
                        break;
                    }
                }
                if (!running) {
                    break;
                }
                mIN = inMessages.remove();

                if(mIN.isMove) {

                    mOUT = new Message();
                    mOUT.setMove(mIN.getMove());
                    mOUT.setGID(mIN.getGID());

                    decideTilePlacement(mIN.getTile());
                    decideBuildType();


                    outMessage.add(mOUT);

                }
                else if (PID.equals(mIN.getPID())) {
                    if (mIN.getIsGameOver()) {
                        break;
                    }
                }
                else {
                    if (mIN.getIsGameOver()) {
                        break;
                    }


                    oppoentsTilePlacement(mIN.getTile(), mIN.getTilePoint(), mIN.getOrientation());
                 try {
                     oponentBuild(mIN.getBuild(), mIN.getBuildPoint(), mIN.getTerrain());
                 } catch(NullPointerException e) {

                 }
                }
            }
        }
        else {
            while(true) {

                while (inMessages.isEmpty()) {  // wait for my turn
                    if(Thread.interrupted()) {
                        running = false;
                        break;
                    }
                }
                if (!running) {
                    break;
                }
                mIN = inMessages.remove();

                if(mIN.isMove) {

                    mOUT = new Message();
                    mOUT.setMove(mIN.getMove());
                    mOUT.setGID(mIN.getGID());

                    decideTilePlacement(mIN.getTile());
                    decideBuildType();

                    outMessage.add(mOUT);

                }
                else {
                    if (PID.equals(mIN.getPID())) {
                        if (mIN.getIsGameOver()) {
                            break;
                        }
                    } else {
                        if (mIN.getIsGameOver()) {
                            break;
                        }

                        oppoentsTilePlacement(mIN.getTile(), mIN.getTilePoint(), mIN.getOrientation());
                        try {
                            oponentBuild(mIN.getBuild(), mIN.getBuildPoint(), mIN.getTerrain());
                        } catch(NullPointerException e) {

                        }
                    }

                }
            }
        }
    }

    public void decideTilePlacement(Tile tile){
        boolean tilePlaced = false;
        tilePlacement = new Point[3];
        for (int vertical = verticalBranchLocation; vertical < biggestYcord+3; vertical++) {

            for (int horizontal = smallestXcord-3; horizontal < biggestXcord+3; horizontal++) {

                tilePlacement[0] = new Point(horizontal,vertical-1);
                tilePlacement[1] = new Point(horizontal+1,vertical-1);
                tilePlacement[2] = new Point(horizontal,vertical);
                try {
                    gamerules.TryToAddTile(tile, tilePlacement);
                    gameboard.addTile(tile, tilePlacement);
                    mOUT.setTile(tile);
                    mOUT.setTilePoint(tilePlacement[2]);
                    mOUT.setOrientation(gameboard.findOrientation(tilePlacement));
                    tilePlaced = true;
                } catch(GameRulesException e) {
                    tilePlaced = false;
                }
                if(tilePlaced) {
                    break;
                }
            }
            if(tilePlaced) {
                lastTilePlacedLocations[0] = tilePlacement[0];
                lastTilePlacedLocations[1] = tilePlacement[1];
                lastTilePlacedLocations[2] = tilePlacement[2];
                maxAndMinCord();
                break;
            }
        }
    }

    public void maxAndMinCord(){
        for(Point point:lastTilePlacedLocations){
            if((int)point.getX() >= biggestXcord){
                biggestXcord = (int)point.getX() + 3;
            }
            if((int)point.getX() <= smallestXcord){
                smallestXcord = (int)point.getX() - 3;
            }
            if((int)point.getY() >= biggestYcord){
                biggestYcord = (int)point.getY() + 3;
            }
            if((int)point.getY() <= smallestYcord){
                smallestYcord = (int)point.getY() - 3;
            }
        }
    }

    public void oppoentsTilePlacement(Tile tile, Point location, int rotation){
        int x = (int)location.getX();
        int y = (int)location.getY();
        Point tileLocation[] = gameboard.rotate(location, rotation);
        gameboard.addTile(tile, tileLocation);

        lastTilePlacedLocations[0] = tileLocation[0];
        lastTilePlacedLocations[1] = tileLocation[1];
        lastTilePlacedLocations[2] = tileLocation[2];

        maxAndMinCord();
    }

    public void decideBuildType(){
        Pieces piece = Pieces.NONE;
        BuildOptions decidedBuildOptions = BuildOptions.NOOP;
        if((whenToPlacetotoro > 4) && (ourPlayer.gettotorosRemaining() > 0)){
            decidedBuildOptions = BuildOptions.TOTORO_SANCTUARY;
            piece = Pieces.P1_TOTORO;
            whenToPlacetotoro = 0;
        }
        else {
            if ((whenToPlacetotoro <= 4) && (ourPlayer.getvillagersRemaining() > 0)) {
                decidedBuildOptions = BuildOptions.NEW_SETTLEMENT;
                piece = Pieces.P1_VILLAGER;
                whenToPlacetotoro++;

            }
        }
        Point tryPieceLocation = lastTilePlacedLocations[1];
        if(notThefirstPiece){
            tryPieceLocation = lastPiecePlaced;
        }else {
            tryPieceLocation = new Point(tryPieceLocation.x + 1, tryPieceLocation.y);
        }

        int tempXLoc = tryPieceLocation.x;
        while( !performBuild(decidedBuildOptions, tryPieceLocation, piece)) {
            if(tryPieceLocation.x >= smallestXcord-3){
                tryPieceLocation = new Point(tryPieceLocation.x-1,tryPieceLocation.y);
            }else{
                if(decidedBuildOptions == BuildOptions.TOTORO_SANCTUARY) {
                    if(ourPlayer.getvillagersRemaining() > 0) {
                        decidedBuildOptions = BuildOptions.NEW_SETTLEMENT;
                        tryPieceLocation = lastPiecePlaced;
                        whenToPlacetotoro = 5;
                        if(timesTotoroBuildInterrupted >= 2){
                            verticalBranchLocation = 2;
                            notThefirstPiece = false;
                            timesTotoroBuildInterrupted = -100;
                            whenToPlacetotoro =0;
                            tryPieceLocation = new Point(0,1);
                        }else {
                            timesTotoroBuildInterrupted = timesTotoroBuildInterrupted+1;
                        }
                    }else{
                        decidedBuildOptions = BuildOptions.NOOP;
                    }

                }else{
                    decidedBuildOptions = BuildOptions.NOOP;
                }

            }
        }

        notThefirstPiece = true;
        lastPiecePlaced = tryPieceLocation;
        if(decidedBuildOptions == BuildOptions.TOTORO_SANCTUARY){
            lastPiecePlaced =  new Point(lastPiecePlaced.x-2,lastPiecePlaced.y);
        }
    }

    public boolean performBuild(BuildOptions buildtype, Point location, Pieces pieceType){

        if(buildtype == BuildOptions.NEW_SETTLEMENT) {

            try {
                gamerules.tryToBuildNewSettlement(ourPlayer, location);
                gameboard.addVillagerToBoard(true, location);
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
                ourPlayer.villagersBeingPlaced(gameboard.getHexAtPointP(location).getLevel());
                mOUT.setBuild(BuildOptions.NEW_SETTLEMENT);
                mOUT.setBuildPoint(location);
                return true;
            }catch(GameRulesException e){
                return false;
            }
            catch(NullPointerException e) {
            return false;
            }
        }
        if(buildtype == BuildOptions.TOTORO_SANCTUARY) {
            try {
                gamerules.tryToAddTotoro(ourPlayer, location, new Point(location.x+1, location.y));
                gameboard.addTotoroToBoard(true, location);
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
                ourPlayer.totoroBeingPlaced();
                mOUT.setBuild(BuildOptions.TOTORO_SANCTUARY);
                mOUT.setBuildPoint(location);
                return true;
            }catch(GameRulesException e){
                return false;
            }catch(NullPointerException e) {
                return false;
            }
        }
        if(buildtype == BuildOptions.TIGER_PLAYGROUND) {
            try {
                gamerules.tryToAddTiger(ourPlayer, location, new Point(location.x + 1, location.y));
                gameboard.addTigerToBoard(true, location);
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
                ourPlayer.tigerBeingPlaced();
                mOUT.setBuild(BuildOptions.TIGER_PLAYGROUND);
                mOUT.setBuildPoint(location);
                return true;
            }catch(GameRulesException e){
                return false;
            }
        }
        if(buildtype == BuildOptions.EXPAND) {
            return false;
        }
        if(buildtype == BuildOptions.NOOP) {
            mOUT.setBuild(BuildOptions.NOOP); // last resort
            return true;
        }
        return false;
    }

    public void oponentBuild(BuildOptions buildtype, Point location, char terrain) throws NullPointerException{

        if(buildtype == BuildOptions.NEW_SETTLEMENT) {
            gameboard.addVillagerToBoard(false, location);
            try {
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TOTORO_SANCTUARY) {
            gameboard.addTotoroToBoard(false, location);
            try {
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TIGER_PLAYGROUND) {
            gameboard.addTigerToBoard(false, location);
            try {
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.EXPAND) {
            ArrayList<Point> expansion;
            try {
                expansion = gamerules.tryToExpand(new Player("Bob"), terrain, location);
                gameboard.expandSettlement(false, expansion);
                settlementBuilder.calculateSettlements(gameboard);
                gamerules.setSettlements(settlementBuilder.getPlayer1Settlements());
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.NOOP) {
            // The Game should have ended... Why am I here
        }

    }

    public GameBoard returnGameBoard(){
        return gameboard;
    }

    public Point[] getlastTilePlacedLocations(){
        return lastTilePlacedLocations;
    }

}


