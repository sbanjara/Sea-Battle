package edu.jsu.mcis.seabattleii;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class ViewPrimaryGrid extends JPanel implements AbstractView {
    
    /* Names for Icon Files */
    
    private static final String ICON_ROOT = "/resources/images/";
    private static final String HIT_ICON_FILE = "primary_hit.png";
    private static final String MISS_ICON_FILE = "primary_miss.png";
    
    /* Standard Colors */
    
    private static final Color GRID_HEADER_BACKGROUND = new Color(5, 23, 35);
    private static final Color GRID_DEFAULT_BACKGROUND = new Color(4, 58, 82);
    private static final Color GRID_LINES = new Color(49, 148, 171);
    private static final Color WINDOW_BACKGROUND = Color.BLACK;
    
    /* Collections of Labels and Icons */

    private ArrayList<ArrayList<ViewGridLabel>> labels;
    private ArrayList<ArrayList<ImageIcon>> icons;
    
    private ImageIcon iconHit;
    private ImageIcon iconMiss;
    
    /* Player ID */
    
    private final int playerId;
    
    public ViewPrimaryGrid(DefaultController controller, int playerId) {
        
        super();
        
        this.playerId = playerId;
        
        initComponents();
        
    }
    
    /* Initialize GUI Components */
    
    private void initComponents() {

        String nextShipName;
        int nextShipSize;
        
        /* Initialize JPanel Properties */
        
        this.setLayout(new GridLayout(Grid.GRID_SIZE + 1, Grid.GRID_SIZE + 1));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, GRID_LINES));
        
        /* Initialize Lists */
        
        labels = new ArrayList<>();
        icons = new ArrayList<>();
        
        /* Construct Icons */

        iconHit = new ImageIcon(getClass().getResource(ICON_ROOT + HIT_ICON_FILE));
        iconMiss = new ImageIcon(getClass().getResource(ICON_ROOT + MISS_ICON_FILE));
        
        ArrayList<ImageIcon> nextShipIconSet;
        
        /* Get icons for each type of Ship in the Fleet */
        
        for (int i = 0; i < Fleet.NAMES.length; ++i) {
            
            /* Get next Ship size and name from Fleet lists */
            
            nextShipIconSet = new ArrayList<>();
            nextShipSize = Fleet.SIZES[i];
            nextShipName = Fleet.NAMES[i];
            
            /* Generate corresponding Icon filenames from Ship names; add to icon set */
            
            for (int j = 0; j < nextShipSize; ++j) {
                nextShipIconSet.add(new ImageIcon(getClass().getResource(ICON_ROOT + "ship_" + nextShipName.substring(0, 1).toLowerCase() + j + ".png")));
            }
            
            /* Add icon set to list */
            
            icons.add(nextShipIconSet);
            
        }

        /* Construct Grid Labels */

        ArrayList<ViewGridLabel> row;
        ViewGridLabel label;
        
        /* Construct Next Row */
        
        for (int y = 0; y < Grid.GRID_SIZE + 1; ++y) {
            
            row = new ArrayList<>();
            
            /* Construct Colums Within Row */
            
            for (int x = 0; x < Grid.GRID_SIZE + 1; ++x) {
                
                label = new ViewGridLabel(this, x-1, y-1);
                
                /* Set JLabel Properties */
                
                label.setBackground(GRID_DEFAULT_BACKGROUND);
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, GRID_LINES));
                
                /* Add to JPanel and to row list */
                
                this.add(label);
                row.add(label);
                
                /* If this label is in a header row/col, set text, border, and color */
                
                if ((x == 0) || (y == 0)) {
                    
                    label.setBackground(GRID_HEADER_BACKGROUND);
                    
                    if ((y == 0) && (x != 0)) {
                        label.setText("" + (char)('A' + x - 1));
                    }
                    
                    else if ((x == 0) && (y != 0)) {
                        label.setText("" + y);
                    }
                    
                }
                
            }
            
            /* Add completed row to list */
            
            labels.add(row);
            
        }
        
        /* Finally, clear the upper-leftmost cell in header row/col */
        
        labels.get(0).get(0).setBorder(BorderFactory.createEmptyBorder());
        labels.get(0).get(0).setBackground(WINDOW_BACKGROUND);
    
    }
    
    /* Display specified Ship in grid by setting row/col label icons */
    
    private void deployShip(Ship s) {
        
        /* Get Ship properties */
        
        int x = s.getX();
        int y = s.getY();
        int nextShipSize = s.getSize();
        int nextShipAlignment = s.getAlignment();
        String nextShipName = s.getName();
        
        /* Create new labels */
        
        ViewGridLabel label;

        ArrayList<ImageIcon> nextShipIconSet = null;

        /* Choose Icon Set by Ship Name */
        
        for (int i = 0; i < Fleet.NAMES.length; ++i) {
            
            if (nextShipName.equals(Fleet.NAMES[i]))
                nextShipIconSet = icons.get(i);
            
        }
        
        /* If Icon Set Found, Place Icons in Grid */
        
        if (nextShipIconSet != null) {

            for (int i = 0; i < nextShipSize; ++i) {
                
                /* Horizontal Deployment */
                
                if (nextShipAlignment == Ship.HORIZONTAL) {
                    
                    /* Get Label; Set Icon */
                    
                    label = labels.get(y+1).get(x+1 + i);
                    label.setIcon(nextShipIconSet.get(i));
                    
                    /* Fix Borders */
                    
                    if (i == 0)
                        label.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, GRID_LINES));
                    else
                        label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, GRID_LINES));

                }
                
                /* Vertical Deployment */
                
                else {
                    
                    /* Get Label; Set Icon */
                    
                    label = labels.get(y+1 + i).get(x+1);
                    label.setIcon(nextShipIconSet.get(i));
                    
                    /* Rotate Label */
                    
                    label.rotateImage(90);
                    
                    /* Fix Borders */
                    
                    if (i == 0)
                        label.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, GRID_LINES));
                    else
                        label.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, GRID_LINES));

                } // End else
                
            } // End for

        } // End if

    }
    
    /*
     * This method is invoked by the Controller when changes to the Model must
     * be reflected in the View.  The change is indicated by the constant
     * identifiers defined in the Controller; the data is given as the original
     * and/or new values in the parameter list.  Controller notifications which
     * do not apply to this View are ignored.
     */
    
    @Override
    public void modelPropertyChange(final PropertyChangeEvent e) {
        
        /* Ship Deployed (Player 1) */
    
        if (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_DEPLOY) && (playerId == 1)) {
            
            Ship ship = (Ship)e.getNewValue();
            deployShip(ship);
            
        }
        
        /* Ship Deployed (Player 2) */
    
        else if (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_DEPLOY) && (playerId == 2)) {
            
            Ship ship = (Ship)e.getNewValue();
            deployShip(ship);
            
        }
        
        /* Primary Grid Square Missed (square represented as a Map containing the X/Y coordinates) */
    
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER2_SHOT_MISSED) && (playerId == 1)) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_SHOT_MISSED) && (playerId == 2)) ) {
            
            if (e.getOldValue() instanceof HashMap) {
                HashMap<String, Integer> square = (HashMap<String, Integer>)e.getOldValue();
                int x = square.get("x");
                int y = square.get("y");
                labels.get(y+1).get(x+1).setIcon(iconMiss);
            }
            
        }
        
        /* Primary Grid Square Hit (square represented as a Map containing the X/Y coordinates) */
    
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_SUNK) && (playerId == 1)) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_HIT) && (playerId == 1)) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_SUNK) && (playerId == 2)) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_HIT) && (playerId == 2)) ) {
            
            if (e.getOldValue() instanceof HashMap) {
                HashMap<String, Integer> square = (HashMap<String, Integer>)e.getOldValue();
                int x = square.get("x");
                int y = square.get("y");
                labels.get(y+1).get(x+1).setIcon(iconHit);
            }
            
        }
        
    }
    
}