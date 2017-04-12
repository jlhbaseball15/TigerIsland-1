import java.awt.*;
import java.util.ArrayList;

/**
 * Created by scott on 4/11/17.
 */
public class MoveCalculator {
    private static GameBoard gameboard;
    private static GameRules gamerules;
    private int maxCoord = 50;

    public MoveCalculator(GameBoard gb, GameRules gr) {
        gameboard = gb;
        gamerules = gr;
    }

    public int possibleTilePlacementNum(Tile tileToBePlayed) {
        ArrayList<Point[]>[] tilePlacementArray =  getTilePlacementArrayList(tileToBePlayed);
        return (tilePlacementArray[0].size() +
                tilePlacementArray[1].size() +
                tilePlacementArray[2].size() +
                tilePlacementArray[3].size() +
                tilePlacementArray[4].size() +
                tilePlacementArray[5].size());
    }

    public int possibleNewSettlementPlacementNum(Player player) {
        ArrayList<Point> villagerPlacementArray = getNewSettlementArrayList(player);
        return villagerPlacementArray.size();
    }


    public int possibleTigerPlacementNum(Player player) {
        ArrayList<Point> tigerPlacementArray = getTigerPlacementArrayList(player);
        return tigerPlacementArray.size();
    }

    public int possibleTotoroPlacementNum(Player player) {
        ArrayList<Point> totoroPlacementArray = getTotoroPlacementArrayList(player);
        return totoroPlacementArray.size();
    }
    /*
    public int possibleSettlementExpandNum(Player player) {

    }*/

    public ArrayList<Point[]>[] getTilePlacementArrayList(Tile tileToBePlayed) {
        Point[] hexLocations;
        ArrayList<Point[]> possiblePlacementsArrayList[] = new ArrayList[6];
        //initialize ArrayList elements
        for (int i = 0; i < 6; i++) {
            possiblePlacementsArrayList[i] = new ArrayList<Point[]>();
        }

        //cycle through y coords
        for (int volcanoLocX = 0; volcanoLocX < maxCoord; volcanoLocX++) {
            //cycle through x coords
            for (int volcanoLocY = 0; volcanoLocY < maxCoord; volcanoLocY++) {
                //cycle through orientations
                for(int orient = 0; orient < 6; orient++) {
                    //Positive volcanoCoords
                    hexLocations = getHexLocationsFromOrient(orient, volcanoLocX, volcanoLocY);
                    tryToAddTilePlacementLocation(tileToBePlayed, orient, hexLocations, possiblePlacementsArrayList);
                    /*try {
                        gamerules.TryToAddTile(tileToBePlayed, hexLocations);
                        possiblePlacementsArrayList[orient].add(hexLocations);
                    } catch(GameRulesException e) {}

                    if (volcanoLocX == 0 && volcanoLocY == 0) {
                        continue;
                    }*/

                    //Negative volcanoCoordX
                    hexLocations = getHexLocationsFromOrient(orient, volcanoLocX * -1, volcanoLocY);
                    tryToAddTilePlacementLocation(tileToBePlayed, orient, hexLocations, possiblePlacementsArrayList);

                    //Negative volcanoCoordY
                    hexLocations = getHexLocationsFromOrient(orient, volcanoLocX, volcanoLocY * -1);
                    tryToAddTilePlacementLocation(tileToBePlayed, orient, hexLocations, possiblePlacementsArrayList);

                    //Negative volcanoCoordX and volcanoCoordY
                    hexLocations = getHexLocationsFromOrient(orient, volcanoLocX * -1, volcanoLocY * -1);
                    tryToAddTilePlacementLocation(tileToBePlayed, orient, hexLocations, possiblePlacementsArrayList);
                }
            }
        }
        return possiblePlacementsArrayList;
    }

    public ArrayList<Point> getNewSettlementArrayList(Player player) {
        Point hexLocation;
        ArrayList<Point> possiblePlacementsArrayList = new ArrayList<>();

        //if player doesn't have any villagers then return 0
        if (player.getvillagersRemaining() == 0) {
            return possiblePlacementsArrayList;
        }

        //cycle through y coords
        for (int coordX = 0; coordX < maxCoord; coordX++) {
            //cycle through x coords
            for (int coordY = 0; coordY < maxCoord; coordY++) {
                //Positive coords
                hexLocation = new Point(coordX, coordY);
                tryToAddNewSettlementLocation(player, hexLocation, possiblePlacementsArrayList);

                //Negative xCoord
                hexLocation = new Point(coordX * -1, coordY);
                tryToAddNewSettlementLocation(player, hexLocation, possiblePlacementsArrayList);

                //Negative yCoord
                hexLocation = new Point(coordX, coordY * -1);
                tryToAddNewSettlementLocation(player, hexLocation, possiblePlacementsArrayList);

                //Negative xCoord and yCoord
                hexLocation = new Point(coordX * -1, coordY * -1);
                tryToAddNewSettlementLocation(player, hexLocation, possiblePlacementsArrayList);
            }
        }
        return possiblePlacementsArrayList;
    }

    public ArrayList<Point> getTigerPlacementArrayList(Player player) {
        Point hexLocation;
        ArrayList<Point> possiblePlacementsArrayList = new ArrayList<>();

        //if player doesn't have any villagers then return 0
        if (player.gettigersRemaining() == 0) {
            return possiblePlacementsArrayList;
        }

        //cycle through y coords
        for (int coordX = 0; coordX < maxCoord; coordX++) {
            //cycle through x coords
            for (int coordY = 0; coordY < maxCoord; coordY++) {
                //Positive coords
                hexLocation = new Point(coordX, coordY);
                tryToAddTiger(player, hexLocation, possiblePlacementsArrayList);

                //Negative xCoord
                hexLocation = new Point(coordX * -1, coordY);
                tryToAddTiger(player, hexLocation, possiblePlacementsArrayList);

                //Negative yCoord
                hexLocation = new Point(coordX, coordY * -1);
                tryToAddTiger(player, hexLocation, possiblePlacementsArrayList);

                //Negative xCoord and yCoord
                hexLocation = new Point(coordX * -1, coordY * -1);
                tryToAddTiger(player, hexLocation, possiblePlacementsArrayList);
            }
        }
        return possiblePlacementsArrayList;
    }

    public ArrayList<Point> getTotoroPlacementArrayList(Player player) {
        Point hexLocation;
        ArrayList<Point> possiblePlacementsArrayList = new ArrayList<>();

        //if player doesn't have any villagers then return 0
        if (player.gettotorosRemaining() == 0) {
            return possiblePlacementsArrayList;
        }

        //cycle through y coords
        for (int coordX = 0; coordX < maxCoord; coordX++) {
            //cycle through x coords
            for (int coordY = 0; coordY < maxCoord; coordY++) {
                //Positive coords
                hexLocation = new Point(coordX, coordY);
                tryToAddTotoro(player, hexLocation, possiblePlacementsArrayList);

                //Negative xCoord
                hexLocation = new Point(coordX * -1, coordY);
                tryToAddTotoro(player, hexLocation, possiblePlacementsArrayList);

                //Negative yCoord
                hexLocation = new Point(coordX, coordY * -1);
                tryToAddTotoro(player, hexLocation, possiblePlacementsArrayList);

                //Negative xCoord and yCoord
                hexLocation = new Point(coordX * -1, coordY * -1);
                tryToAddTotoro(player, hexLocation, possiblePlacementsArrayList);
            }
        }
        return possiblePlacementsArrayList;
    }
    /*
    public Point[] getExpandSettlementArray(Player player) {

    }*/

    private Point[] getHexLocationsFromOrient(int orientNum, int volcanoX, int volcanoY) {
        switch (orientNum) {
            case 0: return new Point[] {new Point(volcanoX, volcanoY - 1),
                    new Point(volcanoX + 1, volcanoY - 1),
                    new Point(volcanoX, volcanoY)};

            case 1: return new Point[] {new Point(volcanoX + 1, volcanoY - 1),
                    new Point(volcanoX + 1, volcanoY),
                    new Point(volcanoX, volcanoY)};

            case 2: return new Point[] {new Point(volcanoX + 1, volcanoY),
                    new Point(volcanoX, volcanoY + 1),
                    new Point(volcanoX, volcanoY)};

            case 3: return new Point[] {new Point(volcanoX, volcanoY + 1),
                    new Point(volcanoX - 1, volcanoY + 1),
                    new Point(volcanoX, volcanoY)};

            case 4: return new Point[] {new Point(volcanoX - 1, volcanoY + 1),
                    new Point(volcanoX - 1, volcanoY),
                    new Point(volcanoX, volcanoY)};

            case 5: return new Point[] {new Point(volcanoX - 1, volcanoY),
                    new Point(volcanoX, volcanoY - 1),
                    new Point(volcanoX, volcanoY)};
            default: break;
        }
        return null;
    }

    private void tryToAddTilePlacementLocation(Tile tile, int orient, Point[] hexLocations, ArrayList<Point[]>[] currentArray) {
        if (currentArray[orient].size() == 0) {
            try {
                gamerules.TryToAddTile(tile, hexLocations);
                currentArray[orient].add(hexLocations);
            } catch (GameRulesException e) {}
            return;
        }

        //check if location is already contained
        boolean locationContained = false;
        for (int i = 0; i < currentArray[orient].size(); i++) {
            Point[] currentPointArray = currentArray[orient].get(i);
            if ((currentPointArray[2].getX() == hexLocations[2].getX()) && (currentPointArray[2].getY() == hexLocations[2].getY())) {
                locationContained = true;
            } else {

            }
        }
        if(locationContained == false) {
            try {
                gamerules.TryToAddTile(tile, hexLocations);
                currentArray[orient].add(hexLocations);
            } catch (GameRulesException e) {
            }
        }
    }

    private void tryToAddNewSettlementLocation(Player player, Point hexLocation, ArrayList<Point> currentList) {
        if (currentList.contains(hexLocation)) {

        } else {
            try {
                gamerules.tryToBuildNewSettlement(player, hexLocation);
                currentList.add(hexLocation);
            } catch(GameRulesException e) {}
        }
    }

    private void tryToAddTiger(Player player, Point hexLocation, ArrayList<Point> currentList) {
        int coordX = (int)hexLocation.getX();
        int coordY = (int)hexLocation.getY();
        Point[] hexNeighborLocations = {new Point(coordX, coordY - 1),
                                        new Point(coordX - 1, coordY),
                                        new Point(coordX - 1, coordY + 1),
                                        new Point(coordX, coordY + 1),
                                        new Point(coordX + 1, coordY),
                                        new Point(coordX + 1, coordY - 1)};


            for (int i = 0; i < hexNeighborLocations.length; i++) {
                boolean doesHexExist = gameboard.hasTileInMap(hexNeighborLocations[i]);
                Hex hex = gameboard.getHexAtPointP(hexLocation);
                //check if hex is null or if it has already been added to list
                if (hex != null && !currentList.contains(hexLocation)) {

                    try {
                        gamerules.tryToAddTiger(player, hexLocation, hexNeighborLocations[i]);
                        currentList.add(hexLocation);
                    } catch(GameRulesException e) {}
                }
            }
    }

    private void tryToAddTotoro(Player player, Point hexLocation, ArrayList<Point> currentList) {
        int coordX = (int)hexLocation.getX();
        int coordY = (int)hexLocation.getY();
        Point[] hexNeighborLocations = {new Point(coordX, coordY - 1),
                new Point(coordX - 1, coordY),
                new Point(coordX - 1, coordY + 1),
                new Point(coordX, coordY + 1),
                new Point(coordX + 1, coordY),
                new Point(coordX + 1, coordY - 1)};


        for (int i = 0; i < hexNeighborLocations.length; i++) {
            boolean doesHexExist = gameboard.hasTileInMap(hexNeighborLocations[i]);
            Hex hex = gameboard.getHexAtPointP(hexLocation);
            //check if hex is null or if it has already been added to list
            if (hex != null && !currentList.contains(hexLocation)) {

                try {
                    gamerules.tryToAddTotoro(player, hexLocation, hexNeighborLocations[i]);
                    currentList.add(hexLocation);
                } catch(GameRulesException e) {}
            }
        }
    }

}
