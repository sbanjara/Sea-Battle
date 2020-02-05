package edu.jsu.mcis.seabattleii;

public class ShipSquare extends Square {
    
    /*
     * This class represents an occupied Square of the primary grid.  Objects of
     * this class carry a reference to the original Ship object of which the
     * current Square is a part, as well as a segment value which represents the
     * part of the ship the Square represents.  This value may be useful as an
     * indicator of which part of the Ship has been hit.
     */
    
    private final Ship ship;
    private final int segment;
    
    /* CONSTRUCTOR */

    public ShipSquare(Ship ship, int segment) {
        super();
        this.ship = ship;
        this.segment = segment;
    }
    
    /* Getters for Ship and segment value */

    public Ship getShip() {
        return this.ship;
    }
    
    public int getSegment() {
        return segment;
    }
    
    /* Square Hit (set the flag in the superclass and mark the Ship as having been hit) */

    @Override
    public void hit() {
        super.hit = true;
        ship.hit();
    }
    
}