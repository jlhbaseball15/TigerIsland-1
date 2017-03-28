import gherkin.lexer.He;

import java.awt.*;
import java.util.ArrayList;

public class GameRules {

    private final int tileSize = 3;
    private final int settlementsizefortotoro=5;
    private final int hexlevelfortiger=3;
    private static int numberOfOverlappedTiles;
    private static Hex hexes[];
    private static Point tileLocations[];
    private GameBoard board;
    private ArrayList<Settlement> settlements;
    private Player CurrentPlayer;
    private Settlement chosenSettlement;


    public GameRules(GameBoard board) {
        this.board = board;
        settlements = new ArrayList<>();
    }

    public void setSettlements(ArrayList<Settlement> newSettlements) {
        settlements = newSettlements;
    }

    public void TryToAddTile(Tile tile, Point TileHexPoints[]) throws GameRulesException{

        numberOfOverlappedTiles = 0;
        hexes = tile.getHexes();
        tileLocations = TileHexPoints;

        if (HexesNotAdjacent()) {
            throw new GameRulesException("The Hexes of a tile must be adjacent");
        }

        if( !board.isEmpty() ) {
            if (TheTileOverlapsAnother()) {
                if (numberOfOverlappedTiles < 3) {
                    throw new GameRulesException("The Tile Is Not Sitting On Three Hexes");
                }
                if (TileDirectlyOnTopOfAnother()) {
                    throw new GameRulesException("The Tile Is Directly Over Another Tile");
                }
                if (TilesOnDifferentLevels()) {
                    throw new GameRulesException("The Tile Is Located Over Different Leveled Tiles");
                }
                if (AddedTilesVolcanoIsNotOnAVolcano()) {
                    throw new GameRulesException("The Tile's Volcano Is Not Aligned With A Volcano Hex");
                }
            }
            else {
                if (NoAdjacentTiles()) {
                    throw new GameRulesException("The Tile Is Not Adjacent To An Existing Tile");
                }
            }
        }

        if (HexBelowHasTigerOrTotoro()) {
            throw new GameRulesException("Tile's Cannot Be Placed On A Totoro or A Tiger");
        }

        if (!settlements.isEmpty() && BelowSettlementIsDestroyed()) {
            throw new GameRulesException("A settlement cannot be destroyed");
        }

    }
    public void TryToAddTotoro(Point Hexlocation) throws GameRulesException{

        if(checksizeofsettlement())
            throw new GameRulesException("Size of settlement is not equal to or greater than 5");
        if(isonvolcano(Hexlocation))
            throw new GameRulesException("Can not build totoro on volcano");
        if(Settlementhasatororo()){
            throw new GameRulesException("Can not build more than one totoros on one settlement");
        }
        if(Playerdoesnothaveremainingtotoropieces()){
            throw new GameRulesException("Has played all Totoro pieces ");
        }
        if(!Selectedhexisnotnexttosettlement(Hexlocation)){
            throw new GameRulesException("Must build Totoro next to the Settlement");
        }
    }
    public void TryToAddTiger(Point Hexlocation) throws GameRulesException{
        if(checklevelofhex(Hexlocation))
            throw new GameRulesException("Level of hex must be three or greater");
        if(isonvolcano(Hexlocation))
            throw new GameRulesException("Can not build tiger on volcano");
        if(Settlementhasatiger()){
            throw new GameRulesException("Can not build more than one tiger on one settlement");
        }
        if(Playerdoesnothaveremainingtigerpieces()){
            throw new GameRulesException("Has played all Tiger pieces ");
        }
        if(!Selectedhexisnotnexttosettlement(Hexlocation)){
            throw new GameRulesException("Must build Tiger next to the Settlement");
        }
    }

    // holding function for settlements will check for valid tile placement
    private boolean BelowSettlementIsDestroyed() {
        Point hex0 = tileLocations[0];
        Point hex1 = tileLocations[1];
        Point hex2 = tileLocations[2];

        int size = 0;

        for (Settlement settle: settlements) {
            size = settle.getSettlement().size();
            if (size == 1 && (settle.getSettlement().contains(hex0)
                    || settle.getSettlement().contains(hex1)
                    || settle.getSettlement().contains(hex2))) {
                return true;
            }
            if (size == 2 && ((settle.getSettlement().contains(hex0) && settle.getSettlement().contains(hex1))
                    || (settle.getSettlement().contains(hex0) && settle.getSettlement().contains(hex2))
                    || (settle.getSettlement().contains(hex1) && settle.getSettlement().contains(hex2)))) {
                return true;
            }
        }

        return false;
    }

    private boolean HexBelowHasTigerOrTotoro() {
        if (board.hasTileInMap(tileLocations[0]) && !board.getHexAtPointP(tileLocations[0]).canHexBeNuked()) {
            return true;
        }
        if (board.hasTileInMap(tileLocations[1]) && !board.getHexAtPointP(tileLocations[1]).canHexBeNuked()) {
            return true;
        }
        return (board.hasTileInMap(tileLocations[2]) && !board.getHexAtPointP(tileLocations[2]).canHexBeNuked());
    }

    private boolean HexesNotAdjacent() {
        int X0 = tileLocations[0].x;
        int Y0 = tileLocations[0].y;
        Point hex1 = tileLocations[1];
        Point hex2 = tileLocations[2];

        int AdjacentCount = 0;
        if (hex1.equals(new Point(X0 + 1, Y0)) ^ hex2.equals(new Point(X0 + 1, Y0))) {
            ++AdjacentCount;
        }
        if (hex1.equals(new Point(X0 + 1, Y0 - 1)) ^ hex2.equals(new Point(X0 + 1, Y0 - 1))) {
            ++AdjacentCount;
        }
        if (hex1.equals(new Point(X0, Y0 + 1)) ^ hex2.equals(new Point(X0, Y0 + 1))) {
            ++AdjacentCount;
        }
        if (hex1.equals(new Point(X0, Y0 - 1)) ^ hex2.equals(new Point(X0, Y0 - 1))) {
            ++AdjacentCount;
        }
        if (hex1.equals(new Point(X0 - 1, Y0)) ^ hex2.equals(new Point(X0 - 1, Y0))) {
            ++AdjacentCount;
        }
        if (hex1.equals(new Point(X0 - 1, Y0 + 1)) ^ hex2.equals(new Point(X0 - 1, Y0 + 1))) {
            ++AdjacentCount;
        }
        return AdjacentCount != 2;
    }

    private boolean TheTileOverlapsAnother() {
        int x, y;
        boolean isOverlapped = false;

        for (int i = 0; i < tileSize; ++i) {
            Point p = tileLocations[i];

            if (board.hasTileInMap(p)) {
                isOverlapped = true;
                ++numberOfOverlappedTiles;
            }
        }

        return isOverlapped;
    }

    private boolean TileDirectlyOnTopOfAnother() {
        int Hex0_tileNum = board.retrieveTileNumFromHex(tileLocations[0]);
        int Hex1_tileNum = board.retrieveTileNumFromHex(tileLocations[1]);
        int Hex2_tileNum = board.retrieveTileNumFromHex(tileLocations[2]);

        return Hex0_tileNum == Hex1_tileNum && Hex1_tileNum == Hex2_tileNum;

    }

    private boolean TilesOnDifferentLevels() {
        int Hex0_LevelNum = board.retrieveLevelNumFromHex(tileLocations[0]);
        int Hex1_LevelNum = board.retrieveLevelNumFromHex(tileLocations[1]);
        int Hex2_LevelNum = board.retrieveLevelNumFromHex(tileLocations[2]);

        return Hex0_LevelNum != Hex1_LevelNum || Hex1_LevelNum != Hex2_LevelNum;

    }

    private boolean AddedTilesVolcanoIsNotOnAVolcano() {
        boolean NoMatch = true;

        for (int i = 0; i < tileSize; ++i) {
            if ((board.retrieveTerrainFromHex(tileLocations[i])) == 'V' && hexes[i].getTerrain() == 'V') {
                NoMatch = false;
                break;
            }
        }

        return NoMatch;
    }

    private boolean NoAdjacentTiles() {
        int x, y;
        for (int i = 0; i < tileSize; ++i) {
            x = tileLocations[i].x;
            y = tileLocations[i].y;

            if (HasANeighbor(x, y)) return false;
        }

        return true;
    }

    private boolean HasANeighbor(int x, int y) {

        if (board.hasTileInMap(x, y - 1)) {
            return true;
        }
        if (board.hasTileInMap(x, y + 1)) {
            return true;
        }
        if (board.hasTileInMap(x - 1, y)) {
            return true;
        }
        if (board.hasTileInMap(x - 1, y + 1)) {
            return true;
        }
        if (board.hasTileInMap(x + 1, y)) {
            return true;
        }
        if (board.hasTileInMap(x + 1, y + 1)) {
            return true;
        }
        return false;
    }
    private boolean checksizeofsettlement(){
        boolean islessthanfive=false;
        int sizeofsettlement = chosenSettlement.getSettlement().size();
        if(sizeofsettlement<settlementsizefortotoro)
            islessthanfive=true;
        return islessthanfive;
    }
    private boolean isonvolcano(Point location){
        boolean isterraintypevolcano = false;
        char terrainofhex=board.retrieveTerrainFromHex(location);
        if(terrainofhex=='V');
        isterraintypevolcano=true;
        return isterraintypevolcano;

    }
    private boolean Playerdoesnothaveremainingtotoropieces(){
        int totorosremaining=CurrentPlayer.gettotorosRemaining();
        boolean hasnototoros=false;
        if(totorosremaining>0)
            hasnototoros=true;
        return hasnototoros;
    }
    private boolean Selectedhexisnotnexttosettlement(Point Hexlocation){
        boolean hexisnexttosettlement=false;
        int x = Hexlocation.x;
        int y = Hexlocation.y;
        Point holdervalue=new Point();
        
        holdervalue.x=x+1;
        holdervalue.y=y;
        if(chosenSettlement.contains(holdervalue));
            hexisnexttosettlement=true;
        
        holdervalue.x=x;
        holdervalue.y=y+1;
        if(chosenSettlement.contains(holdervalue));
          hexisnexttosettlement=true;
        
        holdervalue.x=x-1;
        holdervalue.y=y;
        if(chosenSettlement.contains(holdervalue));
             hexisnexttosettlement=true;
        
        holdervalue.x=x;
        holdervalue.y=y-1;
        if(chosenSettlement.contains(holdervalue));
             hexisnexttosettlement=true;
        
        holdervalue.x=x+1;
        holdervalue.y=y-1;
        if(chosenSettlement.contains(holdervalue));
            hexisnexttosettlement=true;
        
        holdervalue.x=x-1;
        holdervalue.y=y+1;
        if(chosenSettlement.contains(holdervalue));
            hexisnexttosettlement=true;
        
        return hexisnexttosettlement;
    }
    private boolean Settlementhasatororo(){
        boolean totoroexists=false;
        if(chosenSettlement.containsTotoro())
            totoroexists=true;

        return totoroexists;
    }
    private boolean checklevelofhex(Point Hexlocation){
        boolean levelofhexislessthanthree=false;
        int hexlevel =board.retrieveLevelNumFromHex(Hexlocation);
        if(hexlevel<3)
            levelofhexislessthanthree=true;
        return levelofhexislessthanthree;
    }
    private boolean Settlementhasatiger(){
        boolean tigerexists=false;
        if(chosenSettlement.containsTiger())
            tigerexists=true;
        return tigerexists;
    }
    private boolean Playerdoesnothaveremainingtigerpieces(){
        int tigerremain=CurrentPlayer.gettigersRemaining();
        boolean hasnototoros=false;
        if(tigerremain>0)
            hasnototoros=true;
        return hasnototoros;

    }
}
