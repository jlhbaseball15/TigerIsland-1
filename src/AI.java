
import java.awt.*;


/**
 * Created by Saulo on 4/4/2017.
 */
public class AI {
    private static GameBoard gameboard;
    private static GameRules gamerules;
    private Point tilePlacement[];
    private Point lastTilePlacedLocations[];
    private SettlementBuilder settlementBuilder;
    private int biggestXcord;
    private int biggestYcord;
    private int smallestXcord;
    private int smallestYcord;
    private Player ourPlayer;
    int whenToPlacetotoro;



    public AI(boolean areWeFirst){
        gameboard =  new GameBoard();
        gameboard.addStartingTile();
        lastTilePlacedLocations = new Point[3];
        biggestXcord = 1;
        biggestYcord = 1;
        smallestXcord = -1;
        smallestYcord = -1;
        ourPlayer = new Player("ourPlayer");
        settlementBuilder = new SettlementBuilder();
        whenToPlacetotoro = 0;
    }

    public void decideTilePlacement(Tile tile){
        gamerules = new GameRules(gameboard);
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
        if(rotation == 1){
            rotatedPlacement[0] = new Point(x,y-1);
            rotatedPlacement[1] = new Point(x+1,y-1);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 2){
            rotatedPlacement[0] = new Point(x+1,y-1);
            rotatedPlacement[1] = new Point(x+1,y);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 3){
            rotatedPlacement[0] = new Point(x+1,y);
            rotatedPlacement[1] = new Point(x,y+1);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 4){
            rotatedPlacement[0] = new Point(x,y+1);
            rotatedPlacement[1] = new Point(x-1,y+1);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 5){
            rotatedPlacement[0] = new Point(x-1,y+1);
            rotatedPlacement[1] = new Point(x-1,y);
            rotatedPlacement[2] = new Point(x,y);
        }
        if(rotation == 6){
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
                piece = Pieces.P1_TOTORO;
            }
            whenToPlacetotoro++;
            performBuild(decidedBuildOptions, lastTilePlacedLocations[0], piece);
        }
    }

    public void performBuild(BuildOptions buildtype, Point location, Pieces pieceType){

        if(buildtype == BuildOptions.NEW_SETTLEMENT) {

            try {

                gameboard.getHexAtPointP(location).setOccupied(pieceType, 1);
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TOTORO_SANCTUARY) {
            try {

                gameboard.getHexAtPointP(location).setOccupied(pieceType, 1);
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TIGER_PLAYGROUND) {
            try {

                gameboard.getHexAtPointP(location).setOccupied(pieceType, 1);
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.EXPAND) {
        }
        if(buildtype == BuildOptions.NOOP) {
        }
    }

    public void oponentBuild(BuildOptions buildtype, Point location){

        if(buildtype == BuildOptions.NEW_SETTLEMENT) {
            gameboard.getHexAtPointP(location).setOccupied(Pieces.P2_VILLAGER, 1);
            try {
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TOTORO_SANCTUARY) {
            gameboard.getHexAtPointP(location).setOccupied(Pieces.P2_TOTORO, 1);
            try {
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.TIGER_PLAYGROUND) {
            gameboard.getHexAtPointP(location).setOccupied(Pieces.P2_TIGER, 1);
            try {
                settlementBuilder.calculateSettlements(gameboard);
            }catch(GameRulesException e){}
        }
        if(buildtype == BuildOptions.EXPAND) {
        }
        if(buildtype == BuildOptions.NOOP) {
        }
    }

}

