package edu.jsu.mcis.seabattleii;

import java.util.ArrayList;

public class Fleet {
    
    /*
     * These lists specify the complement of ships included in a standard Fleet:
     * one Carrier, one Battleship, two Destroyers, two Submarines, and three
     * Patrol Boats.  The list of NAMES specifies the name of each type of ship,
     * and the list of SIZES specifies the size of each type of ship (arranged
     * in order from largest to smallest); the elements of these lists are
     * paired and used as initializers for new Ship objects in the constructor,
     * according to the list of SHIPS.  The SIZES and NAMES lists must be of
     * equal length!
     *
     * (The names of the icons in the View are derived from the first letter of
     * each ship name, which must be unique.  Any changes to the names should be
     * reflected in changes to the file names.)
     */
    
    public static final int[] SHIPS = {0, 1, 2, 2, 3, 3, 4, 4, 4};
    public static final int[] SIZES = {5, 4, 3, 3, 2};
    public static final String[] NAMES = {"Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
     
    private final ArrayList<Ship> fleet;
    
    public Fleet() {
        
        /* Create new fleet */
        
        fleet = new ArrayList<>();
        
        /* Populate fleet with standard complement of Ships (generated from lists) */
        
        for (int x : SHIPS) {
        
            fleet.add(new Ship(NAMES[x], SIZES[x]));
        
        }
        
    }
    
    /* Getters (return size and individual ships); other ArrayList methods are hidden */

    public int getSize() {
        return fleet.size();
    }
    
    public Ship get(int index) {
        return fleet.get(index);
    }
    
}