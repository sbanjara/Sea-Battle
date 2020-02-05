package edu.jsu.mcis.seabattleii;

import java.util.HashMap;

public final class DefaultModel extends AbstractModel {
    
    private Player player1;
    private Player player2;
    
    String status;
    
    public DefaultModel() {
        status = "";
    }
 
    public void initDefaults() {
        
        /* Initialize player objects */
 
        player1 = new Player();
        player2 = new Player();
        
        /* Set Initial Status */
        
        setStatus("Welcome to Sea Battle II!"); 
        
        /* Deploy default Fleet for Player 1 and Player 2 */
        
        deployFleets();

    }
    
    public void deployFleets() {
        
        Grid grid;        
        Ship next;
        
        /* Create Fleets */
        
        Fleet fleet1 = new Fleet();
        Fleet fleet2 = new Fleet();
        
        /* Get Player 1's Grid */
        
        grid = player1.getPrimary();
        
        /*
         * Iterate through all Ships in Fleet.  Deploy each Ship individually,
         * so that the Controller can be informed of each deployment.  The
         * Controller will then send a notification to the Views so that the
         * Ship will be added to the Primary Grid view for each player.
         */
        
        /* Deploy Player 1 Fleet */
        
        for (int i = 0; i < fleet1.getSize(); ++i) {
            
            next = fleet1.get(i);       // Get next Ship in Fleet
            grid.deployShip(next);      // Deploy Ship
            player1.incrementCount();   // Increment Player's Fleet Count
            
            /*
             * Inform Controller of deployment and print populated Grid
             *
             * (Uncomment the next line once "deployShip()" is finished!)
             */
            
            firePropertyChange(DefaultController.PLAYER1_SHIP_DEPLOY, null, next);
            
        }
        
        /* Print the populated Grid to the console (remove after "deployFleet()" is finished) */
        
        System.out.println("Player 1:\n" + grid + "\n");
        
        /* Get Player 2's Grid */
        
        grid = player2.getPrimary();
        
        /* Deploy Player 2 Fleet */
        
        for (int i = 0; i < fleet2.getSize(); ++i) {
            
            next = fleet2.get(i);       // Get next Ship in Fleet
            grid.deployShip(next);      // Deploy Ship
            player2.incrementCount();   // Increment Player's Fleet Count
            
            /*
             * Inform Controller of deployment and print populated Grid
             *
             * (Uncomment the next line once "deployShip()" is finished!)
             */
            
            firePropertyChange(DefaultController.PLAYER2_SHIP_DEPLOY, null, next);
            
        }
        
        /* Print the populated Grid to the console (remove after "deployFleet()" is finished) */
        
        System.out.println("Player 2:\n" + grid + "\n");
        
    }
    
    /* Update Status Line (also informs controller of property change) */
    
    public void setStatus(String text) {
        
        String oldText = this.status;
        this.status = text;
 
        firePropertyChange(DefaultController.STATUS, oldText, text);
        
    }
    
    /* Display Players' Fleet Counts in Status Line */
    
    public void showFleetCounts() {
        
        StringBuilder s = new StringBuilder();
        
        s.append("Player 1: ").append(player1.getCount()).append(" Ship(s); ");
        s.append("Player 2: ").append(player2.getCount()).append(" Ship(s)");
 
        setStatus(s.toString());
        
    }
    
    /* Player 1 Square Hit; Update Model */
    
    public void setPlayer1SquareHit(HashMap<String, Integer> s) {

        Ship ship;
        
        /* Get location of hit Square (square represented as a Map containing the X/Y coordinates) */
        
        int x = s.get("x");
        int y = s.get("y");
        
        /* Get Square; Mark as Hit */
        
        Square square = player1.getPrimary().get(x, y);
        square.hit();
        
        /* Occupied Square */
        
        if (square instanceof ShipSquare) {
            
            /* Get reference to the Ship in this Square */
            
            ship = ((ShipSquare) square).getShip();
            
            /* Has this ship been sunk? */
            
            if (ship.isSunk()) { // If sunk, decrement fleet count and fire a Sink property change
                
                player1.decrementCount();
                
                if( player1.getCount() == 0 )
                    firePropertyChange(DefaultController.PLAYER1_GAME_OVER, s, ship.getName());
                else 
                    firePropertyChange(DefaultController.PLAYER1_SHIP_SUNK, s, ship.getName());
                
            }
            else { // If not sunk, fire a Hit property change
                firePropertyChange(DefaultController.PLAYER1_SHIP_HIT, s, ship.getName());
            }
            
        }
        
        /* Empty Square */
        
        else { // Fire a Miss property change
            firePropertyChange(DefaultController.PLAYER2_SHOT_MISSED, s, null);
        }
        
        /* Update Fleet Count Display */
        
        showFleetCounts();
        
    }
    
    /* Player 2 Square Hit; Update Model */
    
    public void setPlayer2SquareHit(HashMap<String, Integer> s) {

        Ship ship;
        
        /* Get location of hit square (square represented as a Map containing the X/Y coordinates) */
        
        int x = s.get("x");
        int y = s.get("y");
        
        /* Get Square; Mark as Hit */
        
        Square square = player2.getPrimary().get(x, y);
        square.hit();
        
        /* Occupied Square */
        
        if (square instanceof ShipSquare) {
            
            /* Get reference to the Ship in this Square */
            
            ship = ((ShipSquare) square).getShip();
            
            /* Has this ship been sunk? */
            
            if (ship.isSunk()) { // If sunk, decrement fleet count and fire a Sink property change
                
                player2.decrementCount();
                
                if( player2.getCount() == 0 )
                     firePropertyChange(DefaultController.PLAYER2_GAME_OVER, s, ship.getName());
                else
                    firePropertyChange(DefaultController.PLAYER2_SHIP_SUNK, s, ship.getName());
                
            }
            else { // If not sunk, fire a Hit property change
                firePropertyChange(DefaultController.PLAYER2_SHIP_HIT, s, ship.getName());
            }
        }
        
        else { // Fire a Miss property change
            firePropertyChange(DefaultController.PLAYER1_SHOT_MISSED, s, null);
        }
        
        /* Update Fleet Count Display */
        
        showFleetCounts();
        
    }
    
    
}