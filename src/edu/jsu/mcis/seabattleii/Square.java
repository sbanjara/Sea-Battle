package edu.jsu.mcis.seabattleii;

public abstract class Square {
    
    /*
     * This abstract class represents a Square of the primary grid.  It is
     * implemented by EmptySquare (an empty square) and ShipSquare (an occupied
     * square).  The only property that is common to both is a Boolean flag
     * indicating whether or not the current square has been hit by a shot from
     * the opposing player, so this class provides the flag, and an abstract
     * method so that the subclasses can set the flag and carry out other tasks
     * as appropriate for each type of Square.
     */
    
    protected boolean hit;
    
    public Square() {
        hit = false;
    }
    
    public boolean isHit() {
        return hit;
    }
    
    public abstract void hit();
    
}
