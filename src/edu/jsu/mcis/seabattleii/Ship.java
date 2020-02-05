package edu.jsu.mcis.seabattleii;

public class Ship {
    
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
    private final String name;
    private final int size;
    private boolean sunk;
    private int hits;
    private int x;
    private int y;
    private int alignment;
    
    public Ship(String name, int size) {
        
        this.name = name;
        this.size = size;
        this.sunk = false;
        this.hits = 0;
        this.x = -1;
        this.y = -1;
        this.alignment = -1;
        
    }
    
    @Override
    public String toString() {
        return (name + ": (" + x + "," + y + "), " + (alignment == 0 ? "Horizontal" : "Vertical"));
    }
    
    public boolean isSunk() {
        return sunk;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
    
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
    
    public int getHits() {
        return hits;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAlignment() {
        return alignment;
    }

    public void hit() {
        
        hits++;
        
        if (hits == size) {
            sunk = true;
        }
        
    }
    
}