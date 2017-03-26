import java.awt.*;

/**
 * Created by Saulo on 3/20/2017.
 */
public class GameState {
    private final int TOTALCARDS = 48;
    private Player firstPlayer;
    private Player secondPlayer;
    private Deck mainDeck;
    private int cardsRemaining;
    private GameBoard gameboard;
    private GameRules gameRules;

    public GameState(String nameOfPlayerWhoGoesFirst, String nameOfPlayerWhoGoessecond, Deck deck){
        mainDeck = deck;
        firstPlayer = new Player(nameOfPlayerWhoGoesFirst);
        secondPlayer = new Player(nameOfPlayerWhoGoessecond);
        cardsRemaining = TOTALCARDS;
        gameboard = new GameBoard();
        gameRules = new GameRules(gameboard);
    }

    public void startGame(){
        boolean player1Turn = true;
        boolean tilePlaced = false;
        boolean playerLostDueToNotBuilding = false;

        Point tilePlacement[] = new Point[3];
        BuildOptions build = BuildOptions.NOOP;

        Tile tile;

        while(true) {

            tile = mainDeck.getTile();

            if (player1Turn) {
                while (!tilePlaced) {
                    // player must select where to place tile
                    try {
                        gameRules.TryToAddTile(tile, tilePlacement);
                        gameboard.AddTile(tile, tilePlacement);
                        tilePlaced = true;
                    } catch(GameRulesException e) {
                        System.out.println("Illegal Tile Placement Choose Another Spot");
                    }
                }
                tilePlaced = false;



                // player must now pick a build option
                if (build == BuildOptions.NEW_SETTLEMENT) {
                    BuildNewSettlement(player1Turn);
                }
                else if (build == BuildOptions.EXPAND) {
                    ExpandASettlement(player1Turn);
                }
                else if (build == BuildOptions.TOTORO_SANCTUARY) {
                    BuildTotorSanctuary(player1Turn);
                }
                else if (firstPlayer.gettigersRemaining() >= 1){ // build == BuildOptions.TIGER_SANCTUARY
                    BuildTigerPlayGround(player1Turn);
                }
                else {
                    // if we made it here there are no options, game ends with player losing
                    playerLostDueToNotBuilding = true;
                    break;
                }
                build = BuildOptions.NOOP;
            }
            else {
                while (!tilePlaced) {
                    // player must select where to place tile
                    try {
                        gameRules.TryToAddTile(tile, tilePlacement);
                        gameboard.AddTile(tile, tilePlacement);
                        tilePlaced = true;
                    } catch(GameRulesException e) {
                        System.out.println("Illegal Tile Placement Choose Another Spot");
                    }
                }
                tilePlaced = false;



                // player must now pick a build option
                if (build == BuildOptions.NEW_SETTLEMENT && firstPlayer.getvillagersRemaining() >= 1) {
                    BuildNewSettlement(player1Turn);
                }
                else if (build == BuildOptions.EXPAND) {
                    ExpandASettlement(player1Turn);
                }
                else if (build == BuildOptions.TOTORO_SANCTUARY && firstPlayer.gettotorosRemaining() >= 1) {
                    BuildTotorSanctuary(player1Turn);
                }
                else if (firstPlayer.gettigersRemaining() >= 1){ // build == BuildOptions.TIGER_SANCTUARY
                    BuildTigerPlayGround(player1Turn);
                }
                else {
                    // if we made it here there are no options, game ends with player losing
                    playerLostDueToNotBuilding = true;
                    break;
                }
                build = BuildOptions.NOOP;
            }



            // End of turn time to merge / split settlements
            merge_split_settlements();



            // end of turn check card and piece status;
            if(cardsRemaining <= 0) {
                break;
            }
            else if(firstPlayer.getTotalPiecesRemaining() <= 0) {
                break;
            }

            player1Turn = !player1Turn;
        }



        if(playerLostDueToNotBuilding) {
            if (player1Turn) {
                // player 2 wins
            }
            else {
                // player 1 wins
            }
        }
        else {  // game ended normally, either by using all pieces or running out of tiles
            if (firstPlayer.getScore() > secondPlayer.getScore()) {
                // player 1 wins
            }
            else if (secondPlayer.getScore() > firstPlayer.getScore()) {
                // player 2 wins
            }
            else {   // players tied, use tie breaker
                if (cardsRemaining > 0 && firstPlayer.getTotalPiecesRemaining() <= 0) {
                    // player 1 wins
                } else if (cardsRemaining > 0) {
                    // player 2 wins
                } else {
                    // ran out of cards and a tie, rules don't state who wins!!!
                }
            }
        }

    }

    private void merge_split_settlements() {
        // what to do what to do
    }

    private void BuildTigerPlayGround(boolean player1Turn) {
        // what to do what to do
    }

    private void BuildTotorSanctuary(boolean player1Turn) {
        // what to do what to do
    }

    private void ExpandASettlement(boolean player1Turn) {
        // what to do what to do
    }

    private void BuildNewSettlement(boolean player1Turn) {
        // what to do what to do
    }
}
