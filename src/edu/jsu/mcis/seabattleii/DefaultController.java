package edu.jsu.mcis.seabattleii;

import java.util.HashMap;

public class DefaultController extends AbstractController
{
    /*
     * These static property names are used as the identifiers for the elements
     * and/or events associated with the Models which may need to be reflected
     * in changes to the Views.
     */
    
    public static final String STATUS = "Status";
    
    public static final String PLAYER1_SHIP_DEPLOY = "Player1ShipDeploy";
    public static final String PLAYER2_SHIP_DEPLOY = "Player2ShipDeploy";
    
    public static final String PLAYER1_SQUARE_HIT = "Player1SquareHit";
    public static final String PLAYER1_SHIP_HIT = "Player1ShipHit";
    public static final String PLAYER1_SHIP_SUNK = "Player1ShipSunk";
    public static final String PLAYER1_SHOT_MISSED = "Player1ShotMissed";
    public static final String PLAYER1_GAME_OVER= "Player1GameOver";
    
    public static final String PLAYER2_SQUARE_HIT = "Player2SquareHit";
    public static final String PLAYER2_SHIP_HIT = "Player2ShipHit";
    public static final String PLAYER2_SHIP_SUNK = "Player2ShipSunk";
    public static final String PLAYER2_SHOT_MISSED = "Player2ShotMissed";
    public static final String PLAYER2_GAME_OVER= "Player2GameOver";
    
    /* 
     * These methods are invoked by the Views in response to changes in the
     * Views (in response to user input) which must be reflected in the Model.
     * The methods receive the new data from the View, and then invoke
     * "setModelProperty()" (inherited from AbstractController) so that the
     * proper Model(s) can be found and updated properly.
     */
    
    /* Update Status Line */
 
    public void setStatus(String text) {
        
        setModelProperty(STATUS, text);
        
    }
    
    /* Player 1 Shot Fired (square represented as a Map containing the X/Y coordinates) */
    
    public void player1ShotFired(HashMap<String, Integer> square) {
        
        setModelProperty(PLAYER2_SQUARE_HIT, square);
        
    }
    
    /* Player 2 Shot Fired (square represented as a Map containing the X/Y coordinates) */
    
    public void player2ShotFired(HashMap<String, Integer> square) {
        
        setModelProperty(PLAYER1_SQUARE_HIT, square);
        
    }
    
}