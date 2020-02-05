package edu.jsu.mcis.seabattleii;

import java.util.ArrayList;

public class Grid {
    
    /*
     * This class represents a Grid of Squares.  Objects of this class can be
     * used as a Primary Grid or as a Tracking Grid.  The default size of the
     * Grid (ten by ten Squares) is given as a static constant.
     */

    public static int GRID_SIZE = 10;
    
    private final ArrayList<ArrayList<Square>> grid;
    
    /* CONSTRUCTOR */
    
    public Grid() {
        
        /* Fill new grid with empty Squares */
        
        ArrayList<Square> row; // same as the project 2 model
        
        grid = new ArrayList<>();
        
        for (int y = 0; y < GRID_SIZE; ++y) {
            
            row = new ArrayList<>();
            
            for (int x = 0; x < GRID_SIZE; ++x) {
                row.add(new EmptySquare()); 
            }
            
            grid.add(row);
            
        }
        
    }
    
    /*
     * Deploy the specified Ship into the Grid by replacing the empty Squares
     * with new ShipSquare objects representing individual segments of the Ship.
     */
    
    
    public void deployShip(Ship s) {
        
        /* Initialize Variables */
    
        int row = 0, col = 0, alignment = 0;    // Ship row, col, and alignment
        int shipSize, emptyCount = 0;          // Ship size; empty Square counter
        boolean empty = false;                // Empty footprint found
        
        /* Get Ship Size */

        shipSize = s.getSize();
        
        /*
         * Attempt to find a empty footprint in the Grid for this Ship.  The
         * location and orientation of the footprint is selected randomly.
         * These values must be selected in a way that keeps the Ship within
         * the bounds of the Grid, and that prevents any Ship from overlapping
         * any other Ship.  This main loop is repeated until a footprint which
         * meets both of these requirements is found.
         */

        while ( !empty ) {

            /* Reset empty space counter */

            emptyCount = 0;
            
            /* Select alignment and initial row/column of footprint at random */
            
            alignment = ((int)(Math.random() * 2));
            row = (int)(Math.random() * GRID_SIZE);
            col = (int)(Math.random() * GRID_SIZE);
            
            
            /* Ship will be horizontal; select column within range! */
            
            if(alignment == Ship.HORIZONTAL) {
                col = (int)(Math.random() * (GRID_SIZE - shipSize + 1));
            }
            
            
            /* Ship will be vertical; select row within range! */     
            
            else if(alignment == Ship.VERTICAL) {
                row = (int)(Math.random() * (GRID_SIZE - shipSize +1));  
            }
            
          
            /* Count empty spaces within selected footprint */    
            
            for(int i = 0; i < shipSize; ++i) {
                
                /* If horizontal, check for empty space in next column */
                
                if(alignment == Ship.HORIZONTAL) {
                    Square square = grid.get(row).get(col+i);
                    if(square instanceof EmptySquare) {
                        emptyCount++;
                    }
                }
                
                /* If vertical, check for empty space in next row */  
                
                else if (alignment == Ship.VERTICAL) {                   
                    Square square = grid.get(row+i).get(col);
                    if(square instanceof EmptySquare) {
                        emptyCount++;
                    }                     
                }
                
            }
  

            /* If empty space count equals Ship length, footprint is not occupied; end search */
            
            if(emptyCount == shipSize) {
                empty = true;    
            }
            else {
                empty = false;
            }
            

        } // end while()

        
        /* Place Ship inside empty footprint (create and store new ShipSquare objects) */
        
        if(alignment == Ship.HORIZONTAL) {
            for(int i =0; i < shipSize; ++i) {           
                grid.get(row).set(col + i, new ShipSquare(s, i));           
            }
            
        }
        else if(alignment == Ship.VERTICAL) {
            for(int i =0; i < shipSize; ++i) {           
                grid.get(row+i).set(col, new ShipSquare(s, i));           
            }
            
        }

        
        /* Update Ship properties to reflect new location and alignment */
        s.setX(col);
        s.setY(row);
        s.setAlignment(alignment);
        
        
    }
    
    /* Return the state of the Grid as a String (useful for unit testing) */
    
    @Override
    public String toString() {
        
        StringBuilder o = new StringBuilder();
        
        for (int y = 0; y < GRID_SIZE; ++y) {
            
            for (int x = 0; x < GRID_SIZE; ++x) {
                
                Square s = grid.get(y).get(x);
                
                if (s instanceof EmptySquare) {
                    
                    o.append("- ");
                    
                }
                
                else if (s instanceof ShipSquare) {
                    
                    switch(((ShipSquare) s).getShip().getName()) {
                        case "Carrier":
                            o.append("A ");
                            break;
                        case "Battleship":
                            o.append("B ");
                            break;
                        case "Destroyer":
                            o.append("D ");
                            break;
                        case "Submarine":
                            o.append("S ");
                            break;
                        case "Patrol Boat":
                            o.append("P ");
                            break;
                    }
                    
                }
                
            }
            
            o.append('\n');
            
        }
        
        return (o.toString());
        
    }
    
    /* Getter and Setter for individual Squares; other ArrayList methods are hidden */

    public Square get(int x, int y) {
        
        if ((x < GRID_SIZE) && (y < GRID_SIZE)) {
            return grid.get(y).get(x);
        }
        else {
            return null;
        }
        
    }
    
    public void set(int x, int y, Square s) {
        
        if ((x < GRID_SIZE) && (y < GRID_SIZE)) {
            grid.get(y).set(y, s);
        }
        
    }

}
