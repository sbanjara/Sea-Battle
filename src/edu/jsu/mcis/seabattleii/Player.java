package edu.jsu.mcis.seabattleii;

public class Player {
    
    /* This class encapsulates the Model for a single player */
    
    private final Grid primary;     // Primary Grid
    private final Grid tracking;    // Tracking Grid
    
    private int count;              // Fleet Count
    
    public Player() {
        
        /* Initialize Grids */
        
        primary = new Grid();
        tracking = new Grid();
        
    }
    
    /* Getters and Setters for Grids and Fleet Count */

    public Grid getPrimary() {
        return primary;
    }

    public Grid getTracking() {
        return tracking;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        ++count;
    }
    
    public void decrementCount() {
        --count;
    }
    
}