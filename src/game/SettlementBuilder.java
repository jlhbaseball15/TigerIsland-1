package game;

import java.awt.*;
import java.util.*;

public class SettlementBuilder {
    private ArrayList<Settlement> player1Settlements;
    private ArrayList<Settlement> player2Settlements;
    private HashMap<Point,Boolean> pointsInSettlements;

    public SettlementBuilder(){
        player1Settlements = new ArrayList<>();
        player2Settlements = new ArrayList<>();
        pointsInSettlements = new HashMap<>();
    }

    public void calculateSettlements(GameBoard board) throws GameRulesException{
        if(board.isEmpty()) {
            throw new GameRulesException("game.SettlementBuilder EmptyBoardException");
        }

        java.util.List<Point> boardKeys = new ArrayList<Point>(board.getMap().keySet());

        for(Point point : boardKeys){
            if(board.getHexAtPointP(point).getPiece() != Pieces.NONE){
                if(!pointsInSettlements.containsKey(point)){
                    createSettlementBFS(point, board);
                }
            }
        }

    }

    public ArrayList<Settlement> getPlayer1Settlements(){
        return player1Settlements;
    }

    public ArrayList<Settlement> getPlayer2Settlements(){
        return player2Settlements;
    }

    private void createSettlementBFS(Point point, GameBoard board){
        Settlement settlement = new Settlement();
        Queue<Point> visitNext = new LinkedList<Point>();
        HashMap<Point,Boolean> visited = new HashMap<>();
        visitNext.add(point);

        while(!visitNext.isEmpty()){
            Point currentPoint = visitNext.remove();
            if (!settlement.contains(currentPoint)) {
                settlement.addPointToSettlement(currentPoint);
            }
            pointsInSettlements.put(currentPoint, true);

            //add all neighbors of current point to queue as long as not visited and has piece of same player
            addNeighborsToQueue(currentPoint, visitNext, visited, board);
            if (board.getHexAtPointP(currentPoint).getOccupied() == Pieces.P1_SHAMAN ||
                    board.getHexAtPointP(currentPoint).getOccupied() == Pieces.P2_SHAMAN) {
                settlement.setShaman();
            }
            if(board.getHexAtPointP(currentPoint).getPiece() == Pieces.P1_TIGER ||
               board.getHexAtPointP(currentPoint).getPiece() == Pieces.P2_TIGER){
                settlement.containsTiger();
            } //settlements that contain Tigers or Totoro have boolean flags set
            if (board.getHexAtPointP(currentPoint).getPiece() == Pieces.P1_TOTORO ||
                    board.getHexAtPointP(currentPoint).getPiece() == Pieces.P2_TOTORO){
                settlement.containsTotoro();
            }

            visited.put(currentPoint, true);
        }

        if(isPlayer1Piece(board.getHexAtPointP(point))){
            player1Settlements.add(settlement);
        }
        else{
            player2Settlements.add(settlement);
        }
    }

    private void addNeighborsToQueue(Point point, Queue<Point> visitNext, HashMap<Point, Boolean> visited, GameBoard board){
        int x = (int)point.getX();
        int y = (int)point.getY();
        Point[] neighbors = {new Point(x-1, y), new Point(x-1, y+1), new Point(x, y-1),
                             new Point(x, y+1), new Point(x+1, y-1), new Point(x+1, y)};
        Boolean isPlayer1 = isPlayer1Piece(board.getHexAtPointP(point));

        for(Point p : neighbors){
            Hex hex = board.getHexAtPointP(p);
            if (hex == null) { continue; }
            if(hex.getPiece() != Pieces.NONE && isPlayer1Piece(hex) == isPlayer1){
                if(!visited.containsKey(p)){
                    visitNext.add(p);
                }
            }
        }
    }

    private boolean isPlayer1Piece(Hex hex){
        return hex.getPiece() == Pieces.P1_TIGER || hex.getPiece() == Pieces.P1_TOTORO || hex.getPiece() == Pieces.P1_VILLAGER;
    }

}
