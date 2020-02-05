package edu.jsu.mcis.seabattleii;

public class EmptySquare extends Square {
    
    /* This class represents an empty square of the primary grid */

    public EmptySquare() {
        super();
    }
    
    /* Square Hit (all there is to do for an empty square is set the flag in the superclass) */
    
    @Override
    public void hit() {
        super.hit = true;
    }
    
}